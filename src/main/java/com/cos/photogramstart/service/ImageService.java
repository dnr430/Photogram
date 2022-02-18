package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Transactional(readOnly = true)	// 영속성 컨텍스트 변경 감지를 해서, 더티체킹, flush(반영) -> 이 행동을 안함!
	public Page<Image> imgstory(int principalId, Pageable pageable) {	// 이미지스토리
		Page<Image> images = imageRepository.mStory(principalId, pageable);
		
		// 시나리오 : 2(cos) 로그인
		// images에 좋아요 상태 담기
		images.forEach((image) -> {
			
			image.setLikeCount(image.getLikes().size());
			
			image.getLikes().forEach((like) -> {
				if(like.getUser().getId() == principalId) {	// 해당 이미지에 좋아요한 사람들을 찾아서 현재 로그인한 사람이 좋아요 한것인지 비교
					image.setLikeState(true);
				}
			});
		});
		
		return images;
	}
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Transactional
	public void uploadImage(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {	// 사진업로드
		UUID uuid = UUID.randomUUID();	// uuid : 범용고유식별자
		String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();	// 실제 파일이름이 들어감 (ex: 1.jpg, cat.png 등)
		System.out.println("이미지 파일이름 : " + imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder + imageFileName);
		
		// 통신, I/O -> 예외가 발생할 수 있다.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// image 테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		imageRepository.save(image);
		
		//System.out.println(imageEntity.toString());
		
	}

}
