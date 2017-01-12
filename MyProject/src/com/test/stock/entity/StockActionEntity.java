package com.test.stock.entity;

/**
 * 操作类
 * 
 * @author xiangleiliu
 *
 */
public class StockActionEntity {

	public boolean actionDirection = true;// true为买入，false为卖出
	public int buyNum;
	public int sellNum;
	public double index_now;
	public String timeStr;
	public long time;
	public String dateStr;
	
	
	
}
