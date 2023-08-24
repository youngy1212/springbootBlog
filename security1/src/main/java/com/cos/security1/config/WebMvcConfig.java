package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{  


	//해당 뷰리졸버 : -> 변경설정 -> templates를 html로 변경할께!
	  @Override
	  public void configureViewResolvers(ViewResolverRegistry registry) {
	      MustacheViewResolver resolver = new MustacheViewResolver();

	      resolver.setCharset("UTF-8");
	      resolver.setContentType("text/html;charset=UTF-8");
	      resolver.setPrefix("classpath:/templates/");
	      resolver.setSuffix(".html");

	      registry.viewResolver(resolver);
	  }
}
