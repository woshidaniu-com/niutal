package com.woshidaniu.designer.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementWidgetModel;
import com.woshidaniu.designer.dao.entities.DesignFuncWidgetJQGridColumnModel;

/**
 * 
 *@类名称: IDesignFuncElementWidgetService.java
 *@类描述： 功能页面：元素关联的组件初始化参数信息操作SERVICE接口
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午02:47:38
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignFuncElementWidgetService extends BaseService<DesignFuncElementWidgetModel>{

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
	public List<DesignFuncElementWidgetModel> getFuncElementWidgetList(String func_code,String opt_code);
	
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
	public List<Map<String,String>> getSQLParserColumnList(DesignFuncElementWidgetModel model,boolean isFilter);
	
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
	public List<PairModel> getReportJQGridColumnList(String func_widget_guid);
	
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
	public boolean insertJQGridColumn(DesignFuncWidgetJQGridColumnModel model);
	
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
	public boolean deleteJQGridColumn(DesignFuncWidgetJQGridColumnModel model);
	
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
	public boolean updateJQGridColumn(DesignFuncWidgetJQGridColumnModel model);
	
	/**
	 * 
	 *@描述：根据【功能代码+操作代码】查询 该功能下所有的JQGrid组件配置信息；这里可能是多个JQGrid组件的列信息，需要在自己的逻辑中去处理
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-29下午03:53:10
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncWidgetJQGridColumnModel> getJQGridColumnList(String func_code,String opt_code);
	
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
	public List<DesignFuncWidgetJQGridColumnModel> getJQGridColumnList(DesignFuncElementWidgetModel model);
	
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
	 *@描述：根据参数生成SQL查询数据结果集合，如果有数据范围则会解析数据范围SQL
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-30下午02:17:12
	 *@param querySQL
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getWidgetDataList(Map<String,Object> params);
	
	 
}
