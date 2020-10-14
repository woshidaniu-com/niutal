package com.woshidaniu.tjcx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataUtils {
	public List<HashMap<String, String>> getXbpz(){
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map = null;
		
		map = new HashMap<String, String>();
		map.put("xb", "1");
		map.put("xbmc", "男");
		list.add(map);
		
		map = new HashMap<String, String>();
		map.put("xb", "2");
		map.put("xbmc", "女");
		list.add(map);
		
		map = new HashMap<String, String>();
		map.put("xb", "3");
		map.put("xbmc", "未知");
		list.add(map);
		return list;
	}
	
	public List<HashMap<String, String>> getXbpz(String param){
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map = null;
		if(param != null){
			if(param.equals("2011")){
				map = new HashMap<String, String>();
				map.put("xb", "1");
				map.put("xbmc", "男");
				list.add(map);
			}else if(param.equals("2012")){
				map = new HashMap<String, String>();
				map.put("xb", "2");
				map.put("xbmc", "女");
				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @描述:
	 * @作者：ligl
	 * @日期：2013-9-25 上午11:12:05
	 * @修改记录: 
	 * @param param
	 * @param flzdz ，当为空，无记录返回。当为空字符串时，返回所有记录
	 * @return
	 * List<HashMap<String,String>> 返回类型 
	 * @throws
	 */
	public List<HashMap<String, String>> getXbpz(String param,String flzdz){
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		if(flzdz != null){
			HashMap<String, String> map = null;
			map = new HashMap<String, String>();
			map.put("xb", "1");
			map.put("xbmc", "男");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("xb", "2");
			map.put("xbmc", "女");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("xb", "3");
			map.put("xbmc", "未知");
			list.add(map);
		}

		return list;
	}
	

}
