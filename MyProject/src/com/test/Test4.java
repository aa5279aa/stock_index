package com.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class Test4 {

	static boolean flag = true;
	static int time = 1;

	public static void main(String[] args) {
		String path = "CtripMain/build.gradle";
		path = path.replaceAll("/","\\\\");
		System.out.println(path);
		
	}

	public static void execCMD(String cmd, File execFile) {
		Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
		try {
			Process p = run.exec(cmd, null, execFile);

			// 检查命令是否执行失败。
			if (p.waitFor() != 0) {
				if (p.exitValue() == 1)// p.exitValue()==0表示正常结束，1：非正常结束
					System.err.println("命令执行失败!");
			}
			showProcessResult(p);

			// 如果成功，则继续执行
			// System.out.println("执行dir命令");
			// String[] strs = new String[] {"cmd.exe", "/c","dir"};
			// Process p2 = run.exec(strs);
			// if (p.waitFor() != 0) {
			// if (p.exitValue() == 1)// p.exitValue()==0表示正常结束，1：非正常结束
			// System.err.println("命令执行失败!");
			// }
			// showProcessResult(p2);
			p.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String showProcessResult(Process p) {
		StringBuilder builder = new StringBuilder();
		InputStream errorStream = p.getErrorStream();
		BufferedInputStream error = new BufferedInputStream(errorStream);
		BufferedReader errorBr = new BufferedReader(
				new InputStreamReader(error));
		String lineStr = "";
		try {
			while ((lineStr = errorBr.readLine()) != null) {
				System.out.println(lineStr);// 打印输出信息
				builder.append(lineStr + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (lineStr != null && lineStr.length() > 0) {
			return lineStr;
		}

		BufferedInputStream in = new BufferedInputStream(p.getInputStream());
		BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

		try {
			while ((lineStr = inBr.readLine()) != null) {
				// 获得命令执行后在控制台的输出信息
				System.out.println(lineStr);// 打印输出信息
				builder.append(lineStr + "\n");
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				inBr.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return builder.toString();
	}

}
