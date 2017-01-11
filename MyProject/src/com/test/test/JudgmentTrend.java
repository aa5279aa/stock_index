package com.test.test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.test.util.HttpUtil;
import com.test.util.IOHelper;

//改成Map的形式，每次读取所有的。添加新的。间隔时间改成50秒一次。Map中有的不记录。没有的添加到Map当中
//急跌的话下跌幅度要更大
public class JudgmentTrend {

	// http://hq.sinajs.cn/list=sh601006
	static int[] Volume = new int[8];
	static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	static SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
	static SimpleDateFormat format3 = new SimpleDateFormat("MM-dd");
	static StringBuilder builder=new StringBuilder();
	
	static int last_volume;//上一分钟的量能
	static String outpath="D:\\develop_workspace\\out";
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
					
					// "��ָ֤��,3654.778,3775.912,3588.971,3750.570,3588.024,0,0,304903165,313785738174,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2015-07-07,11:07:19,00"
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
						//计算当前这分钟的量能
						int volum = v-last_volume;//当前这分钟的量能
						//记录
						last_volume=v;
						
						
						String formatstr = format2.format(parse);
						String showstr=null;
						if(time2.after(mStart)&&time2.before(mEnd)){
							showstr=show(formatstr, hours, minutes, v, nowIndex,volum);
						}else if(time2.after(aStart)&&time2.before(aEnd)){
							showstr=show(formatstr, hours, minutes, v, nowIndex,volum);
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

	
	public static String show(String parse,int hours,int minutes,int v,String nowIndex,int volum){
		
		String str = "time:" + parse + ",成交量:" + v + "亿,量能" + volum + ",指数:" + nowIndex;
			
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
