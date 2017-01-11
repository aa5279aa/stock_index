package com.test.stock.entity;

public class IndexChangeEntity {

	//trend
	public final static int Trend_Up=1;
	public final static int Trend_Down=-1;
	public final static int Trend_Parallel=-1;
	
	
	//transaction
	public final static int Transaction_Up=1;
	public final static int Transaction_Down=-1;
	public final static int Transaction_Parallel=-1;
	
	public int mId;//排序id和indexMinuteEntity中的mId对应
	
	public boolean isChange;
	public int mTrend=0;//up=1，dowm=-1，parallel=0
	public int mTransaction=0;//up=1 dowm=-1,，parallel=0
	public int mProbability;//50~100
	public int mStrength;//0~100
	
	
}
