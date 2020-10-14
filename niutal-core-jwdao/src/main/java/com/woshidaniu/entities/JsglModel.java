package com.woshidaniu.entities;

import java.util.List;

import com.woshidaniu.basicutils.BooleanUtils;

/**
 * 
 * 
 * 类名称：JsglModel 
 * 类描述： 角色管理MODEL 
 * 创建人：Administrator 
 * 创建时间：2012-4-1 下午05:22:38
 * 修改人：Administrator 
 * 修改时间：2012-4-1 下午05:22:38 
 * 修改备注：
 * 
 * @version
 * 
 */
public class JsglModel extends CommonModel {

	private static final long serialVersionUID = 8666379269724783798L;
	/**
	 * 功能模块代码
	 */
	private String gwjbdm;
	/**
	 * 功能模块名称
	 */
	private String gwjbmc;
	/**
	 * 父级功能代码
	 */
	private String gnmkdm;
	/**
	 * 角色功能代码
	 */
	private String jsgnmkdm;
	/**
	 * 父级功能名称
	 */
	private String gnmkmc;
	/**
	 * 显示顺序
	 */
	private String xssx;
	/**
	 * 对应页面
	 */
	private String dyym;
	/**
	 * 功能代码
	 */
	private String jsmc;
	private String sfksc;
	private String sfmrjs;
	/**
	 * 是否可二级授权(1是0否,默认为0)
	 */
	private String sfejsq;
	/**
	 * 角色公用标记1公用;0非公用角色
	 */
	private String jsgybj;
	private String jsjb;	// 角色级别
	private String yhnum;	//用户数量
	private String zjsnum;	//子角色数量
	private String fjgndm;	// 父级功能代码
	private String fjsdm;	// 父级角色代码
	private String fjsmc;	// 父级角色名称
	private String fjsjb;	// 父级角色级别
	private String jslxdm; 	// 角色类型代码
	private String jslxmc; 	// 角色类型名称
	private String jssm;
	private String pkValue;
	private String len;
	private String czdm;
	private String czmc;
	
	private String[] gnmkdmcbv;
	private String[] gnczcbv;
	private String[] btns;
	private String[] yhCbv;
	private String color;
	private String sffpyh;
	private String[] sjgndmcbv;
	private String lv;// 级别
	private String childSize;
	private String sjfwzmc;
	private String yhids; // 被排除的用户id
	private String xy_id; // 学院
	private String zy_id; // 专业
	private String nj_id; // 年级
	private String bj_id; // 班级
	private String ids; // ID集合
	private List<String> list; // list集合
	private String level;// 角色级别 供treegrid 生成json使用（因为level在数据库里是关键字不能使用）
	private String leafStr = "false";// 是否叶子节点
	@SuppressWarnings("unused")
	private boolean leaf = false;// 是否叶子节点
	private boolean expanded = false;// 默认展开
	private boolean loaded = true;
	private String jsdlyxj; // 角色登录优先级

	public String[] getSjgndmcbv() {
		return sjgndmcbv;
	}

	public void setSjgndmcbv(String[] sjgndmcbv) {
		this.sjgndmcbv = sjgndmcbv;
	}

	public String getSffpyh() {
		return sffpyh;
	}

	public void setSffpyh(String sffpyh) {
		this.sffpyh = sffpyh;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getYhnum() {
		return yhnum;
	}

	public void setYhnum(String yhnum) {
		this.yhnum = yhnum;
	}

	public String[] getYhCbv() {
		return yhCbv;
	}

	public void setYhCbv(String[] yhCbv) {
		this.yhCbv = yhCbv;
	}



	public String[] getGnmkdmcbv() {
		return gnmkdmcbv;
	}

	public void setGnmkdmcbv(String[] gnmkdmcbv) {
		this.gnmkdmcbv = gnmkdmcbv;
	}

	public String[] getGnczcbv() {
		return gnczcbv;
	}

	public void setGnczcbv(String[] gnczcbv) {
		this.gnczcbv = gnczcbv;
	}

	public String getCzdm() {
		return czdm;
	}

	public void setCzdm(String czdm) {
		this.czdm = czdm;
	}

	public String getCzmc() {
		return czmc;
	}

	public void setCzmc(String czmc) {
		this.czmc = czmc;
	}

	

	public String getFjgndm() {
		return fjgndm;
	}

	public void setFjgndm(String fjgndm) {
		this.fjgndm = fjgndm;
	}



	public String getLen() {
		return len;
	}

	public void setLen(String len) {
		this.len = len;
	}

	
	public String getPkValue() {
		return pkValue;
	}

	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}

	public String getJsmc() {
		return jsmc;
	}

	
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}


	public String getJssm() {
		return jssm;
	}

	public void setJssm(String jssm) {
		this.jssm = jssm;
	}

	

	public String getSfksc() {
		return sfksc;
	}

	public String getChildSize() {
		return childSize;
	}

	public void setChildSize(String childSize) {
		this.childSize = childSize;
	}

	public void setSfksc(String sfksc) {
		this.sfksc = sfksc;
	}


	public String getLv() {
		return lv;
	}

	public void setLv(String lv) {
		this.lv = lv;
	}

	public String[] getBtns() {
		return btns;
	}

	public void setBtns(String[] btns) {
		this.btns = btns;
	}

	public String getSfmrjs() {
		return sfmrjs;
	}

	public void setSfmrjs(String sfmrjs) {
		this.sfmrjs = sfmrjs;
	}


	public String getXy_id() {
		return xy_id;
	}

	public void setXy_id(String xyId) {
		xy_id = xyId;
	}

	public String getZy_id() {
		return zy_id;
	}

	public void setZy_id(String zyId) {
		zy_id = zyId;
	}

	public String getNj_id() {
		return nj_id;
	}

	public void setNj_id(String njId) {
		nj_id = njId;
	}

	public String getBj_id() {
		return bj_id;
	}

	public void setBj_id(String bjId) {
		bj_id = bjId;
	}

	public String getYhids() {
		return yhids;
	}

	public void setYhids(String yhids) {
		this.yhids = yhids;
	}

	public String getSjfwzmc() {
		return sjfwzmc;
	}

	public void setSjfwzmc(String sjfwzmc) {
		this.sjfwzmc = sjfwzmc;
	}

	public String getSfejsq() {
		return sfejsq;
	}

	public void setSfejsq(String sfejsq) {
		this.sfejsq = sfejsq;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}



	public String getJsjb() {
		return jsjb;
	}

	public void setJsjb(String jsjb) {
		this.jsjb = jsjb;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public String getFjsdm() {
		return fjsdm;
	}

	public void setFjsdm(String fjsdm) {
		this.fjsdm = fjsdm;
	}

	public String getLeafStr() {
		return leafStr;
	}

	public void setLeafStr(String leafStr) {
		this.leafStr = leafStr;
	}

	public boolean isLeaf() {
		return BooleanUtils.parse(getLeafStr());
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getFjsmc() {
		return fjsmc;
	}

	public void setFjsmc(String fjsmc) {
		this.fjsmc = fjsmc;
	}

	public String getFjsjb() {
		return fjsjb;
	}

	public void setFjsjb(String fjsjb) {
		this.fjsjb = fjsjb;
	}

	public String getJslxdm() {
		return jslxdm;
	}

	public void setJslxdm(String jslxdm) {
		this.jslxdm = jslxdm;
	}

	public String getJslxmc() {
		return jslxmc;
	}

	public void setJslxmc(String jslxmc) {
		this.jslxmc = jslxmc;
	}

	public String getJsgybj() {
		return jsgybj;
	}

	public void setJsgybj(String jsgybj) {
		this.jsgybj = jsgybj;
	}

	public String getGnmkdm() {
		return gnmkdm;
	}

	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}

	public String getGwjbdm() {
		return gwjbdm;
	}

	public void setGwjbdm(String gwjbdm) {
		this.gwjbdm = gwjbdm;
	}

	public String getGwjbmc() {
		return gwjbmc;
	}

	public void setGwjbmc(String gwjbmc) {
		this.gwjbmc = gwjbmc;
	}

	public String getGnmkmc() {
		return gnmkmc;
	}

	public void setGnmkmc(String gnmkmc) {
		this.gnmkmc = gnmkmc;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getDyym() {
		return dyym;
	}

	public void setDyym(String dyym) {
		this.dyym = dyym;
	}

	public String getZjsnum() {
		return zjsnum;
	}

	public void setZjsnum(String zjsnum) {
		this.zjsnum = zjsnum;
	}

	public String getJsgnmkdm() {
		return jsgnmkdm;
	}

	public void setJsgnmkdm(String jsgnmkdm) {
		this.jsgnmkdm = jsgnmkdm;
	}

	public String getJsdlyxj() {
		return jsdlyxj;
	}

	public void setJsdlyxj(String jsdlyxj) {
		this.jsdlyxj = jsdlyxj;
	}
	
	
}
