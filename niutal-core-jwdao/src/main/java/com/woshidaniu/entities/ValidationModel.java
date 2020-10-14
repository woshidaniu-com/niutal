package com.woshidaniu.entities;

import java.util.List;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 *@类名称:ValidationModel.java
 *@类描述：公共验证模型
 *@创建人：kangzhidong
 *@创建时间：2014-6-17 下午08:38:45
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class ValidationModel extends ModelBase {

	/* 主键名称 */
	private String filed_name;
	/* 主键值 */
	private String filed_value;
	/* 主键原值：更新时用到 */
	private String old_filed_value;
	/* 其他级联字段 */
	private List<PairModel> filed_list;
	private List<PairModel> old_filed_list;
	/* 表名称 */
	private String table;
	/* 邮件根域名*/
	private String yjgym;
	/* 邮件账号*/
	private String yjzh;
	
	public String getYjzh() {
		return yjzh;
	}

	public void setYjzh(String yjzh) {
		this.yjzh = yjzh;
	}

	public String getYjgym() {
		return yjgym;
	}

	public void setYjgym(String yjgym) {
		this.yjgym = yjgym;
	}

	public String getFiled_name() {
		return filed_name;
	}

	public void setFiled_name(String filedName) {
		filed_name = filedName;
	}

	public String getFiled_value() {
		return filed_value;
	}

	public void setFiled_value(String filedValue) {
		filed_value = filedValue;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<PairModel> getFiled_list() {
		return filed_list;
	}

	public void setFiled_list(List<PairModel> filedList) {
		filed_list = filedList;
	}

	public String getOld_filed_value() {
		return old_filed_value;
	}

	public void setOld_filed_value(String oldFiledValue) {
		old_filed_value = oldFiledValue;
	}

	public List<PairModel> getOld_filed_list() {
		return old_filed_list;
	}

	public void setOld_filed_list(List<PairModel> oldFiledList) {
		old_filed_list = oldFiledList;
	}

	
	
}
