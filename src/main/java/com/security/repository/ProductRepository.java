package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	// 아무 메서드도 선언할 필요가 없음. 스프링 데이터가 구현한 
	//JPARepository 인터페이스에서 상속한 메서드만 이용함.
}
