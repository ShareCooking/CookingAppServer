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
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		
		//계정있는지 체크. 계정 있으면 1 없으면 0
		int check = sqlSession.selectOne("Login.idCheck", userVO);
		if(check == 0) {
			paramheader.put("reCode","101");
			paramheader.put("reMsg", MessageUtil.getMessage("101"));
			result.put("header", paramheader);
			return result;
		}
		
		//비밀번호 암호화
		userVO.setUser_pw(AES256Util.aesEncode(userVO.getUser_pw()));
		
		//로그인 계정 여부 확인. 있으면 1, 없으면 0
		int loginYN = sqlSession.selectOne("Login.loginCheck", userVO);	
		
		if(loginYN == 0) {
			paramheader.put("reCode","102");
			paramheader.put("reMsg", MessageUtil.getMessage("102"));
			result.put("header", paramheader);
			return result;
		}
	
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", userVO.getUser_id());
		
		//토큰 생성
		TokenManager token = new TokenManager();
		String myToken = token.encryptToken(map);
		userVO.setUser_token(myToken);
		
		//현재 일시 기록
		LocalDateTime localDateTime = LocalDateTime.now();
		String str = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		userVO.setUser_history(str);
		
		//로그인 상태로 변경
		sqlSession.update("Login.login",userVO);
		
		paramheader.put("reCode","100");
		paramheader.put("reMsg", MessageUtil.getMessage("100"));
		parambody.put("user_id",userVO.getUser_id());
		parambody.put("Token",myToken);
		
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}
	
	@Override
	public Map<String, Object> userCreate(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		
		//비밀번호 암호화
		userVO.setUser_pw(AES256Util.aesEncode(userVO.getUser_pw()));
		
		//회원가입 테이블에 insert. insert 성공하면 3, 아니면 0
		int cnt = sqlSession.insert("Login.userInsert", userVO);
		if(cnt == 3) {
			paramheader.put("reCode","120");
			paramheader.put("reMsg", MessageUtil.getMessage("120"));
		}else {
			paramheader.put("reCode","121");
			paramheader.put("reMsg", MessageUtil.getMessage("121"));
		}
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}
	
	@Override
	public Map<String, Object> idCheck(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		
		//ID 중복체크. 중복이면 1 아니면 0
		int check = sqlSession.selectOne("Login.idCheck", userVO);	
		if(check == 0) {
			paramheader.put("reCode","130");
			paramheader.put("reMsg", MessageUtil.getMessage("130"));
		}else {
			paramheader.put("reCode","131");
			paramheader.put("reMsg", MessageUtil.getMessage("131"));
		}
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}
	
	@Override
	public Map<String, Object> nicknameCheck(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		
		//닉네임 중복체크. 중복이면 1 아니면 0
		int check = sqlSession.selectOne("Login.nicknameCheck", userVO);	
		if(check == 0) {
			paramheader.put("reCode","140");
			paramheader.put("reMsg", MessageUtil.getMessage("140"));
		}else {
			paramheader.put("reCode","141");
			paramheader.put("reMsg", MessageUtil.getMessage("141"));
		}
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}
	
	@Override
	public Map<String, Object> pwChange(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		
		//기존 비밀번호 있으면 변경, 없으면 초기화
		if(userVO.getUser_pw() == null || userVO.getUser_pw().equals("")) {
			/*비밀번호 초기화*/
			
			//과거 비번정보 가져오기
			UserVO reUserVO = sqlSession.selectOne("Login.pwSelect", userVO);
			
			//새로운 비밀번호 암호화
			reUserVO.setUser_pw_new(AES256Util.aesEncode(userVO.getUser_pw_new()));	
			
			//새로 저장할 비번 중복체크. 중복이면 0, 아니면 1
			int check = sqlSession.selectOne("Login.pwCheck", reUserVO);
			if(check == 0) {
				paramheader.put("reCode","153");
				paramheader.put("reMsg", MessageUtil.getMessage("153"));
				result.put("header", paramheader);
				return result;
			}
			
			//새로운 비밀번호 저장
			sqlSession.update("Login.pwChange", reUserVO);
			
			paramheader.put("reCode","150");
			paramheader.put("reMsg", MessageUtil.getMessage("150"));
		}else {
			/*비밀번호 변경*/
			
			//과거 비번정보 가져오기
			UserVO reUserVO = sqlSession.selectOne("Login.pwSelect", userVO);		
			
			//입력한 기존 비번과 DB에 저장된 기존 비번이 일치하는지 확인
			if(!AES256Util.aesEncode(userVO.getUser_pw()).equals(reUserVO.getUser_pw_old1())) {
				paramheader.put("reCode","152");
				paramheader.put("reMsg", MessageUtil.getMessage("152"));
				result.put("header", paramheader);
				return result;
			}
			
			//새로운 비밀번호 암호화
			reUserVO.setUser_pw_new(AES256Util.aesEncode(userVO.getUser_pw_new()));	
			
			//새로 저장할 비번 중복체크. 중복이면 0, 아니면 1
			int check = sqlSession.selectOne("Login.pwCheck", reUserVO);
			if(check == 0) {
				paramheader.put("reCode","153");
				paramheader.put("reMsg", MessageUtil.getMessage("153"));
				result.put("header", paramheader);
				return result;
			}
			
			//새로운 비밀번호 저장
			sqlSession.update("Login.pwChange", reUserVO);
			
			paramheader.put("reCode","151");
			paramheader.put("reMsg", MessageUtil.getMessage("151"));
		}
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}
	
}
