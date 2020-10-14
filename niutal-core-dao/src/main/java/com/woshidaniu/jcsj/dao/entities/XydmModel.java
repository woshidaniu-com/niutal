package com.woshidaniu.jcsj.dao.entities;

import com.woshidaniu.common.query.ModelBase;


public class XydmModel extends ModelBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String bmdm_id;
	private String bmmc;
	
	private String bmlx;
	
	private String bmlxmc;

	public String getBmdm_id() {
		return bmdm_id;
	}

	public void setBmdm_id(String bmdmId) {
		bmdm_id = bmdmId;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getBmlx() {
		return bmlx;
	}

	public void setBmlx(String bmlx) {
		this.bmlx = bmlx;
	}

	public String getBmlxmc() {
		return bmlxmc;
	}

	public void setBmlxmc(String bmlxmc) {
		this.bmlxmc = bmlxmc;
	}

	
}
