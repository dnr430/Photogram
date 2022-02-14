package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.auth.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final UserService userService;
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
			@PathVariable int id, 
			@Valid UserUpdateDto userUpdateDto,
			BindingResult bindingResult,	// 꼭, @Valid가 적혀있는 다음 파라미터에 적어야됨
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {		// signupDto에서 오류가 발생하면 그 오류를 bindingResult의 getFieldErrors 컬렉션에 모아둔다.
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("===================");
				System.out.println(error.getDefaultMessage());
				System.out.println("===================");
			}
			throw new CustomValidationApiException("유효성 검사 실패함", errorMap);	// 강제 발생
		}
		else {
			User userEntity = userService.editUser(id, userUpdateDto.toEntity());
			principalDetails.setUser(userEntity);	// 세션 정보 변경
			return new CMRespDto<>(1, "회원수정완료", userEntity);
		}
	}
}
