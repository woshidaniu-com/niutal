package com.woshidaniu.designer.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.designer.dao.entities.DesignToolbarButtonModel;

/**
 * 
 *@类名称:IDesignQueryFieldService.java
 *@类描述：功能对应页面自定义字段设计service:指定设计器生成的功能页面的页面字段信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-20 下午02:57:30
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignToolbarButtonService extends BaseService<DesignToolbarButtonModel>{
	
	/**
	 * 
	 *@描述：功能代码+操作代码查询自定义功能的操作安娜
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-22上午09:16:13
	 *@param func_code
	 *@param opt_code
	 *@return 
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignToolbarButtonModel> getToolbarButtonList(String func_code);

	/**
	 * 
	 *@描述：根据功能代码查询该功能的报表操作按钮
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3下午02:43:12
	 *@param func_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignToolbarButtonModel> getReportButtonList(String func_code);
	
}
