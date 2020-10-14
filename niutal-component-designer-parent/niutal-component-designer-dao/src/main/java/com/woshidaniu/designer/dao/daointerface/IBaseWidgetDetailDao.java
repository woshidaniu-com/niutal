package com.woshidaniu.designer.dao.daointerface;

import java.util.List;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.designer.dao.entities.BaseWidgetDetailModel;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;

/**
 * 
 *@类名称: IBaseWidgetDetailDao.java
 *@类描述：功能组件描述信息表Dao:指定系统可用功能组件信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 上午10:34:02
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IBaseWidgetDetailDao extends BaseDao<BaseWidgetDetailModel>{

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
	public int getUseCount(BaseWidgetDetailModel t);
	
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
