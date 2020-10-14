package com.woshidaniu.dao.entities;

import org.apache.ibatis.type.Alias;

import com.woshidaniu.common.query.ModelBase;

/**
 * 打印数据源model
 * @author guoqb[1127]
 * @date 2017-3-1 16:41:13
 */
@Alias("dysjyModel")
public class DysjyModel extends ModelBase{
	private static final long serialVersionUID = 1L;
	private String sjyid;					//数据源ID
	private String sjymc;					//数据源名称

	public String getSjyid() {
		return sjyid;
	}
	public void setSjyid(String sjyid) {
		this.sjyid = sjyid;
	}
	public String getSjymc() {
		return sjymc;
	}
	public void setSjymc(String sjymc) {
		this.sjymc = sjymc;
	}
}