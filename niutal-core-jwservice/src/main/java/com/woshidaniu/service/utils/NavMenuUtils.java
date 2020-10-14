package com.woshidaniu.service.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Predicate;

import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.PredicateUtils;

public final class NavMenuUtils {
	
	protected static StringBuilder getRoleSubMenuList(BaseMap parentNav,List<BaseMap> userNavMenuTreeList) {
		String gnmkdm_p = String.valueOf(parentNav.get("gnmkdm"));
		Predicate navPredicate = PredicateUtils.treeMapPredicate("fjgndm", gnmkdm_p);
		//筛选当前父功能模块节点的子功能模块节点数据
		List<BaseMap> childNavList = CollectionUtils.findAll(userNavMenuTreeList, navPredicate);
		if(childNavList != null && childNavList.size() > 0){
			StringBuilder builder  = new StringBuilder();
			builder.append("<ul class=\"dropdown-menu\" role=\"menu\" aria-labelledby=\"drop_"+ gnmkdm_p +"\">");
			for (BaseMap navMap : childNavList) {
				String gnmkdm = String.valueOf(navMap.get("gnmkdm"));
				String gnmkmc = String.valueOf(navMap.get("gnmkmc"));
				String sfgnym = String.valueOf(navMap.get("sfgnym"));
				String dyym = String.valueOf(navMap.get("dyym"));
				if("#".equals(dyym)){
					//有下一级菜单
					builder.append("<li class=\"dropdown-submenu\">");
					builder.append("<a target=\"_blank\">").append(gnmkmc).append("</a>");
					StringBuilder subBuilder = getRoleSubMenuList(navMap,userNavMenuTreeList);
					if(subBuilder != null){
						builder.append(subBuilder.toString());
					}
					builder.append("</li>");
				}else{
					//无下一级菜单
					builder.append("<li><a class=\"nav-target\" tabindex=\"-1\" ");
					//功能模块代码
					builder.append(" data-gnmkdm=\"").append(gnmkdm).append("\"");
					//功能页面地址
					builder.append(" data-dyym=\"").append(dyym).append("\"");
					//功能模块名称
					builder.append(" data-gnmkmc=\"").append(gnmkmc).append("\"");
					//是否功能页面
					builder.append(" data-sfgnym=\"").append(sfgnym).append("\"");
					builder.append(" href=\"javascript:void(0);\" >");
					builder.append(gnmkmc);
					builder.append("</a></li>");
				}
			}
			builder.append("</ul>");
			return builder;
		}
		return null;
	}
	
	public static StringBuilder getRoleMenuList(List<String> topMenuList,List<BaseMap> userNavMenuTreeList) {
		StringBuilder builder  = new StringBuilder();
		//优先获得最顶层的菜单集合
		Predicate navPredicate = PredicateUtils.treeMapPredicate("fjgndm", "N");
		List<BaseMap> topNavMenuList = CollectionUtils.findAll(userNavMenuTreeList, navPredicate);
		builder.append("<ul class=\"nav navbar-nav\">");
		for (BaseMap navMap : topNavMenuList) {
			String gnmkdm = String.valueOf(navMap.get("gnmkdm"));
			String gnmkmc = String.valueOf(navMap.get("gnmkmc"));
			//筛选当前系统模块的顶层功能菜单:顶层菜单为空则认为全部显示
			if(topMenuList == null || (topMenuList != null && topMenuList.contains(gnmkdm))){
				builder.append("<li class=\"dropdown\">");
				builder.append("<a id=\"drop_"+gnmkdm+"\" href=\"#\" role=\"button\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">").append(gnmkmc);
				StringBuilder subBuilder = getRoleSubMenuList(navMap,userNavMenuTreeList);
				if(subBuilder != null){
					builder.append("<b class=\"caret\"></b> </a>");
					builder.append(subBuilder.toString());
				}else{
					builder.append("</a>");
				}
				builder.append("</li>");
			}
		}
		builder.append("</ul>");
		return builder;
	}
	
	public static StringBuilder getNavMenuList(List<String> topMenuList,List<BaseMap> userNavMenuTreeList) {
		StringBuilder builder  = new StringBuilder();
		//优先获得最顶层的菜单集合
		Predicate navPredicate = PredicateUtils.treeMapPredicate("fjgndm", "N");
		List<BaseMap> topNavMenuList = CollectionUtils.findAll(userNavMenuTreeList, navPredicate);
		builder.append("<ul class=\"nav navbar-nav\">");
		for (BaseMap navMap : topNavMenuList) {
			String gnmkdm = String.valueOf(navMap.get("gnmkdm"));
			String gnmkmc = String.valueOf(navMap.get("gnmkmc"));
			//筛选当前系统模块的顶层功能菜单
			if(topMenuList.contains(gnmkdm)){
				builder.append("<li class=\"dropdown\">");
				builder.append("<a id=\"drop_"+gnmkdm+"\" href=\"#\" role=\"button\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">").append(gnmkmc);
				StringBuilder subBuilder = getRoleSubMenuList(navMap,userNavMenuTreeList);
				if(subBuilder != null){
					builder.append("<b class=\"caret\"></b> </a>");
					builder.append(subBuilder.toString());
				}else{
					builder.append("</a>");
				}
				builder.append("</li>");
			}
		}
		builder.append("</ul>");
		return builder;
	}
	
	public static String getStudentOrTeacherNavMenuList(List<HashMap<String, String>> list) {
		//组织原始数据
		List<List<Map<String, String>>> gnmkdmList = JsgnmkUtils.getStudentOrTeacherGnmkdmList(list);
		StringBuffer html  = new StringBuffer();
		 if(list.size()>0){
			 List<Map<String,String>> list1  = gnmkdmList.get(0);//一级菜单
			 List<Map<String,String>> list2  = gnmkdmList.get(1);//二级菜单
			 html.append("<ul class=\"nav navbar-nav\">");
			 for(int i=0;i<list1.size();i++){
				//一级菜单
				Map<String,String>  map1  = list1.get(i);
				html.append("<li class=\"dropdown\">");
				html.append("<a id=\"drop1\" href=\"#\" role=\"button\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">")
				.append(String.valueOf(map1.get("yjgnmkmc"))).append("<b class=\"caret\"></b> </a>");
				html.append("<ul class=\"dropdown-menu\" role=\"menu\" aria-labelledby=\"drop1\">");
				for(int n=0;n<list2.size();n++){
					//二级菜单
					Map<String,String>  map2  = list2.get(n);
					if(String.valueOf(map1.get("yjgnmkdm")).equals(String.valueOf(map2.get("yjgnmkdm")))){
							//无三级菜单
							html.append(" <li><a tabindex=\"-1\" onclick=\"clickMenu('");
							//功能模块代码
							html.append(String.valueOf(map2.get("ejgndm"))).append("','");
							//功能页面地址
							html.append(String.valueOf(map2.get("dyym"))).append("','");
							//功能模块名称
							html.append(String.valueOf(map2.get("ejgnmc"))).append("','");
							//是否功能页面
							html.append(String.valueOf(map2.get("sfgnym")));
							html.append("'); return false;\" ");
							html.append(" href=\"javascript:void(0);\" target=\"_blank\">");
							html.append(String.valueOf(map2.get("ejgnmc")));
							html.append("</a></li>");
					}
				}
	 
				html.append("</ul>");
				html.append("</li>");
			}
			html.append("</ul>");
		 }
		return html.toString();
	}
	
	/*
	StringBuffer html  = new StringBuffer();
	List<List<Map<String, String>>> list  =  getJscdList(yhm,jsdm);
	List<Map<String, String>> list1  = (List<Map<String, String>>) list.get(0);
	List<Map<String, String>> list2  = (List<Map<String, String>>) list.get(1);
	List<Map<String, String>> list3  = (List<Map<String, String>>) list.get(2);
	List<Map<String, String>> list4 = (List<Map<String, String>>) list.get(3);
	
	for(int i=0;i<list1.size();i++){
		//一级菜单
		Map<String,String>  map1  = (Map<String,String>)list1.get(i);
		html.append("<li class=\"dropdown\">");
		html.append("<a id=\"drop1\" href=\"#\" role=\"button\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">")
		.append(String.valueOf(map1.get("yjmkmc"))).append("<b class=\"caret\"></b> </a>");
		html.append("<ul class=\"dropdown-menu\" role=\"menu\" aria-labelledby=\"drop1\">");
		for(int n=0;n<list2.size();n++){
			//二级菜单
			Map<String,String>  map2  = (Map<String,String>)list2.get(n);
			if(String.valueOf(map1.get("yjmkdm")).equals(String.valueOf(map2.get("yjmkdm")))){
				if("#".equals(String.valueOf(map2.get("dyym")))){
					//有三级菜单
					html.append("<li class=\"dropdown-submenu\"><a target=\"_blank\">");
					html.append(String.valueOf(map2.get("ejmkmc")));
					html.append("</a><ul class=\"dropdown-menu\">");
					for(int m=0;m<list3.size();m++){
						Map<String, String> map3 = (Map<String, String>) list3.get(m);
						if(String.valueOf(map2.get("ejmkdm")).equals(String.valueOf(map3.get("ejmkdm")))){
							if ("#".equals(String.valueOf(map3.get("dyym")))) {
								html.append("<li class=\"dropdown-submenu\"><a target=\"_blank\">");
								html.append(String.valueOf(map3.get("sjmkmc")));
								html.append("</a><ul class=\"dropdown-menu\">");
								for (int j = 0; j < list4.size(); j++) {
									Map<String, String> map4 = (Map<String, String>) list4.get(j);
									if (String.valueOf(map3.get("sjmkdm")).equals(String.valueOf(map4.get("sjmkdm")))) {
										
										html.append("<li><a tabindex=\"-1\"  onclick=\"clickMenu('");
										
										html.append(String.valueOf(map4.get("sijmkdm"))).append("','");
										html.append(String.valueOf(map4.get("dyym"))).append("','");
										html.append(String.valueOf(map4.get("sijmkmc"))).append("','");
										html.append(String.valueOf(map4.get("sfgnym"))).append("'); return false;\" ");
										
										html.append(" href=\"javascript:void(0);\"  target=\"_blank\">");
										html.append(String.valueOf(map4.get("sijmkmc")));
										html.append("</a></li>");
									}
								}
							} else {
								html.append("<li><a tabindex=\"-1\"  onclick=\"clickMenu('"); 
								html.append(String.valueOf(map3.get("sjmkdm"))).append("','");
								html.append(String.valueOf(map3.get("dyym"))).append("','");
								html.append(String.valueOf(map3.get("sjmkmc"))).append("','");
								html.append(String.valueOf(map3.get("sfgnym"))).append("'); return false;\" ");
								html.append(" href=\"javascript:void(0);\"  target=\"_blank\">");
								html.append(String.valueOf(map3.get("sjmkmc")));
								html.append("</a></li>");
							}
						}
					}
					html.append("</ul></li>");
				}else{
					//无三级菜单
					html.append(" <li><a tabindex=\"-1\" onclick=\"clickMenu('");
					//功能模块代码
					html.append(String.valueOf(map2.get("ejmkdm"))).append("','");
					//功能页面地址
					html.append(String.valueOf(map2.get("dyym"))).append("','");
					//功能模块名称
					html.append(String.valueOf(map2.get("ejmkmc"))).append("','");
					//是否功能页面
					html.append(String.valueOf(map2.get("sfgnym")));
					
					html.append("'); return false;\" ");
					html.append(" href=\"javascript:void(0);\" target=\"_blank\">");
					html.append(String.valueOf(map2.get("ejmkmc")));
					html.append("</a></li>");
				}
			}
		}

		html.append("</ul>");
		html.append("</li>");
	} 
	 
	return html.toString();*/
	
}
