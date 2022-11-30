package com.cooking.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooking.common.util.AES256Util;
import com.cooking.common.util.MessageUtil;
import com.cooking.common.util.TokenManager;
import com.cooking.service.LoginService;
import com.cooking.vo.UserVO;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	@Resource(name="sqlSession")
	private SqlSession sqlSession;
	
	@Override
	public Map<String, Object> login(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		
		int emailCheck = sqlSession.selectOne("Login.emailCheck", userVO);
		
		//계정 체크. 계정 있으면 1, 없으면 0
		if(emailCheck == 0) {
			paramheader.put("reCode","900");
			paramheader.put("reMsg",MessageUtil.getMessage("900"));
			result.put("header", paramheader);
			return result;
		}
		
		//비밀번호 암호화 해서 비교
		userVO.setUser_pw(AES256Util.aesEncode(userVO.getUser_pw()));
		
		//로그인 계정 여부 확인. 있으면 seq 값 get, 없으면 null
		String seq = sqlSession.selectOne("Login.login", userVO);	
		userVO.setUser_seq(seq);
		
		if(seq == null || seq.equals("")) {
			//잘못된 PW입력
			paramheader.put("reCode","901");
			paramheader.put("reMsg",MessageUtil.getMessage("901"));
			result.put("header", paramheader);
			return result;
		}
		
		//토큰 생성
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_seq", seq);
		map.put("user_email", userVO.getUser_email_id()+"@"+userVO.getUser_email_addr());
		
		TokenManager token = new TokenManager();
		String myToken = token.encryptToken(map);
		userVO.setUser_token(myToken);
		
		//오늘 날짜 저장
		LocalDateTime today = LocalDateTime.now();
		String day = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		userVO.setUser_history(day);
		
		sqlSession.update("Login.tokenUpdate",userVO);
		
		paramheader.put("reCode","200");
		paramheader.put("reMsg",MessageUtil.getMessage("200"));
		
		parambody.put("user_seq",seq);						//seq
		parambody.put("user_email",map.get("user_email"));	//email
		parambody.put("user_token",myToken);				//토큰
		
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}
	
	@Override
	public Map<String, Object> emailCheck(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		
		//이메일 중복 체크. 이미 있으면 1, 없으면 0
		int emailCheck = sqlSession.selectOne("Login.emailCheck", userVO);
		
		if(emailCheck == 0) {
			paramheader.put("reCode","204");
			paramheader.put("reMsg",MessageUtil.getMessage("204"));
			parambody.put("user_email",userVO.getUser_email_id()+'@'+userVO.getUser_email_addr());
		}else {
			paramheader.put("reCode","902");
			paramheader.put("reMsg",MessageUtil.getMessage("902"));
		}
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}

	@Override
	public Map<String, Object> nicknameCheck(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		
		//닉네임 중복 체크. 이미 있으면 1, 없으면 0
		int emailCheck = sqlSession.selectOne("Login.nicknameCheck", userVO);
		
		if(emailCheck == 0) {
			paramheader.put("reCode","204");
			paramheader.put("reMsg",MessageUtil.getMessage("204"));
			parambody.put("user_nickname",userVO.getUser_nickname());
		}else {
			paramheader.put("reCode","902");
			paramheader.put("reMsg",MessageUtil.getMessage("902"));
		}
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}
	
	@Override
	public Map<String, Object> pwChange(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		
		int emailCheck = sqlSession.selectOne("Login.emailCheck", userVO);
		
		//계정 체크. 계정 있으면 1, 없으면 0
		if(emailCheck == 0) {
			paramheader.put("reCode","900");
			paramheader.put("reMsg",MessageUtil.getMessage("900"));
			result.put("header", paramheader);
			return result;
		}
		
		if(userVO.getUser_pw() == null || userVO.getUser_pw().equals("")) {
			/*비밀번호 초기화*/
			
			//과거 비번 정보 가져오기
			UserVO reUserVO = sqlSession.selectOne("Login.pwSelect", userVO);

			//비밀번호 암호화
			reUserVO.setUser_pw_new(AES256Util.aesEncode(userVO.getUser_pw_new()));
			
			//과거 비번 체크. 중복되면 1 아니면 0
			int pwCheck = sqlSession.selectOne("Login.pwCheck", reUserVO);
			if(pwCheck == 1) {
				paramheader.put("reCode","903");
				paramheader.put("reMsg",MessageUtil.getMessage("903"));
				result.put("header", paramheader);
				return result;
			}
			
			//비밀번호 저장
			sqlSession.update("Login.pwChange", reUserVO);
			
			paramheader.put("reCode","202");
			paramheader.put("reMsg",MessageUtil.getMessage("202"));
		}else {
			/*비밀번호 변경*/
			
			//과거 비번 정보 가져오기
			UserVO reUserVO = sqlSession.selectOne("Login.pwSelect", userVO);

			//기존 비밀번호 일치 여부 체크
			if(!AES256Util.aesEncode(userVO.getUser_pw()).equals(reUserVO.getUser_pw_old1())) {
				paramheader.put("reCode","901");
				paramheader.put("reMsg",MessageUtil.getMessage("901"));
				result.put("header", paramheader);
				return result;
			}
			
			//비밀번호 암호화
			reUserVO.setUser_pw_new(AES256Util.aesEncode(userVO.getUser_pw_new()));
			
			//과거 비번 체크. 중복되면 1 아니면 0
			int pwCheck = sqlSession.selectOne("Login.pwCheck", reUserVO);
			if(pwCheck == 1) {
				paramheader.put("reCode","903");
				paramheader.put("reMsg",MessageUtil.getMessage("903"));
				result.put("header", paramheader);
				return result;
			}
			
			//비밀번호 저장
			sqlSession.update("Login.pwChange", reUserVO);
			
			paramheader.put("reCode","202");
			paramheader.put("reMsg",MessageUtil.getMessage("202"));
		}
		result.put("header", paramheader);
		return result;
	}

}
