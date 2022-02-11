package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service	// IoC
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	// 1. 패스워드는 알아서 체킹하니까 신경쓸 필요 없다.
	// 2. 리턴이 잘되면 자동으로 UserDetails 타입을 세션으로 만든다.
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User userEntity = userRepository.findByUsername(username);	// 해당 username이 있는지 찾는다.
		
		if(userEntity == null) {	// 못찾음
			return null;
		}
		else {	// 찾음
			return new PrincipalDetails(userEntity);	// 세션에 저장될때 userEntity를 들고있으므로, 세션에 저장된 User의 데이터를 활용할 수 있다.
		}
	}

}
