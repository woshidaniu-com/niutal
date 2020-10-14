package com.woshidaniu.entities;

import java.util.List;

/**
 * 
 * 
 * 类名称：YhglModel 类描述： 用户管理MODEL 创建人：Administrator 创建时间：2012-4-10 下午06:46:43
 * 修改人：caozf 修改时间：2012-7-4 下午13:46:43 修改备注：
 * 
 * @version
 * 
 */
public class YhglModel extends CommonModel {

	private static final long serialVersionUID = 1479974290911672755L;
	private String yhm;// 用户名
	private String xm;// 姓名
	private String kl;// 密码
	private String sjhm;// 联系电话
	private String dzyx;// 电子邮箱
	private String sfqy;// 是否启用
	private String jgdm;// 机构代码(部门代码)
	private String jgmc;// 机构名称(部门名称)
	private String jglb;// 机构类别
	private String pkValue;
	private String yhlx;// 用户类型
	private String sjly;// 数据来源：JW_XJGL_XSJBXXB：学生；jw_jg_jzgxxb：教师
	private String lxmc;// 用户类型名称

	private String[] cbvjsxx; // 角色信息主键
	private String[] old_jsdm; // 旧的角色代码集合：在用户角色设置功能，此字段用于接收修改前的角色代码
	
	private String jsdm;// 角色代码

	private String jsmc;// 角色名称
	private String jsgybj;// 角色公用标记1公用;0非公用角色
	private String jss;// 拥有角色数

	private String ymm;// 原密码

	private String yhmmdj;// 用户密码等级状态【1：弱，2：中，3：高，4：超高】
	// 密码
	private String mm;

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getSfqy() {
		return sfqy;
	}

	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
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

	public String getKl() {
		return kl;
	}

	public void setKl(String kl) {
		this.kl = kl;
	}

	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}

	public String getDzyx() {
		return dzyx;
	}

	public void setDzyx(String dzyx) {
		this.dzyx = dzyx;
	}

	public String[] getCbvjsxx() {
		return cbvjsxx;
	}

	public void setCbvjsxx(String[] cbvjsxx) {
		this.cbvjsxx = cbvjsxx;
	}

	public String getPkValue() {
		return pkValue;
	}

	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}

	public String getLxmc() {
		return lxmc;
	}

	public void setLxmc(String lxmc) {
		this.lxmc = lxmc;
	}

	public String getJsdm() {
		return jsdm;
	}

	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}

	public String getJsmc() {
		return jsmc;
	}

	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}

	public String getJss() {
		return jss;
	}

	public void setJss(String jss) {
		this.jss = jss;
	}

	public String getYmm() {
		return ymm;
	}

	public void setYmm(String ymm) {
		this.ymm = ymm;
	}

	public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}

	public String getJgmc() {
		return jgmc;
	}

	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}

	public String getJglb() {
		return jglb;
	}

	public void setJglb(String jglb) {
		this.jglb = jglb;
	}

	public String getYhlx() {
		return yhlx;
	}

	public void setYhlx(String yhlx) {
		this.yhlx = yhlx;
	}

	public String getYhmmdj() {
		return yhmmdj;
	}

	public void setYhmmdj(String yhmmdj) {
		this.yhmmdj = yhmmdj;
	}

	public String getJsgybj() {
		return jsgybj;
	}

	public void setJsgybj(String jsgybj) {
		this.jsgybj = jsgybj;
	}

	public String[] getOld_jsdm() {
		return old_jsdm;
	}

	public void setOld_jsdm(String[] oldJsdm) {
		old_jsdm = oldJsdm;
	}

	public String getSjly() {
		return sjly;
	}

	public void setSjly(String sjly) {
		this.sjly = sjly;
	}
	
}
