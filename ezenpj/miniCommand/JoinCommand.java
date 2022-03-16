package com.kook.ezenpj.miniCommand;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import com.kook.ezenpj.dao.MiniDao;
import com.kook.ezenpj.dto.JoinDto;
import com.kook.ezenpj.util.Constant;

public class JoinCommand implements MiniCommand {
	
	@Override
	public void execute(Model model, HttpServletRequest request) {
		BCryptPasswordEncoder passwordEncoder = Constant.passwordEncoder;
		String bId = request.getParameter("pid");
		String bPw = request.getParameter("ppw");
		String baddress = request.getParameter("paddress");
		String bhobby = request.getParameter("phobby");
		String bprofile = request.getParameter("pprofile");
		
		String bPw_org = bPw;
		bPw = passwordEncoder.encode(bPw_org);  // 여기서 bPw는 암호화됨
		System.out.println(bPw + " size " + bPw.length());
		
		JoinDto dto = new JoinDto(bId,bPw,baddress,bhobby,bprofile);
		
		MiniDao mdao = Constant.mdao;
		
		String result = mdao.join(dto);
		
		request.setAttribute("result", result);  //controller에서 결과 사용
	}
}
