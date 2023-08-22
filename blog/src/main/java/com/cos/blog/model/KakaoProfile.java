package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


//Jackson 라이브러리에서 제공하는 어노테이션 중 하나로, JSON 데이터를 객체로 역직렬화할 때 알 수 없는 프로퍼티를 무시하도록 지정하는 역할
@JsonIgnoreProperties(ignoreUnknown=true) 
@Data
public class KakaoProfile {
	
	public long id;
	public String connected_at;
	public Properties properties;
	public KakaoAccount kakao_account;

	@JsonIgnoreProperties(ignoreUnknown=true) 
	@Data
	public class Properties {
		public String nickname;
		public String profileImage;
		public String thumbnailImage;
	}

	@JsonIgnoreProperties(ignoreUnknown=true) 
	@Data
	public class KakaoAccount {
		public Boolean profileNicknameNeedsAgreement;
		public Boolean profileImageNeedsAgreement;
		public Profile profile;
		public Boolean hasEmail;
		public Boolean emailNeedsAgreement;
		public Boolean isEmailValid;
		public Boolean isEmailVerified;
		public String email;

		@JsonIgnoreProperties(ignoreUnknown=true) 
		@Data
		public class Profile {
			public String nickname;
			public String thumbnailImageUrl;
			public String profileImageUrl;
			public Boolean isDefaultImage;
		}
	}
}


