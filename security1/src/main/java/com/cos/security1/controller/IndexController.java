package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //view를 리턴하겠다.
public class IndexController {

	
	@GetMapping({ "", "/" })
	public String index() {
		
		//머스테치 :( spring 권장 탬플릿)
		//기본 폴더 scr/main/resources/
		//뷰리졸버 설정:templates(prefix),mustache(suffix) 생략가능! 
		return "index";
	}
}
