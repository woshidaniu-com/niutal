package com.woshidaniu.datarange.dao.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 * 类名称：SjfwzbModel 
 * 类描述：数据范围组 
 * 创建人：caozf 
 * 创建时间：2012-7-10
 */
public class SjfwzModel extends ModelBase {

	private static final long serialVersionUID = 3413415042616438795L;
	private String sjfwz_id; // 数据范围组ID
	private String sjfwzmc; // 数据范围名称
	private String sjfwztj; // 数据范围条件
	private String kzdx;	// 数据范围控制对象
	
	
	public SjfwzModel() {
		super();
	}

	public SjfwzModel(String sjfwzmc, String sjfwztj) {
		this.sjfwzmc = sjfwzmc;
		this.sjfwztj = sjfwztj;
	}

	public String getSjfwz_id() {
		return sjfwz_id;
	}

	public void setSjfwz_id(String sjfwzId) {
		sjfwz_id = sjfwzId;
	}

	public String getSjfwzmc() {
		return sjfwzmc;
	}

	public void setSjfwzmc(String sjfwzmc) {
		this.sjfwzmc = sjfwzmc;
	}

	public String getSjfwztj() {
		return sjfwztj;
	}

	public void setSjfwztj(String sjfwztj) {
		this.sjfwztj = sjfwztj;
	}

	public String getKzdx() {
		return kzdx;
	}

	public void setKzdx(String kzdx) {
		this.kzdx = kzdx;
	}

	
}
