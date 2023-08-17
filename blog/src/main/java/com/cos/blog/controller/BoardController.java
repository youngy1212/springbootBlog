package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;


@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	// 컨트롤로에서 세션을 어떻게 찾는지?
	// @AuthenticationPrincipal PrincipalDetail principal
	@GetMapping("/")
	public String index(Model model,@PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pageable) { 
		model.addAttribute("boards",boardService.글목록(pageable));
		return "index"; //viewResolver 작동!! model 정보를 들고있음
	}
	
	// USER 권한이 필요
		@GetMapping("/board/saveForm")
		public String saveForm() {
			return "board/saveForm";
	}
	
}
