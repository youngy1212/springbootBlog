package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity// User 클래스가 MySQL에 테이블이 생성이 된다.
public class User {
	
	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB(mysql)의 넘버링 전략을 따라간다.
	private int id; // 시퀀스, auto_increment
	 
	@Column(nullable = false, length = 30, unique = true) 
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) // 123456 => 해쉬 (비밀번호 암호화)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email; // myEmail, my_email

	//@ColumnDefault("user")
	//DB는 RoleType이란게 없기때문에 string이란걸 알려줘야함
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다.-> admin, user,manger (범위가 정해져있음)

	
	//카카오로그인/구글 로그인체크 (회원정보변경X)
	private String oauth; // kakao, google
	
	// 내가 직접 시간을 넣으려면 Timestamp.valueOf(LocalDateTime.now())
	@CreationTimestamp //시간이 자동입력
	private Timestamp createDate;
	
	
}
