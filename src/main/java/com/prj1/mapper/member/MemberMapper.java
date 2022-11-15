package com.prj1.mapper.member;

import com.prj1.domain.member.MemberDto;

import java.util.List;

public interface MemberMapper {
    int insert(MemberDto member);

    List<MemberDto> selectAll();

    MemberDto selectById(String id);

    int update(MemberDto member);

    int deleteById(String id);

    MemberDto selectByEmail(String email);

    MemberDto selectByNickName(String nickName);
}
