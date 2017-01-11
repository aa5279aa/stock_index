package com.test.stock.util;

import java.util.Calendar;
import java.util.Date;

public class TimeControl {

	public static String nowTimeStr;
	public static Date nowTimeDate;

	// format_yyyy_MM_dd_HH_mm
	private static String startTime = "2015-12-3 16:25";

	public static void init() {

		nowTimeStr = "";

		new Thread(new Runnable() {

			@Override
			public void run() {

				Date dateByyyyy_MM_dd_HH_mm = TimeHelper
						.getDateByyyyy_MM_dd_HH_mm(startTime);

				while (true) {

					nowTimeDate = dateByyyyy_MM_dd_HH_mm;
					nowTimeStr = TimeHelper
							.getyyyy_MM_dd_HH_mm(dateByyyyy_MM_dd_HH_mm
									.getTime());

					try {
						Thread.sleep(60 * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dateByyyyy_MM_dd_HH_mm);
					calendar.add(Calendar.MINUTE, 1);

					dateByyyyy_MM_dd_HH_mm = calendar.getTime();

				}

			}
		}).start();

	}

}
