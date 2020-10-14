/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.entites;

import java.io.Serializable;

import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.search.core.SearchModel;

/**
 * 问卷答卷状态
 */
public class WjdjztModel implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String wjid;
	private String wjmc;
	private String lxid;
	private String zjz;
	private String djzt;
	private String xh;
	private String xm;
	private String xbdm;
	private String mzdm;
	private String zzmmdm;
	private String sfzh;
	private String kzzd1;
	private String yhm;
	private String njdm_id;
	private String njmc;
	private String bjdm_id;
	private String bjmc;
	private String bmdm_id;
	private String bmmc;
	private String zydm_id;
	private String zymc;
	private String xbmc;
	private String zzmmmc;
	private String drjid;
	private String wjfz;
	
	private int startRow;
	private int endRow;
	
	private SearchModel searchModel = new SearchModel();
	
	public QueryModel queryModel;
	
	public SearchModel getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}

	public String getWjid() {
		return wjid;
	}

	public void setWjid(String wjid) {
		this.wjid = wjid;
	}

	public String getWjmc() {
		return wjmc;
	}

	public void setWjmc(String wjmc) {
		this.wjmc = wjmc;
	}

	public String getLxid() {
		return lxid;
	}

	public void setLxid(String lxid) {
		this.lxid = lxid;
	}

	public String getZjz() {
		return zjz;
	}

	public void setZjz(String zjz) {
		this.zjz = zjz;
	}

	public String getDjzt() {
		return djzt;
	}

	public void setDjzt(String djzt) {
		this.djzt = djzt;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getXbdm() {
		return xbdm;
	}

	public void setXbdm(String xbdm) {
		this.xbdm = xbdm;
	}

	public String getMzdm() {
		return mzdm;
	}

	public void setMzdm(String mzdm) {
		this.mzdm = mzdm;
	}

	public String getZzmmdm() {
		return zzmmdm;
	}

	public void setZzmmdm(String zzmmdm) {
		this.zzmmdm = zzmmdm;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getKzzd1() {
		return kzzd1;
	}

	public void setKzzd1(String kzzd1) {
		this.kzzd1 = kzzd1;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getNjdm_id() {
		return njdm_id;
	}

	public void setNjdm_id(String njdm_id) {
		this.njdm_id = njdm_id;
	}

	public String getNjmc() {
		return njmc;
	}

	public void setNjmc(String njmc) {
		this.njmc = njmc;
	}

	public String getBjdm_id() {
		return bjdm_id;
	}

	public void setBjdm_id(String bjdm_id) {
		this.bjdm_id = bjdm_id;
	}

	public String getBjmc() {
		return bjmc;
	}

	public void setBjmc(String bjmc) {
		this.bjmc = bjmc;
	}

	public String getBmdm_id() {
		return bmdm_id;
	}

	public void setBmdm_id(String bmdm_id) {
		this.bmdm_id = bmdm_id;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getZydm_id() {
		return zydm_id;
	}

	public void setZydm_id(String zydm_id) {
		this.zydm_id = zydm_id;
	}

	public String getZymc() {
		return zymc;
	}

	public void setZymc(String zymc) {
		this.zymc = zymc;
	}

	public String getXbmc() {
		return xbmc;
	}

	public void setXbmc(String xbmc) {
		this.xbmc = xbmc;
	}

	public String getZzmmmc() {
		return zzmmmc;
	}

	public void setZzmmmc(String zzmmmc) {
		this.zzmmmc = zzmmmc;
	}

	public String getDrjid() {
		return drjid;
	}

	public void setDrjid(String drjid) {
		this.drjid = drjid;
	}

	public String getWjfz() {
		return wjfz;
	}

	public void setWjfz(String wjfz) {
		this.wjfz = wjfz;
	}

	public QueryModel getQueryModel() {
		return queryModel;
	}

	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	@Override
	public String toString() {
		return "WjdjztModel [wjid=" + wjid + ", wjmc=" + wjmc + ", lxid=" + lxid + ", zjz=" + zjz + ", djzt=" + djzt
				+ ", xh=" + xh + ", xm=" + xm + ", xbdm=" + xbdm + ", mzdm=" + mzdm + ", zzmmdm=" + zzmmdm + ", sfzh="
				+ sfzh + ", kzzd1=" + kzzd1 + ", yhm=" + yhm + ", njdm_id=" + njdm_id + ", njmc=" + njmc + ", bjdm_id="
				+ bjdm_id + ", bjmc=" + bjmc + ", bmdm_id=" + bmdm_id + ", bmmc=" + bmmc + ", zydm_id=" + zydm_id
				+ ", zymc=" + zymc + ", xbmc=" + xbmc + ", zzmmmc=" + zzmmmc + ", drjid=" + drjid + ", wjfz=" + wjfz
				+ ", startRow=" + startRow + ", endRow=" + endRow + ", searchModel=" + searchModel + ", queryModel="
				+ queryModel + "]";
	}
}
