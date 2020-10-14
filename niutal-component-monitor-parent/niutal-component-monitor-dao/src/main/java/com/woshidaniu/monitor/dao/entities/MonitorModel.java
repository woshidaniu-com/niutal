package com.woshidaniu.monitor.dao.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 *@类名称	: MonitorModel.java
 *@类描述	：
 *@创建人	：kangzhidong
 *@创建时间	：2017年6月22日 下午12:03:51
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
@SuppressWarnings("serial")
public class MonitorModel extends ModelBase {

	protected String host;
	
	protected String jvm;
	
	protected String key;
	
	protected String time;
	
	protected String value;
	
	public MonitorModel(String host, String jvm, String key, String time, String value) {
		this.host = host;
		this.jvm = jvm;
		this.key = key;
		this.time = time;
		this.value = value;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getJvm() {
		return jvm;
	}

	public void setJvm(String jvm) {
		this.jvm = jvm;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
