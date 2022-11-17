package com.prj1.service.member;

import com.prj1.domain.board.BoardDto;
import com.prj1.domain.member.MemberDto;
import com.prj1.mapper.board.BoardMapper;
import com.prj1.mapper.board.ReplyMapper;
import com.prj1.mapper.member.MemberMapper;
import com.prj1.service.board.BoardSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private BoardSerivce boardService;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int insert(MemberDto member){
        String pw = member.getPassword();
        member.setPassword(passwordEncoder.encode(pw));
        return memberMapper.insert(member);
    }

    public List<MemberDto> list() {
        return memberMapper.selectAll();
    }

    public MemberDto getById(String id) {
        return memberMapper.selectById(id);
    }


    public int modify(MemberDto member) {
        int cnt =0;
        try {
            if (member.getPassword() != null) {
                String encodedPw = passwordEncoder.encode(member.getPassword());
                member.setPassword(encodedPw);
            }

            return memberMapper.update(member);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cnt;
    }

    public int remove(String id) {

        // 멤버의 좋아요 삭제
        boardMapper.deleteLikeByMemberId(id);

        // 멤버의 댓글 삭제
        replyMapper.deleteByMemberId(id);

        // 멤버가 쓴 게시물 목록 조회
        List<BoardDto> list = boardMapper.listByMemberId(id);
        for (BoardDto board : list) {
            boardService.remove(board.getId());
        }
        return memberMapper.deleteById(id);
    }

    public MemberDto getByEmail(String email) {
        return memberMapper.selectByEmail(email);

    }

    public MemberDto getByNickName(String nickName) {
        return memberMapper.selectByNickName(nickName);

    }
}
