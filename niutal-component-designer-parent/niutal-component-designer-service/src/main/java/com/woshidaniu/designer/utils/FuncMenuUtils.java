/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.designer.utils;

import java.util.List;

import org.apache.commons.collections.Predicate;

import com.woshidaniu.designer.dao.entities.DesignFuncMenuButtonModel;
import com.woshidaniu.designer.dao.entities.DesignFuncMenuModel;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;

/**
 * 
 *@类名称:FuncMenuUtils.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：2015-5-7 下午05:24:12
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public class FuncMenuUtils {
	
	/**
	 * 
	 *@描述：组织菜单，按钮所属关系
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-7下午07:31:27
	 *@param parentFuncModel
	 *@param funcMenuList
	 *@param funcBtnList
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public static void initMenuList(final DesignFuncMenuModel parentFuncModel,List<DesignFuncMenuModel> funcMenuList,List<DesignFuncMenuButtonModel> funcBtnList) {
		//筛选当前父功能模块节点的子功能模块节点数据
		List<DesignFuncMenuModel> funcMenuList_filter = CollectionUtils.findAll(funcMenuList, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				DesignFuncMenuModel funcMenuModel = (DesignFuncMenuModel)object;
				//去除无用属性
				funcMenuModel.setQueryList(null);
				funcMenuModel.setQueryModel(null);
				funcMenuModel.setTotalResult(null);
				if(funcMenuModel.getParent_func_code()!= null && funcMenuModel.getParent_func_code().equalsIgnoreCase(parentFuncModel.getFunc_code())){
					return true;
				}
				return false;
			}
		});
		//如果有子菜单,则进行子菜单的处理
		if(funcMenuList_filter != null && funcMenuList_filter.size() > 0){
			//循环子功能模块
			for (DesignFuncMenuModel funcMenuModel : funcMenuList_filter) {
				funcMenuModel.setQueryList(null);
				funcMenuModel.setQueryModel(null);
				funcMenuModel.setTotalResult(null);
				//从一级功能菜单开始初始化各级别功能模块
				initMenuList(funcMenuModel,funcMenuList,funcBtnList);
			}
			int max_ordinal = 1;
			for (DesignFuncMenuModel designFuncMenuModel : funcMenuList_filter) {
				max_ordinal = Math.max(max_ordinal, Integer.parseInt(StringUtils.getSafeStr(designFuncMenuModel.getFunc_ordinal(), "1")));
			}
			max_ordinal += 5;
			parentFuncModel.setMax_ordinal(String.valueOf(max_ordinal));
			parentFuncModel.setChildFuncList(funcMenuList_filter);
		}else{
			//筛选当前前父功能模的功能操作数据
			List<DesignFuncMenuButtonModel> funcBtnList_filter = CollectionUtils.findAll(funcBtnList, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					DesignFuncMenuButtonModel funcBtnModel = (DesignFuncMenuButtonModel)object;
					//去除无用属性
					funcBtnModel.setQueryList(null);
					funcBtnModel.setQueryModel(null);
					funcBtnModel.setTotalResult(null);
					if(funcBtnModel.getFunc_code().equalsIgnoreCase(parentFuncModel.getFunc_code())){
						return true;
					}
					return false;
				}
			});
			int max_ordinal = 1;
			for (DesignFuncMenuButtonModel funcBtnModel : funcBtnList_filter) {
				max_ordinal = Math.max(max_ordinal, Integer.parseInt(StringUtils.getSafeStr(funcBtnModel.getBtn_ordinal(), "1")));
			}
			max_ordinal += 2;
			parentFuncModel.setMax_ordinal(String.valueOf(max_ordinal));
			//操作按钮
			parentFuncModel.setFuncBtnList(funcBtnList_filter);
		}
	}

}
