package com.cooking.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	public static final String FORMAT_DEFAULT = "yyyy.MM.dd HH:mm";
	public static final String FORMAT_DOT_YYYYMMDD = "yyyy.MM.dd";
	public static final String FORMAT_YYYYMMDDHHMMSSMMM = "yyyy.MM.dd HH:mm:ss.SSS";
	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyy.MM.dd HH:mm:ss";
	public static final String FORMAT_YYYYMMDD = "yyyy-MM-dd";
	public static final String FORMAT_YYYYMMDD_TRIM = "yyyyMMdd";
	public static final String FORMAT_MD = "M/d";
	public static final String FORMAT_HH = "HH";
	public static final String FORMAT_TIMESTAMP = "yyyyMMddHHmmssSSS";

	/**
	 * 두시간의 차이를 millisecond 단위로 반환하는 함수
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long subtract(Date startTime, Date endTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);

		long start = calendar.getTimeInMillis();
		calendar.setTime(endTime);
		long end = calendar.getTimeInMillis();

		return end - start;
	}

	/**
	 * calendar 변수에서 지정하는 단위 이하를 버려서 반환하는 함수
	 * @param date
	 * @param calendar ( Calendar.DATE/Calendar.HOUR/Calendar.MINUTE)
	 * @return
	 * @throws ParseException
	 */
	public static Date floor(Date date, int calendar) throws ParseException {
		DateFormat df =  null;
		DateFormat parseDf = new SimpleDateFormat("yyyyMMddHHmmss");

		if(calendar == Calendar.DATE) {
			df = new SimpleDateFormat("yyyyMMdd000000");
		}else if(calendar == Calendar.HOUR) {
			df = new SimpleDateFormat("yyyyMMddHH0000");
		}else if(calendar == Calendar.MINUTE) {
			df = new SimpleDateFormat("yyyyMMddHHmm00");
		}else {
			df = new SimpleDateFormat("yyyyMMddHHmmss");
		}
		return parseDf.parse(df.format(date));
	}

	/**
	 * now 에 입력된 시간을 기준으로 i 만큼 떨어진 시간을 반환.
	 * 이때 date 변수는 차이나는 값의 단위를 나타내며, Calendar.MINUTE/Calendar.HOUR/.. 등의 값
	 * @param date
	 * @param unit
	 * @param i ,
	 * @return
	 */
	public static Date addTime(Date date, int unit, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(unit, i);
		return calendar.getTime();
	}

	/**
	 * 현재날짜 입력받은 포맷으로 리턴
	 * @param dateFmt
	 * @return
	 */
	public static String curDateFmt(String dateFmt) {

		DateFormat fmt = new SimpleDateFormat(dateFmt);
		Date curDate = new Date();

		return fmt.format(curDate);
	}

	/**
	 * Date 타입 날짜 String 포맷으로 리턴
	 * @param Date
	 * @return
	 */
	public static String toDateString(Date value) {
		return toDateString(value, FORMAT_DEFAULT);
	}

	public static String toDateString(Date value, String formatText) {
		if (value != null) {
			return (new SimpleDateFormat(formatText)).format(value);
		}
		return "";
	}
	
	public static String sysDt() {

		SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyyMMddHHmmss");
		Date time = new Date(); 
		String result = dateFormat.format(time); 
		
		return result;
		
	}
}
