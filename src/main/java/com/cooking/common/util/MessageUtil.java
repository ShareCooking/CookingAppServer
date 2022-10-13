package com.cooking.common.util;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtil {
	private static MessageSourceAccessor msgAccessor;
	
	@SuppressWarnings("static-access")
	public void setMessageSourceAccessor(MessageSourceAccessor msgAccessor) {
		this.msgAccessor = msgAccessor;
	}
	
	public static String getMessage(String key) {
		return msgAccessor.getMessage(key, Locale.KOREAN);
	}
	
	public static String getMessage(String key, Object[] objs) {
		return msgAccessor.getMessage(key, objs, Locale.KOREAN);
	}
}
