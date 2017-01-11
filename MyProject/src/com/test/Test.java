package com.test;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.text.html.ImageView;

public class Test {

	static int volum = 1;
	public static final int TRANSIT_ENTER_MASK = 0x100;

	public static final int MAX_VALUE = 0x7FFFFFFF;
	public static final int MIN_VALUE = 0x80000000;

	static List<String> list;

	static int i = 0__10_0_0;
	static int i2 = 01000;
	static int i3 = 0x10_0_0;

	public static void main(String[] args) {

		int i1 = 83;
		int i2 = 201;
		int i3 = 212;
		
		System.out.print(Integer.toString(i1, 16));
		System.out.print(Integer.toString(i2, 16));
		System.out.print(Integer.toString(i3, 16));

	}

	public static boolean ifTrue(String str) {
		if (str.contains("0米") && !str.contains(".0米")) {
			return false;
		}
		return true;
	}

	// public static boolean ifTT(String str){
	// return str.contains(".0米");
	// }

	static class A {
		public A() {
			// TODO Auto-generated constructor stub
		}
	}

	static class B extends A {
		public B() {
			super();
			// TODO Auto-generated constructor stub
		}
	}

	abstract class AA {

		public AA() {
			// TODO Auto-generated constructor stub
		}

	}

}
