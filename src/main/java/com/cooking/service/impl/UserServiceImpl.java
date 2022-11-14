package com.cooking.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooking.common.util.MessageUtil;
import com.cooking.service.UserService;
import com.cooking.vo.UserVO;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	@Resource(name="sqlSession")
	private SqlSession sqlSession;
	
	@Override
	public Map<String, Object> userUpdate(UserVO userVO) throws Exception {
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		
		System.out.println(userVO);
		
		sqlSession.update("User.userUpdate", userVO);	
		
		paramheader.put("reCode","200");
		paramheader.put("reMsg",MessageUtil.getMessage("200"));
	
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}
	
	@Override
	public Map<String, Object> userDelete(UserVO userVO) throws Exception{
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> parambody =new HashMap<String,Object>();
		
		sqlSession.delete("User.userDelete", userVO);	
		
		paramheader.put("reCode","230");
		paramheader.put("reMsg",MessageUtil.getMessage("230"));
	
		result.put("header", paramheader);
		result.put("body", parambody);
		return result;
	}

}
