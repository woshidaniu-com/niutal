/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dataauth.dao.entities;

import java.io.Serializable;

public class GroupItem implements Serializable{
	private static final long serialVersionUID = 5899415734933283518L;
	/**权限组标识*/
	private String groupId;
	/**选项值*/
	private String itemKey;
	/**显示名称*/
	private String itemName;
	
	/**显示名称拼音首字母*/
	private String firstPinYin;
	private Integer has;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getItemKey() {
		return itemKey;
	}
	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getFirstPinYin() {
		return firstPinYin;
	}
	public void setFirstPinYin(String firstPinYin) {
		this.firstPinYin = firstPinYin;
	}
	public Integer getHas() {
		return has;
	}
	public void setHas(Integer has) {
		this.has = has;
	}
}
