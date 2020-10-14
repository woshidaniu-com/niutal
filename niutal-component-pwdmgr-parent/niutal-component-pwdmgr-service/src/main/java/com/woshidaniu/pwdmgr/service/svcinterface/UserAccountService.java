package com.woshidaniu.pwdmgr.service.svcinterface;

import java.util.Map;

import com.woshidaniu.api.entity.UserAccount;
import com.woshidaniu.basemodel.BaseMap;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.pwdmgr.api.model.BindData;

public interface UserAccountService extends BaseService<UserAccount>{
	
	/**
	 * 
	 *@描述		：通过页面绑定的参数查询用户信息
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月13日下午2:14:21
	 *@param data
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public BaseMap getUserAccount(BindData data);
	
	/**
	 * @description	： 通过email获得用户账户
	 * @param data
	 * @return
	 */
	public BaseMap getUserAccountByEmail(BindData data);
	
	/**
	 * @description	： 通过phone号码获得账户
	 * @param data
	 * @return
	 */
	public BaseMap getUserAccountByPhone(BindData data);
	
	/**
	 * @description	： 更新账号
	 * @param map
	 * @return
	 */
	public boolean updateAccount(Map<String, Object> map);
	
}
