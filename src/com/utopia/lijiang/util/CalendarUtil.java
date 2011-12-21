package com.utopia.lijiang.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtil {

	public static Calendar getTimeAfterInSecs(int secs){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, secs);
		return cal;
	}
	
	public static Calendar getCurrentTime(){
		Calendar cal = Calendar.getInstance();
		return cal;
	}
	
	public static Calendar getTodayAt(int hours){
		Calendar today =  Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.clear();
		
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH);
		int day = today.get(Calendar.DATE);
		
		cal.set(year,month,day,hours,0,0);
		return cal;
	}
	
	public static String getDateTimeString(Calendar cal){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		df.setLenient(false);
		String s = df.format(cal.getTime());
		return s;
	}
}
