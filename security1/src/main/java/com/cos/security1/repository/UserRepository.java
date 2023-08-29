package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

/**
 * 
 * @author cos JPA는 기본 CRUD를 JpaRepository가 상속하는 CrudRepository가 가지고 있음. JPA는
 *         method names 전략을 가짐. README.md 사진 참고
 */

public interface UserRepository extends JpaRepository<User, Integer> {

	//findBy 규칙 -> Username 문법 ( JPA 쿼리메서드 )
	// SELECT * FROM user WHERE username = ?1(파라미터1번)
	User findByUsername(String username);
	

	// SELECT * FROM user WHERE provider = ?1 and providerId = ?2
	// Optional<User> findByProviderAndProviderId(String provider, String
	// providerId);

}
