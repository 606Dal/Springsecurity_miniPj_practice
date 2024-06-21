package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import com.security.CustomUserDetails;

public class AuthenticationProviderService implements AuthenticationProvider{

	// 필요한 종속성인 UserDetailsService + PasswordEncoder 구현 주입
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private SCryptPasswordEncoder sCryptPasswordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) 
			throws AuthenticationException {
		
		String username = authentication.getName();
		String password = authentication
							.getCredentials()
							.toString();
		// userDetailsService로 데이터베이스에서 사용자 세부 정보 검색
		CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
		
		// 해당 사용자에 맞는 해싱 알고리즘으로 암호 검증
		switch (user.getUser().getAlgorithm()) {
			case BCRYPT:
				return checkPassword(user, password, bCryptPasswordEncoder);
			case SCRYPT:
				return checkPassword(user, password, sCryptPasswordEncoder);
		}
		
		throw new BadCredentialsException("Bad credentials(잘못된 자격 증명)");
	}
	
	private Authentication checkPassword(CustomUserDetails user,
										String rawPassword,
										PasswordEncoder encoder) {
		if (encoder.matches(rawPassword, user.getPassword())) {
			return new UsernamePasswordAuthenticationToken(
					user.getUsername(),
					user.getPassword(),
					user.getAuthorities());
		} else {
			throw new BadCredentialsException("Bad credentials(잘못된 자격 증명)");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
	}
}
