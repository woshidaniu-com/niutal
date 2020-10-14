package com.woshidaniu.dao.entities;

import org.apache.ibatis.type.Alias;

import com.woshidaniu.common.query.ModelBase;

/**
 * 打印模板类型model
 * @author guoqb[1127]
 * @date 2017-3-17 14:37:10
 */
@Alias("dymblxModel")
public class DymblxModel extends ModelBase{
	private static final long serialVersionUID = 1L;
	private String dm;					//模板类型代码
	private String mc;					//模板类型名称
	private String sjydm;				//数据源代码

	public String getDm() {
		return dm;
	}
	public void setDm(String dm) {
		this.dm = dm;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getSjydm() {
		return sjydm;
	}
	public void setSjydm(String sjydm) {
		this.sjydm = sjydm;
	}
}