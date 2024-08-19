package com.docmall.demo.util;

import java.io.File;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component // 스프링에서 클래스를 자동관리.
public class FileUtils {

	// 파일업로드되는 폴더가 프로젝트 외부에 존재하여, 보안적인 이슈가 있으므로, 업로드 파일들을 바이트배열로 읽어서 클라이언트로 보낸다.
	public static ResponseEntity<byte[]> getFile(String uploadPath, String fileName) throws Exception {
		ResponseEntity<byte[]> entity = null;
		
		File file = new File(uploadPath, fileName);
		
		if(!file.exists()) {
			return entity;
		}
		
		HttpHeaders headers = new HttpHeaders();
		// Files.probeContentType(file.toPath()) : MIME TYPE 정보  예>image/jpeg
		headers.add("Content-Type", Files.probeContentType(file.toPath()));
		
		entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		
		return entity;
	}
	
	
	
}
