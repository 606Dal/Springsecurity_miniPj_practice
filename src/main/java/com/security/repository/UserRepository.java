package com.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entity.User;
/*
 * User 엔티티에 대한 스프링 데이터 리포지토리 - JPA 리포지토리 이용.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findUserByUsername(String u); // 찾은 이름을 기준으로 쿼리를 실행
	
}
