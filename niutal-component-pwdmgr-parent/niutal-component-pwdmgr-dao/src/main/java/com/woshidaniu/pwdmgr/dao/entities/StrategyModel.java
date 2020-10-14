package com.woshidaniu.pwdmgr.dao.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 *@类名称		： StrategyModel.java
 *@类描述		：密码找回策略Model对象
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月10日 下午1:28:10
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@SuppressWarnings("serial")
public class StrategyModel extends ModelBase {

	/**
	 * 策略表ID
	 */
	protected String id;

	/**
	 * 策略名称，该名称必须与策略实现对象name方法提供的返回值一致
	 */
	protected String name;

	/**
	 * 策略描述，简述该策略的实现方式
	 */
	protected String desc;

	/**
	 * 策略状态标记，1：启用(该状态下系统必要有与name对应的策略实现,才能有效)，0：停用
	 */
	protected String status;


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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Strategy [id=" + id + ", name=" +  name + ", desc=" + desc + ", status=" + status + "]";
	}

}
