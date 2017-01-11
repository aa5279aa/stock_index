package com.test.test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.test.util.HttpUtil;
import com.test.util.IOHelper;

//改成Map的形式，每次读取所有的。添加新的。间隔时间改成50秒一次。Map中有的不记录。没有的添加到Map当中
public class JudgmentTrend2 {

	// http://hq.sinajs.cn/list=sh601006
	static int[] Volume = new int[8];
	static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	static SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
	static SimpleDateFormat format3 = new SimpleDateFormat("MM-dd");
	static StringBuilder builder=new StringBuilder();
	
	static String outpath="D:\\workspace\\out";
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				Date mStart=new Date();
				mStart.setHours(9);
				mStart.setMinutes(30);
				mStart.setSeconds(0);
				
				Date mEnd=new Date();
				mEnd.setHours(11);
				mEnd.setMinutes(30);
				mEnd.setSeconds(59);
						
				Date aStart=new Date();
				aStart.setHours(13);
				aStart.setMinutes(0);
				aStart.setSeconds(0);
				
				Date aEnd=new Date();
				aEnd.setHours(15);
				aEnd.setMinutes(0);
				aEnd.setSeconds(59);
				
				
				String datestr = format3.format(aStart);
				File file=new File(outpath+File.separator+datestr+".txt");
				
				
				
				while (true) {
					
//					var hq_str_sh000001="上证指数,3609.959,3664.291,3507.744,3652.837,3490.540,0,0,369920479,450616485183,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2015-08-21,15:04:04,00";
					String url = "http://hq.sinajs.cn/list=sh000001";

					String sendGet = HttpUtil.sendGet(url);
					String[] split = sendGet.split(",");

					String openIndex = split[1];// 开盘指数
					String yesIndex = split[2];// 昨天收盘指数
					String nowIndex = split[3];// 当前指数
					String maxIndex = split[4];// 最高指数
					String minIndex = split[5];// 最低指数
					long volume = Long.parseLong(split[9]);
					String time = split[31];

					try {
						Date parse = format.parse(time);

						int hours = parse.getHours();
						int minutes = parse.getMinutes();
						int v = (int) (volume / 100000000);
//						if(do8(new int[]{9,30}, new int[]{11,30})||do8(new int[]{13,0},new int[]{15,0})){
						
						Calendar instance = Calendar.getInstance();
						instance.set(Calendar.HOUR_OF_DAY, hours);
						instance.set(Calendar.MINUTE, minutes);
						Date time2 = instance.getTime();
						
						String formatstr = format2.format(parse);
						String showstr=null;
						if(time2.after(mStart)&&time2.before(mEnd)){
							showstr=show(formatstr, hours, minutes, v, nowIndex);
						}else if(time2.after(aStart)&&time2.before(aEnd)){
							showstr=show(formatstr, hours, minutes, v, nowIndex);
						}else{
							
						}
						
						if(showstr!=null){
							//输出
							System.out.println(showstr);
							//保存
							IOHelper.writerStrByCodeToFile(file, "utf-8", true, showstr+"\n");
						}

						if(time2.after(aEnd)){
							//大于则跳出循环
							break;
						}
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					
					try {
						Thread.sleep(60 * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	
	public static String show(String parse,int hours,int minutes,int v,String nowIndex){
		
		String str="time:" + parse + ",成交量:" + v + "亿," + "指数:" + nowIndex;
			
		if (hours == 10 && minutes == 0) {
			Volume[0] = v;
		} else if (hours == 10 && minutes == 30) {
			Volume[0] = v;
		} else if (hours == 11 && minutes == 0) {
			Volume[0] = v;
		} else if (hours == 11 && minutes == 30) {
			Volume[0] = v;
		} else if (hours == 13 && minutes == 30) {
			Volume[0] = v;
		} else if (hours == 14 && minutes == 0) {
			Volume[0] = v;
		} else if (hours == 14 && minutes == 30) {
			Volume[0] = v;
		} else if (hours == 15 && minutes == 0) {
			Volume[0] = v;
		} else {

		}
		return str;
	}
	
	public static boolean do8(int[] star,int[] end){
	    SimpleDateFormat localTime=new SimpleDateFormat("HH:mm");
	    try{
	        Date date=new Date();
	        Date edate=date;
	        edate.setHours(end[0]);
	        edate.setMinutes(end[1]);
	        
	        Date sdate=date;
	        sdate.setHours(star[0]);
	        sdate.setMinutes(star[1]);
	        
	        if(date.after(sdate)&& date.before(edate)){
	            return true;
	        }
	    }catch(Exception e){
	    	
	    }
	    return false;
	}
}
