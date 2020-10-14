package com.woshidaniu.service.utils;

import java.util.List;

import org.apache.commons.collections.Predicate;

import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.PredicateUtils;
import com.woshidaniu.common.query.BaseMap;

public final class NavMenuUtils {
	
	protected static List<BaseMap> getRoleSubMenuList(BaseMap parentNav,List<BaseMap> userNavMenuTreeList) {
		String gnmkdm_p = String.valueOf(parentNav.get("gnmkdm"));
		Predicate navPredicate = PredicateUtils.treeMapPredicate("fjgndm", gnmkdm_p);
		//筛选当前父功能模块节点的子功能模块节点数据
		List<BaseMap> childNavList = CollectionUtils.findAll(userNavMenuTreeList, navPredicate);
		if(childNavList != null && childNavList.size() > 0){
			for (BaseMap navMap : childNavList) {
				String sfgnym = String.valueOf(navMap.get("sfgnym"));
				String dyym = String.valueOf(navMap.get("dyym"));
				if("#".equals(dyym) || "0".equals(sfgnym)){
					//有下一级菜单
					List<BaseMap> roleSubMenuList = getRoleSubMenuList(navMap,userNavMenuTreeList);
					if(roleSubMenuList != null){
						navMap.put("children", roleSubMenuList);
					}
				}
			}
			return childNavList;
		}
		return null;
	}
	

	public static List<BaseMap> getNavTreeMenuList(List<BaseMap> userNavMenuTreeList) {
		return getNavTreeMenuList(userNavMenuTreeList, "N");
	}
	
	public static List<BaseMap> getNavTreeMenuList(List<BaseMap> userNavMenuTreeList,String fjgndm) {
		//优先获得最顶层的菜单集合
		Predicate navPredicate = PredicateUtils.treeMapPredicate("fjgndm", fjgndm);
		List<BaseMap> topNavMenuList = CollectionUtils.findAll(userNavMenuTreeList, navPredicate);
		for (BaseMap navMap : topNavMenuList) {
			List<BaseMap> roleSubMenuList =  getRoleSubMenuList(navMap,userNavMenuTreeList);
			if(roleSubMenuList != null){
				navMap.put("children", roleSubMenuList);
			}
		}
		return topNavMenuList;
	}
	
}
