package com.woshidaniu.zxzx.dao.entities;

import org.apache.ibatis.type.Alias;

import com.woshidaniu.dao.entities.YhglModel;

@Alias(value="ZxzxYhglModel")
public class ZxzxYhglModel extends YhglModel {

	/**
	 * com.woshidaniu.zxzx.dao.entities.ZxzxYhglModel
	 */
	private static final long serialVersionUID = -3480390520763397618L;
	
	private String bkdm;

	public String getBkdm() {
		return bkdm;
	}

	public void setBkdm(String bkdm) {
		this.bkdm = bkdm;
	}
	
	

}
