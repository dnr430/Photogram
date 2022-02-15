package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor	// 전체생성자
@NoArgsConstructor	// 빈생성자
@Data
@Entity	// 디비에 테이블을 생성
public class Image {	// N, 1
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int id;
	private String caption;	// 오늘 나 너무 피곤해!!(내용)
	private String postImageUrl;	// 사진을 전송받아서 그 사진을 서버의 특정 폴더에 저장 - DB에 그 저장된 경로를 ISNERT
	
	@JoinColumn(name="userId")
	@ManyToOne
	private User user;	// 1, 1
	
	// 이미지 좋아요 기능
	
	// 댓글 기능
	
	private LocalDateTime createDate;
	
	@PrePersist	// DB에 INSERT 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
