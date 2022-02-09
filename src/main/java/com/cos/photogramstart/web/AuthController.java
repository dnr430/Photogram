package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.internal.build.AllowSysOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final 필드를 DI 할때 사용
@Controller // 1. IoC 2. 파일을 리턴하는 컨트롤러
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;


//	 public AuthController(AuthService authService) {
//		 this.authService = authService; 
//	}


	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}

	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}

	// 회원가입버튼 -> /auth/signup -> /auth/signin
	// 회원가입버튼 X
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // key = value (x-www-form-urlencoded)
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {		// signupDto에서 오류가 발생하면 그 오류를 bindingResult의 getFieldErrors 컬렉션에 모아둔다.
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("=============================");
				System.out.println(error.getDefaultMessage());
				System.out.println("=============================");
			}
		}
		
		log.info(signupDto.toString());
		// User <- SignupDto
		User user = signupDto.toEntity();
		log.info(user.toString());
		
		User userEntity = authService.Join(user);
		System.out.println(userEntity);

		return "auth/signin";
	}
}