package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.util.IOHelper;

public class Test3 {

	public static void main(String[] args) {

		List<String[]> list = new ArrayList<>();

		try {
			InputStream fromFileToIputStream = IOHelper
					.fromFileToIputStream("D:\\develop_workspace\\log.txt");
			list = readStrByCodeForList(fromFileToIputStream, "utf-8");
			List<Map<String, String>> convertList = convertList(list);
			for (Map<String, String> map : convertList) {
				System.out.print(map.get("log_timeStr") + ",");
				System.out.print(map.get("log_servicecodeStr") + ",");
				System.out.println(map.get("log_servicenameStr"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<Map<String, String>> convertList(List<String[]> list) {
		List<Map<String, String>> logList = new ArrayList<Map<String, String>>(
				list.size());
		for (String[] str : list) {
			try {
				String title = str[0];
				String log = str[1];

				int start = title.indexOf(":");
				String log_timeStr = title.substring(start + 1,
						title.indexOf("--", start));
				String log_servicecodeStr = title.substring(
						title.indexOf("[") + 1, title.indexOf("]"));
				start = log.indexOf("\",\"");
				int end = log.indexOf("\":", start);
				String log_servicenameStr = log.substring(start + 3, end);
				Map<String, String> map = new HashMap<String, String>();
				map.put("log_timeStr", log_timeStr);
				map.put("log_servicecodeStr", log_servicecodeStr);
				map.put("log_servicenameStr", log_servicenameStr);
				logList.add(map);
			} catch (Exception e) {
				continue;
			}

		}
		return logList;
	}

	public static List<String[]> readStrByCodeForList(InputStream is,
			String code) throws IOException {
		List<String[]> list = new ArrayList<>();
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(is, code));
		String line;
		String[] strs;
		while ((line = reader.readLine()) != null) {
			if (line.length() > 0) {
				strs = new String[2];
				strs[0] = line;
				strs[1] = reader.readLine();
				list.add(strs);
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}