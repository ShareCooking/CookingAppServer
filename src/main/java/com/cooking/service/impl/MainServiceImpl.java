package com.cooking.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooking.common.util.AES256Util;
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
			result.put("msg","로그인 성공");
		}else {
			result.put("msg","잘못된 ID,PW 입니다.");
		}
		return result;
	}

	@Override
	public Map<String, Object> resist(UserVO userVO) throws Exception {
		//비밀번호 암호화
		userVO.setUser_pw(AES256Util.aesEncode(userVO.getUser_pw()));
		
		int cnt = sqlSession.insert("Main.UserInsert", userVO);	//회원가입 테이블에 insert. insert 성공하면 3, 아니면 0
		
		Map<String,Object> result = new HashMap<>();
		
		if(cnt == 3) {
			result.put("msg","회원가입 성공");
		}else {
			result.put("msg","회원가입중 에러 발생! 관리자에게 문의하세요.");
		}
		
		return result;
	}
	
}
