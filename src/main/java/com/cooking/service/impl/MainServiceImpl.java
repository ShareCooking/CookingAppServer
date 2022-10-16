package com.cooking.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooking.common.util.AES256Util;
import com.cooking.common.util.TokenManager;
import com.cooking.service.MainService;
import com.cooking.vo.UserVO;

@Service
public class MainServiceImpl implements MainService{

	@Autowired
	@Resource(name="sqlSession")
	private SqlSession sqlSession;
	
	/*
	 * @Override public String main() throws Exception { String result =
	 * sqlSession.selectOne("Main.mainSelect"); return result; }
	 */


	@Override
	public Map<String, Object> login(UserVO userVO) throws Exception {
		//비밀번호 암호화 해서 비교
		userVO.setUser_pw(AES256Util.aesEncode(userVO.getUser_pw()));
		
		int loginYN = sqlSession.selectOne("Main.login", userVO);	//로그인 계정 여부 확인. 있으면 1, 없으면 0
		
		Map<String,Object> result = new HashMap<>();
		
		if(loginYN == 1) {
			Map<String,Object> map = new HashMap<>();
			map.put("id", userVO.getUser_id());
			
			TokenManager token = new TokenManager();
			String myToken = token.encryptToken(map);
			userVO.setUser_token(myToken);	//토큰 생성
			
			sqlSession.update("Main.TokenUpdate",userVO);

			result.put("user_id",userVO.getUser_id());	//ID
			result.put("Token",myToken);	//토큰
			result.put("msg","로그인 성공");
		}else {
			result.put("msg","잘못된 ID,PW 입니다.");
		}
		return result;
	}

	@Override
	public Map<String, Object> resist(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<>();
		//비밀번호 암호화
		userVO.setUser_pw(AES256Util.aesEncode(userVO.getUser_pw()));
		
		int cnt = sqlSession.insert("Main.UserInsert", userVO);	//회원가입 테이블에 insert. insert 성공하면 3, 아니면 0
		
		if(cnt == 3) {
			result.put("msg","회원가입 성공");
		}else {
			result.put("msg","회원가입중 에러 발생! 관리자에게 문의하세요.");
		}
		return result;
	}

	@Override
	public Map<String, Object> logout(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<>();
		int cnt = sqlSession.update("Main.logout",userVO);
		
		if(cnt == 1) {
			result.put("msg","로그아웃 성공");
		}else {
			result.put("msg","회원가입중 에러 발생! 관리자에게 문의하세요.");
		}
		return result;
	}

	@Override
	public Map<String, Object> tokenSelect(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<>();
		
		int cnt = sqlSession.selectOne("Main.tokenSelect", userVO);		//토근으로 로그인 여부확인
		
		if(cnt == 1) {
			result.put("msg","로그인 되어있습니다.");
			result.put("login","Y");
		}else {
			result.put("msg","로그인 되어있는 계정이 없습니다.");
			result.put("login","N");
		}
		
		return result;
	}

	@Override
	public Map<String, Object> pwChange(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<>();
		
		Map<String,Object> map = sqlSession.selectOne("Main.pwSelect", userVO);
		
		userVO.setUser_seq(map.get("USER_SEQ").toString());
		//비밀번호 암호화
		userVO.setUser_pw(AES256Util.aesEncode(userVO.getUser_pw()));
		userVO.setUser_pw_old1(AES256Util.aesEncode(userVO.getUser_pw()));
		
		//과거 비밀번호에 입력 (제일 최근 비번이 1. 뒤로 갈수록 과거 비번)
		userVO.setUser_pw_old2((String) map.get("USER_PW_OLD1"));
		if(map.get("USER_PW_OLD2") == null) { userVO.setUser_pw_old3(""); }else { userVO.setUser_pw_old3(map.get("USER_PW_OLD2").toString());}
		if(map.get("USER_PW_OLD3") == null) { userVO.setUser_pw_old4(""); }else { userVO.setUser_pw_old4(map.get("USER_PW_OLD3").toString());}
		if(map.get("USER_PW_OLD4") == null) { userVO.setUser_pw_old5(""); }else { userVO.setUser_pw_old5(map.get("USER_PW_OLD4").toString());}
		
		int cnt = sqlSession.update("Main.pwChangeMain", userVO);	//tb_user update
		cnt += sqlSession.update("Main.pwChangeOld", userVO);		//tb_user_pw_old update
		
		if(cnt ==  2) {
			result.put("msg","비밀번호 변경 성공");
			result.put("login","Y");
		}else {
			result.put("msg","비밀번호 변경 실패");
			result.put("login","N");
		}
		
		return result;
	}
	
}
