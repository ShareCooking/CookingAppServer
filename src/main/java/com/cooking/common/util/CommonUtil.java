package com.cooking.common.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {

	/**
	 * SHA512 암호화 모듈
	 *
	 * @param data
	 * @return
	 */
	public static String encryptSHA512(String data) {
		if (data == null)
			return null;

		String m_StrAfterData = "";

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-512");
			sha.update(data.getBytes());
			StringBuffer sb = new StringBuffer();
			for (byte b : sha.digest()) {
				sb.append(Integer.toHexString(0xff & b));
			}
			m_StrAfterData = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			m_StrAfterData = "";
			throw new RuntimeException(e);
		}

		return m_StrAfterData;
	}

	/**
	 * SHA256 Encode
	 *
	 * @param plainText
	 * @return
	 */
	public static String getSha256(String plainText) {
		if (plainText == null)
			return null;

		String sha256 = "";

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] bytes = plainText.getBytes();
			md.update(bytes);

			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			sha256 = sb.toString();
		} catch (Exception e) {
			sha256 = "";
			throw new RuntimeException(e);
		}

		return sha256;
	}

	/**
	 * e.printStackTrace() 문자로 변환
	 *
	 * @param t
	 * @return
	 */
	public static String makeStackTrace(Throwable t) {
		if (t == null)
			return "";

		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			t.printStackTrace(new PrintStream(bout));
			bout.flush();

			return new String(bout.toByteArray());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 방화벽이나 클라우드로 운영하는 경우 클라이언트의 원 IP주소를 가져올 수 없을 때 다음 함수 사용
	 *
	 * @param request
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		log.debug("[Client IP] X-FORWARDED-FOR : {}", ip);

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
			log.debug("[Client IP] Proxy-Client-IP : {}", ip);
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			log.debug("[Client IP]  WL-Proxy-Client-IP : {}", ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			log.debug("[Client IP] HTTP_CLIENT_IP : {}", ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			log.debug("[Client IP] HTTP_X_FORWARDED_FOR : {}", ip);
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
			log.debug("[Client IP] getRemoteAddr : {}", ip);
		}
		log.debug("[Client IP] Result : IP Address : {}", ip);

		return ip;
	}

	public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
		Map<String, Object> retMap = new HashMap<String, Object>();

		if (json != JSONObject.NULL) {
			retMap = toMap(json);
		}
		return retMap;
	}

	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keys();
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

	

	/**
	 * OS Winodws 여부
	 *
	 * @return
	 */
	private static boolean chkWindows() {
		String osName = System.getProperty("os.name");
		boolean rtnFlag = false;

		if (osName.toUpperCase().indexOf("WINDOWS") != -1)
			rtnFlag = true;

		return rtnFlag;
	}

	/**
	 * JSON 형태의 문자열 JSONObject로 변환
	 *
	 * @param input
	 * @return
	 */
	public static JSONObject toJSON(String input) {
		return new JSONObject(input);
	}

	/**
	 * JSONObject에서 문자열 값 찾기
	 *
	 * @param key
	 * @param jsonObject
	 * @return
	 */
	public static String getJsonStr(String key, JSONObject jsonObject) {
		return jsonObject.getString(key);
	}

	/**
	 * JSONObject에서 JSONObject 값 찾기
	 *
	 * @param key
	 * @param jsonObject
	 * @return
	 */
	public static JSONObject getJsonObj(String key, JSONObject jsonObject) {
		return jsonObject.getJSONObject(key);
	}

	/**
	 * JSONObject에서 JSONArray 값 찾기
	 *
	 * @param key
	 * @param jsonObject
	 * @return
	 */
	public static JSONArray getJsonArry(String key, JSONObject jsonObject) {
		return jsonObject.getJSONArray(key);
	}

	public static String getDate(long time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		Date xDate = new Date(time);
		String xStrDate = formatter.format(xDate);
		return xStrDate.trim();
	}

	public static String getDateTime(long time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Date xDate = new Date(time);
		String xStrDate = formatter.format(xDate);
		return xStrDate.trim();
	}

	public static String byteCalculation(String bytes) {

		String retFormat = "0";
		Double size = Double.parseDouble(bytes);

		String[] s = { "bytes", "KB", "MB", "GB", "TB", "PB" };

		if (!bytes.equals("0")) {
			int idx = (int) Math.floor(Math.log(size) / Math.log(1024));
			DecimalFormat df = new DecimalFormat("#,###.##");
			double ret = ((size / Math.pow(1024, Math.floor(idx))));
			retFormat = df.format(ret) + " " + s[idx];
		} else {
			retFormat += " " + s[0];
		}

		return retFormat;
	}
}
