package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
	
		//SELECT *FROM user WHERE username = 1?
		Optional<User>findByUsername(String username);
		

	
		/*JPA Naming 전략
		// SELECT * FROM user WHERE username = ?1 And password =?2 
		//1번
		User findByUsernameAndPassword(String username, String password);
		
		//2번
		@Query(value = "SELECT * FROM user WHERE username = ?1 And password =?2",nativeQuery = true)
		User login(String username, String password);*/
		
}
