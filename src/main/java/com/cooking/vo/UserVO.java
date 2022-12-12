package com.cooking.vo;

import lombok.Data;

@Data
public class UserVO {
	
	private String user_seq;		//시퀀스
	
	private String user_email_id;	//이메일 아이디
	private String user_email_addr;	//이메일 주소
	private String user_pw;			//pw
	
	private String user_nickname;	//닉네임
	private String user_addr1;		//주소1 (시,군,구)
	private String user_addr2;		//주소2 (읍,면,동,리)
	private String user_addr3;		//주소3 (상세주소) 
	private String user_tel1;		//전화번호 앞 3자리
	private String user_tel2;		//전화번호 중간 3~4자
	private String user_tel3;		//전화번호 끝 4자리
	private String user_btd;		//생년월일

	private String user_pw_new;		//새로운 pw1
	private String user_pw_old1;	//과거pw1
	private String user_pw_old2;	//과거pw2
	private String user_pw_old3;	//과거pw3
	private String user_pw_old4;	//과거pw4
	private String user_pw_old5;	//과거pw5
	
	private String user_token;		//token
	private String user_history;
}
