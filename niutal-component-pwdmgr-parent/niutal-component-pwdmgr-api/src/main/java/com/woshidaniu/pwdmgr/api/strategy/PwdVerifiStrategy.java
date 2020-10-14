package com.woshidaniu.pwdmgr.api.strategy;

import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.api.model.ResultData;

public interface PwdVerifiStrategy {

	/**
	 * 
	 *@描述		：账号核实字段名称，该名称将对应系统提供的账号核实字段名称
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月12日下午3:27:35
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String name();
	
	/**
	 * 
	 *@描述		： 账号核实字段验证结果
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月12日下午3:27:49
	 *@param data
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public ResultData verifi(BindData data);
	
}
