package com.prj1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.mybatis.spring.annotation.MapperScan;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

@Configuration
@MapperScan("com.prj1.mapper")
public class CustomConfig {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;
    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;

    @Value("${aws.s3.file.url.prefix}")
    private String imgUrl;

    @Autowired
    private ServletContext servletContext;

    @PostConstruct
    public void init(){
        servletContext.setAttribute("imgUrl", imgUrl);
    }

    /*Security*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/member/login").defaultSuccessUrl("/board/list",true);
        http.logout().logoutUrl("/member/logout").logoutSuccessUrl("/member/login");
        http.csrf().disable();

        return http.build();
    }
    /* password를 암호화하여 비교하기 위한 Bean*/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public S3Client s3Client(){
        return S3Client.builder()
                .credentialsProvider(awsCredentialsProvider())
                .region(Region.AP_NORTHEAST_2).build();
    }
    @Bean
    public AwsCredentialsProvider awsCredentialsProvider(){
        return StaticCredentialsProvider.create(awsCredentials());
    }
    @Bean
    public AwsCredentials awsCredentials(){
        return AwsBasicCredentials.create(accessKeyId, secretAccessKey);
    }
}
