package com.cos.security1.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행
//로그인 진행이 완료되면 시큐리티 session이 만들어줌(Security ContextHolder)
//오브젝트 => Authentication 타입 객체
//Authentication 안에 User 정보가 있어야됨.
//User 오브젝트 타입 => UserDetails 타입 객체

//Security Session => Authentication => UserDetails(PrincipalDetails)
public class PrincipalDetails implements UserDetails {
	// , OAuth2User
	private User user; // 콤포지션

	// 해당 User의 권한을 리턴하는 곳
	// 일반 시큐리티 로그인시 사용
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
		collet.add(()->{ return user.getRole();});
		return collet;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}//오래사용한건 아니니?

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}//활성화 되어있니?

	@Override
	public boolean isEnabled() {
		//우리사이트 1년동안 로그인 안하면 휴먼계정으로 하기로함.
		// user.getLoginDate() 현재시간 - 로그인시간 1년넘으면 false...이런식으로 사용
		return true;
	}

}
