package com.cooking.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

import com.cooking.common.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertiesConfiguration {

	private static String root_path = "META-INF/config/";

	private static String _getProperties(String module, String key) throws Exception {
		Properties props = new Properties();
		InputStream in = null;
		try {
			File pFile = (new ClassPathResource(String.valueOf(root_path) + module)).getFile();
			in = new FileInputStream(pFile);
			props.load(in);
		} catch (Exception e) {
			log.error(CommonUtil.makeStackTrace(e));
		} finally {
			if (in != null)
				in.close();
		}
		return props.getProperty(key);
	}

	public static String getString(String module, String key) throws Exception {
		_getProperties(module, key);
		return _getProperties(module, key);
	}

	public static Integer getInteger(String module, String key, int defaultNum) {
		try {
			String reVal = _getProperties(module, key);
			return Integer.valueOf(Integer.parseInt(reVal));
		} catch (Exception e) {
			return Integer.valueOf(defaultNum);
		}
	}

	public static Long getLong(String module, String key) throws Exception {
		return new Long(_getProperties(module, key));
	}

	public static boolean getBoolean(String module, String key) throws Exception {
		String strval = _getProperties(module, key);
		if (strval == null)
			return false;
		return Boolean.parseBoolean(strval);
	}
}