package com.woshidaniu.designer.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementWidgetModel;
import com.woshidaniu.designer.dao.entities.DesignFuncWidgetJQGridColumnModel;

/**
 * 
 *@类名称: IDesignFuncElementWidgetDao.java
 *@类描述：功能页面:组件初始化参数信息DAO接口:指定设计器生成的功能页面中该组件参数信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午03:24:20
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignFuncElementWidgetDao extends BaseDao<DesignFuncElementWidgetModel>{

	
	/**
	 * 
	 *@描述：更新【功能页面组件数据查询SQL】
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-15下午04:43:44
	 *@param t
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int updateWidgetSQL(DesignFuncElementWidgetModel t);
	
	/**
	 * 
	 *@描述：根据 【功能代码+操作代码】 查询对应的功能页面自定义元素关联js组件信息集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-30下午03:04:40
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncElementWidgetModel> getFuncElementWidgetList(@Param("func_code")String func_code,@Param("opt_code")String opt_code);
	
	
	/**
	 * 
	 *@描述：向【功能代码+操作代码】对应功能的指定JQGrid组件添加【JQGrid组件列】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-29下午03:52:01
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int insertJQGridColumn(DesignFuncWidgetJQGridColumnModel model);
	
	/**
	 * 
	 *@描述：删除【功能代码+操作代码】对应功能的指定JQGrid组件添加【JQGrid组件列】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-29下午03:52:20
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int deleteJQGridColumn(DesignFuncWidgetJQGridColumnModel model);
	
	/**
	 * 
	 *@描述：更新【功能代码+操作代码】对应功能的指定JQGrid组件添加【JQGrid组件列】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-29下午03:52:32
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int updateJQGridColumn(DesignFuncWidgetJQGridColumnModel model);
	
	/**
	 * 
	 *@描述：根据【功能代码+操作代码】查询 该功能下所有的JQGrid组件配置信息；这里可能是多个JQGrid组件的列信息，需要在自己的逻辑中去处理
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-22上午09:16:13
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncWidgetJQGridColumnModel> getJQGridColumnList(@Param("func_code")String func_code,@Param("opt_code")String opt_code);

	/**
	 * 
	 *@描述：根据功能组件ID查询JQGrid组件列信息 :用于作为报表或者弹窗参数的列
	 *@创建人:kangzhidong
	 *@创建时间:Aug 12, 201511:56:04 AM
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<PairModel> getReportJQGridColumnList(@Param("func_widget_guid")String func_widget_guid);
	
	/**
	 * 
	 *@描述：根据组件model查询JQGrid组件列信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-12下午02:49:14
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncWidgetJQGridColumnModel> getFuncJQGridColumnList(DesignFuncElementWidgetModel model);
	
	/**
	 * 
	 *@描述：根据组件model查询JQGrid组件列名称
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-13下午01:44:05
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<String> getFuncJQGridColumnNameList(DesignFuncElementWidgetModel model);
	
	/**
	 * 
	 *@描述：根据解析出的SQL中使用的查询表名称，得到不重复的字段描述信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-14下午01:47:43
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getSQLParserColumnList(DesignFuncElementWidgetModel model);
	
	/**
	 * 
	 *@描述：根据SQL查询数据结果集合，如果有数据范围则会解析数据范围SQL
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-30下午02:17:12
	 *@param querySQL
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getWidgetDataListByScope(@Param("querySQL")String querySQL);
	
	/**
	 * 
	 *@描述：根据SQL查询数据结果集合，如果有数据范围则会解析数据范围SQL
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-30下午02:18:00
	 *@param querySQL
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getPagedWidgetDataListByScope(@Param("querySQL")String querySQL);

	
}
