package com.woshidaniu.tjcx.dao.entites;

import java.io.Serializable;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计图表
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:00:18
 * @版本： V1.0
 * @修改记录:
 */
public class TjtbModel  implements Serializable{
	private static final long serialVersionUID = 7532662280629320158L;
	private String hxlHead;// 
	private String hxlContent;// 
	private String zxlContent;// 

	private String hxlContentCode;// 
	private String zxlContentCode;// 
	private String tjxJson;// 
	private String hxlhjContentJson;// 
	private String zxlhjContentJson;// 
	private String zxlHead;// 
	private String zxlHeadCode;// 
	private String contentJson;
	private String gnmk;
	private String xmdm;// 项目ID
	private String gltj;// 过滤条件：转化后的sql语句
	private String gltjmc;// 过滤条件名称：供查询列表使用
	private String curBbl;// 当前选中报表列sql
	private String curBblmc;

	public TjtbModel() {
		super();
	}

	public String getHxlHead() {
		return hxlHead;
	}

	public void setHxlHead(String hxlHead) {
		this.hxlHead = hxlHead;
	}

	public String getHxlContent() {
		return hxlContent;
	}

	public void setHxlContent(String hxlContent) {
		this.hxlContent = hxlContent;
	}

	public String getTjxJson() {
		return tjxJson;
	}

	public void setTjxJson(String tjxJson) {
		this.tjxJson = tjxJson;
	}

	public String getHxlhjContentJson() {
		return hxlhjContentJson;
	}

	public void setHxlhjContentJson(String hxlhjContentJson) {
		this.hxlhjContentJson = hxlhjContentJson;
	}

	public String getZxlhjContentJson() {
		return zxlhjContentJson;
	}

	public void setZxlhjContentJson(String zxlhjContentJson) {
		this.zxlhjContentJson = zxlhjContentJson;
	}

	public String getZxlHead() {
		return zxlHead;
	}

	public void setZxlHead(String zxlHead) {
		this.zxlHead = zxlHead;
	}

	public String getContentJson() {
		return contentJson;
	}

	public void setContentJson(String contentJson) {
		this.contentJson = contentJson;
	}

	public String getZxlContent() {
		return zxlContent;
	}

	public void setZxlContent(String zxlContent) {
		this.zxlContent = zxlContent;
	}

	public String getGnmk() {
		return gnmk;
	}

	public void setGnmk(String gnmk) {
		this.gnmk = gnmk;
	}

	public String getXmdm() {
		return xmdm;
	}

	public void setXmdm(String xmdm) {
		this.xmdm = xmdm;
	}

	public String getGltj() {
		return gltj;
	}

	public void setGltj(String gltj) {
		this.gltj = gltj;
	}

	public String getGltjmc() {
		return gltjmc;
	}

	public void setGltjmc(String gltjmc) {
		this.gltjmc = gltjmc;
	}

	public String getCurBbl() {
		return curBbl;
	}

	public void setCurBbl(String curBbl) {
		this.curBbl = curBbl;
	}

	public String getCurBblmc() {
		return curBblmc;
	}

	public void setCurBblmc(String curBblmc) {
		this.curBblmc = curBblmc;
	}

	public String getHxlContentCode() {
		return hxlContentCode;
	}

	public void setHxlContentCode(String hxlContentCode) {
		this.hxlContentCode = hxlContentCode;
	}

	public String getZxlContentCode() {
		return zxlContentCode;
	}

	public void setZxlContentCode(String zxlContentCode) {
		this.zxlContentCode = zxlContentCode;
	}

	public String getZxlHeadCode() {
		return zxlHeadCode;
	}

	public void setZxlHeadCode(String zxlHeadCode) {
		this.zxlHeadCode = zxlHeadCode;
	}

}
