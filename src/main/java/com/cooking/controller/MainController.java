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
	
	
	/*게시물 확인*/
	
	/*게시물 작성*/
	@ResponseBody
	@RequestMapping(value = "/bbsCreate.do")
	public Map<String,Object> bbsCreate(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO userVO) throws Exception{
		Map<String,Object> paramheader =new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			result = mainService.bbsCreate(userVO);
		}catch(Exception e) {
			e.printStackTrace();
			paramheader.put("reCode","109");
			paramheader.put("reMsg", MessageUtil.getMessage("109"));
			result.put("header", paramheader);
		}
		return result;
	}
	
	
	/*좋아요 누르기*/
	
	/*댓글,대댓글 작성*/
}
