/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.comm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 982 张昌路
 * 导入map
 */
public class ImportErrorMessage extends TreeMap<Integer, String> implements Map<Integer, String> {
	
	private static final long serialVersionUID = -4649830838101945614L;
	
	private String _UPDATE_SPLIT = ",";

	public String update(Integer key, String value) {
		String v = get(key);
		if (null != v) {
			value = v + _UPDATE_SPLIT + value;
		}
		put(key, value);
		return value;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends String> m) {
		if (null != m) {
			for (Iterator<? extends Map.Entry<? extends Integer, ? extends String>> i = m
					.entrySet().iterator(); i.hasNext();) {
				Map.Entry<? extends Integer, ? extends String> e = i.next();
				update(e.getKey(), e.getValue());
			}
		}
	}

	public List<String> toList() {
		List<String> list = new LinkedList<String>();
		for (Iterator<? extends Map.Entry<? extends Integer, ? extends String>> it = this
				.entrySet().iterator(); it.hasNext();) {
			list.add(it.next().getValue());
		}
		return list;
	}
}
