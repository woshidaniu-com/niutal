package com.woshidaniu.designer.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.designer.dao.entities.DesignFuncElementFieldModel;

/**
 * 
 *@类名称: IDesignFuncElementFieldService.java
 *@类描述：功能页面自定义元素关联字段信息操作SERVICE接口:指定设计器生成的功能页面元素的字段信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午03:15:28
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignFuncElementFieldService extends BaseService<DesignFuncElementFieldModel>{
	 
	/**
	 * 
	 *@描述：根据 【功能代码+操作代码】 查询对应的功能页面自定义元素关联字段信息集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-30下午03:04:40
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncElementFieldModel> getFuncElementFieldList(String func_code,String opt_code);
	
}
