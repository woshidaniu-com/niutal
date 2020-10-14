/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.entites;

import java.io.Serializable;
import java.util.List;

/**
 * @author 		：Penghui.Qu[445]
 * @description	：分发对象条件
 */
public class FfdxtjModel implements Serializable{

	private static final long serialVersionUID = -5801421612354224398L;
	
	//服务接口类型
	private String sjly;
	
	//本层次相关
	private String id;
	private String name;
	private String key;
	private String value;
	
	private List<?> itemList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getSjly() {
		return sjly;
	}
	public void setSjly(String sjly) {
		this.sjly = sjly;
	}
	public List<?> getItemList() {
		return itemList;
	}
	public void setItemList(List<?> itemList) {
		this.itemList = itemList;
	}
	@Override
	public String toString() {
		return "FfdxtjModel [sjly=" + sjly + ", id=" + id + ", name=" + name + ", key=" + key + ", value=" + value
				+ ", itemList=" + itemList + "]";
	}
}
