package com.prj1.service.board;

import com.prj1.domain.board.ReplyDto;
import com.prj1.mapper.board.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    @Autowired
    private ReplyMapper mapper;

    public int addReply(ReplyDto reply) {
        return mapper.insert(reply);
    }

    public List<ReplyDto> listReplyByBoardId(int boardId,String userName) {
        return mapper.selectReplyByBoardId(boardId, userName);
    }

    public int removeById(int id) {
        return mapper.deleteById(id);
    }

    public ReplyDto getById(int id) {
        return mapper.selectById(id);
    }

    public int modify(ReplyDto reply) {
        return mapper.update(reply);
    }

}


