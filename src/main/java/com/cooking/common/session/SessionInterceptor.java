package com.cooking.common.session;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cooking.common.util.MessageUtil;
import com.cooking.common.util.TokenManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object obj) throws Exception {
		String uri = req.getRequestURI();
		
		//resources 경로 check
		if (uri.startsWith("/resources")) {
			log.debug(MessageUtil.getMessage("001"));
			return false; 
		}
		
		//null check
		if(req.getHeader("token")==null || req.getHeader("userId")==null) {
			log.debug(MessageUtil.getMessage("002"));
			return false;
		}
		
		//로그인,로그아웃 상관없이 true
		if(uri.contains("idCheck.do")||uri.contains("nicknameCheck.do")||uri.contains("pwChange.do")) {
			log.debug(MessageUtil.getMessage("000"));
			return true;
		}
		
		//로그인,로그아웃 검사
		if(!req.getHeader("userId").equals("") && !req.getHeader("token").equals("")) {
			log.debug(MessageUtil.getMessage("003"));
			String token = req.getHeader("token");
			String user_id = req.getHeader("userId");
			
			//토큰값 복호화
			TokenManager tokenManager = new TokenManager();
			Map<String, Object> deToken = tokenManager.decryptToken(token);
			
			//복호화한 토큰값과 ID 비교
			if(deToken.get("id").equals(user_id)) {	
				if(uri.contains("login.do")||uri.contains("userCreate.do")) {
					log.debug(MessageUtil.getMessage("005"));
					return false;
				}
			}
		}else {
			log.debug(MessageUtil.getMessage("004"));
			if(!(uri.contains("login.do")||uri.contains("userCreate.do"))) {
				log.debug(MessageUtil.getMessage("006"));
				return false;
			}
		}
		log.debug(MessageUtil.getMessage("000"));
		return true;
	}
	

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object obj, Exception exc) throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object obj, ModelAndView mav) throws Exception {
		
	}

}
