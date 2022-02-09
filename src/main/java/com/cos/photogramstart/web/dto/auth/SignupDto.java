package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data		// Getter, Setter
public class SignupDto {
	
	@Max(20)	// 유효성검사
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	
	public User toEntity() {	// builder 패턴 사용
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
}