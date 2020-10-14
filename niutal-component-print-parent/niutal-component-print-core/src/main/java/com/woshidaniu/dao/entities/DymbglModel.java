package com.woshidaniu.dao.entities;

import org.apache.ibatis.type.Alias;

import com.woshidaniu.common.query.ModelBase;
import com.woshidaniu.search.core.SearchModel;

/**
 * 打印模板管理model
 * @author guoqb[1127]
 * @date 2017-2-27 16:15:40
 */
@Alias("dymbglModel")
public class DymbglModel extends ModelBase{
	private static final long serialVersionUID = 1L;
	private SearchModel searchModel;
	private String id;					//主键
	private String mc;					//打印模板名称
	private String nr;					//打印模板内容
	private String sm;					//打印模板说明
	private String qyzt;				//启用状态
	private String mblxdm;				//模板类型代码
	private String zgh;					//职工号
	private String bg;					//背景图

	/***************非业务字段 start**************/
	private String mblxmc;				//模板类型名称
	private String oldmc;				//打印模板名称old
	/***************非业务字段 end****************/

	public SearchModel getSearchModel() {
		return searchModel;
	}
	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getNr() {
		return nr;
	}
	public void setNr(String nr) {
		this.nr = nr;
	}
	public String getSm() {
		return sm;
	}
	public void setSm(String sm) {
		this.sm = sm;
	}
	public String getQyzt() {
		return qyzt;
	}
	public void setQyzt(String qyzt) {
		this.qyzt = qyzt;
	}
	public String getMblxdm() {
		return mblxdm;
	}
	public void setMblxdm(String mblxdm) {
		this.mblxdm = mblxdm;
	}
	public String getZgh() {
		return zgh;
	}
	public void setZgh(String zgh) {
		this.zgh = zgh;
	}
	public String getMblxmc() {
		return mblxmc;
	}
	public void setMblxmc(String mblxmc) {
		this.mblxmc = mblxmc;
	}
	public String getOldmc() {
		return oldmc;
	}
	public void setOldmc(String oldmc) {
		this.oldmc = oldmc;
	}
	public String getBg() {
		return bg;
	}
	public void setBg(String bg) {
		this.bg = bg;
	}
}