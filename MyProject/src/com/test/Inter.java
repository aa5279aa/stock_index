package com.test;

import com.test.Test.A;

public interface Inter {

	String s="1";//默认等于final声明了
	A a=new A();
	
	
	static class X{
		
		public void statcccc(){
			System.out.println("xx");
		}
		
	}
}
