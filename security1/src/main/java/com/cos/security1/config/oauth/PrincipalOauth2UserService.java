package com.cos.security1.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Service
public class PrincipalOauth2UserService  extends DefaultOAuth2UserService{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Autowired
	private UserRepository userRepository;
	
	// userRequest 는 code를 받아서 accessToken을 응답 받은 객체
	//구글로 부터 받은 userReuset데이터에 대한 후처리되는 객체
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		// code를 통해 구성한 정보
		System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
		//RegistrationID로 어떤 Oauth로 로그인 했는지 알 수 있음
		
		//구글 로그인 -> code를 리턴(OAuth-Client라이브러리) -> AcessToken
		//userRequset 정보 -> loadUser함수 -> 구글로 부터 회원프로필을 받음
		
		OAuth2User oAuth2User = super.loadUser(userRequest); // google의 회원 프로필 조회
		// token을 통해 응답받은 회원정보
		
		
		System.out.println("getAttributes : " + oAuth2User.getAttributes());
		
		//회원가입을 강제로 진행
		String provider = userRequest.getClientRegistration().getClientId(); //구글
		String providerId = oAuth2User.getAttribute("sub");
		String username = provider+"_"+providerId;
		String email = oAuth2User.getAttribute("email");
		String password = bCryptPasswordEncoder.encode("겟인데어");
		String role = "ROLE_USER";
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) { //강제 회원가입 진행 
			userEntity = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity);
		}
		
		return new PrincipalDetails(userEntity,  oAuth2User.getAttributes());
		
		//return processOAuth2User(userRequest);
	}

	
}
