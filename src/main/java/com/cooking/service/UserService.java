package com.cooking.service;

import java.util.Map;

import com.cooking.vo.UserVO;

public interface UserService {

	Map<String, Object> userDelete(UserVO userVO) throws Exception;

	Map<String, Object> userUpdate(UserVO userVO) throws Exception;

}
