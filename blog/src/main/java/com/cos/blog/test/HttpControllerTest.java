package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML 파일)
//@controller

//사용자가 요청 -> 응답 (Date)
@RestController
public class HttpControllerTest {
	
		private static final String TAG = "HttpControllerTest : ";
		
		// localhost:8001/blog/http/lombok
		@GetMapping("/http/lombok")
		public String lombokTest() {
			//빌더를 쓰면 순서 틀릴리 없음! 오류가 없음
			Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
			System.out.println(TAG+"getter : "+m.getUsername());
			m.setUsername("cos");
			System.out.println(TAG+"setter : "+m.getUsername());
			return "lombok test 완료";
		}
	
		// 인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다. post~등 503오류!
		// http://localhost:8081/http/get (select)
		@GetMapping("/http/get")
		public String getTest(Member m) { //id=1&username=ssar&password=1234&email=ssar@nate.com // MessageConverter (스프링부트)
			return "get 요청 : "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
		}
		
		// http://localhost:8081/http/post (insert)
		@PostMapping("/http/post") // text/plain(string text), application/json
		public String postTest(@RequestBody Member m) { // MessageConverter (스프링부트)
			return "post 요청 : "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
		}
		
		@PutMapping("/http/put")
		public String putTest(@RequestBody Member m) {
			return "put 요청 : "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
		}
		
		@DeleteMapping("/http/delete")
		public String deleteTest() {
			return "delete 요청";
		}
}
