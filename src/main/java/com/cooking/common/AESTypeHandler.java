package com.cooking.common;

import java.security.GeneralSecurityException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.NoSuchPaddingException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.cooking.common.util.AES256Util;
import com.cooking.common.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 1.개요 :
 * </pre>
 *
 * @Author  : baek
 * @Date    : 2016. 4. 29.
 * @Version :
 */
@Slf4j
public class AESTypeHandler implements TypeHandler<String> {
	public String getResult(ResultSet arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		String retValue = null;

		try {
			if(arg1 != null) {
				byte[] chars = arg0.getBytes(arg1);

				if(chars != null) {
					retValue = AES256Util.aesDecode(new String(chars));
				}
			}
		} catch (NoSuchPaddingException e) {
			log.error(CommonUtil.makeStackTrace(e));
		} catch (GeneralSecurityException e) {
			retValue = new String(arg0.getBytes(arg1));
			log.error(CommonUtil.makeStackTrace(e));
		} catch(Exception e) {
			log.error(CommonUtil.makeStackTrace(e));
		}

		return retValue;
	}

	public String getResult(ResultSet arg0, int arg1) throws SQLException {
		String retValue = null;

		try {
			retValue = AES256Util.aesDecode(new String(arg0.getBytes(arg1)));
		} catch (NoSuchPaddingException e) {
			log.error(CommonUtil.makeStackTrace(e));
		} catch (GeneralSecurityException e) {
			log.error(CommonUtil.makeStackTrace(e));
		} catch(Exception e) {
			log.error(CommonUtil.makeStackTrace(e));
		}

		return retValue;
	}

	public String getResult(CallableStatement arg0, int arg1) throws SQLException {
		String retValue = null;

		try {
			retValue = AES256Util.aesDecode(new String(arg0.getBytes(arg1)));
		} catch (NoSuchPaddingException e) {
			log.error(CommonUtil.makeStackTrace(e));
		} catch (GeneralSecurityException e) {
			log.error(CommonUtil.makeStackTrace(e));
		} catch(Exception e) {
			log.error(CommonUtil.makeStackTrace(e));
		}

		return retValue;
	}

	public void setParameter(PreparedStatement arg0, int arg1, String arg2, JdbcType arg3) throws SQLException {
		String password = null;

		if (arg2 != null) {
			try {
				password = AES256Util.aesEncode(arg2);
			} catch (NoSuchPaddingException e) {
				log.error(CommonUtil.makeStackTrace(e));
			} catch (GeneralSecurityException e) {
				log.error(CommonUtil.makeStackTrace(e));
			} catch(Exception e) {
				log.error(CommonUtil.makeStackTrace(e));
			}
		}

		arg0.setString(arg1, password);
	}
}
