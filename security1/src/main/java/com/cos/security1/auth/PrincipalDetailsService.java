package com.cos.security1.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

//시큐리티 설정에서 loginProcessingUrl("/loginProc") 요청이 오면 
// 요청이 오면 자동으로 UserDetailService 타입으로 loC되어있는 loadUserByUsername 함수 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	//시큐리티 session(내부 Authentication(내부 UserDetails))
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 유저가 있는지 체크
		User user = userRepository.findByUsername(username); // findByUsername 만들어줘야함
		if (user == null) {
			return null;
		} else {
			return new PrincipalDetails(user);
		}
//		
	}
}
