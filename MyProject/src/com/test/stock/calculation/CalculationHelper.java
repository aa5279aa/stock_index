package com.test.stock.calculation;

import com.test.stock.entity.IndexDayEntity;
import com.test.stock.entity.IndexMinuteEntity;

public class CalculationHelper {

	// 对比两个
	public static double calculationTrend(IndexMinuteEntity indexMinute1,
			IndexMinuteEntity indexMinute2) {

		double trend = (indexMinute1.mIndex_now - indexMinute2.mIndex_now)
				/ indexMinute2.mIndex_now;

		return trend * 100;
	}

}
