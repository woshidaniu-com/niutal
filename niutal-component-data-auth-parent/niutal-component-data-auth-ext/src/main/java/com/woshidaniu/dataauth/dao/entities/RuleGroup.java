/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dataauth.dao.entities;

import java.util.HashMap;
import java.util.Map;

import com.woshidaniu.common.query.ModelBase;


public class RuleGroup extends ModelBase{
	private static final long serialVersionUID = -3753849184201122388L;
	/**标识*/
	private String groupId;
	/**数据权限规则组代码*/
	private String groupCode;
	/**数据权限规则组名称*/
	private String groupName;
	/**数据权限规则组类型(user/role)*/
	private String groupType;
	/**包含的选项(一条返回GroupItem对象结果集的sql)*/
	private String selectItem;
	
	/**用户名*/
	private String yhm;
	/**角色代码*/
	private String jsdm;
	/**选项值(key集合)*/
	private String itemValues;
	
	private Integer has;
	
	public Map<String, String> toMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("groupId", groupId);map.put("groupCode", groupCode);map.put("groupName", groupName);map.put("groupType", groupType);map.put("selectItem", selectItem);
		return map;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getSelectItem() {
		return selectItem;
	}

	public void setSelectItem(String selectItem) {
		this.selectItem = selectItem;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getJsdm() {
		return jsdm;
	}

	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}

	public String getItemValues() {
		return itemValues;
	}

	public void setItemValues(String itemValues) {
		this.itemValues = itemValues;
	}

	public Integer getHas() {
		return has;
	}

	public void setHas(Integer has) {
		this.has = has;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupCode == null) ? 0 : groupCode.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + ((groupType == null) ? 0 : groupType.hashCode());
		result = prime * result + ((has == null) ? 0 : has.hashCode());
		result = prime * result + ((itemValues == null) ? 0 : itemValues.hashCode());
		result = prime * result + ((jsdm == null) ? 0 : jsdm.hashCode());
		result = prime * result + ((selectItem == null) ? 0 : selectItem.hashCode());
		result = prime * result + ((yhm == null) ? 0 : yhm.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleGroup other = (RuleGroup) obj;
		if (groupCode == null) {
			if (other.groupCode != null)
				return false;
		} else if (!groupCode.equals(other.groupCode))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (groupType == null) {
			if (other.groupType != null)
				return false;
		} else if (!groupType.equals(other.groupType))
			return false;
		if (has == null) {
			if (other.has != null)
				return false;
		} else if (!has.equals(other.has))
			return false;
		if (itemValues == null) {
			if (other.itemValues != null)
				return false;
		} else if (!itemValues.equals(other.itemValues))
			return false;
		if (jsdm == null) {
			if (other.jsdm != null)
				return false;
		} else if (!jsdm.equals(other.jsdm))
			return false;
		if (selectItem == null) {
			if (other.selectItem != null)
				return false;
		} else if (!selectItem.equals(other.selectItem))
			return false;
		if (yhm == null) {
			if (other.yhm != null)
				return false;
		} else if (!yhm.equals(other.yhm))
			return false;
		return true;
	}
}
