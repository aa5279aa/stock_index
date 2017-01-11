package com.test;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.test.util.IOHelper;
import com.test.valuerepeat.RepateEntity;

public class Test6 {

	public static void main(String[] args) {
		final List<RepateEntity> list=new ArrayList<>();
		final int max = 10000000;
		
		System.out.println(max);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				System.out.println("XX1");
				for(int i=0;i<max;i++){
					list.add(new RepateEntity());
				}
				System.out.println("XX2"+max);
				for(int i=0;i<max;i++){
					list.add(new RepateEntity());
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("XX3");
				}
				System.out.println("XX4");
				
			}
		}).start();
		
	}

	public void test(){
		InputStream fromFileToIputStream = IOHelper
				.fromFileToIputStream("d://t.txt");
		String fromIputStreamToString = IOHelper
				.fromIputStreamToString(fromFileToIputStream);
		Pattern compile = Pattern.compile("(?s).*?Date\\((.*?)\\-.*?");
		Matcher matcher = compile.matcher(fromIputStreamToString);
		while (matcher.find()) {
			String group = matcher.group(1);
			System.out.println(group);
			if(group.length()=="1473793140000".length()){
				Date date = new Date(Long.parseLong(group));
				System.out.println(formatDate(date));
			}
		}
	}
	public static String formatDate(Date date){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format2 = format.format(date);
		return format2;
	}
	
}
