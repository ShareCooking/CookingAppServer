package com.cooking.service;

import java.util.Map;

import com.cooking.vo.UserVO;

public interface MainService {

	//String main() throws Exception;
	
	Map<String,Object> login(UserVO userVO) throws Exception;
}
