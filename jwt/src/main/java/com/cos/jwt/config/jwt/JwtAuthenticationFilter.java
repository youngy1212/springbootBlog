package com.cos.jwt.config.jwt;

import java.io.IOException;
import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.dto.LoginRequestDto;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음
// /login요청해서 username, password 전송하면(POST)
//UsernamePasswordAuthenticationFilter 동작을 함 
//.formLogin().disable() 이라서 작동 X 
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	// Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager

	/// login요청하면 로그인 시도를 위해 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("로그인 시도중! ");

		// 1. username, password를 받아서
//		try {
//			BufferedReader br = request.getReader();
//			
//			String input = null;
//			while((input = br.readLine()) != null) {
//				System.out.println(input);
//			}

		// json으로 로그인 한다면?
		ObjectMapper om = new ObjectMapper();
		LoginRequestDto loginRequestDto = null;
		try {
			loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("JwtAuthenticationFilter : " + loginRequestDto);

		// 토큰만들기
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginRequestDto.getUsername(), loginRequestDto.getPassword());

		System.out.println("JwtAuthenticationFilter : 토큰생성완료");

		// 2. 정상인지 로그인 시도를 해봄.authenticationManager로 로그인 하면,
		// PrincipalDetailsService가 호출 -> loadUserByUsername 자동실행

		// PrincipalDetailsService의 loadUserByUsername() 함수 실행 후 정상이면 authentication 리턴
		// DB에 있는 username과 password가 일치한다.
		// Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
		// Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
		// 결론은 인증 프로바이더에게 알려줄 필요가 없음.
		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		// 3. PrincipalDetails를 세션이 담고(권한관리를 위해) 해당사항이 없으면 실행 X
		// authentication 내 로그인 정보
		PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("Authentication 내 로그인 정보: " + principalDetailis.getUser().getUsername());// 로그인이 정상적으로 됨

		// authentication 객체가 session영역에 저장해야되고, 그 방법이 return 해주면됨.
		// 리턴의 이유는 권한 관리 security가 대신 해주기 때문에 편하려고 하는것임
		// 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 단지 권한 처리때문에 session에 넣어줌.

		return authentication;

	}

	// attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수 실행
	// JWT 토큰을 만들어서 request요청한 사용자에게 JWT 토큰을 response 해주면 됨
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication 실행 !! == 인증완료되었음");
		PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();

		// 4. JWT토큰을 만들어서 응답
		// HASH 암호 방식
		String jwtToken = JWT.create().withSubject(principalDetailis.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
				.withClaim("id", principalDetailis.getUser().getId())
				.withClaim("username", principalDetailis.getUser().getUsername())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));

		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
	}

}
