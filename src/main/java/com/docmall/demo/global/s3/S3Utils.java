package com.docmall.demo.global.s3;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException.ErrorType;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Utils {

	private final AmazonS3 amazonS3;
	
	@Value("${cloud.aws.s3.bucketName}")
	private String bucket;
	
	// 폴더 생성
	public void createFolder(String bucketName, String folderName) {
		amazonS3.putObject(bucketName, folderName + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
	}

	// 다중 파일 업로드
	public void fileUpload(List<MultipartFile> files, List<String> fileList) throws Exception {
		if(amazonS3 != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String today = sdf.format(date);

			if(!files.isEmpty()) {
				createFolder(bucket + "/contact", today);
			}

			ObjectMetadata objectMetadata = new ObjectMetadata();
			for(int i=0; i<files.size(); i++) {
				objectMetadata.setContentType(files.get(i).getContentType());
				objectMetadata.setContentLength(files.get(i).getSize());
				objectMetadata.setHeader("filename", files.get(i).getOriginalFilename());
				amazonS3.putObject(new PutObjectRequest(bucket + "/contact/" + today, fileList.get(i), files.get(i).getInputStream(), objectMetadata));
			}
		} else {
//			throw new AppException(ErrorType.aws_credentials_fail, null);
			throw new Exception();
		}
	}

	// 다중 파일 삭제
	public void fileDelete(String filePath, String fileName) throws Exception {
		if(amazonS3 != null) {
			amazonS3.deleteObject(new DeleteObjectRequest(filePath, fileName));
		} else {
//			throw new AppException(ErrorType.aws_credentials_fail, null);
			throw new Exception();
		}
	}
}
