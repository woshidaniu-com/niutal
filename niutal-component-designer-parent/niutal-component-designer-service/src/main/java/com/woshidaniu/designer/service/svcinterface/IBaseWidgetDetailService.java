package com.woshidaniu.designer.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.designer.dao.entities.BaseWidgetDetailModel;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;


/**
 * 
 *@类名称: IBaseWidgetDetailService.java
 *@类描述：功能组件描述信息表service接口
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午02:08:52
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IBaseWidgetDetailService extends BaseService<BaseWidgetDetailModel>{
	
	/**
	 * 
	 *@描述：查询【功能组件描述】被使用次数
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-29下午02:22:36
	 *@param t
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int getUseCount(BaseWidgetDetailModel model);
	
	/**
	 * 
	 *@描述：查询所以的组件基本信息，返回字段：widget_guid,widget_name
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-20下午05:06:45
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseWidgetDetailModel> getWidgetDetailList();
	
	/**
	 * 
	 *@描述：根据功能ID查询的当前功能没有使用的组件基本信息，返回字段：widget_guid,widget_name
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-20下午05:06:45
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseWidgetDetailModel> getFuncWidgetDetailList(DesignFuncModel model);
	
}
