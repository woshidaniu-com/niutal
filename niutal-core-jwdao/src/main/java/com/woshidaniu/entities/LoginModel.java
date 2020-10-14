package com.woshidaniu.entities;

import com.woshidaniu.common.query.ModelBase;

@SuppressWarnings("serial")
public class LoginModel extends ModelBase {
	
	// 业务系统ID
	protected String appid;
	// 系统双方约定的秘钥:基于Base64加密的值
	protected String token;
	// 32位MD5加密信息（大写）:格式为：卡号+用户类型+时间搓+token值组合的MD5值
	protected String verify;
	// 时间搓;格式: yyyyMMddHHmmss
	protected String time ;
	
	protected String yhlybid; //用户id
	protected String mm; // 密码
	protected String yzm; // 验证码
	protected String jgh;
	protected String xm;
	protected String jg_id; // 部门代码
	protected String jgdm;//机构代码
	protected String sfqy;//是否启用
	protected String ylzd1;
	protected String ylzd2;
	protected String dzyx; // 电子邮箱
	protected String[] cbv;
	protected String pkValue;
	protected String yhlx; //用户类型，xs,js：方便区别用户角色
	protected String userName; // 用户名
	protected String fjgndm;// 父级功能代码
	protected String gnmkdm;// 功能模块代码	
	protected String xyzyxx; //学院专业信息
	protected String yhmmdj;// 用户密码等级状态【1：弱，2：中，3：高，4：超高】
	protected String sfxnyh;//是否校内用户：1：校内用户，0校外用户
	protected String sfzc;//学生是否注册
	
	public String getSfzc() {
		return sfzc;
	}

	public void setSfzc(String sfzc) {
		this.sfzc = sfzc;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getXyzyxx() {
		return xyzyxx;
	}

	public void setXyzyxx(String xyzyxx) {
		this.xyzyxx = xyzyxx;
	}

	public String getYhlx() {
		return yhlx;
	}

	public void setYhlx(String yhlx) {
		this.yhlx = yhlx;
	}

	public String getGnmkdm() {
		return gnmkdm;
	}

	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFjgndm() {
		return fjgndm;
	}

	public void setFjgndm(String fjgndm) {
		this.fjgndm = fjgndm;
	}

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getYzm() {
		return yzm;
	}

	public void setYzm(String yzm) {
		this.yzm = yzm;
	}

	public String getYlzd1() {
		return ylzd1;
	}

	public void setYlzd1(String ylzd1) {
		this.ylzd1 = ylzd1;
	}

	public String getYlzd2() {
		return ylzd2;
	}

	public void setYlzd2(String ylzd2) {
		this.ylzd2 = ylzd2;
	}

	public String getDzyx() {
		return dzyx;
	}

	public void setDzyx(String dzyx) {
		this.dzyx = dzyx;
	}

	public String getJgh() {
		return jgh;
	}

	public void setJgh(String jgh) {
		this.jgh = jgh;
	}

	public String getJg_id() {
		return jg_id;
	}

	public void setJg_id(String jgId) {
		jg_id = jgId;
	}

	public String[] getCbv() {
		return cbv;
	}

	public void setCbv(String[] cbv) {
		this.cbv = cbv;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getPkValue() {
		return pkValue;
	}

	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}

	public String getJgdm() {
		return jgdm;
	}

	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}

	public String getSfqy() {
		return sfqy;
	}

	public void setYhlybid(String yhlybid) {
		this.yhlybid = yhlybid;
	}

	public String getYhlybid() {
		return yhlybid;
	}

	public String getYhmmdj() {
		return yhmmdj;
	}

	public void setYhmmdj(String yhmmdj) {
		this.yhmmdj = yhmmdj;
	}

	public String getSfxnyh() {
		return sfxnyh;
	}

	public void setSfxnyh(String sfxnyh) {
		this.sfxnyh = sfxnyh;
	}
	
}
