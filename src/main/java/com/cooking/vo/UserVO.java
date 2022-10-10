package com.cooking.vo;

import lombok.Data;

@Data
public class UserVO {
	
	private String user_id;
	private String user_pw;
	
	private String user_nickname;
	private String user_addr1;
	private String user_addr2;
	private String user_addr3;
	private int user_tel1;
	private int user_tel2;
	private int user_tel3;
	private int user_btd;
	

	private String user_pw_old1;
	private String user_pw_old2;
	private String user_pw_old3;
	private String user_pw_old4;
	private String user_pw_old5;
	
}
