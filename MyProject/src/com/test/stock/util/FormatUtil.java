package com.test.stock.util;

import java.text.DecimalFormat;

public class FormatUtil {

	public static String formatDouble2(double d){
		String sal = new DecimalFormat("#.00").format(d);
	    return sal;
	}
}
