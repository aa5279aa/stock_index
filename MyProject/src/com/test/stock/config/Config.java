package com.test.stock.config;

public class Config {

	public static final String path = "D:\\develop_workspace\\out\\01-12.txt";// 文件路径地址
	public static final String date = path.substring(path.lastIndexOf("\\")+1,
			path.lastIndexOf("."));// 文件路径地址
	public static final int startRange_HOUR = 13;
	public static final int startRange_MINUTE = 00;
	public static final int endRange_HOUR = 15;
	public static final int endRange_MINUTE = 00;

	
	public static final int minute_interval=1;
	
}
