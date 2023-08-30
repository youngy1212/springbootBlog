package com.cos.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;

@Configuration// IoC 빈(bean)을 등록
//@EnableWebSecurity //시큐리티 필더가 스프링 필터체인에 등록
//@EnableMethodSecurity(securedEnabled = true, prePostEnabled =  true )//Secured 어노테이션 활성화
public class SecurityConfig {
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;

	
	@Bean //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/user/**").authenticated()
				.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
				// .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or
				// hasRole('ROLE_USER')")
				// .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and
				// hasRole('ROLE_USER')")
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll()
				.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/loginProc")
				//loginProc주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인 진행				
				.defaultSuccessUrl("/")
				.and()
				.oauth2Login()
				.loginPage("/login")
				.userInfoEndpoint() 
				.userService(principalOauth2UserService); 
		
		//로그인이 완료된 후 .userInfoEndpoint() 
		//구글 로그인이 완료되면 후처리가 필요함 1.코드받기(인증), 2.엑세스토큰(권한) 3. 사용자 프로필정보
		//4-1. 정보를 토대로 회원가입을 자동으로 진행시키고다함
		//4-2. (이메일, 전화번호, 이름, 아이디) 쇼핑몰 ->(집주소), 백화점몰 ->(vip, 일반등급)
		// TIP. 코드 X(액세스 토큰 + 사용자프로필정보 O)

		return http.build();
	}

}
