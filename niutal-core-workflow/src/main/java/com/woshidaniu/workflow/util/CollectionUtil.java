package com.woshidaniu.workflow.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 集合的工具类,如[LIST,ARRAY,SET,MAP]
 * 
 * Create on 2013-6-19 下午02:55:17
 * 
 * All CopyRight By http://www.woshidaniu.com/ AT 2013 Version 1.0
 * 
 * @author : HJL [718]
 */
public final class CollectionUtil {

	/**
	 * 判断list是否为空
	 * 
	 * @param list
	 * @return boolean
	 */
	public static final boolean isEmpty(List<? extends Serializable> list) {
		if (null == list || list.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断map是否为空
	 * 
	 * @param map
	 * @return boolean
	 */
	public static final boolean isEmpty(Map<String, ? extends Object> map) {
		if (null == map || map.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断序列化的对象是否为空
	 * 
	 * @param obj
	 * @return boolean
	 */
	public static final boolean isSerialNull(Serializable obj) {
		if (null == obj) {
			return true;
		}
		if (obj instanceof Number) {
			Number num = (Number) obj;
			if (num.doubleValue() == 0 || num.floatValue() == 0 || num.longValue() == 0 || num.intValue() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see #isSerialNull(Serializable)
	 * @param obj
	 * @return boolean
	 */
	public static final boolean isSerialNotNull(Serializable obj) {
		return !isSerialNull(obj);
	}

	/**
	 * @see #isEmpty(List)
	 * @param list
	 * @return boolean
	 */
	public static final boolean isNotEmpty(List<? extends Serializable> list) {
		return !isEmpty(list);
	}

	/**
	 * @see #isEmpty(Map)
	 * @param map
	 * @return boolean
	 */
	public static final boolean isNotEmpty(Map<String, ? extends Object> map) {
		return !isEmpty(map);
	}

	/**
	 * 判断数组是否为空
	 * 
	 * @param array
	 * @return boolean
	 */
	public static final boolean isArrayNull(Serializable[] array) {
		if (null == array || array.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * @see #isArrayNull(Serializable[])
	 * @param array
	 * @return boolean
	 */
	public static final boolean isArrayNotNull(Serializable[] array) {
		return !isArrayNull(array);
	}

	/**
	 * 判斷一個對象是否為空
	 * 
	 * @param obj
	 * @return boolean
	 */
	public static final boolean isEmpty(Object obj) {
		if (null == obj) {
			return true;
		}
		return false;
	}

	/**
	 * @see #isEmpty(Object)
	 * @param obj
	 * @return boolean
	 */
	public static final boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
}
