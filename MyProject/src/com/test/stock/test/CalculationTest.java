package com.test.stock.test;

import java.util.ArrayList;
import java.util.List;

import com.test.stock.calculation.TrendCalculation;
import com.test.stock.config.Config;
import com.test.stock.entity.IndexChangeEntity;
import com.test.stock.entity.IndexDayEntity;
import com.test.stock.entity.IndexMinuteEntity;
import com.test.stock.read.ReadHelper;
import com.test.stock.util.MathUtil;

public class CalculationTest {

	public static void main(String[] args) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				List<IndexMinuteEntity> rangeList = ReadHelper.getRangeList();
				IndexChangeEntity indexChangeEntity;

				List<IndexMinuteEntity> trendList = new ArrayList<IndexMinuteEntity>();
				int lasttrend = 0;// 之前保持的趋势状态
				IndexDayEntity yestodayIndex = new IndexDayEntity();
				yestodayIndex.index_end = 2870;

				IndexChangeEntity lastIndexChangeEntity = null;
				while (rangeList.size() > 0) {
					IndexMinuteEntity currentIndex = rangeList.remove(0);

					if (trendList.size() == 0) {
						trendList.add(currentIndex);
						System.out.println("时间:" + currentIndex.mTimeStr
								+ ",指数:" + currentIndex.mIndex_now + ",状态:初始化");
						continue;
					}

					IndexMinuteEntity lastIndexMinute = trendList.get(trendList
							.size() - 1);
//					// 下跌
//					if (currentIndex.mIndex_now < lastIndexMinute.mIndex_now) {
//						if (lasttrend == 1) {
//							trendList.add(currentIndex);
//							indexChangeEntity = TrendCalculation
//									.getTrendPointforDown(currentIndex,
//											trendList, lastIndexChangeEntity,
//											yestodayIndex);
//							trendList.clear();
//							trendList.add(currentIndex);
//							currentIndex.mIndexChangeEntity = indexChangeEntity;
//							lastIndexChangeEntity = indexChangeEntity;
//						} else {
//							trendList.add(currentIndex);
//							indexChangeEntity = TrendCalculation
//									.getTrendPointforDown(currentIndex,
//											trendList, lastIndexChangeEntity,
//											yestodayIndex);
//							currentIndex.mIndexChangeEntity = indexChangeEntity;
//						}
//						lasttrend = -1;
//					} else {
//						// 上涨
//						if (lasttrend == -1) {
//							trendList.add(currentIndex);
//							indexChangeEntity = TrendCalculation
//									.getTrendPointforRise(currentIndex,
//											trendList, lastIndexChangeEntity,
//											yestodayIndex);
//							trendList.clear();
//							trendList.add(currentIndex);
//							currentIndex.mIndexChangeEntity = indexChangeEntity;
//							lastIndexChangeEntity = indexChangeEntity;
//						} else {
//							trendList.add(currentIndex);
//							indexChangeEntity = TrendCalculation
//									.getTrendPointforRise(currentIndex,
//											trendList, lastIndexChangeEntity,
//											yestodayIndex);
//							currentIndex.mIndexChangeEntity = indexChangeEntity;
//						}
//						lasttrend = 1;
//					}
//					currentIndex.mIndexChangeEntity = indexChangeEntity;
//					System.out.println("时间:" + currentIndex.mTimeStr + ",指数:"
//							+ currentIndex.mIndex_now + ",状态:"
//							+ (lasttrend == 1 ? "上涨" : "下降") + ",即时得分:"
//							+ indexChangeEntity.mActualPoint + ",综合得分:"
//							+ indexChangeEntity.mCompositePoint);

					try {
						Thread.sleep(Config.minute_interval * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}).start();

	}

	public int getVolumIndex(double currentVolum, double[] last30Volums) {
		int voumIndex = 50;
		double averageVolumRate = currentVolum
				/ MathUtil.getAverage(last30Volums);
		if (averageVolumRate <= 1) {
			if (averageVolumRate < 0.5) {
				voumIndex = 0;
			} else {
				voumIndex = (int) (1d - averageVolumRate) * 10;
			}
		} else {
			voumIndex = (int) (averageVolumRate - 1) * 50 + 50;
		}
		return voumIndex;
	}

	public int getTrendIndex(double currentIndex, double lastIndex2,
			double lastIndex5, double lastIndex1) {
		int trendIndex = 50;
		trendIndex = (int) (lastIndex1 - currentIndex) * 40
				+ (int) (lastIndex2 - currentIndex) * 30
				+ (int) (lastIndex5 - currentIndex) * 30;

		return trendIndex;
	}

}
