package com.test.valuerepeat;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.test.util.IOHelper;

public class Test5 {

	public static final int State_NORMAL = 0;// 正常
	public static final int State_NULL = 1;// 属性定义为空
	public static final int State_REPEAT = 2;// 属性定义重复
	public static final int State_CONTAIN = 3;// 属性定义包含

	static List<RepateEntity> repatValue = new ArrayList<>();

	public static void main(String[] args) {

		// 避免属性定义重复
		final String path1 = "D:\\develop_workspace\\git_warehouse\\android_2\\CTHotel\\CTHotelMain\\res\\values\\styles.xml";
		final String path2 = "D:\\develop_workspace\\git_warehouse\\android_2\\CTLibs\\CTBusiness\\res\\values\\styles.xml";
		// final String path3 =
		// "D:\\develop_workspace\\git_warehouse\\android_2\\CTHotel\\CTHotelMain\\res\\values\\styles.xml";

		List<String> pathList = new ArrayList<>();
		pathList.add(path1);
		pathList.add(path2);
		// pathList.add(path3);

		// 读取所有属性
		for (String filepath : pathList) {
			readFile(repatValue, filepath);
		}

		// 遍历输出重复值
		printRepeat(repatValue);

	}

	public static void printRepeat(List<RepateEntity> repatValue) {
		for (int i = 0; i < repatValue.size(); i++) {
			RepateEntity repateEntity = repatValue.get(i);
			if (repateEntity.itemSize == 0) {
				System.out.println(repateEntity.filename + ",name:"
						+ repateEntity.name + "为空");
				continue;
			}
			for (int j = 0; j < repatValue.size(); j++) {
				if (i == j) {
					continue;
				}
				RepateEntity compareRepateEntity = repatValue.get(j);
				// 判断是否重复
				int repeatState = getRepeatState(repateEntity,
						compareRepateEntity);

				if (repeatState == State_REPEAT) {
					System.out.println(repateEntity.filename + ",name:"
							+ repateEntity.name + "重复" + ",重复位置："
							+ compareRepateEntity.filename + ",name:"
							+ compareRepateEntity.name);
				} else if (repeatState == State_CONTAIN) {
					// System.out.println(repateEntity.filename+",name:"+repateEntity.name
					// +
					// "包含"+",包含位置："+compareRepateEntity.filename+",name:"+compareRepateEntity.name);
				} else {
					// State_NORMAL
					// System.out.println(repateEntity.name+"正常");
				}
			}
		}
	}

	public static int getRepeatState(RepateEntity repateEntity,
			RepateEntity compareRepateEntity) {
		int state = State_REPEAT;
		// 判断parent是否相等，不相等的话直接返回
		if (isParentEquals(repateEntity.parentName,
				compareRepateEntity.parentName)) {
			return State_NORMAL;
		}
		for (String key : repateEntity.itemMap.keySet()) {
			String value = repateEntity.itemMap.get(key);
			String compareValue = compareRepateEntity.itemMap.get(key);
			if (compareValue == null) {
				return State_NORMAL;
			}
			if (!dipDpequals(value, compareValue)) {
				return State_NORMAL;
			}
		}
		if (repateEntity.itemSize < compareRepateEntity.itemSize) {
			state = State_CONTAIN;
			repateEntity.addContainEntity(compareRepateEntity);
		} else {
			repateEntity.addRepeatEntity(compareRepateEntity);
		}
		return state;
	}

	public static boolean isParentEquals(String name, String parentName) {
		// 只有都有，并且不同时，才返回true
		if (name != null && parentName != null && !name.endsWith(parentName)) {
			return true;
		}
		return false;

	}

	public static boolean dipDpequals(String str1, String str2) {
		str1 = str1.toLowerCase();
		str2 = str2.toLowerCase();
		if (str1.contains("dip")) {
			str1.replace("dip", "dp");
		}
		if (str2.contains("dip")) {
			str2.replace("dip", "dp");
		}
		return str1.equals(str2);
	}

	public static void readFile(List<RepateEntity> list, String filepath) {

		InputStream is = IOHelper.fromFileToIputStream(filepath);
		String fromIputStreamToString = IOHelper.fromIputStreamToString(is);

		int startIndex = 0;
		int endIndex = 0;

		while (true) {
			startIndex = fromIputStreamToString.indexOf("<style", endIndex);
			endIndex = fromIputStreamToString.indexOf("</style>", startIndex);
			if (startIndex < 0 || endIndex < 0) {
				break;
			}
			String styleStr = fromIputStreamToString.substring(startIndex,
					endIndex);// 找到一个style记录
			RepateEntity readOneStyle = readOneStyle(styleStr);
			readOneStyle.location = filepath;
			File file = new File(filepath);
			readOneStyle.filename = file.getParentFile().getParentFile()
					.getParentFile().getName()
					+ "/"
					+ file.getParentFile().getParentFile().getName()
					+ "/"
					+ file.getParentFile().getName()
					+ "/"
					+ file.getName();
			// System.out.println(readOneStyle);
			list.add(readOneStyle);
		}
	}

	public static RepateEntity readOneStyle(String styleStr) {

		RepateEntity repateEntity = new RepateEntity();
		Matcher matcher = compileName.matcher(styleStr);
		if (matcher.find()) {
			String name = matcher.group(1);
			repateEntity.name = name;
		}

		Matcher matcher2 = compileParentName.matcher(styleStr);
		if (matcher2.find()) {
			String parentName = matcher2.group(1);
			repateEntity.parentName = parentName;
		}

		Matcher matcher3 = itemValue.matcher(styleStr);
		while (matcher3.find()) {
			String key = matcher3.group(1);
			String value = matcher3.group(2);
			// System.out.println(key+","+value);
			repateEntity.itemMap.put(key, value);
		}
		repateEntity.itemSize = repateEntity.itemMap.size();
		return repateEntity;
	}

	static Pattern compileName = Pattern.compile(".*?name=\"(.*?)\".*?>");
	static Pattern compileParentName = Pattern
			.compile(".*?parent=\"(.*?)\".*?>");
	static Pattern itemValue = Pattern
			.compile(".*?item name=\"(.*?)\".*?>(.*?)</item>.*?");

}
