package com.woshidaniu.designer.dao.daointerface;

import java.util.List;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.designer.dao.entities.DesignWidgetResourceModel;

/**
 * 
 *@类名称: IDesignWidgetResourceDao.java
 *@类描述：功能页面:功能组件脚本样式资源信息DAO：指定功能页面引入的各个组件的资源信息js/css
 *@创建人：kangzhidong
 *@创建时间：2015-4-27 下午05:29:44
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignWidgetResourceDao extends BaseDao<DesignWidgetResourceModel>{

	/**
	 * 
	 *@描述：根据功能ID查询引用的组件和自定义资源信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-28下午04:41:32
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignWidgetResourceModel> getFuncResourceList(DesignFuncModel model);
	
	/**
	 * 
	 *@描述：根据功能ID查询引用组件关联的js、css资源
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-27下午05:35:28
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignWidgetResourceModel> getFuncWidgetResourceList(DesignFuncModel model);
	
	/**
	 * 
	 *@描述：根据功能ID查询引用的自定义资源信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-28下午04:41:32
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignWidgetResourceModel> getFuncFileResourceList(DesignFuncModel model);
}
