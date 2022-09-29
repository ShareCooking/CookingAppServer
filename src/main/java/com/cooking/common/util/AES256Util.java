package com.cooking.common.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES256Util {
	
	private static volatile AES256Util INSTANCE;

	private static String secretKey = "J3K4N6P7Q8SATBUDWEXFZH2J3M5N6P8R";
	static String IV = "";
	
	public static AES256Util getInstance() {
		if(INSTANCE == null) {
			synchronized (AES256Util.class) {
				if (INSTANCE == null) {
					INSTANCE = new AES256Util();
				}
			}
		}
		return INSTANCE;		
	}
	
	private AES256Util() {
		IV = "TBUCVEXFYGZJ3K4M";
	}
	
	/**
	 * 
	 * <pre>
	 * 1.개요     : 암호화
	 * 2.처리내용 : 입력 정보를 암호화
	 * </pre>
	 *
	 * @Method Name : aesEncode
	 * @param str
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String aesEncode(String str) throws java.io.UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		
		byte[] keyData = secretKey.getBytes();
		
		SecretKey secureKey = new SecretKeySpec(keyData, "AES");
		
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));

		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String enStr = new String(Base64.encodeBase64(encrypted));

		return enStr;
	}

	/**
	 * 
	 * <pre>
	 * 1.개요     : 복호화
	 * 2.처리내용 : 입력 정보를 복호화
	 * </pre>
	 *
	 * @Method Name : aesDecode
	 * @param str
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String aesDecode(String str) throws java.io.UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		
		byte[] keyData = secretKey.getBytes();
		SecretKey secretKey = new SecretKeySpec(keyData, "AES");
		
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(IV.getBytes("UTF-8")));
		
		byte[] byteStr = Base64.decodeBase64(str.getBytes());
		
		return new String(c.doFinal(byteStr), "UTF-8");
		
	}
}
