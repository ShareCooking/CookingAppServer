package com.cooking.vo;

import lombok.Data;

@Data
public class UserVO {
	
	private String user_seq;		//사용자 시퀀스(고유번호)
	
	private String user_id;			//사용자 ID
	private String user_pw;			//현재 패스워드
	private String user_pw_new;		//새로운 패스워드
	private String user_history;	//마지막 로그인 일시
	
	private String user_nickname;	//닉네임
	private String user_addr1;		//주소1 (시,군,구)
	private String user_addr2;		//주소2 (읍,면,동,리)
	private String user_addr3;		//주소3 (상세주소)
	private String user_tel1;		//전화번호 앞 3자리
	private String user_tel2;		//전화번호 중간 3~4자리
	private String user_tel3;		//전화번호 마지막 4자리
	private String user_btd;		//생년월일 (YYYYMMDD)
	
	private String user_pw_old1;	//현재 비밀번호
	private String user_pw_old2;	//과거 비밀번호2
	private String user_pw_old3;	//과거 비밀번호3
	private String user_pw_old4;	//과거 비밀번호4
	private String user_pw_old5;	//과거 비밀번호5
	
	private String user_token;		//토큰
	
	
}
