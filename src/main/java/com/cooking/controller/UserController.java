package com.cooking.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cooking.common.util.MessageUtil;
import com.cooking.service.UserService;
import com.cooking.vo.UserVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/cooking/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * 회원가입
	 * 
	 * @param request 
	 * @param response : 회원가입 여부
	 * @param userVO
	 * @return : Map
	 * @throws Exception
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/userCreate.do")
	public Map<String,Object> userCreate(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO userVO) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader = new HashMap<String,Object>();
		try {
			result = userService.userCreate(userVO);
		}catch(Exception e) {
			e.printStackTrace();
			paramheader.put("reCode","991");
			paramheader.put("reMsg",MessageUtil.getMessage("991"));
			result.put("header", paramheader);
		}
		return result;
	}
	
	/**
	 * 정보수정
	 * 
	 * @param request
	 * @param response 내 정보 수정 성공 여부
	 * @param userVO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/userUpdate.do")
	public Map<String,Object> userUpdate(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO userVO) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader = new HashMap<String,Object>();
		try {
			result = userService.userUpdate(userVO);
		}catch(Exception e) {
			e.printStackTrace();
			paramheader.put("reCode","992");
			paramheader.put("reMsg",MessageUtil.getMessage("992"));
			result.put("header", paramheader);
		}
		return result;
	}
	
	/**
	 * 회원탈퇴 
	 * 
	 * @param request
	 * @param response 회원탈퇴 성공 여부
	 * @param userVO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/userDelete.do")
	public Map<String,Object> userDelete(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO userVO) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader = new HashMap<String,Object>();
		try {
			result = userService.userDelete(userVO);
		}catch(Exception e) {
			e.printStackTrace();
			paramheader.put("reCode","993");
			paramheader.put("reMsg",MessageUtil.getMessage("993"));
			result.put("header", paramheader);
		}
		return result;
	}
	
}
