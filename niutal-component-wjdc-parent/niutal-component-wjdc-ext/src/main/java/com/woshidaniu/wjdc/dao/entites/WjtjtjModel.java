/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.entites;

import java.io.Serializable;
import java.util.List;
/**
 * @author Penghui.Qu(445)
 * 试题管理DAO
 * 
  * @author ：康康（1571）
 * 整理，优化
 * */
public class WjtjtjModel  implements Serializable{

	private static final long serialVersionUID = -515705226392456941L;
	
	private String id;
	private String name;
	private String sjly;
	private String key;
	private String value;
	private String type;
	
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

	public String getSjly() {
		return sjly;
	}

	public void setSjly(String sjly) {
		this.sjly = sjly;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<?> getItemList() {
		return itemList;
	}

	public void setItemList(List<?> itemList) {
		this.itemList = itemList;
	}

	@Override
	public String toString() {
		return "WjtjtjModel [id=" + id + ", name=" + name + ", sjly=" + sjly + ", key=" + key + ", value=" + value
				+ ", type=" + type + ", itemList=" + itemList + "]";
	}
}
