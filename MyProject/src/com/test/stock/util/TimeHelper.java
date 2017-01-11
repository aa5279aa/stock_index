package com.test.stock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeHelper {
	
	static SimpleDateFormat format_HHmm = new SimpleDateFormat("HH:mm",
			Locale.CHINA);
	static SimpleDateFormat format_MM_dd_HH_mm = new SimpleDateFormat(
			"MM-dd HH:mm", Locale.CHINA);
	static SimpleDateFormat format_yyyy_MM_dd = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.CHINA);
	static SimpleDateFormat format_yyyy_MM_dd_HH_mm = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm", Locale.CHINA);
	static SimpleDateFormat format_yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.CHINA);

	public static String getHH_mm(long dateLong) {
		Date date = new Date(dateLong);
		String str = format_HHmm.format(date);
		return str;
	}
	
	public static Date getHH_mm(String timestr) {
		Date date = null;
		try {
			date = format_HHmm.parse(timestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static String getMM_dd_HH_mm(long dateLong) {
		Date date = new Date(dateLong);
		String str = format_yyyy_MM_dd_HH_mm.format(date);
		return str;
	}

	public static Date getDateByHH_mm(String datestr) {
		try {
			Date parse = format_MM_dd_HH_mm.parse(datestr);
			parse.setYear(new Date().getYear());
			return parse;
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public static String getyyyy_MM_dd(long dateLong) {
		Date date = new Date(dateLong);
		String str = format_yyyy_MM_dd.format(date);
		return str;
	}
	
	public static String getyyyy_MM_dd() {
		Date date = new Date(System.currentTimeMillis());
		String str = format_yyyy_MM_dd.format(date);
		return str;
	}

	public static Date getDateByyyyy_MM_dd(String datestr) {
		try {
			Date parse = format_yyyy_MM_dd.parse(datestr);
			return parse;
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public static String getyyyy_MM_dd_HH_mm(long dateLong) {
		Date date = new Date(dateLong);
		String str = format_yyyy_MM_dd_HH_mm.format(date);
		return str;
	}

	public static Date getDateByyyyy_MM_dd_HH_mm(String datestr) {
		try {
			Date parse = format_yyyy_MM_dd_HH_mm.parse(datestr);
			return parse;
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public static String getyyyy_MM_dd_HH_mm_ss(long dateLong) {
		Date date = new Date(dateLong);
		String str = format_yyyy_MM_dd_HH_mm_ss.format(date);
		return str;
	}

	public static Date getDateByyyyy_MM_dd_HH_mm_ss(String datestr) {
		try {
			Date parse = format_yyyy_MM_dd_HH_mm_ss.parse(datestr);
			return parse;
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public static String getyyyyy_MM_ddByDate(Date date) {

		String format = format_yyyy_MM_dd.format(date);
		return format;
	}

	/**
	 * 获取是星期几
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek < 0)
			dayOfWeek = 0;
		return dayOfWeek;
	}
	
	/**
	 * 获取 月日 格式
	 * @param date
	 * @return
	 */
	public static String getDate(Date date) {
		SimpleDateFormat format=new SimpleDateFormat("MMdd",Locale.CHINA);
		String format2 = format.format(date);
		return format2;
	}
	
	/**
	 * 获取是一个月的第几个星期
	 * @return
	 */
	public static int getWeekNum(Date date){
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		int i=instance.get(Calendar.WEEK_OF_YEAR);
		
		return i%2;
	}
	
	public static long getTimeByHourAndMinute(int hour,int minute){
		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date());
		instance.set(instance.YEAR, instance.MONTH, instance.DAY_OF_YEAR, hour, minute);
		
		return instance.getTime().getTime();
	}
	
	public static int[] getHourAndMinute(long date){
		int[] time=new int[2];
		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date(date));
		
		time[0]=instance.get(Calendar.HOUR_OF_DAY);
		time[1]=instance.get(Calendar.MINUTE);
				
		return time;
	}
	
	public static Date getLastMonday(){
		Calendar instance=Calendar.getInstance();
		instance.setTimeInMillis(System.currentTimeMillis());
		while (instance.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY){
			instance.setTimeInMillis(instance.getTimeInMillis()-24*3600*1000);
			System.out.println(instance.getTime());
		}
		return instance.getTime();
	}
	
	public static int getWaitMinutes(int starthour,int startminute) {
		int[] time = TimeHelper.getHourAndMinute(System.currentTimeMillis());
		int now_hour = time[0];
		int now_minute = time[1];

		int difference_hour = starthour - now_hour;
		int difference_minute;

		if (difference_hour > 0) {
			difference_minute = difference_hour * 60 + startminute
					- now_minute;
		} else if (difference_hour == 0) {
			difference_minute = startminute - now_minute;
			difference_minute = difference_minute >= 0 ? difference_minute
					: difference_minute + 60;
		} else {
			difference_hour += 24;
			difference_minute = difference_hour * 60 + startminute
					- now_minute;
		}
		return difference_minute;
	}
	
	/**
	 * 判断是否属于新的一个小时
	 * @return
	 */
	public static int getNewHour(long lasttime){
		long currentTimeMillis = System.currentTimeMillis();
		int[] last_times = TimeHelper.getHourAndMinute(lasttime);
		int last_hour = last_times[0];
		int last_minute = last_times[1];
		
		
		int[] now_times = TimeHelper.getHourAndMinute(currentTimeMillis);
		int now_hour = now_times[0];
		int now_minute = now_times[1];
		
		if(now_hour==last_hour){
			long l = currentTimeMillis-lasttime;
			if(l<3600000){
				int wait=(now_minute-last_minute)*60*000;
				return wait>0?wait:0;
			}
		}
		return 0;
	}
	
}

