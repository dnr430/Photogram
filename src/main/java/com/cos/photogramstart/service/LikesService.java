package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikesService {
	private final LikesRepository likesRepository;
	
	@Transactional
	public void iLike(int imageId, int principalId) {	// 좋아요
		likesRepository.mLikes(imageId, principalId);
	}
	
	@Transactional
	public void unILike(int imageId, int principalId) {	// 좋아요 취소
		likesRepository.mUnLikes(imageId, principalId);
	}
	
}
