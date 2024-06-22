package com.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import com.security.service.AuthenticationProviderService;

@Configuration
@EnableWebSecurity // 이게 뭔지 나중에
public class ProjectConfig {
	
	//@Autowired
	//private AuthenticationProviderService authenticationProvider;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SCryptPasswordEncoder sCryptPasswordEncoder() {
		// 책 예제에는 비어있어서 기본값 사용
		return new SCryptPasswordEncoder(65536, 8, 1, 32, 64);
	}
	
	// 나중에 되는지 확인 필요 // 확인
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) 
			throws Exception {
		//HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		//requestCache.setMatchingRequestParameterName(null);
		
		http.formLogin((form) -> form
				.defaultSuccessUrl("/main", true));
		
		http.authorizeHttpRequests((authorize) -> authorize
				.anyRequest().authenticated());
		
		return http.build();
	}
	
	
	
	
}
