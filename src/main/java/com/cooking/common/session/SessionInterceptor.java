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
		
		if (uri.startsWith("/resources")) {
			log.debug(MessageUtil.getMessage("001"));
			return false; 
		}
		
		//null check
		if(req.getHeader("user_seq")==null || req.getHeader("user_email")==null || req.getHeader("user_token")==null) {
			log.debug(MessageUtil.getMessage("002"));
			return false;
		}
		
		if(uri.contains("emailCheck.do")||uri.contains("nicknameCheck.do")||uri.contains("pwChange.do")) {
			log.debug(MessageUtil.getMessage("000"));
			return true;
		}
		
		//로그인,로그아웃 검사
		if(!req.getHeader("user_seq").equals("") && !req.getHeader("user_email").equals("") && !req.getHeader("user_token").equals("")) {
			log.debug(MessageUtil.getMessage("003"));
			String user_seq = req.getHeader("user_seq");		//헤더로 seq 	 값 가져오기
			String user_email = req.getHeader("user_email");	//헤더로 email 값 가져오기
			String user_token = req.getHeader("user_token");	//헤더로 token 값 가져오기
			
			//토큰 복호화
			TokenManager tokenManager = new TokenManager();
			Map<String, Object> deToken = tokenManager.decryptToken(user_token);
			
			//복호화한 토큰값과 seq,email 비교
			if(deToken.get("user_seq").equals(user_seq) && deToken.get("user_email").equals(user_email)) {
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
