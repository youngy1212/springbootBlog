package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
		// SELECT * FROM user WHERE username = 1?;
		//Optional<User> findByUsername(String username);


}
