package com.cooking.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooking.common.util.AES256Util;
import com.cooking.common.util.MessageUtil;
import com.cooking.service.UserService;
import com.cooking.vo.UserVO;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	@Resource(name="sqlSession")
	private SqlSession sqlSession;
	
	@Override
	public Map<String, Object> userCreate(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		
		//비밀번호 암호화
		userVO.setUser_pw(AES256Util.aesEncode(userVO.getUser_pw()));
		
		//회원정보 저장
		sqlSession.insert("User.userInsert", userVO);
		
		paramheader.put("reCode","201");
		paramheader.put("reMsg",MessageUtil.getMessage("201"));
		result.put("header", paramheader);
		
		return result;
	}
	
	@Override
	public Map<String, Object> userUpdate(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		
		//회원정보 수정
		int check = sqlSession.update("User.userUpdate", userVO);
		
		if(check == 0) {
			paramheader.put("reCode","904");
			paramheader.put("reMsg",MessageUtil.getMessage("904"));
		}else {
			paramheader.put("reCode","202");
			paramheader.put("reMsg",MessageUtil.getMessage("202"));
		}
		result.put("header", paramheader);
		return result;
	}
	
	@Override
	public Map<String, Object> userDelete(UserVO userVO) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		
		//회원정보 삭제
		sqlSession.delete("User.userDelete", userVO);
		
		paramheader.put("reCode","203");
		paramheader.put("reMsg",MessageUtil.getMessage("203"));
	
		result.put("header", paramheader);
		return result;
	}

}
