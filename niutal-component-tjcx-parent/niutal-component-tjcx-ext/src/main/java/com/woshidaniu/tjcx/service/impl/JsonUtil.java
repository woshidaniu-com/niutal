package com.woshidaniu.tjcx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称:
 * @类功能描述:json处理类
 * @作者： ligl
 * @时间： 2013-9-24 上午09:16:38
 * @版本： V1.0
 * @修改记录:
 */
public class JsonUtil {
	private static final transient Logger log = LoggerFactory
			.getLogger(JsonUtil.class);

	/**
	 * 把json转化为list对象,json格式要求：外层以data为key,value为json数组，数组内部为可转化生成的对象
	 * 如：{data:[{tjdm:'wjqk',tjgx:'='},{tjdm:'pjcj',tjgx:'='}]},对象为：XmwhTjszForm
	 * 格式错误，转化失败，直接返回空
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List jsonToList(String json, Class clazz) {
		Object[] array = null;
		JSONObject str = null;
		JSONArray jsonArray = null;
		try {
			if (json != null) {
				str = JSONObject.fromObject(json);
				jsonArray = str.getJSONArray("data");
				array = (Object[]) JSONArray.toArray(jsonArray, clazz);
			}
		} catch (Exception e) {
			log.warn("json格式解析错误:" + json);
			return null;
		}
		List list = new ArrayList();
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] != null) {
					list.add(array[i]);
				}
			}
		}
		return list;
	}

	/**
	 * 把json转化为list对象,json格式要求：外层以data为key,value为json数组，数组内部为可转化生成的对象
	 * 如：{data:[{tjdm:'wjqk',tjgx:'='},{tjdm:'pjcj',tjgx:'='}]},对象为：XmwhTjszForm
	 * 格式错误，转化失败，直接返回空
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List jsonToList(String json) {
		Object[] array = null;
		JSONObject str = null;
		JSONArray jsonArray = null;
		try {
			if (json != null) {
				str = JSONObject.fromObject(json);
				jsonArray = str.getJSONArray("data");
				array = (Object[]) JSONArray.toArray(jsonArray);
			}
		} catch (Exception e) {
			log.warn("json格式解析错误:" + json);
			return null;
		}
		List list = new ArrayList();
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] != null) {
					list.add(array[i]);
				}
			}
		}
		return list;
	}

}
