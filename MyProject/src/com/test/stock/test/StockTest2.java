package com.test.stock.test;

import java.util.ArrayList;
import java.util.List;

import com.test.stock.entity.IndexMinuteEntity;
import com.test.stock.read.ReadHelper;
import com.test.stock.util.FormatUtil;

/**
 * double[0]顺势 double[1]逆势
 * 
 * 加入买入卖出指标 point[0]买入 point[1]卖出
 * 
 * @author xiangleiliu
 *
 */
public class StockTest2 {

	public static double bugAndSellScore = 2500;

	public static double holdMoney = 700000;// 持有金额
	public static int holdSum = 200;// 持有股票
	public static double stockValue = 0;// 市值

	public static void main(String[] args) {
		try {
			new StockTest2().getTrendPoint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void action100Stock(double index_now, int num) {
		if (num == 0) {
			return;
		}
		if (num > 0) {
			double spendMoney = index_now * num;
			if (spendMoney > holdMoney) {
				System.out.println("所剩余额不足，不能买入");
				return;
			}
			holdMoney -= spendMoney;
			holdSum += num;
			System.out.println("买入成功，买入" + num + "股，" + "花费" + spendMoney
					+ "，剩余金额" + holdMoney + "，持有股票数量" + holdSum);
		} else {
			num = num * -1;
			if (num > holdSum) {
				System.out.println("所剩股票数量不足，不能卖出");
				return;
			}
			double incomeMoney = index_now * num;
			holdMoney += incomeMoney;
			holdSum -= num;
			System.out.println("卖出成功，卖出" + num + "股，" + "收入" + incomeMoney
					+ "，剩余金额" + holdMoney + "，持有股票数量" + holdSum);
		}
	}

	public void getTrendPoint() throws Exception {

		List<IndexMinuteEntity> rangeList = ReadHelper.getRangeList();

		List<IndexMinuteEntity> list = new ArrayList<>();
		for (int i = 0; i < rangeList.size(); i++) {
			IndexMinuteEntity indexMinuteEntity = rangeList.get(i);
			if (i < 30) {
				list.add(indexMinuteEntity);
			} else {
				list.remove(0);
				list.add(indexMinuteEntity);
			}
			if (list.size() <= 15) {
				continue;
			}
			double volumePointFor30 = getVolumePointFor30(list);// 53
			double[] indexPoint = getIndexPoint(list);
			double buyPoint = indexPoint[0];
			double sellPoint = indexPoint[1];

			double buyScore = volumePointFor30 * buyPoint;
			double sellScore = volumePointFor30 * sellPoint;

			showResult(indexMinuteEntity, buyScore, sellScore);
		}

	}

	public void showResult(IndexMinuteEntity indexMinuteEntity,
			double buyScore, double sellScore) {

		stockValue = holdMoney + indexMinuteEntity.mIndex_now * holdSum;
		if (buyScore > bugAndSellScore) {
			System.out.println(indexMinuteEntity.mTimeStr + ",当前指数："
					+ indexMinuteEntity.mIndex_now + ",买分："
					+ FormatUtil.formatDouble2(buyScore) + "，卖分："
					+ FormatUtil.formatDouble2(sellScore));
			System.out.println("符合买入指标，尝试买入100股");
			action100Stock(indexMinuteEntity.mIndex_now, 100);
			System.out.println("目前市值：" + FormatUtil.formatDouble2(stockValue));
		} else if (sellScore > bugAndSellScore) {

			System.out.println(indexMinuteEntity.mTimeStr + ",当前指数："
					+ indexMinuteEntity.mIndex_now + ",买分："
					+ FormatUtil.formatDouble2(buyScore) + "，卖分："
					+ FormatUtil.formatDouble2(sellScore));

			System.out.println("符合卖出指标，尝试卖出100股");
			action100Stock(indexMinuteEntity.mIndex_now, -100);
			System.out.println("目前市值：" + FormatUtil.formatDouble2(stockValue));
		} else {
//			System.out.println(indexMinuteEntity.mTimeStr + ",当前指数："
//					+ indexMinuteEntity.mIndex_now + ",买分："
//					+ FormatUtil.formatDouble2(buyScore) + "，卖分："
//					+ FormatUtil.formatDouble2(sellScore) + "____不操作");
		}
		
	}

	/**
	 * 返回两种指数，分为指 买入指数 和 卖出指数
	 * 
	 * @param inputlist
	 * @return
	 * @throws Exception
	 */
	public double[] getIndexPoint(List<IndexMinuteEntity> inputlist)
			throws Exception {
		double[] points = new double[2];
		if (inputlist.size() <= 15) {
			points[0] = 10.0;
			points[1] = 10.0;
			return points;
		}

		List<IndexMinuteEntity> list = getSubList(inputlist,
				inputlist.size() - 15, inputlist.size() - 1);

		boolean direction = list.get(list.size() - 3).mIndex_now
				- list.get(0).mIndex_now > 0;// true为向上，false为向下

		double weight1 = getIndexPointWeightFor1(list)[1] * 40 / 100;
		double weight2 = getIndexPointWeightFor3(list)[1] * 30 / 100;
		double weight3 = getIndexPointWeightFor15(list)[1] * 30 / 100;

		double along = weight1 + weight2 + weight3;// 逆势

		weight1 = getIndexPointWeightFor1(list)[0] * 40 / 100;
		weight2 = getIndexPointWeightFor3(list)[0] * 30 / 100;
		weight3 = getIndexPointWeightFor15(list)[0] * 30 / 100;
		double inverse = (weight1 + weight2 + weight3) * 0.8;// 顺势.顺势打折

		if (direction) {
			points[0] = inverse;
			points[1] = along;
		} else {
			points[0] = along;
			points[1] = inverse;
		}
		return points;// 满分100
	}

	/**
	 * 计算量能的指数
	 * 
	 * @param list
	 * @return
	 */
	public double getVolumePointFor30(List<IndexMinuteEntity> list) {
		// 最近量能
		double averageVolumeFor30 = getAverageVolumeFor30(list);
		IndexMinuteEntity lastIndex = list.get(list.size() - 1);
		double score = 0;
		double times = lastIndex.mVolume_v / averageVolumeFor30;
		if (times > 3) {
			score = 30;
		} else if (times < 1) {
			score = 10;
		} else {
			score = times * 10;
		}

		// 当前量能大于上一分钟的量能
		IndexMinuteEntity last2Index = list.get(list.size() - 2);
		if (lastIndex.mVolume_v > last2Index.mVolume_v) {
			score += 10;
		}

		// 5分钟量能
		double averageVolumeFor30By5 = getAverageVolumeFor30By5(list);
		times = averageVolumeFor30By5 / averageVolumeFor30;
		if (times > 3) {
			score += 60;
		} else if (times < 1) {
			score += 10;
		} else {
			score += times * 20;
		}
		return score;
	}

	public double getAverageVolumeFor30(List<IndexMinuteEntity> list) {
		double average = 0;
		for (int i = list.size() - 1; i >= 0; i--) {
			IndexMinuteEntity indexMinute = list.get(i);
			average += indexMinute.mVolume_v;
			if (list.size() > 30 && i < (list.size() - 30)) {
				break;
			}
		}
		return average / list.size();
	}

	public double getAverageVolumeFor30By5(List<IndexMinuteEntity> inputlist) {
		// 超过30个截断为30
		List<IndexMinuteEntity> list;
		if (inputlist.size() > 30) {
			list = getSubList(inputlist, inputlist.size() - 30,
					inputlist.size() - 1);
		} else {
			list = inputlist;
		}
		double numeratorSum = list.get(list.size() - 1).mVolume_sum
				- list.get(list.size() - 6).mVolume_sum;
		return numeratorSum / 5;
	}

	public double[] getIndexPointWeightFor1(List<IndexMinuteEntity> inputlist) {
		double[] trend = new double[2];
		int size = inputlist.size();
		IndexMinuteEntity nowIndex = inputlist.get(size - 1);
		IndexMinuteEntity lastIndex = inputlist.get(size - 2);

		double d = nowIndex.mIndex_now - lastIndex.mIndex_now;
		// 顺势指数
		double abs = Math.abs(d);

		if (abs > 20) {
			trend[0] = 100;
		} else {
			trend[0] = abs * 5;
		}

		// 逆势指数，小于5
		if (abs == 0.0) {
			trend[1] = 100;
		} else if (abs < 5) {
			trend[1] = (5 - abs) * 20;
		} else {
			trend[1] = 10;
		}
		return trend;
	}

	public double[] getIndexPointWeightFor3(List<IndexMinuteEntity> inputlist) {
		double[] trend = new double[2];
		int size = inputlist.size();
		IndexMinuteEntity now = inputlist.get(size - 1);
		IndexMinuteEntity last1 = inputlist.get(size - 2);
		IndexMinuteEntity last2 = inputlist.get(size - 3);
		IndexMinuteEntity last3 = inputlist.get(size - 4);

		// 判断行情是否反转，如果反转的话，判断绝对值
		double gap3 = last2.mIndex_now - last3.mIndex_now;
		double gap2 = last1.mIndex_now - last2.mIndex_now;
		double gap1 = now.mIndex_now - last1.mIndex_now;

		if (!((gap1 > 0 && gap2 > 0 && gap3 > 0) || (gap1 < 0 && gap2 < 0 && gap3 < 0))) {
			// 如果反转，则判断gap3的绝对值是否足够小
			if (Math.abs(gap1) < 1) {
				trend[1] = 100;
			} else if (Math.abs(gap1) < 2) {
				trend[1] = 50;
			}
			return trend;
		}

		// 顺势 趋势变大
		double abs1 = Math.abs(gap1);
		double abs2 = Math.abs(gap2);
		double abs3 = Math.abs(gap3);
		if (abs1 > abs2 && abs2 > abs3) {
			trend[0] = 100;
		}
		// 逆势 趋势变小
		if (abs1 < abs2 && abs2 < abs3) {
			trend[1] = 100;
		}
		return trend;
	}

	/**
	 * 这里的逻辑需要改一下，15分钟和3分钟的判断逻辑。。判断15分钟内前10分钟和后5分钟的走势。
	 * 前10分钟趋势越大越好，后5分钟相对于前10分钟要改变明显
	 * 
	 * @param inputlist
	 * @return
	 * @throws Exception
	 */
	public double[] getIndexPointWeightFor15(List<IndexMinuteEntity> inputlist)
			throws Exception {
		double[] trend = new double[2];
		if (inputlist.size() != 15) {
			throw new Exception("长度异常");
		}

		IndexMinuteEntity index0 = inputlist.get(0);
		IndexMinuteEntity index9 = inputlist.get(9);
		IndexMinuteEntity index14 = inputlist.get(14);

		double gap10 = index9.mIndex_now - index0.mIndex_now;
		double gap5 = index14.mIndex_now - index9.mIndex_now;

		// 确保方向一致
		if (gap10 * gap5 <= 0) {
			return trend;
		}
		double times = gap10 / gap5;

		trend[1] = (times - 2) * 10 + 20;
		trend[1] = trend[1] > 100 ? 100 : trend[1];
		trend[1] = trend[1] < 20 ? 20 : trend[1];

		times = gap5 / gap10;
		trend[0] = (times - 0.5) * 40 + 20;
		trend[0] = trend[0] > 100 ? 100 : trend[0];
		trend[0] = trend[0] < 20 ? 20 : trend[0];

		return trend;
	}

	public List<IndexMinuteEntity> getSubList(
			List<IndexMinuteEntity> inputlist, int start, int end) {
		List<IndexMinuteEntity> list = new ArrayList<>();

		for (int i = start; i <= end; i++) {
			list.add(inputlist.get(i));
		}
		return list;
	}

}
