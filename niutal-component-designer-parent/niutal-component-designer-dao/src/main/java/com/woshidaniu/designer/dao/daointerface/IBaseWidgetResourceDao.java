package com.woshidaniu.designer.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.designer.dao.entities.BaseWidgetResourceModel;

/**
 * 
 *@类名称: IBaseWidgetResourceDao.java
 *@类描述：组件脚本样式资源信息dao：指定系统中各个组件的资源信息js/css
 *@创建人：kangzhidong
 *@创建时间：2015-4-27 下午05:22:38
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IBaseWidgetResourceDao extends BaseDao<BaseWidgetResourceModel>{

	/**
	 * 
	 *@描述：查询【组件脚本样式资源】被使用次数
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-29下午02:22:36
	 *@param t
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int getUseCount(BaseWidgetResourceModel model);
	
	/**
	 * 
	 *@描述：根据功能组件ID查询该【组件脚本样式资源】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-6下午04:48:25
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseWidgetResourceModel> getWidgetResourceList(@Param("widget_guid")String widget_guid);
	
}
