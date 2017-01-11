package com.test.stock.util;

public class MathUtil {

	public static double getAverage(double[] array) {
		double sum = 0d;
		for (double d : array) {
			sum += d;
		}
		return sum / array.length;
	}
}
