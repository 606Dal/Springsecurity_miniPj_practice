package com.security.service;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.CustomUserDetails;
import com.security.entity.User;
import com.security.repository.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Supplier<UsernameNotFoundException> s =
				() -> new UsernameNotFoundException(
					  "Problem during authentication(인증 중 문제가 발생했습니다)!");
		
		User u = userRepository
				//사용자를 포함한 Optional 인스턴스를 반환하거나 사용자가 없으면 비어있는 Optional 인스턴스를 반환
				.findUserByUsername(username)
				.orElseThrow(s); // Optional 인스턴스가 비어 있으면 위에서 생성한 예외를 투척, 그렇지 않으면 User 인스턴스 반환
				
		return new CustomUserDetails(u); // User 인스턴스를 CustomUserDetails 데코레이터로 래핑하고 반환
	}

}
