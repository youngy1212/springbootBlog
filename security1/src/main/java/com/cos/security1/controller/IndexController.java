package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller // view를 리턴하겠다.
public class IndexController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping({ "", "/" })
	public String index() {

		// 머스테치 :( spring 권장 탬플릿)
		// 기본 폴더 scr/main/resources/
		// 뷰리졸버 설정:templates(prefix),mustache(suffix) 생략가능!
		return "index";
	}

	@GetMapping("/user")
	public @ResponseBody String user() {// @AuthenticationPrincipal PrincipalDetails principal
//		System.out.println("Principal : " + principal);
//		System.out.println("OAuth2 : "+principal.getUser().getProvider());
//		// iterator 순차 출력 해보기
//		Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator();
//		while (iter.hasNext()) {
//			GrantedAuthority auth = iter.next();
//			System.out.println(auth.getAuthority());
//		}

		return "유저 페이지입니다.";
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "어드민 페이지입니다.";
	}

	// @PostAuthorize("hasRole('ROLE_MANAGER')")
	// @PreAuthorize("hasRole('ROLE_MANAGER')")
//	@Secured("ROLE_MANAGER")
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "매니저 페이지입니다.";
	}

	// 스프링 시큐리티 해당 주소를 낚아챔 - SecurityConfing 비작동 (내가 설정해서)
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/join")
	public String join() {
		return "join";
	}

	@PostMapping("/joinProc")
	public String joinProc(User user) {
		System.out.println("회원가입 진행 : " + user);
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		userRepository.save(user);
		return "redirect:/";
	}
	
	//pre 메소드 실행이전 post 실행이후
	//@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@PostAuthorize("hasRole('ROLE_MANAGER')")
	//@Secured("ROLE_MANAGER'")
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보.";
	}
	
	
}
