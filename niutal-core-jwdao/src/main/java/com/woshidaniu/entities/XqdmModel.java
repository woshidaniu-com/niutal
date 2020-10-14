package com.woshidaniu.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 类描述：校区代码
 * 创建人：caozf
 * 创建时间：2012-6-11
 * @version 
 */
public class XqdmModel extends ModelBase {

	private static final long serialVersionUID = 3386656514060517042L;
	private String xqh_id; //校区号ID
	private String xqh;    //校区号
	private String xqmc;    //校区名称                 
	private String xqdz;    //校区地址                 
	private String num;		//查询记录数量
	
	public String getXqh_id() {
		return xqh_id;
	}

	public void setXqh_id(String xqhId) {
		xqh_id = xqhId;
	}

	public String getXqmc() {
		return xqmc;
	}

	public void setXqmc(String xqmc) {
		this.xqmc = xqmc;
	}

	public String getXqdz() {
		return xqdz;
	}

	public void setXqdz(String xqdz) {
		this.xqdz = xqdz;
	}

	public String getXqh() {
		return xqh;
	}

	public void setXqh(String xqh) {
		this.xqh = xqh;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	
}
