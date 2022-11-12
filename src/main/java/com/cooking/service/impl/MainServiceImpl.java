package com.cooking.service.impl;

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

	@Override
	public Map<String, Object> bbsCreate(UserVO userVO) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
