package com.cooking.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cooking.common.util.MessageUtil;
import com.cooking.service.MainService;
import com.cooking.vo.UserVO;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequestMapping("/cooking/main")
public class MainController{

	@Autowired
	private MainService mainService; 
	
	/* 대쉬보드 조회 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/main.do") public String main(HttpServletRequest
	 * request, HttpServletResponse response) throws Exception{ return
	 * mainService.main();
	 * 
	 * 
	 * }
	 */
	
	/**
	 * 로그인
	 * 
	 * @param request 
	 * @param response : 성공여부
	 * @param userVO
	 * @return : Map
	 * @throws Exception
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/login.do")
	public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO userVO) throws Exception{
		Map<String,Object> result =new HashMap<String,Object>();
		try {
			result = mainService.login(userVO);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("message", MessageUtil.getMessage("error.login"));
		}
		return result;
	}
	
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
	@RequestMapping(value = "/resist.do")
	public Map<String,Object> resist(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO userVO) throws Exception{
		Map<String,Object> result =new HashMap<String,Object>();
		try {
			result = mainService.resist(userVO);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("message", MessageUtil.getMessage("error.insert"));
		}
		return result;
	}
	
	/**
	 * 로그아웃
	 * 
	 * @param request
	 * @param response : 로그아웃 여부
	 * @param userVO
	 * @return
	 * @throws Exception
	 */
	/*
	@ResponseBody
	@RequestMapping(value = "/logout.do")
	public Map<String,Object> logout(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO userVO) throws Exception{
		Map<String,Object> result =new HashMap<String,Object>();
		try {
			result = mainService.logout(userVO);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("message", MessageUtil.getMessage("error.logout"));
		}
		return result;
	}
	*/
	
	/**
	 * 비밀번호 변경
	 * 
	 * @param request
	 * @param response : 비밀번호 변경 성공 여부
	 * @param userVO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/pwChange.do")
	public Map<String,Object> pwChange(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO userVO) throws Exception{
		Map<String,Object> result =new HashMap<String,Object>();
		try {
			result = mainService.pwChange(userVO);
		}catch(Exception e) {
			e.printStackTrace();
			result.put("message", MessageUtil.getMessage("error.pwChange"));
		}
		return result;
	}
}
