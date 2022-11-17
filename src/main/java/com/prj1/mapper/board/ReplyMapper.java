package com.prj1.mapper.board;

import com.prj1.domain.board.ReplyDto;

import java.util.List;


public interface ReplyMapper {

    int insert(ReplyDto reply);

    List<ReplyDto> selectReplyByBoardId(int boardId,String username);

    int deleteById(int id);

    ReplyDto selectById(int id);

    int update(ReplyDto reply);

    int deleteByBoardId(int id);


    int deleteByMemberId(String id);
}