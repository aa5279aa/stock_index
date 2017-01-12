package com.test.stock.read;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.test.stock.config.Config;
import com.test.stock.entity.IndexMinuteEntity;
import com.test.util.IOHelper;

public class ReadHelper {

	// 请在线程中调用
	public static List<IndexMinuteEntity> getAllList() {
		List<IndexMinuteEntity> list = new ArrayList<>();
		InputStream is = IOHelper.fromFileToIputStream(Config.path);

		String msg = IOHelper.fromIputStreamToString(is);
		// time:09:31,成交量:84亿,指数:3183.587
		String[] lows = msg.split("\n");

		IndexMinuteEntity indexMinute;

		for (int i = 0; i < lows.length; i++) {
			String[] line = lows[i].split(",");
			if (line.length == 4) {
				String[] times = new String[2];
				times[0] = line[0].substring(0, line[0].indexOf(":"));
				times[1] = line[0].substring(line[0].indexOf(":") + 1,
						line[0].length());
				String[] volums = line[1].split(":");
				String[] indexs = line[3].split(":");
				indexMinute = new IndexMinuteEntity(
						IndexMinuteEntity.Board_SH_Main);
				indexMinute.mId = i;
				indexMinute.mTimeStr = times[1];
				indexMinute.mVolume_sum = Double.parseDouble(volums[1]
						.substring(0, volums[1].length() - 1));
				if (list.size() > 0) {
					IndexMinuteEntity lastIndexMinute = list
							.get(list.size() - 1);
					indexMinute.mVolume_v = indexMinute.mVolume_sum
							- lastIndexMinute.mVolume_sum;
				}

				indexMinute.mIndex_now = Double.parseDouble(indexs[1]);
				indexMinute.mDateStr = Config.date;
				indexMinute.init();
				list.add(indexMinute);
			}
		}
		return list;
	}

	// 获取某一时间段
	public static List<IndexMinuteEntity> getRangeList() {
		List<IndexMinuteEntity> list = new ArrayList<>();

		List<IndexMinuteEntity> allList = getAllList();
		IndexMinuteEntity fristIndexMinute = allList.get(0);

		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTimeInMillis(fristIndexMinute.mTime);
		startCalendar.set(Calendar.HOUR_OF_DAY, Config.startRange_HOUR);
		startCalendar.set(Calendar.MINUTE, Config.startRange_MINUTE);

		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTimeInMillis(fristIndexMinute.mTime);
		endCalendar.set(Calendar.HOUR_OF_DAY, Config.endRange_HOUR);
		endCalendar.set(Calendar.MINUTE, Config.endRange_MINUTE);

		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i < allList.size(); i++) {
			IndexMinuteEntity indexMinute = allList.get(i);
			calendar.setTimeInMillis(indexMinute.mTime);
			if (calendar.after(startCalendar) && calendar.before(endCalendar)) {
				list.add(indexMinute);
			}

		}
		return list;
	}
}
