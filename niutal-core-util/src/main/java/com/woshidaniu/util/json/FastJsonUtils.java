package com.woshidaniu.util.json;


import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.woshidaniu.format.fastjson.FastJSONObject;



/**
 * 
 *@类名称:FastJsonUtils.java
 *@类描述：Json工具类
 *@创建人：kangzhidong
 *@创建时间：2014-7-1 下午08:01:03
 *@版本号:v1.0
 */
@Deprecated
public class FastJsonUtils extends FastJSONObject {

	/**
	 * 方法已作废：参见 @FastJSONObject.toCleanJSONString
	 */
	@Deprecated
	public static <T> String buildJSON(T element) {
		return FastJsonUtils.buildJSON(element,true);
	}
	
	/**
	 * 方法已作废：参见 @FastJSONObject.toCleanJSONString
	 */
	@Deprecated
	public static <T> String buildJSON(T element,boolean notEmpty) {
		return notEmpty?JSONObject.toJSONString(element,features): JSONObject.toJSONString(element);
	}
	
	/**
	 * 方法已作废：参见 @FastJSONObject.toCleanJSONString
	 */
	@Deprecated
	public static <T> String buildListJSON(List<T> list) {
		return FastJsonUtils.buildListJSON(list,true);
	}
	
	/**
	 * 方法已作废：参见 @FastJSONObject.toCleanJSONString
	 */
	@Deprecated
	public static <T> String buildListJSON(List<T> list,boolean notEmpty) {
		if(list==null){	return null;}
		return notEmpty?JSONArray.toJSONString(list,features): JSONArray.toJSONString(list);
	}
	
	/**
	 * 方法已作废：参见 @FastJSONObject.toCleanJSONString
	 */
	@Deprecated
	public static <T> String buildPageJSON(List<T> list) {
		return FastJsonUtils.buildPageJSON(list,true);
	}
	
	/**
	 * 方法已作废：参见 @FastJSONObject.toCleanJSONString
	 */
	@Deprecated
	public static <T> String buildPageJSON(List<T> list,boolean notEmpty) {
		if(list==null){	return null;}
		JSONObject pageObj = new JSONObject();
		pageObj.put("totalCount", list.size());
		pageObj.put("result", notEmpty?JSONArray.toJSONString(list,features): JSONArray.toJSONString(list));
		return pageObj.toString();
	}
	
	/**
	 * 方法已作废：参见 @FastJSONObject.toCleanJSONString
	 */
	@Deprecated
	public static <T> String buildPageJSON(List<T> list, int count) {
		return FastJsonUtils.buildPageJSON(list,count,true);
	}
	
	/**
	 * 方法已作废：参见 @FastJSONObject.toCleanJSONString
	 */
	@Deprecated
	public static <T> String buildPageJSON(List<T> list, int count,boolean notEmpty) {
		if(list==null){return null;}
		JSONObject pageObj = new JSONObject();
		pageObj.put("totalCount", count);
		pageObj.put("result", notEmpty?JSONArray.toJSONString(list,features): JSONArray.toJSONString(list));
		return pageObj.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(FastJsonUtils.buildJSON("ssss"));
		List<String>  sss = JSONObject.parseArray("['李']", String.class);
		System.out.println(sss);
	}
}
