package com.test.stock.entity;

import java.util.Date;

import com.test.stock.util.TimeHelper;

/**
 * Created by xiangleiliu on 2015/8/14.
 */
public class IndexMinuteEntity {

	public static final int Board_SH_Main = 1;
	public static final int Board_SZ_Main = 2;
	public static final int Board_SZ_Small = 3;
	public static final int Board_SZ_Gem = 4;

	public int mId;//排序id和indexChangeEntity中的mId对应
	
	public int mBoard_Type;

	public double mIndex_now;
	public long mTime;
	public String mDateStr;
	public String mTimeStr;
//	public String id;
	public double mVolume_sum;
	public double mVolume_v;
//	public double range_drop;
	public IndexChangeEntity mIndexChangeEntity;//index change

	public IndexMinuteEntity(int board_Type) {
		this.mBoard_Type = board_Type;
	}

	public void init() {
		Date dateByyyyy_MM_dd_HH_mm = TimeHelper
				.getDateByyyyy_MM_dd_HH_mm("2015-" + mDateStr + " " + mTimeStr);
		mTime = dateByyyyy_MM_dd_HH_mm.getTime();
	}

	public void setIndexChangeEntity(IndexChangeEntity indexChangeEntity){
		this.mIndexChangeEntity=indexChangeEntity;
	}
	
	
}
