package com.cooking.service.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooking.service.MainService;

@Service
public class MainServiceImpl implements MainService{

	@Autowired
	@Resource(name="sqlSession")
	private SqlSession sqlSession;
	
	@Override
	public String main() throws Exception {
		String result = sqlSession.selectOne("Main.mainSelect");
		return result;
	}
	
}
