package com.docmall.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	//의존성 주입
	@Autowired
	private BoardMapper boardMapper;

	@Override
	public void write(BoardVO vo) {
		boardMapper.write(vo);
		
	}

	@Override
	public List<BoardVO> list() {
		// TODO Auto-generated method stub
		return boardMapper.list();
	}

	@Override
	public BoardVO get(Long bno) {
		
		// 조회수증가
		boardMapper.readCount(bno);
		
		return boardMapper.get(bno);
	}

	@Override
	public void modify(BoardVO vo) {
		// TODO Auto-generated method stub
		boardMapper.modify(vo);
		
	}

	@Override
	public void delete(Long bno) {
		// TODO Auto-generated method stub
		boardMapper.delete(bno);
	}
	
}
