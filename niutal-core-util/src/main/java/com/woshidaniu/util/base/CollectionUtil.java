package com.woshidaniu.util.base;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;


/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：集合工具类，扩展之Spring CollectionUtils。
 * <p>
 * @deprecated
 * @see com.woshidaniu.basicutils.CollectionUtils
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月7日下午2:42:50
 */
@Deprecated
public final class CollectionUtil extends CollectionUtils{

	private CollectionUtil(){
		super();
	}
	
	
	/**
	 * 
	 * <p>方法说明：去除List中所有重复的值<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月7日下午3:11:09<p>
	 * @param list
	 * @return 去重后的List
	 */
	public static <T> List<T> removeDupValue(final List<T> list) {
		Set<T> set = new HashSet<T>();
		set.addAll(list);
		List<T> retList = new ArrayList<T>();
		retList.addAll(set);
		return retList;
	}
	
	
	
	
	/**
	 * 
	 * <p>方法说明：将一个大的List分割成小的List<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月7日下午5:45:52<p>
	 * @param list 要分割的List
	 * @param size 每个小List的大小
	 * @return 元素为小List的List
	 */
	public static <T> List<List<?>> splitList(final List<T> list,final int size) {
		List<List<?>> retList = new ArrayList<List<?>>();
		if (isEmpty(list)) {
			return retList;
		}

		List<T> tmpList = new ArrayList<T>();
		
		for (int i = 0 , j = list.size() ; i < j ; i++){
			
			tmpList.add(list.get(i));
			
			if ((i+1) % size == 0 || i+1 == j){
				retList.add(tmpList);
				tmpList = new ArrayList<T>();
			}
		}
		return retList;
	}


}
