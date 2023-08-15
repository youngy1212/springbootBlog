package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@AllArgsConstructor

@Data
@NoArgsConstructor
public class Member {
	private  int id;
	private  String username;
	private  String password;
	private  String email;
	//final : 불변성 유지

	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
}
