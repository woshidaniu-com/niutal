package com.woshidaniu.zdybd.dao.entities;

/**
 * 
 * @系统名称: 新框架
 * @模块名称: 自定义表单
 * @类功能描述: 代码名称
 * @作者： ligl
 * @时间： 2013-11-28 下午06:38:14
 * @版本： V1.0
 * @修改记录:
 */
public class DmmcModel implements java.io.Serializable {
	private static final long serialVersionUID = 8092604081767160033L;
	private String dm = null;// 代码
	private String mc = null;// 名称

	public DmmcModel() {
		super();
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

}
