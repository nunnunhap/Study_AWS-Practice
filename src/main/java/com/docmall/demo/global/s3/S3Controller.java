package com.docmall.demo.global.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/s3/*")
@RequiredArgsConstructor
@Controller
public class S3Controller {

	private final S3ImageService s3ImageService;
	private final S3Utils s3Utils;
	
	@Value("${cloud.aws.s3.bucketName}")
	private String bucketName;
	
	@GetMapping("/upload")
	public void s3Upload() {
		
	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> s3Upload(@RequestPart(value = "image", required = false) MultipartFile image) throws Exception{

		s3Utils.createFolder(bucketName, "contact");
		
		String profileImage = s3ImageService.upload(image);
	  return ResponseEntity.ok(profileImage);
	}
	
	@GetMapping("/delete")
	public ResponseEntity<?> s3delete(@RequestParam String addr) throws Exception{
	    
		log.info("파일명: " + addr);
		
//		s3ImageService.deleteImageFromS3(addr);
		
		s3ImageService.deleteFile(addr);
	    return ResponseEntity.ok(null);
	}
}
