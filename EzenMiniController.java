package com.kook.ezenpj.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kook.ezenpj.dao.MiniDao;
import com.kook.ezenpj.miniCommand.JoinCommand;
import com.kook.ezenpj.miniCommand.MiniCommand;
import com.kook.ezenpj.util.Constant;

@Controller
public class EzenMiniController {
	//bean주입하여 Constant에 설정
	private MiniDao mdao;
	@Autowired
	public void setMdao(MiniDao mdao) {
		this.mdao = mdao;
		Constant.mdao = mdao;
	}
	
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		Constant.passwordEncoder = passwordEncoder;
	}
	
	private MiniCommand mcom;  //command의 인터페이스 객체를 선언하여 다형성에 의해 구현 클래스 객체를 대입 사용
	
	// ========== join ===========
	@RequestMapping("/join_view")
	public String join_view() {
		return "join_view";
	}
	
	@RequestMapping(value="/join",produces="application/text; charset=UTF8")
	@ResponseBody  //jsp가 아닌 문자열이나 객체를 보내기 때문에 필요
	public String join(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("join");
		mcom = new JoinCommand();  // join요청에 따른 command클래스로 MiniCommand인터페이스 구현 클래스
		mcom.execute(model, request);
		String result = (String)request.getAttribute("result");
		System.out.println(result);
		if(result.equals("success"))
			return "join-success";
		else
			return "join-failed";		
		
	}
	
	// ========== login ============
	@RequestMapping("/login_view")
	public String login_view(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) {
		System.out.println("login_view");
		
		return "login_view";
	}
	
	@RequestMapping(value= "/processLogin", method = RequestMethod.GET)
	public ModelAndView processLogin(
			@RequestParam(value = "log", required = false) String log,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			HttpSession session
			) {
		System.out.println("processLogin");
		ModelAndView model = new ModelAndView();
		if(log != null && log != "" ) {  
			//security form이 아닌 곳(a href="processLogin?log=1")에서 로그인 창 요청시
			model.addObject("log", "before login!");
		}
		if(error != null && error != "" ) {  //로그인시 에러발생하면 security에서 요청(값은 1)
			model.addObject("error", "Invalid username or password!");
		}
		if(logout != null && logout != "" ) {  //로그아웃 성공시 security에서 요청(값은 1)
			model.addObject("logout", "You've been logged out successfully.");
		}
		model.setViewName("login_view");
		
		return model;
	}
	
	//========= product ============
	@RequestMapping("/product")
	public String product(HttpServletRequest request, HttpServletResponse response,
			 Model model, Authentication authentication) {
		System.out.println("product");
		
		//id와 role정보를 얻어내는 방법
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		System.out.println(userDetails.getUsername());  //aaa@naver.com (로그인한 아이디)
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String auth = authorities.toString();  //role을 얻어서 문자열로 변환
		System.out.println(auth);  //[ROLE_USER]형태
		return "product_view";
	}
}
