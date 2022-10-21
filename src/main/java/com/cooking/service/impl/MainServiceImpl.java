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
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		
		if(loginYN == 1) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", userVO.getUser_id());
			
			TokenManager token = new TokenManager();
			String myToken = token.encryptToken(map);
			userVO.setUser_token(myToken);					//토큰 생성
			
			sqlSession.update("Main.TokenUpdate",userVO);
			
			paramheader.put("msg","로그인 성공");
			parambody.put("user_id",userVO.getUser_id());	//ID
			parambody.put("Token",myToken);					//토큰
		}else {
			paramheader.put("msg","잘못된 ID,PW 입니다.");
		}
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}

	@Override
	public Map<String, Object> resist(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		
		//비밀번호 암호화
		userVO.setUser_pw(AES256Util.aesEncode(userVO.getUser_pw()));
		
		int cnt = sqlSession.insert("Main.UserInsert", userVO);	//회원가입 테이블에 insert. insert 성공하면 3, 아니면 0
		if(cnt == 3) {
			paramheader.put("msg","회원가입 성공");
			parambody.put("resist","Y");
		}else {
			paramheader.put("msg","회원가입중 에러 발생! 관리자에게 문의하세요.");
			parambody.put("resist","N");
		}
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}

	/*
	@Override
	public Map<String, Object> logout(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		
		int cnt = sqlSession.update("Main.logout",userVO);
		if(cnt == 1) {
			paramheader.put("msg","로그아웃 성공");
			parambody.put("logout","Y");
		}else {
			paramheader.put("msg","회원가입중 에러 발생! 관리자에게 문의하세요.");
			parambody.put("logout","N");
		}
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}
	*/
	
	@Override
	public Map<String, Object> pwChange(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		Map<String,Object> map = sqlSession.selectOne("Main.pwSelect", userVO);
		
		//비밀번호 암호화
		map.put("USER_PW_NEW", AES256Util.aesEncode(userVO.getUser_pw()));
		
		int cnt = sqlSession.update("Main.pwChangeMain", map);	//user 메인 테이블에 update
		cnt += sqlSession.update("Main.pwChangeOld", map);		//user 과거PW 저장 테이블에 update
		
		if(cnt ==  2) {
			paramheader.put("msg","비밀번호 변경 성공");
			parambody.put("login","Y");
		}else {
			paramheader.put("msg","비밀번호 변경 실패");
			parambody.put("login","N");
		}
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}
	
}
