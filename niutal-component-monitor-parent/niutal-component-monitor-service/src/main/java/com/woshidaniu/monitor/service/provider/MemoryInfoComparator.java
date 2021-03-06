package com.woshidaniu.monitor.service.provider;

import java.util.Comparator;
import java.util.Map;

public class MemoryInfoComparator implements Comparator<Map<String, Object>> {

	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		long timestamp1 = Long.parseLong(o1.get("os.timestamp").toString());
		long timestamp2 = Long.parseLong(o2.get("os.timestamp").toString());
		//筛选范围内的数据
		if(timestamp1 > timestamp2){
			return 1;
		} else if(timestamp1 < timestamp2){
			return -1;
		}
		return 0;
	}
	
}
