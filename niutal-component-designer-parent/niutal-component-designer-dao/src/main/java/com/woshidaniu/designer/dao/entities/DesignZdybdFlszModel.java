package com.woshidaniu.designer.dao.entities;

import java.util.List;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 *@类名称:DesignZdybdFlszModel.java
 *@类描述：自定义表单：分类设置信息对象Model
 *@创建人：kangzhidong
 *@创建时间：Oct 27, 2015 10:19:47 AM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignZdybdFlszModel extends ModelBase {
	
	/*===============自定义表单:分类设置信息========================*/
	
	private String flszid;// 分类设置id
	private String gndm;// 功能代码
	private String flflszid;// 父类id
	private String flmc;// 分类名称
	private String flsm;// 分类说明
	private String bdls;// 表单列数 为表单整体字段列数设置，不包含照片列(由程序自动计算)
	private String bdms;// 表单模式 1:单条记录模式:2:多条记录模式;3:功能自定义代码
	private String sfqy;// 是否启用 1:是,0:否
	private String sfzk;// 是否展开 1:是,0:否
	private String sfmk;// 是否模块 1:是,0:否
	private String bdszz;// 表单设置值
	private String xsxx;// 显示顺序
	private String gnlx;// 功能类型
	private String yzsz;// 验证设置
	private String bhmk;// 包含模块 1:包含模块;0:不包含模块
	//当前分类下定义的字段
	private List<DesignZdybdZddyModel>  element_zddy_list;
	
	
	/**
	 * @return the flszid
	 */
	public String getFlszid() {
		return flszid;
	}
	/**
	 * @param flszid the flszid to set
	 */
	public void setFlszid(String flszid) {
		this.flszid = flszid;
	}
	/**
	 * @return the gndm
	 */
	public String getGndm() {
		return gndm;
	}
	/**
	 * @param gndm the gndm to set
	 */
	public void setGndm(String gndm) {
		this.gndm = gndm;
	}
	/**
	 * @return the flflszid
	 */
	public String getFlflszid() {
		return flflszid;
	}
	/**
	 * @param flflszid the flflszid to set
	 */
	public void setFlflszid(String flflszid) {
		this.flflszid = flflszid;
	}
	/**
	 * @return the flmc
	 */
	public String getFlmc() {
		return flmc;
	}
	/**
	 * @param flmc the flmc to set
	 */
	public void setFlmc(String flmc) {
		this.flmc = flmc;
	}
	/**
	 * @return the flsm
	 */
	public String getFlsm() {
		return flsm;
	}
	/**
	 * @param flsm the flsm to set
	 */
	public void setFlsm(String flsm) {
		this.flsm = flsm;
	}
	/**
	 * @return the bdls
	 */
	public String getBdls() {
		return bdls;
	}
	/**
	 * @param bdls the bdls to set
	 */
	public void setBdls(String bdls) {
		this.bdls = bdls;
	}
	/**
	 * @return the bdms
	 */
	public String getBdms() {
		return bdms;
	}
	/**
	 * @param bdms the bdms to set
	 */
	public void setBdms(String bdms) {
		this.bdms = bdms;
	}
	/**
	 * @return the sfqy
	 */
	public String getSfqy() {
		return sfqy;
	}
	/**
	 * @param sfqy the sfqy to set
	 */
	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}
	/**
	 * @return the sfzk
	 */
	public String getSfzk() {
		return sfzk;
	}
	/**
	 * @param sfzk the sfzk to set
	 */
	public void setSfzk(String sfzk) {
		this.sfzk = sfzk;
	}
	/**
	 * @return the sfmk
	 */
	public String getSfmk() {
		return sfmk;
	}
	/**
	 * @param sfmk the sfmk to set
	 */
	public void setSfmk(String sfmk) {
		this.sfmk = sfmk;
	}
	/**
	 * @return the bdszz
	 */
	public String getBdszz() {
		return bdszz;
	}
	/**
	 * @param bdszz the bdszz to set
	 */
	public void setBdszz(String bdszz) {
		this.bdszz = bdszz;
	}
	/**
	 * @return the xsxx
	 */
	public String getXsxx() {
		return xsxx;
	}
	/**
	 * @param xsxx the xsxx to set
	 */
	public void setXsxx(String xsxx) {
		this.xsxx = xsxx;
	}
	/**
	 * @return the gnlx
	 */
	public String getGnlx() {
		return gnlx;
	}
	/**
	 * @param gnlx the gnlx to set
	 */
	public void setGnlx(String gnlx) {
		this.gnlx = gnlx;
	}
	/**
	 * @return the yzsz
	 */
	public String getYzsz() {
		return yzsz;
	}
	/**
	 * @param yzsz the yzsz to set
	 */
	public void setYzsz(String yzsz) {
		this.yzsz = yzsz;
	}
	/**
	 * @return the bhmk
	 */
	public String getBhmk() {
		return bhmk;
	}
	/**
	 * @param bhmk the bhmk to set
	 */
	public void setBhmk(String bhmk) {
		this.bhmk = bhmk;
	}
	/**
	 * @return the element_zddy_list
	 */
	public List<DesignZdybdZddyModel> getElement_zddy_list() {
		return element_zddy_list;
	}
	/**
	 * @param elementZddyList the element_zddy_list to set
	 */
	public void setElement_zddy_list(List<DesignZdybdZddyModel> elementZddyList) {
		element_zddy_list = elementZddyList;
	}

}
