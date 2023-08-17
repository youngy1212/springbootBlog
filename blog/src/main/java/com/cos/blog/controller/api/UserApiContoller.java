package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiContoller {

	@Autowired
	private UserService userService;
	
//	@Autowired
//	private AuthenticationManager authenticationManager;

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
		user.setRole(RoleType.USER);
		userService.회원가입(user);

		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로
		// 변환해서 리턴 (Jackson)
	}
	
//	@PutMapping("/user")
//	public ResponseDto<Integer> update(@RequestBody User user) { // key=value, x-www-form-urlencoded
//		userService.회원수정(user);
//		// 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음.
//		// 하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해줄 것임.
//		// 세션 등록
//
//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}

}
