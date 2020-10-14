/**
 * 
 */
package com.woshidaniu.search.data.query;

import com.woshidaniu.search.dao.entities.SearchConfigModel;

/**
 * 数据查询器抽象类
 * 
 * 
 * @author xiaokang
 *
 */
public abstract class AbstractValueSourceQuerier implements ValueSourceQuerier {

	//配置对象
	private SearchConfigModel config;
	//数据查询方式
	private String method;
	//源
	private String source;
	
	private String initalLabelValue;
	
	public AbstractValueSourceQuerier() {
		super();
	}
	
	public AbstractValueSourceQuerier(String method, String source) {
		super();
		this.method = method;
		this.source = source;
	}
	
	public AbstractValueSourceQuerier(SearchConfigModel config, String method,
			String source) {
		super();
		this.config = config;
		this.method = method;
		this.source = source;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public SearchConfigModel getConfig() {
		return config;
	}

	public void setConfig(SearchConfigModel config) {
		this.config = config;
	}

	public String getInitalLabelValue() {
		return initalLabelValue;
	}

	public void setInitalLabelValue(String initalLabelValue) {
		this.initalLabelValue = initalLabelValue;
	}
	
}
