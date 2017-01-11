package com.test.stock.test;

import java.util.ArrayList;
import java.util.List;

import com.test.stock.entity.IndexMinuteEntity;
import com.test.stock.read.ReadHelper;
import com.test.stock.util.FormatUtil;

/**
 * double[0]顺势 double[1]逆势
 * 
 * 加入买入卖出指标
 * @author xiangleiliu
 *
 */
public class StockTest2 {
	public static void main(String[] args) {
		try {
			new StockTest2().getTrendPoint();
		} catch (Exception e) {
			e.printStackTrace();
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
			double indexPoint = getIndexPoint(list);
			double score = volumePointFor30 * indexPoint;
			System.out.println(indexMinuteEntity.mTimeStr + ",得分：" + FormatUtil.formatDouble2(score));
		}

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

	public double getIndexPoint(List<IndexMinuteEntity> inputlist)
			throws Exception {

		if (inputlist.size() <= 15) {
			return 10.0;
		}

		List<IndexMinuteEntity> list = getSubList(inputlist,
				inputlist.size() - 15, inputlist.size() - 1);

		// 判断方向
		boolean direction = list.get(list.size() - 1).mIndex_now
				- list.get(0).mIndex_now > 0;// true为↑
		double weight1 = getIndexPointWeightFor1(list)[1] * 40 / 100;// 39.6
		double weight2 = getIndexPointWeightFor3(list)[1] * 30 / 100;// 30
		double weight3 = getIndexPointWeightFor15(list)[1] * 30 / 100;
		double indexPoint = weight1 + weight2 + weight3;
		return indexPoint;// 满分100
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
