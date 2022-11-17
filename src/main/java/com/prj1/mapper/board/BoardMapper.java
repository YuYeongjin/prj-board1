package com.prj1.mapper.board;

import com.prj1.domain.board.BoardDto;

import java.util.List;

public interface BoardMapper {


	int insert(BoardDto board);

	List<BoardDto> list(int offset, int records, String type, String keyword);

	int update(BoardDto board);

	int delete(int id);

	int countAll(String type,String keyword);

	int insertFile(int id,String fileName);

	int deleteFileByBoardId(int id);

	int deleteFileByName(int id, String fileName);

	int getLikeByBoardIdAndMemberId(String boardId, String memberId);

	void deleteLike(String boardId, String memberId);

	void insertLike(String boardId, String memberId);

	int countLikeByBoardId(String boardId);

	BoardDto select(int id, String username);

	default BoardDto select(int id){
		return select(id,null);
	}

	void deleteLikeByBoardId(int id);

	void deleteLikeByMemberId(String id);

	List<BoardDto> listByMemberId(String id);
}








