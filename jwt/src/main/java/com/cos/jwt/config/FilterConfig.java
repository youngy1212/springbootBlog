package com.cos.jwt.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cos.jwt.filter.MyFilter1;

@Configuration
public class FilterConfig {
	
	//SecurityConfig 가 아닌 따로 실행시키는법 
	//따로 체인걸 필요 없음
	//but SecurtiyCofing보다 먼저 늦게 실행 때문에 
	//SecurtiyCofing 보다 먼저 실행시키고 싶다면 addfilterBefore를 
	// SecurtiyCofing 에서 실행
	
	
	@Bean
	public FilterRegistrationBean<MyFilter1> filter1(){
		FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
		bean.addUrlPatterns("/**");
		bean.setOrder(0);//낮은번호가 제일 먼저 실행
		return bean;
	}

}
