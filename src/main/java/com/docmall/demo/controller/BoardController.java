package com.docmall.demo.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.service.BoardService;
import com.docmall.demo.util.FileUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j  // log 객체지원
@RequestMapping("/board/*")
@Controller
public class BoardController {

	//CKeditor 파일업로드 경로
	@Value("${file.ckdir}")
	private String uploadCKPath;
	
	//의존성 주입
	@Autowired
	private BoardService boardService;
	
	
	
	//글쓰기 폼
	@GetMapping("write")
	public void write() {
		log.info("called write...");
		
	}
	
	//글쓰기 저장
	@PostMapping("write")
	public String write(BoardVO vo) {
		
		log.info("게시판 입력데이타: " + vo);
		
		//db저장.
		boardService.write(vo);
		
		return "redirect:/board/list";
	}
	
	//CKeditor 업로드
	// MultipartFile upload : CKeditor의 업로드탭에서 나온 파일첨부 <input type="file" name="upload"> 을 참조함.
	// HttpServletRequest request : 클라이언트의 요청정보를 가지고 있는 객체.
	// HttpServletResponse response : 서버에서 클라이언트에게 보낼 정보를 응답하는 객체
	@PostMapping("/imageupload")
	public void imageupload(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) {
		
		OutputStream out = null;
		PrintWriter printWriter = null; // 서버에서 클라이언트에게 응답정보를 보낼때 사용.(업로드한 이미지정보를 브라우저에게 보내는 작업용도)
		
		try {
			//1)CKeditor를 이용한 파일업로드 처리.
			String fileName = upload.getOriginalFilename(); // 업로드 할 클라이언트 파일이름
			byte[] bytes = upload.getBytes(); // 업로드 할 파일의 바이트배열
			
			String ckUploadPath = uploadCKPath + fileName; // "C:\\Dev\\upload\\ckeditor\\" + "abc.gif"
			
			out = new FileOutputStream(ckUploadPath); // "C:\\Dev\\upload\\ckeditor\\abc.gif" 파일생성. 0 byte
			
			out.write(bytes); // 빨대(스트림)의 공간에 업로드할 파일의 바이트배열을 채운상태.
			out.flush(); // "C:\\Dev\\upload\\ckeditor\\abc.gif" 0 byte -> 크기가 채워진 정상적인 파일로 인식.
			
			//2)업로드한 이미지파일에 대한 정보를 클라이언트에게 보내는 작업
			
			printWriter = response.getWriter();
			
			
			String fileUrl = "/board/display/" + fileName; // 매핑주소/이미지파일명
//			String fileUrl = fileName;
			
			
			// Ckeditor 4.12에서는 파일정보를 다음과 같이 구성하여 보내야 한다.
			// {"filename" : "abc.gif", "uploaded":1, "url":"/ckupload/abc.gif"}
			// {"filename" : 변수, "uploaded":1, "url":변수}
			printWriter.println("{\"filename\" :\"" + fileName + "\", \"uploaded\":1,\"url\":\"" + fileUrl + "\"}"); // 스트림에 채움.
			printWriter.flush();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(out != null) {
				try {
					out.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			if(printWriter != null) printWriter.close();
		}
	}
	
	@GetMapping("/display/{fileName}")
	public ResponseEntity<byte[]> getFile(@PathVariable("fileName") String fileName) {
		
		log.info("파일이미지: " + fileName);
		
		
		ResponseEntity<byte[]> entity = null;
		
		try {
			entity = FileUtils.getFile(uploadCKPath, fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return entity;
		
	}
	
	// 글목록
	@GetMapping("list")
	public void list(Model model ) {
		
		// 데이타소스(list)를 jsp에서 사용할 경우에는 파라미터를 Model 를 사용한다.
		
		List<BoardVO> list = boardService.list();
		model.addAttribute("list", list);
		
		log.info("리스트");
	}
	
	// 게시물조회, 게시물수정
	@GetMapping(value = {"get", "modify"})
	public void get(Long bno, Model model) {
		
		log.info("게시물번호: " + bno);
		
		//게시물정보 읽어오기(조회수증가 작업포함).
		BoardVO boardVO = boardService.get(bno);
		model.addAttribute("boardVO", boardVO);
	}
	
	//게시물 수정하기
	@PostMapping("modify")
	public String modify(BoardVO vo) {
		
		
		log.info("수정데이타: " + vo);
		
		boardService.modify(vo);
		
		return "redirect:/board/list";
	}
	
	//게시물 삭제하기
	@GetMapping("delete")
	public String delete(Long bno) {
		
		log.info("삭제 글번호:" + bno);
		
		boardService.delete(bno);
		
		return "redirect:/board/list";
	}
}
