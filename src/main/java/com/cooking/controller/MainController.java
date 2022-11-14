package com.cooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cooking.service.MainService;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequestMapping("/cooking/main")
public class MainController{

	@Autowired
	private MainService mainService; 
	
	//게시물 확인
	
	//게시물 작성
	
	//좋아요 누르기
	
	//댓글/대댓글 작성
	
}
