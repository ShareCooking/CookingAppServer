package com.cooking.common.util;

import java.net.InetAddress;

import org.springframework.stereotype.Component;

import com.cooking.common.PropertiesConfiguration;

@Component
public class ServerUtil {
	
	private String currServerIp = null; // 현재 서버 IP
	private String serverRealIp = null; //운영서버

	public boolean serverChk() throws Exception {
		currServerIp = InetAddress.getLocalHost().getHostAddress(); // 현재 서버 IP
		serverRealIp = PropertiesConfiguration.getString("config.properties","serverRealIp");
		
		System.out.println("currServerIp============================>"+currServerIp.toString());
			return true;
	}
}
