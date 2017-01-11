package com.test.stock.calculation;

import java.util.List;

import com.test.stock.entity.IndexChangeEntity;
import com.test.stock.entity.IndexDayEntity;
import com.test.stock.entity.IndexMinuteEntity;

public class TrendCalculation {

	/**
	 * 传入当前趋势队列
	 * 
	 * @param indexMinuteEntityList
	 * @return
	 */
//	public static IndexChangeEntity calculationIndexChange(
//			List<IndexMinuteEntity> indexMinuteEntityList) {
//
//		IndexChangeEntity currentChangeEntity = new IndexChangeEntity();
//
//		IndexMinuteEntity currentMinuteEntity = indexMinuteEntityList
//				.get(indexMinuteEntityList.size() - 1);
//
//		if (indexMinuteEntityList.size() == 1) {
//			return currentChangeEntity;
//		}
//
//		IndexMinuteEntity lastIndexMinuteEntity = indexMinuteEntityList
//				.get(indexMinuteEntityList.size() - 1);
//
//		// 设置当前趋势和变盘方向
//		setTrendAndChange(currentMinuteEntity, currentChangeEntity,
//				lastIndexMinuteEntity);
//
//		if (currentChangeEntity.isChange) {
//			actionChangeTrend(indexMinuteEntityList, currentChangeEntity);
//		} else {
//			actionNoChangeTrend(indexMinuteEntityList, currentChangeEntity);
//		}
//
//		currentChangeEntity.isChange = lastIndexMinuteEntity.mIndexChangeEntity.mTrend == currentChangeEntity.mTrend;
//
//		return currentChangeEntity;
//
//	}
//
//	private static void setTrendAndChange(
//			IndexMinuteEntity currentMinuteEntity,
//			IndexChangeEntity currentChangeEntity,
//			IndexMinuteEntity lastIndexMinuteEntity) {
//		if (currentMinuteEntity.mIndex_now != lastIndexMinuteEntity.mIndex_now) {
//			currentChangeEntity.mTrend = currentMinuteEntity.mIndex_now > lastIndexMinuteEntity.mIndex_now ? 1
//					: -1;
//		}
//		currentChangeEntity.isChange = lastIndexMinuteEntity.mIndexChangeEntity.mTrend == currentChangeEntity.mTrend;
//	}
//
//	/**
//	 * 如果未变盘， 1.量能变大，区间变大，正向加分，加分力度变大。加分力度和量能有关 2.量能变大，区间变小，反向加分，加分力度变大
//	 * 3.量能变小，区间变大，正向加分，加分力度变小。区间和正反向有关 4.量能变小，区间变小，反向加分，加分力度变小
//	 */
//	public static void actionNoChangeTrend(
//			List<IndexMinuteEntity> indexMinuteEntityList,
//			IndexChangeEntity currentChangeEntity) {
//
//		// 1.计算区间大小，来计算正反向指数
//
//		if (currentChangeEntity.mTrend == 1) {
//			// 计算交易方向和概率
//			setParamsForTransactionAndProbabitly(indexMinuteEntityList,
//					currentChangeEntity);
//
//			// 计算强度
//			setParamsForForStrength(indexMinuteEntityList, currentChangeEntity);
//
//		} else {
//			// 1.2
//			
//
//		}
//
//	}
//
//	/**
//	 * 如果变盘，则差值越大，行情继续的概率越大
//	 * 
//	 * @param indexMinuteEntityList
//	 */
//	private static void actionChangeTrend(
//			List<IndexMinuteEntity> indexMinuteEntityList,
//			IndexChangeEntity currentChangeEntity) {
//		IndexMinuteEntity currentIndexMinuteEntity = indexMinuteEntityList
//				.get(indexMinuteEntityList.size() - 1);
//		IndexMinuteEntity lastIndexMinuteEntity = indexMinuteEntityList
//				.get(indexMinuteEntityList.size() - 2);
//
//		double range = currentIndexMinuteEntity.mIndex_now
//				- lastIndexMinuteEntity.mIndex_now;
//
//		// 计算变盘概率，只和差值有关系，差距越大，继续变盘的概率就越小
//		if (range > 5) {
//			currentChangeEntity.mProbability = 90;
//		} else if (range > 2) {
//			currentChangeEntity.mProbability = 70;
//		} else if (range > 1) {
//			currentChangeEntity.mProbability = 50;
//		} else {
//			currentChangeEntity.mProbability = 30;
//		}
//
//		// 计算变盘力度，和之前趋势的长度相关，和量能有相关，和差值相关。
//		if (range > 5) {
//			currentChangeEntity.mStrength = 90;
//		} else {
//			currentChangeEntity.mStrength = 10;
//		}
//
//		// 计算
//
//	}
//
//	// // 下降趋势中
//	public static IndexChangeEntity getTrendPointforDown(
//			IndexMinuteEntity indexMinuteEntity,
//			List<IndexMinuteEntity> trendList,
//			IndexChangeEntity lastIndexChangeEntity,
//			IndexDayEntity yestodayIndex) {
//		IndexChangeEntity indexChangeEntity = new IndexChangeEntity();
//		int size = trendList.size();
//
//		if (size > 30) {
//			int trendPoint3 = getTrendPoint3ForDown(trendList.get(6),
//					trendList.get(5), trendList.get(2), yestodayIndex);
//			int trendPoint2 = getTrendPoint2ForDown(trendList.get(3),
//					trendList.get(2), trendList.get(1));
//			int trendPoint1 = getTrendPoint1ForDown(trendList.get(1),
//					trendList.get(0));
//			indexChangeEntity.mActualPoint = (trendPoint3 * 10 + trendPoint2
//					* 20 + trendPoint1 * 70) / 100;
//		} else if (size > 6) {
//			int trendPoint3 = getTrendPoint3ForDown(trendList.get(6),
//					trendList.get(5), trendList.get(2), yestodayIndex);
//			int trendPoint2 = getTrendPoint2ForDown(trendList.get(3),
//					trendList.get(2), trendList.get(1));
//			int trendPoint1 = getTrendPoint1ForDown(trendList.get(1),
//					trendList.get(0));
//			indexChangeEntity.mActualPoint = (trendPoint3 * 10 + trendPoint2
//					* 20 + trendPoint1 * 50)
//					/ 100 * size / 30;
//		} else if (size > 3) {
//			int trendPoint2 = getTrendPoint2ForDown(trendList.get(3),
//					trendList.get(2), trendList.get(1));
//			int trendPoint1 = getTrendPoint1ForDown(trendList.get(1),
//					trendList.get(0));
//
//			indexChangeEntity.mActualPoint = (trendPoint2 * 20 + trendPoint1 * 40)
//					/ 100 * size / 30;
//
//		} else if (size > 1) {
//			int trendPoint1 = getTrendPoint1ForDown(trendList.get(1),
//					trendList.get(0));
//
//			indexChangeEntity.mActualPoint = trendPoint1 * 30 / 100 * size / 30;
//		} else {
//			indexChangeEntity.mActualPoint = 0;
//		}
//		indexChangeEntity.mId = indexMinuteEntity.mId;
//
//		setCompositePoint(indexMinuteEntity, indexChangeEntity,
//				lastIndexChangeEntity);
//		indexMinuteEntity.mIndexChangeEntity = indexChangeEntity;
//
//		return indexChangeEntity;
//	}
//
//	public static void setCompositePoint(IndexMinuteEntity indexMinuteEntity,
//			IndexChangeEntity indexChangeEntity,
//			IndexChangeEntity lastIndexChangeEntity) {
//
//		if (lastIndexChangeEntity == null) {
//			indexChangeEntity.mCompositePoint = indexChangeEntity.mActualPoint;
//			return;
//		}
//
//		int i = indexMinuteEntity.mId - lastIndexChangeEntity.mId;
//		if (i == 0) {
//			indexChangeEntity.mCompositePoint = lastIndexChangeEntity.mActualPoint
//					* 8 / 10 + indexChangeEntity.mActualPoint * 2 / 10;
//		} else if (i == 1) {
//			indexChangeEntity.mCompositePoint = lastIndexChangeEntity.mActualPoint
//					* 6 / 10 + indexChangeEntity.mActualPoint * 4 / 10;
//
//		} else if (i == 2) {
//			indexChangeEntity.mCompositePoint = lastIndexChangeEntity.mActualPoint
//					* 4 / 10 + indexChangeEntity.mActualPoint * 6 / 10;
//
//		} else if (i == 3) {
//			indexChangeEntity.mCompositePoint = lastIndexChangeEntity.mActualPoint
//					* 2 / 10 + indexChangeEntity.mActualPoint * 8 / 10;
//
//		} else if (i == 4) {
//			indexChangeEntity.mCompositePoint = lastIndexChangeEntity.mActualPoint
//					* 1 / 10 + indexChangeEntity.mActualPoint * 9 / 10;
//		}
//
//	}
//
//	/**
//	 * 前提是都是增加状态 点1 1.(6,5)分钟斜率大于(2,5)分钟斜率 2.(5,2)分钟斜率不低于0.1%/分钟
//	 */
//
//	public static int getTrendPoint3ForDown(IndexMinuteEntity i6,
//			IndexMinuteEntity i5, IndexMinuteEntity i2, IndexDayEntity iLastDay) {
//
//		double rate65 = i6.mIndex_now - i5.mIndex_now;
//		double rate52 = (i5.mIndex_now - i2.mIndex_now) / 3;
//
//		if (rate65 < rate52)
//			return 0;
//
//		if ((rate52 / iLastDay.index_end) > 0.1)
//			return 100;
//
//		return 0;
//	}
//
//	/**
//	 * 前提是都是增加状态 点2 (2,1)分钟斜率小于(3,2)分钟斜率
//	 */
//	public static int getTrendPoint2ForDown(IndexMinuteEntity i3,
//			IndexMinuteEntity i2, IndexMinuteEntity i1) {
//		double rate32 = i3.mIndex_now - i2.mIndex_now;
//		double rate21 = i2.mIndex_now - i1.mIndex_now;
//
//		if (rate32 > rate21) {
//			return 100;
//		}
//		return 0;
//	}
//
//	public static int getTrendPoint1ForDown(IndexMinuteEntity i1,
//			IndexMinuteEntity i0) {
//
//		double diff = i1.mIndex_now - i0.mIndex_now;
//		if (diff < 0.5)
//			return 0;
//
//		return (int) (Math.abs((diff) - 0.5) * 5);
//	}
//
//	/**************************************************************************
//	 * 华丽分界线
//	 *
//	 * @param lastIndexChangeEntity
//	 **************************************************************************************/
//
//	// 上涨趋势当中
//	public static int setParamsForTransactionAndProbabitly(
//			List<IndexMinuteEntity> trendList,
//			IndexChangeEntity currentChangeEntity) {
//		IndexChangeEntity indexChangeEntity = new IndexChangeEntity();
//		int size = trendList.size();
//
//		if (size > 30) {
//			int trendPoint3 = getTrendPoint3ForUp(trendList.get(6),
//					trendList.get(5), trendList.get(2));
//			int trendPoint2 = getTrendPoint2ForDown(trendList.get(3),
//					trendList.get(2), trendList.get(1));
//			int trendPoint1 = getTrendPoint1ForDown(trendList.get(1),
//					trendList.get(0));
//			indexChangeEntity.mActualPoint = (trendPoint3 * 10 + trendPoint2
//					* 20 + trendPoint1 * 70) / 100;
//		} else if (size > 6) {
//			int trendPoint3 = getTrendPoint3ForDown(trendList.get(6),
//					trendList.get(5), trendList.get(2));
//			int trendPoint2 = getTrendPoint2ForDown(trendList.get(3),
//					trendList.get(2), trendList.get(1));
//			int trendPoint1 = getTrendPoint1ForDown(trendList.get(1),
//					trendList.get(0));
//			indexChangeEntity.mActualPoint = (trendPoint3 * 10 + trendPoint2
//					* 20 + trendPoint1 * 70)
//					/ 100 * size / 30;
//		} else if (size > 3) {
//			int trendPoint2 = getTrendPoint2ForDown(trendList.get(3),
//					trendList.get(2), trendList.get(1));
//			int trendPoint1 = getTrendPoint1ForDown(trendList.get(1),
//					trendList.get(0));
//
//			indexChangeEntity.mActualPoint = (trendPoint2 * 20 + trendPoint1 * 70)
//					/ 100 * size / 30;
//		} else if (size > 1) {
//			int trendPoint1 = getTrendPoint1ForDown(trendList.get(1),
//					trendList.get(0));
//			indexChangeEntity.mActualPoint = trendPoint1 * 7 / 10;
//		} else {
//			indexChangeEntity.mActualPoint = 0;
//		}
//		indexChangeEntity.mId = indexMinuteEntity.mId;
//		indexChangeEntity.mActualPoint *= -1;
//
//		setCompositePoint(indexMinuteEntity, indexChangeEntity,
//				lastIndexChangeEntity);
//		indexMinuteEntity.mIndexChangeEntity = indexChangeEntity;
//
//		return indexChangeEntity;
//	}
//
//	/**
//	 * 前提是都是增加状态 点1 1.(6,5)分钟斜率大于(2,5)分钟斜率 2.(5,2)分钟斜率不低于0.1%/分钟
//	 */
//
//	public static int getTrendPoint3ForUp(IndexMinuteEntity i6,
//			IndexMinuteEntity i5, IndexMinuteEntity i2) {
//
//		double rate65 = i6.mIndex_now - i5.mIndex_now;
//		double rate52 = (i5.mIndex_now - i2.mIndex_now) / 3;
//
//		if (rate65 < rate52)
//			return 0;
//
//
//		return 0;
//	}
//
//	/**
//	 * 前提是都是增加状态 点2 (2,1)分钟斜率小于(3,2)分钟斜率
//	 */
//	public static int getTrendPoint2ForUp(IndexMinuteEntity i3,
//			IndexMinuteEntity i2, IndexMinuteEntity i1) {
//		double rate32 = i3.mIndex_now - i2.mIndex_now;
//		double rate21 = i2.mIndex_now - i1.mIndex_now;
//
//		if (rate32 > rate21) {
//			return 100;
//		}
//		return 0;
//	}
//
//	public static int getTrendPoint1ForUp(IndexMinuteEntity i1,
//			IndexMinuteEntity i0) {
//
//		double diff = i1.mIndex_now - i0.mIndex_now;
//		if (diff > 20)
//			return 0;
//
//		return (int) (Math.abs((20 - diff) - 0.5) * 5);
//	}
//
//	private static void setParamsForForStrength(
//			List<IndexMinuteEntity> indexMinuteEntityList,
//			IndexChangeEntity currentChangeEntity) {
//		// TODO 自动生成的方法存根
//
//	}

}
