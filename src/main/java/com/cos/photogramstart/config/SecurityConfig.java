package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity	// 해당 파일로 시큐리티를 활성화
@Configuration		// IoC  
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// SecurityConfig가 IoC에 등록될때 @Bean을 읽어서 리턴해서 IoC가 들고있음 -> DI에서 쓰면됨
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화됨.
		http.csrf().disable();		// CSRF 토큰 비활성화
		http.authorizeRequests()
			.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**").authenticated()	// 이런식의 페이지를 요청한다. 인증이 필요한 페이지인데 인증이 안되어 있다면
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/auth/signin")	// GET -> 인증이 필요한 페이지를 요청
			.loginProcessingUrl("/auth/signin")	// POST -> 스프링 시큐리티가 로그인 프로세스 진행
			.defaultSuccessUrl("/");
	}

}
