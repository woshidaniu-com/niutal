package com.woshidaniu.designer.dao.daointerface;

import java.util.List;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;

/**
 * 
 *@类名称: IDesignFuncSQLScriptDao.java
 *@类描述：功能页面:功能SQL脚本数据查询DAO
 *@创建人：kangzhidong
 *@创建时间：2015-5-26 下午03:33:54
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignFuncSQLScriptDao extends BaseDao<DesignFuncModel>{
	
	/**
	 * 
	 *@描述：根据功能代码查询功能模块代码信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26下午03:37:16
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getFuncMenuList(DesignFuncModel model);
	/**
	 * 
	 *@描述：根据功能代码查询功能操作代码信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26下午03:37:30
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getFuncBtnList(DesignFuncModel model);
	/**
	 * 
	 *@描述：根据功能代码查询角色功能操作代码信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26下午03:42:37
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getFuncRoleBtnList(DesignFuncModel model);
	/**
	 * 
	 *@描述：根据功能代码查询自定义功能信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26下午03:44:35
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getDesignFuncList(DesignFuncModel model);
	/**
	 * 
	 *@描述：根据功能代码查询自定义功能关联元素信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26下午03:44:35
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getDesignFuncElementList(DesignFuncModel model);
	/**
	 * 
	 *@描述：根据功能代码查询自定义功能关联元素上绑定的组件信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26下午03:44:35
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:func_jqgrid_field_list
	 */
	public List<BaseMap> getDesignFuncWidgetList(DesignFuncModel model);
	/**
	 * 
	 *@描述：根据功能代码查询自定义功能关联元素绑定的JQGrid组件列信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26下午03:50:45
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getDesignFuncGQGridFieldList(DesignFuncModel model);
	/**
	 * 
	 *@描述：根据功能代码查询元素关联查询条件数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26下午03:54:14
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getDesignFuncQueryList(DesignFuncModel model);
	/**
	 * 
	 *@描述：根据功能代码查询元素关联查询条件字段数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26下午03:59:08
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getDesignFuncQueryFieldList(DesignFuncModel model);
	/**
	 * 
	 *@描述：根据功能代码查询元素关联字段数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-26下午03:59:44
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getDesignFuncFieldList(DesignFuncModel model);
	
	/**
	 * 
	 *@描述：根据功能代码查询功能相关的自动完成查询输入框信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-17上午09:46:41
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getDesignFuncAutoQueryFieldList(DesignFuncModel model);
	
	/**
	 * 
	 *@描述：根据功能代码查询功能相关的自动完成输入框信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-17上午09:46:41
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getDesignFuncAutoFieldList(DesignFuncModel model);
	
	/**
	 * 
	 *@描述：根据功能代码查询功能相关的js,css资源信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-17上午09:47:13
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getDesignFuncResourceList(DesignFuncModel model);
	
	/**
	 * 
	 *@描述：根据功能代码自定义报表信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-17上午09:47:31
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getDesignFuncReportList(DesignFuncModel model);
}
