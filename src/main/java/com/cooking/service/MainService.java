package com.cooking.service;

import java.util.Map;

import com.cooking.vo.UserVO;

public interface MainService {

	Map<String, Object> bbsCreate(UserVO userVO);
	
}
