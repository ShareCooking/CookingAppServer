package com.cooking.service;

import java.util.Map;

import com.cooking.vo.UserVO;

public interface MainService {

	//String main() throws Exception;
	
	Map<String,Object> login(UserVO userVO) throws Exception;
	
	Map<String,Object> resist(UserVO userVO) throws Exception;
	
	//Map<String,Object> logout(UserVO userVO) throws Exception;
	
	Map<String, Object> pwChange(UserVO userVO) throws Exception;
}
