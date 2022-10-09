package com.cooking.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		// TODO Auto-generated method stub
		int loginYN = sqlSession.selectOne("Main.login", userVO);
		
		System.out.println("login=============>"+loginYN);
		
		Map<String,Object> result = new HashMap<>();
		
		if(loginYN == 1) {
			result.put("msg","로그인 성공");
		}else {
			result.put("msg","잘못된 ID,PW 입니다.");
		}
		return result;
	}
	
}
