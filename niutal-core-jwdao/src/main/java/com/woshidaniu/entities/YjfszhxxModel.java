package com.woshidaniu.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 *@类名称:YjfszhxxModel.java
 *@类描述：邮件发送账号信息Model
 *@创建人：zfankai
 *@创建时间：2016-1-7 上午11:32:50
 *@版本号:v1.0
 */
public class YjfszhxxModel extends ModelBase {

	private static final long serialVersionUID = -2319191395517137971L;
	private String yjfszhxx_id;		//邮件发送账号信息ID
	private String yjzh;			//邮件账号
	private String yjzhmm;			//邮件账号密码
	private String fslx;			//发送类型
	
	
	public String getYjfszhxx_id() {
		return yjfszhxx_id;
	}
	public void setYjfszhxx_id(String yjfszhxxId) {
		yjfszhxx_id = yjfszhxxId;
	}
	public String getYjzh() {
		return yjzh;
	}
	public void setYjzh(String yjzh) {
		this.yjzh = yjzh;
	}
	public String getYjzhmm() {
		return yjzhmm;
	}
	public void setYjzhmm(String yjzhmm) {
		this.yjzhmm = yjzhmm;
	}
	public String getFslx() {
		return fslx;
	}
	public void setFslx(String fslx) {
		this.fslx = fslx;
	}
	
}
