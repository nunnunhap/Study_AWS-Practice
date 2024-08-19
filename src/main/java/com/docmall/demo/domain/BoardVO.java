package com.docmall.demo.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// bno, title, content, writer, regdate, updatedate, viewcount

@Getter
@Setter
@ToString
public class BoardVO {

	private	Long	bno; // 참조타입 Wrapper Class - Long class
	private	String	title;
	private String	content;
	private String	writer;
	private Date	regdate;
	private Date	updatedate;
	private int		viewcount;
	
	
}
