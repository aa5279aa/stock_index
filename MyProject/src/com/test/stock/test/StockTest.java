package com.test.stock.test;

import java.util.List;

import com.test.stock.entity.IndexMinuteEntity;
import com.test.stock.read.ReadHelper;

public class StockTest {
	public static void main(String[] args) {

		List<IndexMinuteEntity> rangeList = ReadHelper.getRangeList();

		IndexMinuteEntity indexMinute;
		for (int i = 0; i < rangeList.size(); i++) {
			indexMinute = rangeList.get(i);
			String timeStr = indexMinute.mTimeStr;
			String indexNowStr = String.valueOf(indexMinute.mIndex_now);
			String indexRangeStr = "0";
			if (i > 1) {
				indexRangeStr = String.format("%.3f", indexMinute.mIndex_now
						- rangeList.get(i - 1).mIndex_now);
			}
			String volumStr = String.valueOf(indexMinute.mVolume_v);

			System.out.println("时间：" + timeStr + ",指数：" + indexNowStr + ",指数差："
					+ indexRangeStr + ",量能：" + volumStr);
		}

	}
}
