package com.cooking.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {
	public DateUtil(){
		super();
	}

	/**
     * 시스템의 현재시간 가져오기.
     */
	public static String nowTime(String timeformat) {
        SimpleDateFormat formatter = new SimpleDateFormat (timeformat);
        return formatter.format(new java.util.Date());
	}

	/**
     * 원하는 포맷으로 날짜스트링 리턴.
     */
	public static String dateToFormattedString0(java.util.Date d, String timeformat) {
        SimpleDateFormat formatter = new SimpleDateFormat (timeformat);
        String reStr = formatter.format(d);
        if(timeformat.equals("HH:mm")){
        	reStr = reStr.substring(0, 3) + "00";
        }
        return reStr;
	}

	public static String dateToFormattedString(java.util.Date d, String timeformat) {
        SimpleDateFormat formatter = new SimpleDateFormat (timeformat);
        return formatter.format(d);
	}

	/**
	 * dateString을 formatString형식으로 입력하면 Date반환
	 * @param dateString
	 * @param formatString
	 * @return
	 */
	public static Date getDate(String dateString, String formatString) {
        Date date = null;
        try{
			SimpleDateFormat formatter = new SimpleDateFormat(formatString);
			date = formatter.parse(dateString);
        }catch(Exception ex){
            log.error(CommonUtil.makeStackTrace(ex));
        }
        return date;
	}


	/**
     * 시스템의 요일 가져오기(월요일이면 1).
     */
	public static int nowWeek(){
		int year = Integer.parseInt(nowTime("yyyy"));
		int month = Integer.parseInt(nowTime("MM"));
		int day = Integer.parseInt(nowTime("dd"));
		int k = getDayOfWeek(year,month,day);

		return k;
	}

	/**
	 * 주어진 날이 해당하는 주의 마지막날과 2주전 날짜 가져오기
	 * weeklyReport.jsp용
	 * @param yyyyMMdd
	 * @return
	 */
	public static String[] getFromTo14(String yyyyMMdd){
		int year = Integer.parseInt(yyyyMMdd.substring(0, 4));
		int month = Integer.parseInt(yyyyMMdd.substring(4, 6));
		int day = Integer.parseInt(yyyyMMdd.substring(6, 8));
		int k = getDayOfWeek(year,month,day);

		Calendar cal = Calendar.getInstance();
    	cal.setTime(getDate(yyyyMMdd, "yyyyMMdd"));
    	cal.add(Calendar.DATE, 6-k);
    	String edate = dateToFormattedString(cal.getTime(), "yyyyMMdd");
    	cal.add(Calendar.DATE, -13);
    	String sdate = dateToFormattedString(cal.getTime(), "yyyyMMdd");
		String[] re = {sdate, edate};
		return re;
	}

	/**
	 * 현재일에 7일을 더해서 리턴
	 * @param yyyyMMdd
	 * @return
	 */
	public static String getNextWeekDay(String yyyyMMdd){
		Calendar cal = Calendar.getInstance();
    	cal.setTime(getDate(yyyyMMdd, "yyyyMMdd"));
    	cal.add(Calendar.DATE, 7);
		return dateToFormattedString(cal.getTime(), "yyyyMMdd");
	}
	/**
	 * 현재일에 7일을 빼서 리턴
	 * @param yyyyMMdd
	 * @return
	 */
	public static String getBeforeWeekDay(String yyyyMMdd){
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(yyyyMMdd, "yyyyMMdd"));
		cal.add(Calendar.DATE, -7);
		return dateToFormattedString(cal.getTime(), "yyyyMMdd");
	}

	/**
	 * 금주면 그대로 리턴, 전주면 7일 더해서 리턴
	 * weeklyReport.jsp용
	 * @param lastDay 기준이 되는 2주의 마지막날
	 * @param yyyyMMdd 비교일
	 * @return
	 */
	public static boolean isThisWeek(String lastDay, String yyyyMMdd){
		Date d = getDate(yyyyMMdd, "yyyyMMdd");
		long diff = (getDate(lastDay, "yyyyMMdd").getTime() - d.getTime()) /86400000;
		if(diff > 6)//전주
			return false;
		else
			return true;
	}

	public static String getNextDay(String yyyyMMdd){
		Calendar cal = Calendar.getInstance();
    	cal.setTime(getDate(yyyyMMdd, "yyyyMMdd"));
    	cal.add(Calendar.DATE, 1);
    	String next = dateToFormattedString(cal.getTime(), "yyyyMMdd");

		return next;
	}

	public static String getBeforeDay(String yyyyMMdd){
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(yyyyMMdd, "yyyyMMdd"));
		cal.add(Calendar.DATE, -1);
		String next = dateToFormattedString(cal.getTime(), "yyyyMMdd");

		return next;
	}

	public static boolean isLeapYear(int year) {
        if ((year%4==0)&&(year%100!=0)||(year%400==0)){
              return true;
        }
        else{
              return false;
        }
	}
	public static String getDayOfWeek_kor(String date){
		String[] week = new String[] {"일", "월", "화", "수", "목", "금", "토"};
		int week_num = getDayOfWeek(date);
		return week[week_num];
	}
	public static int getDayOfWeek(String date) {
		int year = StringUtil.toInt(date.substring(0,4));
		int month = StringUtil.toInt(date.substring(4,6));
		int day = StringUtil.toInt(date.substring(6,8));
        // 0~6의 값을 반환한다. 결과가 0이면 일요일이다.
        return convertDatetoDay(year, month, day)%7;
	}
	public static int getDayOfWeek(int year, int month, int day) {
        // 0~6의 값을 반환한다. 결과가 0이면 일요일이다.
        return convertDatetoDay(year, month, day)%7;
	}


	public static int convertDatetoDay(int year, int month, int day) {
        int numOfLeapYear =0;       // 윤년의 수

        // 전년도까지의 윤년의 수를 구한다.
        for(int i=0;i < year; i++) {
              if(isLeapYear(i))
                    numOfLeapYear++;
        }

        // 전년도까지의 일 수를 구한다.
        int toLastYearDaySum = (year-1) * 365 + numOfLeapYear;

        // 올해의 현재 월까지의 일수 계산
        int thisYearDaySum =0;
        //                               1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
        int[] endOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        for(int i=0; i < month-1; i++) {
              thisYearDaySum+=endOfMonth[i];
        }

        // 윤년이고, 2월이 포함되어 있으면 1일을 증가시킨다.
        if (month > 2 && isLeapYear(year)) {
              thisYearDaySum++;
        }

        thisYearDaySum+=day;

        return toLastYearDaySum+thisYearDaySum-1;
	}


	public static String getTimeStamp(int iMode) {
		String sFormat;
		// if (iMode == 1) sFormat = "E MMM dd HH:mm:ss z yyyy";   // Wed Feb 03 15:26:32 GMT+09:00 1999
		if (iMode == 1) sFormat = "MMMM dd, yyyy HH:mm:ss z";   // Jun 03, 2001 15:26:32 GMT+09:00
		else if (iMode == 2) sFormat = "MM/dd/yyyy";// 02/15/1999
		else if (iMode == 3) sFormat = "yyyyMMdd";// 19990215
		else if (iMode == 4) sFormat = "HHmmss";// 121241
		else if (iMode == 5) sFormat = "dd MMM yyyy";// 15 Jan 1999
		else if (iMode == 6) sFormat = "yyyyMMddHHMM"; //200101011010
		else if (iMode == 7) sFormat = "yyyyMMddHHmmss"; //20010101101052
		else if (iMode == 8) sFormat = "HHmmss";
		else if (iMode == 9) sFormat = "yyyy/MM/dd/HH/mm"; //200101011010
		else if (iMode == 10) sFormat = "yyyyMMddHHmmssSSS"; //200101011010000
		else if (iMode == 11) sFormat = "yyyy/MM/dd E HH:mm"; // 2010/11/11 Wed 09:00
		else sFormat = "E MMM dd HH:mm:ss z yyyy";// Wed Feb 03 15:26:32 GMT+09:00 1999

		Locale locale = new Locale("en", "EN");
		// SimpleTimeZone timeZone = new SimpleTimeZone(32400000, "KST");
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat, locale);
		// formatter.setTimeZone(timeZone);
		// SimpleDateFormat formatter = new SimpleDateFormat(sFormat);

		return formatter.format(new Date());
	}

	/*
		현재시간을 패턴에 맞는 스트링으로 리턴.
	*/
	public static final String formatDateString(String aPattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(aPattern);
		return sdf.format(new GregorianCalendar().getTime());
	}

	public static int getPartitionNum() {
		int part = 0;

		int year = Integer.parseInt(DateUtil.nowTime("yyyy"));
		int month = Integer.parseInt(DateUtil.nowTime("MM"));

		part = (year+1%2 == 0 ? 0 : 12) + month + 1;

		return part > 24 ? part-24 : part;

	}


    public static java.util.Calendar StrToDate(String date,String delim) {

        java.util.StringTokenizer token = new java.util.StringTokenizer(date, delim);

        java.util.Calendar cal = java.util.Calendar.getInstance();

        //각 필드별로 값들을 셋팅해줍니다.
        cal.set(java.util.Calendar.YEAR, Integer.parseInt(token.nextToken()));
        cal.set(java.util.Calendar.MONTH, Integer.parseInt(token.nextToken())-1);
        cal.set(java.util.Calendar.DATE, Integer.parseInt(token.nextToken()));
        cal.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(token.nextToken()));
        cal.set(java.util.Calendar.MINUTE, Integer.parseInt(token.nextToken()));
        return cal;
    }

    public static String DateToStr(java.util.Calendar cal) {
        StringBuffer str = new StringBuffer();

        str.append(cal.get(java.util.Calendar.YEAR));

        //월에 해당하는 값은 해당 일자 +1을 해줘야 해당 일자가 출력됨.
        if(cal.get(java.util.Calendar.MONTH)+1<10) str.append("0");
        str.append(cal.get(java.util.Calendar.MONTH)+1);

        if(cal.get(java.util.Calendar.DATE)<10) str.append("0");
        str.append(cal.get(java.util.Calendar.DATE));

        if(cal.get(java.util.Calendar.HOUR_OF_DAY)<10) str.append("0");
        str.append(cal.get(java.util.Calendar.HOUR_OF_DAY));

        if(cal.get(java.util.Calendar.MINUTE)<10) str.append("0");
        str.append(cal.get(java.util.Calendar.MINUTE));

        return str.toString();
    }
    public static String DateToStr2(java.util.Calendar cal) {
        StringBuffer str = new StringBuffer();

        str.append(cal.get(java.util.Calendar.YEAR));

        //월에 해당하는 값은 해당 일자 +1을 해줘야 해당 일자가 출력됨.
        if(cal.get(java.util.Calendar.MONTH)+1<10) str.append("0");
        str.append(cal.get(java.util.Calendar.MONTH)+1);

        if(cal.get(java.util.Calendar.DATE)<10) str.append("0");
        str.append(cal.get(java.util.Calendar.DATE));
        return str.toString();
    }

    /**
     * diffdate 와 nowdate 를 비교한다.
     * @param diffdate
     * @param nowdate
     * @return true : nowdate가 큰경우 , false : nowdate가 작은경우
     */
    public static boolean dateDiff(String diffdate, String nowdate){
    	boolean result = false;
    	Date nowday = getDate(nowdate,"yyyyMMddHHmmssSSS");
    	Date diffday = getDate(diffdate, "yyyyMMddHHmmssSSS");

		if(diffday.after(nowday)){
			result = true;
		}

		return result;

    }


    /**
     * unix시간을 yyyyMMddHHmmss형식의 String으로 리턴
     * @param unixTime
     * @return
     */
    public static String unixToString(long unixTime)
    {
        long tmpTime = unixTime * 1000;
        Date date = new Date(tmpTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

}
