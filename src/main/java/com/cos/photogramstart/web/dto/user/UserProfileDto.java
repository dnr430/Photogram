package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
	private boolean pageOwnerState;	// 페이지의 주인인지 아닌지에 대한 여부 데이터
	private int imageCount;	// 게시글수
	private boolean subscribeState;	// 구독한 상태인지
	private int subscribeCount;	// 구독자수
	private User user;
}
