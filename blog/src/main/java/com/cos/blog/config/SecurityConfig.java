package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.config.auth.PrincipalDetailService;

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@Configuration // 빈등록 (IoC관리) 모든 요청을 체크!
@EnableWebSecurity // 시큐리티 필터가 등록이 된다.
//Controller에서 특정 권한이 있는 유저만 접근을 허용하려면 @PreAuthorize 어노테이션을 사용하는데, 해당 어노테이션을 활성화 시키는 어노테이션이다.
@EnableGlobalMethodSecurity(prePostEnabled = true) 
public class SecurityConfig {
	
	//강의기준 WebSecurityConfigurerAdapter 상속받아서 사용했는데!! 이제는 SecurityFilterChain 사용
	// 해당 SecurityFilterChain에는 자동으로 bean으로 UserDetailsService와 PasswordEncoder가 빈으로 등록되어있다면
	//자동으로 관리됨!! 
	
		@Autowired
		private PrincipalDetailService principalDetailService;
		

	
		@Bean // IoC가능
		public BCryptPasswordEncoder encodePWD() {
			return new BCryptPasswordEncoder();
		}
		
		   @Bean
		    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
			   http
				.csrf().disable()  // csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)
				.authorizeRequests()
					.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**") //이 루트는 누구나 들어올 수있어
					.permitAll() 
					.anyRequest()
					.authenticated() //이외에 모든 요청은 인증해야해!
				.and()
					.formLogin()
					.loginPage("/auth/loginForm")
					.loginProcessingUrl("/auth/loginProc")
					.defaultSuccessUrl("/"); // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
			   
		        return http.build();
		    }
	
		 /*
		@Bean (구버전) 
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
		
		시큐리티가 대신 로그인해주는  password를 가로채기를 하는데
		 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
		 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
		 
		@Override  (구버전) 
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
			//에이있는 password 인코딩을 위의 메소드 encodePWD() 했다고 알려줌
		}
			*/

	
}
