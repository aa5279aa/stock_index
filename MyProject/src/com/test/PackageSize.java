package com.test;

import java.io.File;
import java.math.BigDecimal;

//计算包大小
public class PackageSize {

	public static void main(String[] args) {

		File file = new File(
				"D:\\develop_workspace\\git_warehouse\\android_2\\CtripMain\\tttt");
		File[] listFiles = file.listFiles();

		for (File f : listFiles) {
			float length = ((float) f.length() / 1024) / 1024;
			BigDecimal b = new BigDecimal(length);
			float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			System.out.println(f.getName().replace("ctrip_android_", "")
					.replace(".so", "")
					+ " = " + String.valueOf(f1) + "M");
		}
	}
}
