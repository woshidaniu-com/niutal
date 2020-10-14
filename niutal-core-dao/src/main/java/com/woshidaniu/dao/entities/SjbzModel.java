package com.woshidaniu.dao.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 数据标准
 * @author Penghui.Qu
 *
 */
public class SjbzModel extends ModelBase {

	private static final long serialVersionUID = 1L;

	private String dm;//代码
	private String mc;//名称
	private String tableName;//表名
	private String lx;
	private String zt;
	
	public static final String ZT_1 = "1"; //启用状态
	public static final String ZT_0 = "0"; //停用状态
	
	public String getLx() {
		return lx;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
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
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	
}
