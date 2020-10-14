/**
 * 
 */
package com.woshidaniu.common.datarange;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author zhidong kang
 * @desc 数据范围定义对象
 *
 */
public class DataRangeItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 23263734834186739L;

	private String[] dataIds;
	
	private String info;
	
	private QueryType type = QueryType.IN;

	public String[] getDataIds() {
		return dataIds;
	}

	public void setDataIds(String[] dataIds) {
		this.dataIds = dataIds;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public QueryType getType() {
		return type;
	}

	public void setType(QueryType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "DataRangeItem [dataIds=" + Arrays.toString(dataIds) + ", info=" + info + ", type=" + type + "]";
	}
	
	
}
