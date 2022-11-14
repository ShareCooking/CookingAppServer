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
import com.cooking.service.LoginService;
import com.cooking.vo.UserVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/cooking/login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	
	/**
	 * 로그인
	 * 
	 * @param request 
	 * @param response : 로그인 성공여부
	 * @param userVO
	 * @return : Map
	 * @throws Exception
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/login.do")
	public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO userVO) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader = new HashMap<String,Object>();
		try {
			result = loginService.login(userVO);
		}catch(Exception e) {
			e.printStackTrace();
			paramheader.put("reCode","109");
			paramheader.put("reMsg",MessageUtil.getMessage("109"));
			result.put("header", paramheader);
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
	@RequestMapping(value = "/userCreate.do")
	public Map<String,Object> userCreate(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO userVO) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader = new HashMap<String,Object>();
		try {
			result = loginService.userCreate(userVO);
		}catch(Exception e) {
			e.printStackTrace();
			paramheader.put("reCode","129");
			paramheader.put("reMsg",MessageUtil.getMessage("129"));
			result.put("header", paramheader);
		}
		return result;
	}
	
	/**
	 * email중복검사	
	 * 
	 * @param request
	 * @param response email 사용가능 여부
	 * @param userVO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/emailCheck.do")
	public Map<String,Object> emailCheck(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO userVO) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader = new HashMap<String,Object>();
		try {
			result = loginService.emailCheck(userVO);
		}catch(Exception e) {
			e.printStackTrace();
			paramheader.put("reCode","139");
			paramheader.put("reMsg",MessageUtil.getMessage("139"));
			result.put("header", paramheader);
		}
		return result;
	}
	
	/*닉넴 중복검사*/
	
	@ResponseBody
	@RequestMapping(value = "/nicknameCheck.do")
	public Map<String,Object> nicknameCheck(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO userVO) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader = new HashMap<String,Object>();
		try {
			result = loginService.nicknameCheck(userVO);
		}catch(Exception e) {
			e.printStackTrace();
			paramheader.put("reCode","149");
			paramheader.put("reMsg",MessageUtil.getMessage("149"));
			result.put("header", paramheader);
		}
		return result;
	}
	
	/**
	 * 비밀번호 초기화/변경
	 * 
	 * @param request
	 * @param response : 비밀번호 초기화/변경 성공 여부
	 * @param userVO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/pwChange.do")
	public Map<String,Object> pwChange(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO userVO) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> paramheader = new HashMap<String,Object>();
		try {
			result = loginService.pwChange(userVO);
		}catch(Exception e) {
			e.printStackTrace();
			paramheader.put("reCode","159");
			paramheader.put("reMsg",MessageUtil.getMessage("159"));
			result.put("header", paramheader);
		}
		return result;
	}
}
