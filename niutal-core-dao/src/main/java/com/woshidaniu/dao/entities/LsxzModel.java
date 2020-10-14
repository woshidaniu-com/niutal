package com.woshidaniu.dao.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 老师信息选择
 * @author Jiangdong.Yi
 *
 */
public class LsxzModel extends ModelBase {

	private static final long serialVersionUID = 1L;
	private String dzyx;	//电子邮箱
	private String sfqy;	//是否启用(1为启用,0为不启用)
	private String sjly;	//数据来源表名
	private String zgh;	//职工号
	private String xm;	//姓名
	private String mm;	//密码
	private String lxdh;	//联系电话
	
	private String sfdx="0";//是否多选 ;  默认单选
	private String doSearch;//查询页面跳转
	public String getDzyx() {
		return dzyx;
	}
	public void setDzyx(String dzyx) {
		this.dzyx = dzyx;
	}
	public String getSfqy() {
		return sfqy;
	}
	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}
	public String getSjly() {
		return sjly;
	}
	public void setSjly(String sjly) {
		this.sjly = sjly;
	}
	public String getZgh() {
		return zgh;
	}
	public void setZgh(String zgh) {
		this.zgh = zgh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getMm() {
		return mm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getSfdx() {
		return sfdx;
	}
	public void setSfdx(String sfdx) {
		this.sfdx = sfdx;
	}
	public String getDoSearch() {
		return doSearch;
	}
	public void setDoSearch(String doSearch) {
		this.doSearch = doSearch;
	}
	

}
