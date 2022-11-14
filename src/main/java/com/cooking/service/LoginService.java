package com.cooking.service;

import java.util.Map;

import com.cooking.vo.UserVO;

public interface LoginService {
	
	Map<String,Object> login(UserVO userVO) throws Exception;
	
	Map<String,Object> userCreate(UserVO userVO) throws Exception;
	
	Map<String, Object> pwChange(UserVO userVO) throws Exception;

	Map<String, Object> emailCheck(UserVO userVO) throws Exception;

	Map<String, Object> nicknameCheck(UserVO userVO) throws Exception;

}
