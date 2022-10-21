package com.cooking.common.session;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cooking.common.util.TokenManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object obj) throws Exception {
		String uri = req.getRequestURI();
		
		if (uri.startsWith("/resources")) {
			log.debug("resources 로 시작됨.");
			return false; 
		}
		
		//null check
		if(req.getHeader("token")==null || req.getHeader("userId")==null) {
			log.debug("token,userId 없음");
			return false;
		}
		
		//로그인,로그아웃 검사
		if(!req.getHeader("userId").equals("") && !req.getHeader("token").equals("")) {
			log.debug("로그인 상태");
			String token = req.getHeader("token");		//헤더로 토큰값 가져오기
			String user_id = req.getHeader("userId");	//헤더로 ID값 가져오기
			
			TokenManager tokenManager = new TokenManager();
			Map<String, Object> deToken = tokenManager.decryptToken(token);
			if(deToken.get("id").equals(user_id)) {	//복호화한 토큰값과 ID 비교
				if(uri.startsWith("/cooking/main/login.do")||uri.startsWith("/cooking/main/resist.do")) {
					log.debug("로그인 상태 : 로그인,회원가입 페이지 접근불가");
					return false;
				}
			}
		}else {
			log.debug("로그아웃 상태");
			if(!(uri.startsWith("/cooking/main/login.do")||uri.startsWith("/cooking/main/resist.do"))) {
				log.debug("로그아웃 상태 : 로그인,회원가입 페이지외에 접근불가");
				return false;
			}
		}
		
		return true;
	}
	

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object obj, Exception exc) throws Exception {
		System.out.println("afterCompletion");
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object obj, ModelAndView mav) throws Exception {
		System.out.println("postHandle");
	}

}
