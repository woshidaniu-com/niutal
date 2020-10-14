package com.woshidaniu.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 类描述：年级代码
 * 创建人：caozf
 * 创建时间：2012-6-11
 * @version 
 */
public class NjdmModel extends ModelBase {

	private static final long serialVersionUID = -2319191395517137971L;
	private String njdm_id; //年级代码 
	private String njdm;    //年级代码    
	private String njxh;    //年级序号    
	private String njmc;    //年级名称    
	private String num;		//查询记录数量
	private String sfsy;	//是否使用
	
	public void setSfsy(String sfsy) {
		this.sfsy = sfsy;
	}
	
	public String getSfsy() {
		return sfsy;
	}
	
	public String getNjdm_id() {
		return njdm_id;
	}

	public void setNjdm_id(String njdmId) {
		njdm_id = njdmId;
	}

	public String getNjxh() {
		return njxh;
	}

	public void setNjxh(String njxh) {
		this.njxh = njxh;
	}

	public String getNjmc() {
		return njmc;
	}

	public void setNjmc(String njmc) {
		this.njmc = njmc;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getNjdm() {
		return njdm;
	}

	public void setNjdm(String njdm) {
		this.njdm = njdm;
	}

	 
}
