package com.woshidaniu.pwdmgr.dao.entities;

import java.util.ArrayList;
import java.util.List;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 * @类名称 ： VerifiModel.java
 * @类描述 ：账号核实字段表对象模型
 * @创建人 ：kangzhidong
 * @创建时间 ：2017年4月12日 下午2:57:26
 * @修改人 ：
 * @修改时间 ：
 * @版本号 :v1.0
 */
@SuppressWarnings("serial")
public class VerifiModel extends ModelBase {

	/**
	 * 账号核实字段表ID
	 */
	protected String id;
	/**
	 * 账号核实字段名称
	 */
	protected String name;
	/**
	 * 账号核实字段Label名称
	 */
	protected String label;
	/**
	 * 账号核实字段描述，作为提示信息
	 */
	protected String desc;
	/**
	 * 账号核实字段校验规则
	 */
	protected String rules;
	/**
	 * 账号核实字段是否必填，1：是，0：否
	 */
	protected String required;
	/**
	 * 账号核实字段启用状态标记，1：启用，0：停用
	 */
	protected String status;
	/**
	 * 集合便于接收页面参数
	 */
	protected List<VerifiModel> list = new ArrayList<VerifiModel>();

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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<VerifiModel> getList() {
		return list;
	}

	public void setList(List<VerifiModel> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Verifi [id=" + id + ", name=" + name + ", label=" + label
				+ ", strategy_desc=" + desc + ", rules=" + rules + ", required=" + required
				+ ", status=" + status + "]";
	}

}
