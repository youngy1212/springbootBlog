package com.cos.security1.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.auth.PrincipalDetails;
import com.cos.security1.config.oauth.provider.FaceBookUserInfo;
import com.cos.security1.config.oauth.provider.GoogleUserInfo;
import com.cos.security1.config.oauth.provider.NaverUserInfo;
import com.cos.security1.config.oauth.provider.OAuth2UserInfo;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import java.util.Map;

@Service
public class PrincipalOauth2UserService  extends DefaultOAuth2UserService{
	
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
		// Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청 !!!! =============");
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			System.out.println("페이스북 로그인 요청 !!!! =============");
			oAuth2UserInfo = new FaceBookUserInfo(oAuth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			System.out.println("네이버 로그인 요청 !!!! =============");
			oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
		} else {
			System.out.println("우리는 구글과 페이스북 과 네이버만 지원해요.");
		}
		
		String provider = oAuth2UserInfo.getProvider();
		String providerId = oAuth2UserInfo.getProviderId();
		String username = provider+"_"+providerId;
		String email = oAuth2UserInfo.getEmail();
		String role = "ROLE_USER";
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) { //강제 회원가입 진행 
			userEntity = User.builder()
					.username(username)
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
