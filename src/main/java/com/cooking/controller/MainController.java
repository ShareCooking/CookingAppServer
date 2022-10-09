package com.cooking.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	/*로그인*/
	@ResponseBody
	@RequestMapping(value = "/login.do")
	public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response, UserVO userVO) throws Exception{
		
		System.out.println(userVO);
		Map<String,Object> result = mainService.login(userVO);
		
		
		return result;
	}
}
