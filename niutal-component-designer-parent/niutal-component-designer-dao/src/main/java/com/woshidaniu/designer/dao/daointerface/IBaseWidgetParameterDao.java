package com.woshidaniu.designer.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.designer.dao.entities.BaseWidgetParameterModel;

/**
 * 
 *@类名称: IBaseWidgetParameterDao.java
 *@类描述：功能js组件初始化参数信息DAO：指定系统中的js组件初始化需要哪些参数，以及默认值
 *@创建人：kangzhidong
 *@创建时间：2015-4-30 下午12:01:36
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IBaseWidgetParameterDao extends BaseDao<BaseWidgetParameterModel>{

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
	public List<BaseWidgetParameterModel> getWidgetParameterList(@Param("widget_guid")String widget_guid);
	
}
