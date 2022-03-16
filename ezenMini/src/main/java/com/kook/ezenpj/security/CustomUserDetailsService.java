package com.kook.ezenpj.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kook.ezenpj.dao.MiniDao;
import com.kook.ezenpj.dto.JoinDto;
import com.kook.ezenpj.util.Constant;

public class CustomUserDetailsService implements UserDetailsService{
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MiniDao mdao = Constant.mdao;
		JoinDto dto = mdao.login(username);
		System.out.println("dto " + dto);
		if(dto == null) {
			System.out.println("null");
			throw new UsernameNotFoundException("No user found with username");
			//spring security에서 예외를 처리하여 로그인 실패 처리
		}
		String pw = dto.getPpw();
		Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		// role값(권한구분 값, ROLE_USER, ROLE_ADMIN, ROLE_MANAGE 등)을 저장하는 리스트 객체
		// db에 authority컬럼을 주고 저장했다면 dto.getAuthority()로 얻은 값을 new SimpleGrantedAuthority("얻은 값")
		roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		UserDetails user = new User(username, pw, roles);
		//얻은 id,pw,roles를 이용하여 UserDetails객체를 만들어 반환해줌
		Constant.username = username;  //로그인 성공 후 id필요시 어디서나 가져다 씀
		System.out.println("custom");
		return user;  //spring에서 pw일치 여부 체크하고 권한 설정 등의 작업을 해줌
	}

}
