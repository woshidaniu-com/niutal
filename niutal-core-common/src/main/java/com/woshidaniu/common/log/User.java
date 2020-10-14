package com.woshidaniu.common.log;

import java.io.Serializable;
import java.util.List;

import com.woshidaniu.util.RoleUtils;

/**
 * 
 * 
 * 类名称：User 类描述：存储用户信息 创建人：hhy 创建时间：2011-12-20 上午10:51:28 修改人：caozf
 * 修改时间：2012-07-04 上午13:51:28 修改备注：
 * 
 * @version
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = -1244035756450161717L;

	// 用户名[职工号/学号]，session可获取
	private String yhm;
	// 姓名，session可获取
	private String xm;
	// 用户类型：student（学生）、teacher（老师），session可获取
	private String yhlx;
	// 部门代码，session可获取
	private String bmdm_id;
	// 部门名称，session可获取
	private String bmmc;
	// 当前登录角色代码 ，session可获取
	private String jsdm;
	// 角色代码列表，session可获取
	private List<String> jsdms;
	// 禁用:0,启用:1 ，session可获取
	private String sfqy;

	// =================[教务专用属性]兼容教务逻辑==========================
	// 部门代码，session可获取
	private String jg_id;
	private String jgdm;
	// 用户来源表Id，session可获取
	private String yhlybid;
	private List<Object> jsList;
	// 角色功能代码集合 以,连接的字符
	private List<String> jsgnmkdmList;
	// 学院专业信息
	private String xyzyxx;
	// 岗位级别
	private String gwjbdm;
	// 级别名称
	private String gwjbmc;
	// 附属用户名
	private String fsYhm;
	// 附属级别[1]主用户:被附属,[0]副用户:附属于
	private String fsJb;
	// 非业务字段 默认为超高
	private String yhmmdj = "1";// 用户密码等级状态【1：弱，2：中，3：高，4：超高】
	// 角色类型代码(01:校级;02：院级;)
	private String jslxdm;
	private String sfxnyh; // 是否校内用户：1：校内用户，0校外用户
	private String sfzc; // 学生是否注册
	// ===============================================================

	public enum UserType {
		STUDENT, TEACHER
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getYhlx() {
		return yhlx;
	}

	public void setYhlx(String yhlx) {
		this.yhlx = yhlx;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getBmdm_id() {
		return bmdm_id;
	}

	public void setBmdm_id(String bmdmId) {
		bmdm_id = bmdmId;
	}

	public List<String> getJsdms() {
		return jsdms;
	}

	public void setJsdms(List<String> jsdms) {
		this.jsdms = jsdms;
	}

	public String getJsdm() {
		return jsdm;
	}

	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}

	public String getSfqy() {
		return sfqy;
	}

	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}

	public String getJg_id() {
		return jg_id;
	}

	public void setJg_id(String jg_id) {
		this.jg_id = jg_id;
	}

	public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}

	public String getYhlybid() {
		return yhlybid;
	}

	public void setYhlybid(String yhlybid) {
		this.yhlybid = yhlybid;
	}

	public List<Object> getJsList() {
		return jsList;
	}

	public void setJsList(List<Object> jsList) {
		this.jsList = jsList;
	}

	public List<String> getJsgnmkdmList() {
		return jsgnmkdmList;
	}

	public void setJsgnmkdmList(List<String> jsgnmkdmList) {
		this.jsgnmkdmList = jsgnmkdmList;
	}

	public String getXyzyxx() {
		return xyzyxx;
	}

	public void setXyzyxx(String xyzyxx) {
		this.xyzyxx = xyzyxx;
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

	public String getFsYhm() {
		return fsYhm;
	}

	public void setFsYhm(String fsYhm) {
		this.fsYhm = fsYhm;
	}

	public String getFsJb() {
		return fsJb;
	}

	public void setFsJb(String fsJb) {
		this.fsJb = fsJb;
	}

	public String getYhmmdj() {
		return yhmmdj;
	}

	public void setYhmmdj(String yhmmdj) {
		this.yhmmdj = yhmmdj;
	}

	public String getJslxdm() {
		return jslxdm;
	}

	public void setJslxdm(String jslxdm) {
		this.jslxdm = jslxdm;
	}

	public String getSfxnyh() {
		return sfxnyh;
	}

	public void setSfxnyh(String sfxnyh) {
		this.sfxnyh = sfxnyh;
	}

	public String getSfzc() {
		return sfzc;
	}

	public void setSfzc(String sfzc) {
		this.sfzc = sfzc;
	}
	
	public boolean isAdmin(){
		return RoleUtils.isAdmin(getJsdm());
	}
	
	public boolean isStudent(){
		return RoleUtils.isStudent(getJsdm());
	}
	
	public boolean isTeacher(){
		return RoleUtils.isTeacher(getJsdm());
	}
	
	public boolean isMonitor() {
		return RoleUtils.isMonitor(getJsdm());
	}
	
	@Override
	public String toString() {
		return getYhm() + "[" + getXm() + "]";
	}

}
