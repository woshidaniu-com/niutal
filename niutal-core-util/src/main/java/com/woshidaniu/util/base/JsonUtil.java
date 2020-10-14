/**
 * @部门:学工产品事业部
 * @日期：2013-4-27 上午10:03:39 
 */
package com.woshidaniu.util.base;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：JSON工具类
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月13日上午9:09:44
 */
@Deprecated
public class JsonUtil {
	
	private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

	private JsonUtil(){
		super();
	}
	
	
	/**
	 * 
	 * <p>方法说明：JSON字符串转对象<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月13日上午9:10:04<p>
	 * @param json 字符串
	 * @param clazz 对象类型
	 * @return clazz同类型对象
	 */
	public static <T> Object jsonToModel(String json, Class<T> clazz) {
		Object bean = null;
		JSONObject jsonObject = null;
		try {
			jsonObject = JSONObject.fromObject(json);
			bean = JSONObject.toBean(jsonObject, clazz);
		} catch (Exception e) {
			log.error(String.format("json字符串转model出错：%s ==> %s" , json,clazz.getName()));
		}
		return bean;
	}


	
	/**
	 * 把json转化为list对象,json格式要求：外层以data为key,value为json数组，数组内部为可转化生成的对象
	 * 如：{data:[{tjdm:'wjqk',tjgx:'='},{tjdm:'pjcj',tjgx:'='}]},对象为：XmwhTjszForm
	 * 格式错误，转化失败，直接返回空
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
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
