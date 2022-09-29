package com.cooking.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtil {

	public static boolean isNumeric(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * yyyymmddhhmmss 형식의 string 문자를 yyyy-mm-dd hh:mm:ss 형식으로 변경합니다.
	 *
	 * @param dateTime
	 * @return
	 */
	public static String getStringToTimeString(String dateTime) {

		if (!isNumeric(dateTime)) {
			log.error("is not number!");
			return null;
		}

		if (dateTime.length() != 14) {
			log.error("the format does not match! (ex. yyyymmddhhmmss)");
			return null;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(dateTime.substring(0,4));
		sb.append("-");
		sb.append(dateTime.substring(4,6));
		sb.append("-");
		sb.append(dateTime.substring(6,8));
		sb.append(" ");
		sb.append(dateTime.substring(8,10));
		sb.append(":");
		sb.append(dateTime.substring(10,12));
		sb.append(":");
		sb.append(dateTime.substring(12,14));

		return sb.toString();
	}

	public static String nullToString(String data) {
		return (data == null)?"":data;
	}

	public static String nullToString(String data, String altData) {
		return (data == null)?"":altData;
	}

	public static int nullToInteger(Integer data) {
		return (data == null)?0:data;
	}

	public static int nullToInteger(Integer data, int altData) {
		return (data == null)?0:altData;
	}

	public static Map jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if(json != JSONObject.NULL)
        {
            retMap = toMap(json);
        }
        return retMap;
    }

	 public static Map toMap(JSONObject object) throws JSONException {
	        Map<String, Object> map = new HashMap<String, Object>();

	        Iterator<String> keysItr = object.keys();
	        while(keysItr.hasNext())
	        {
	            String key = keysItr.next();
	            Object value = object.get(key);

	            if(value instanceof JSONArray)
	            {
	                value = toList((JSONArray) value);
	            }

	            else if(value instanceof JSONObject)
	            {
	                value = toMap((JSONObject) value);
	            }
	            map.put(key, value);
	        }
	        return map;
	  }

	  public static List toList(JSONArray array) throws JSONException {
	        List<Object> list = new ArrayList<Object>();
	        for(int i = 0; i < array.length(); i++)
	        {
	            Object value = array.get(i);
	            if(value instanceof JSONArray)
	            {
	                value = toList((JSONArray) value);
	            }

	            else if(value instanceof JSONObject)
	            {
	                value = toMap((JSONObject) value);
	            }
	            list.add(value);
	        }
	        return list;
	  }

	  public static String getFileNameWithoutExtension(String fileName) {
		    int index = fileName.lastIndexOf(".");
		    return fileName.substring(0, index);
	  }

	  public static String getFileExtension(String fileName) {
		    int index = fileName.lastIndexOf(".");
		    return fileName.substring(index + 1);
	  }

	  public static int toInt(String str) {
		    int rtn = 0;
		    try {
		      if (str != null)
		        rtn = Integer.parseInt(str);
		    } catch (Exception e) {
		      rtn = 0;
		    }
		    return rtn;
	  }

	  public static int toInt(String str, int value) {
			int rtn = 0;
			try {
			  if (str != null)
			    rtn = Integer.parseInt(str);
			} catch (Exception e) {
			  rtn = value;
			}
			return rtn;
	  }
}
