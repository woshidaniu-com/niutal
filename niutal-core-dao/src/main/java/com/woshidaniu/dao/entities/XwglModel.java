package com.woshidaniu.dao.entities;

import java.io.Serializable;

import com.woshidaniu.common.annotation.HTMLEncode;
import com.woshidaniu.common.query.ModelBase;
import com.woshidaniu.search.core.SearchModel;

public class XwglModel extends ModelBase implements Serializable{

	private static final long serialVersionUID = -6482693152174114206L;
	
	private String xwbh; // 标题
	@HTMLEncode
	private String xwbt; // 标题
	private String fbsj;// 发布时间
	private String fbr;// 发布人
	private String fbdx;// 发布对象
	private String ydqx;// 阅读权限
	private String fbnr;// 新闻内容
	private String xwnr;// 新闻内容
	private String sffb;// 是否发布
	private String sfzd;//新闻是否置顶
	private String fbrxm;
	
	private String[] fbdxs;	//发布对象集合
	private String ydqxmc;// 阅读权限名称
	private String fbdxmcs;	//发布对象名称集合
	private String sfzx;	//是否最新
	
	private String sfzy; //是否是重要新闻
	private SearchModel searchModel;
	
	
	
	public SearchModel getSearchModel() {
		return searchModel;
	}
	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}
	public String getFbrxm() {
		return fbrxm;
	}
	public void setFbrxm(String fbrxm) {
		this.fbrxm = fbrxm;
	}
	public String getXwnr() {
		return xwnr;
	}
	public void setXwnr(String xwnr) {
		this.xwnr = xwnr;
	}
	public String getXwbh() {
		return xwbh;
	}
	public void setXwbh(String xwbh) {
		this.xwbh = xwbh;
	}
	public String getFbdx() {
		return fbdx;
	}
	public void setFbdx(String fbdx) {
		this.fbdx = fbdx;
	}
	public String getXwbt() {
		return xwbt;
	}
	public void setXwbt(String xwbt) {
		this.xwbt = xwbt;
	}
	public String getFbsj() {
		return fbsj;
	}
	public void setFbsj(String fbsj) {
		this.fbsj = fbsj;
	}
	public String getFbr() {
		return fbr;
	}
	public void setFbr(String fbr) {
		this.fbr = fbr;
	}
	public String getFbnr() {
		return fbnr;
	}
	public void setFbnr(String fbnr) {
		this.fbnr = fbnr;
	}
	public String getSffb() {
		return sffb;
	}
	public void setSffb(String sffb) {
		this.sffb = sffb;
	}
	public String getSfzd() {
		return sfzd;
	}
	public void setSfzd(String sfzd) {
		this.sfzd = sfzd;
	}
	public String[] getFbdxs() {
		return fbdxs;
	}
	public void setFbdxs(String[] fbdxs) {
		this.fbdxs = fbdxs;
	}
	public String getFbdxmcs() {
		return fbdxmcs;
	}
	public void setFbdxmcs(String fbdxmcs) {
		this.fbdxmcs = fbdxmcs;
	}
	public String getSfzx() {
		return sfzx;
	}
	public void setSfzx(String sfzx) {
		this.sfzx = sfzx;
	}
	public String getSfzy() {
		return sfzy;
	}
	public void setSfzy(String sfzy) {
		this.sfzy = sfzy;
	}
	public String getYdqx() {
		return ydqx;
	}
	public void setYdqx(String ydqx) {
		this.ydqx = ydqx;
	}
	public String getYdqxmc() {
		return ydqxmc;
	}
	public void setYdqxmc(String ydqxmc) {
		this.ydqxmc = ydqxmc;
	}

}
