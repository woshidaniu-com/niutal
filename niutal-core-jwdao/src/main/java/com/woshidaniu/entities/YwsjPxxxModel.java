package com.woshidaniu.entities;

import java.util.List;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 *@类名称:YwsjPxxxModel.java
 *@类描述：业务数据排序信息
 *@创建人：kangzhidong
 *@创建时间：2015-2-4 下午04:37:01
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class YwsjPxxxModel extends ModelBase{

	/**
	 * 功能模块代码
	 */
	private String gnmkdm;
	/**
	 * 业务数据ID
	 */
	private String ywsj_id;
	/**
	 * 用户ID:以便排序排序信息与用户挂钩
	 */
	private String yh_id;
	/**
	 * 优先级：排序字段的先后顺序
	 */
	private String yxj;
	/**
	 * 排序字段;如：xh
	 */
	private String zdmc;
	/**
	 * 排序字段描述;如：学号
	 */
	private String zdms;
	/**
	 * 排序方式：asc 或者 desc
	 */
	private String pxfs;
	/**
	 * 排序信息集合
	 */
	private List<YwsjPxxxModel> list;
	
	public String getGnmkdm() {
		return gnmkdm;
	}

	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}

	public String getYwsj_id() {
		return ywsj_id;
	}

	public void setYwsj_id(String ywsjId) {
		ywsj_id = ywsjId;
	}

	public String getYh_id() {
		return yh_id;
	}

	public void setYh_id(String yhId) {
		yh_id = yhId;
	}

	public String getYxj() {
		return yxj;
	}

	public void setYxj(String yxj) {
		this.yxj = yxj;
	}

	public String getZdmc() {
		return zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getZdms() {
		return zdms;
	}

	public void setZdms(String zdms) {
		this.zdms = zdms;
	}

	public String getPxfs() {
		return pxfs;
	}

	public void setPxfs(String pxfs) {
		this.pxfs = pxfs;
	}

	public List<YwsjPxxxModel> getList() {
		return list;
	}

	public void setList(List<YwsjPxxxModel> list) {
		this.list = list;
	}

}
