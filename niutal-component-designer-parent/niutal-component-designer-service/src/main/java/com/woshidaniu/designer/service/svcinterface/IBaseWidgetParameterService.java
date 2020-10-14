package com.woshidaniu.designer.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.designer.dao.entities.BaseWidgetParameterModel;


/**
 * 
 *@类名称: IBaseWidgetParameterService.java
 *@类描述：功能js组件初始化参数信息Service接口：指定系统中的js组件初始化需要哪些参数，以及默认值
 *@创建人：kangzhidong
 *@创建时间：2015-5-13 下午04:02:22
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IBaseWidgetParameterService extends BaseService<BaseWidgetParameterModel>{
	
	/**
	 * 
	 *@描述：根据功能组件ID查询该【功能js组件初始化参数】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-6下午04:48:25
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseWidgetParameterModel> getWidgetParameterList(String widget_guid);
	
}
