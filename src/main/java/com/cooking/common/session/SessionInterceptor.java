package com.cooking.common.session;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cooking.common.util.TokenManager;
import com.cooking.service.MainService;
import com.cooking.vo.UserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionInterceptor implements HandlerInterceptor {
	
	@Autowired
	private MainService mainService; 
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object obj) throws Exception {
		
		String uri = req.getRequestURI();
		
		if (uri.startsWith("/resources")) {
			System.out.println("resources 로 시작됨.");
			return false; 
		}
		
		//System.out.println(req.getHeader("user_id")+", " +req.getHeader("Token"));
		
		if(!(req.getHeader("user_id") ==""||req.getHeader("user_id") == null)){
			UserVO userVO = new UserVO();
			userVO.setUser_id(req.getHeader("user_id"));
			userVO.setUser_token(req.getHeader("Token"));
			Map<String,Object> result = mainService.tokenSelect(userVO);	//토큰으로 로그인 여부 확인
			
			if (result.get("login") == "Y") {	//현재 상태 : 로그인
				if(uri.startsWith("/cooking/main/login.do")||uri.startsWith("/cooking/main/resist.do")) {	//로그인 상태일때 로그인창과 회원가입창은 들어가지 못함.
					System.out.println("로그인 상태일때 로그인창과 회원가입창은 들어가지 못함");
					return false; 
				}
			}else {	//현재 상태 : 로그아웃
				if(!uri.startsWith("/cooking/main/login.do")&&!uri.startsWith("/cooking/main/resist.do")) {	//로그아웃 상태일때 로그인창과 회원가입창 외에 다른창은 들어가지 못함.
					System.out.println("로그아웃 상태일때 로그인창과 회원가입창 외에 다른창은 들어가지 못함.");
					return false; 
				}
			}
		}else {
			if(!uri.startsWith("/cooking/main/login.do")&&!uri.startsWith("/cooking/main/resist.do")) {	//로그아웃 상태일때 로그인창과 회원가입창 외에 다른창은 들어가지 못함.
				System.out.println("로그아웃 상태일때 로그인창과 회원가입창 외에 다른창은 들어가지 못함.");
				return false; 
			}
		}
		return true;
		
	}
	

	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse res, Object obj, Exception exc)
			throws Exception {
		System.out.println("afterCompletion");
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res,
			Object obj, ModelAndView mav) throws Exception {
		System.out.println("postHandle");
	}

}
