package com.woshidaniu.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.query.BaseMap;

/**
 * 多角色上下文菜单服务
 * @author 1571
 */
public interface IMutileRoleContextFuncMenuService {

	List<BaseMap> getTopNavMenuList(String yhm, List<String> jsdms, String localeKey);
	
	List<BaseMap> getChildNavMenuList(String yhm, List<String> jsdms,String parentGnmkdm, String localeKey);
	
	List<BaseMap> getChildNavMenuTreeList(String yhm, List<String> jsdms,String parentGnmkdm, String localeKey);
	
	List<BaseMap> getNavMenuTreeList(String yhm, List<String> jsdms, String localeKey);
}
