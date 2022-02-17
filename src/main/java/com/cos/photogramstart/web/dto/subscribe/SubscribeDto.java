package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
	private int id;							// 누구를 구독할지의 누구역할
	private String username;				// 유저네임
	private String profileImageUrl;		// 사진이미지
	private Integer subscribeState;	// 구독상태여부
	private Integer equalUserState;	// 로그인한 사용자의 동일인 여부
}
