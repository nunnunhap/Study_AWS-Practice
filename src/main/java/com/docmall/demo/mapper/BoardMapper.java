package com.docmall.demo.mapper;

import java.util.List;

import com.docmall.demo.domain.BoardVO;

//@Mapper
public interface BoardMapper {

	//글쓰기 저장
	void write(BoardVO vo);
	
	//글목록
	List<BoardVO> list();
	
	//게시물조회
	BoardVO get(Long bno);
	
	//조회수증가
	void readCount(Long bno);
	
	//글수정하기
	void modify(BoardVO vo);
	
	//글삭제하기
	void delete(Long bno);
	
	
}
