package com.cooking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cooking.service.MainService;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequestMapping("/nice/main")
public class MainController{

	@Autowired
	private MainService mainService;

	/* 대쉬보드 조회 */
	@ResponseBody
	@RequestMapping(value = "/main.do")
	public String main(HttpServletRequest request, HttpServletResponse response){


		return "main";

	}
	
	
	
	
}
