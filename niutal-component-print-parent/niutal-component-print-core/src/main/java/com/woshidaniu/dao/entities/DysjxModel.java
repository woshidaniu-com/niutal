package com.woshidaniu.dao.entities;

import org.apache.ibatis.type.Alias;

import com.woshidaniu.common.query.ModelBase;

/**
 * 打印数据项model
 * @author guoqb[1127]
 * @date 2017-3-1 16:57:19
 */
@Alias("dysjxModel")
public class DysjxModel extends ModelBase{
	private static final long serialVersionUID = 1L;
	private String sjyid;				//数据源ID
	private String dm;					//数据源代码
	private String mc;					//数据源名称
	private String lx;					//数据源类型

	public String getSjyid() {
		return sjyid;
	}
	public void setSjyid(String sjyid) {
		this.sjyid = sjyid;
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
	public String getLx() {
		return lx;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
}