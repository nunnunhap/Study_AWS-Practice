package com.docmall.demo.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import jakarta.servlet.MultipartConfigElement;

/*
 스프링부트 2.7 에서는 multipart가 기본 bean으로 등록되어있다.
 스프링부트 3 이상부터는 multipart 설정 클래스를 생성하여, bean으로 등록해야 한다. 
 */

@Configuration
public class MultipartConfig {

	// 스프링에서 자동으로 관리. 리턴타입 multipartResolver bean등록및관리.
	@Bean // 라이브러리에서 제공하는 클래스를 스프링에서 관리.
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	/*
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation(null);
		factory.setMaxRequestSize(null);
		factory.setMaxFileSize(null);
		
		return factory.createMultipartConfig();
	}
	*/
}
