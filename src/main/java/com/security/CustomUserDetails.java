package com.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.security.entity.User;

public class CustomUserDetails implements UserDetails {

	private final User user;
	
	public CustomUserDetails(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthorities().stream()
			.map(a -> new SimpleGrantedAuthority(
						a.getName())) //데이터베이스에서 각 사용자에 대해 발견된 각 권한 이름을 SimpleGrantedAuthority로 매핑
			.collect(Collectors.toList()); // SimpleGrantedAuthority의 모든 인스턴스를 목록으로 수집하고 반환
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
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public final User getUser() {
		return user;
	}

}
