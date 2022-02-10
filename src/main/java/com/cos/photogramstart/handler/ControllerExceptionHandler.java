package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.auth.CMRespDto;

@RestController
@ControllerAdvice	// 모든 Exception을 처리한다.
public class ControllerExceptionHandler {
	
	@ExceptionHandler(CustomValidationException.class)	// CustomValidationException이 발동하는 모든 Exception을 처리한다.
	public String validationException(CustomValidationException e) {
		// CMRespDto, Script 비교
		// 1. 클라이언트에게 응답할 때는 Script가 좋음.
		// 2. Ajax통신 - CMRespDto
		// 3. Android통신 - CMRespDto
		return Script.back(e.getErrorMap().toString());
	}
	
//	public CMRespDto<?> validationException(CustomValidationException e) {
//		return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap());
//	}

}
