package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}

	// 권한 : 한개가 아닐 수 있음. (3개 이상의 권한을 들고 있을 수도 있음)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {	// 권한을 가져오는 함수, 권한은 user가 들고있다.
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(() -> { return user.getRole(); });
		
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {	// 계정 만료 여부
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {	// 계정 잠김 여부
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {	// 비밀번호 만기 여부
		return true;
	}

	@Override
	public boolean isEnabled() {	// 계정 활성화 여부
		return true;
	}

}
