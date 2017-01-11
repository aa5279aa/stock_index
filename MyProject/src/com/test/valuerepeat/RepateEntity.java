package com.test.valuerepeat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepateEntity {

	public String name;
	public String parentName;
	public String filename;// 位置
	public String location;// 位置
	public int itemSize = 0;
	HashMap<String, String> itemMap = new HashMap<>();
	List<RepateEntity> repateList;//重复的
	List<RepateEntity> containList;//包含的
	
	
	public void addRepeatEntity(RepateEntity repateEntity){
		if(repateList == null){
			repateList = new ArrayList<>();
		}
		repateList.add(repateEntity);
	}
	
	public void addContainEntity(RepateEntity repateEntity){
		if(containList == null){
			containList = new ArrayList<>();
		}
		containList.add(repateEntity);
	}
	
	@Override
	public String toString() {
		return "RepateEntity [name=" + name + ", parentName=" + parentName
				+ ", location=" + location + ", itemSize=" + itemSize
				+ ", itemMap=" + itemMap + "]";
	}

	
	
}
