package com.woshidaniu.tjcx.dao.entites;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 快照设置表
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:00:18
 * @版本： V1.0
 * @修改记录:
 */
public class KzszModel extends ModelBase {
	private static final long serialVersionUID = -3934154393342948142L;
	private String kzszid;// 设置快照ID
	private String xmdm;// 项目ID
	private String szmc;// 设置名称
	private String gltj;// 过滤条件：转化后的sql语句
	private String newGltj;// 过滤条件：转化后的sql语句
	private String oldGltj;// 过滤条件：转化后的sql语句
	private String bbhxl;// 报表横向列：保存字段，以逗号分割
	private String bbzxl;// 报表纵向列：保存字段，以逗号分割
	private String tsx;// 特殊项：保存字段，以逗号分割"
	private String czy;// 操作员
	private String tjsj;// 添加时间
	private String gltjmc;// 过滤条件名称：供查询列表使用
	private String gnmk;
	private String tjlbFhbs;// 返回标识，针对统计查询返回，标识,1:为返回状态
	private String kzlx;// 快照类型
	private String sfgy;// 是否公有
	private String curBbl;// 当前选中报表列sql
	private String kzjsr;// 快照接收人
	private String zdxsms;// 字段显示模式1:仅在功能首页显示 2:仅在列表页面显示

	private String kzms;//快照描述
	
	private int limit = -1; //统计数据限制
	
	public KzszModel() {
		super();
	}

	public KzszModel(String xmdm) {
		super();
		this.xmdm = xmdm;
	}

	public String getKzszid() {
		return kzszid;
	}

	public void setKzszid(String kzszid) {
		this.kzszid = kzszid;
	}

	public String getXmdm() {
		return xmdm;
	}

	public void setXmdm(String xmdm) {
		this.xmdm = xmdm;
	}

	public String getSzmc() {
		return szmc;
	}

	public void setSzmc(String szmc) {
		this.szmc = szmc;
	}

	public String getGltj() {
		return gltj;
	}

	public void setGltj(String gltj) {
		this.gltj = gltj;
	}

	public String getBbhxl() {
		return bbhxl;
	}

	public void setBbhxl(String bbhxl) {
		this.bbhxl = bbhxl;
	}

	public String getBbzxl() {
		return bbzxl;
	}

	public void setBbzxl(String bbzxl) {
		this.bbzxl = bbzxl;
	}

	public String getTsx() {
		return tsx;
	}

	public void setTsx(String tsx) {
		this.tsx = tsx;
	}

	public String getCzy() {
		return czy;
	}

	public void setCzy(String czy) {
		this.czy = czy;
	}

	public String getTjsj() {
		return tjsj;
	}

	public void setTjsj(String tjsj) {
		this.tjsj = tjsj;
	}

	public String getGltjmc() {
		return gltjmc;
	}

	public void setGltjmc(String gltjmc) {
		this.gltjmc = gltjmc;
	}

	public String getGnmk() {
		return gnmk;
	}

	public void setGnmk(String gnmk) {
		this.gnmk = gnmk;
	}

	public String getTjlbFhbs() {
		return tjlbFhbs;
	}

	public void setTjlbFhbs(String tjlbFhbs) {
		this.tjlbFhbs = tjlbFhbs;
	}

	public String getKzlx() {
		return kzlx;
	}

	public void setKzlx(String kzlx) {
		this.kzlx = kzlx;
	}

	public String getSfgy() {
		return sfgy;
	}

	public void setSfgy(String sfgy) {
		this.sfgy = sfgy;
	}

	public String getKzjsr() {
		return kzjsr;
	}

	public void setKzjsr(String kzjsr) {
		this.kzjsr = kzjsr;
	}

	public String getCurBbl() {
		return curBbl;
	}

	public void setCurBbl(String curBbl) {
		this.curBbl = curBbl;
	}

	public String getNewGltj() {
		return newGltj;
	}

	public void setNewGltj(String newGltj) {
		this.newGltj = newGltj;
	}

	public String getOldGltj() {
		return oldGltj;
	}

	public void setOldGltj(String oldGltj) {
		this.oldGltj = oldGltj;
	}

	public String getZdxsms() {
		return zdxsms;
	}

	public void setZdxsms(String zdxsms) {
		this.zdxsms = zdxsms;
	}

	public String getKzms() {
		return kzms;
	}

	public void setKzms(String kzms) {
		this.kzms = kzms;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
