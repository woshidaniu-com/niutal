package com.woshidaniu.designer.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.designer.dao.entities.DesignFuncElementQueryFieldModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementQueryModel;

/**
 * 
 *@类名称: IDesignQueryFieldService.java
 *@类描述：功能对应页面自定义字段设计service:指定设计器生成的功能页面的页面字段信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-20 下午02:57:30
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignFuncElementQueryService extends BaseService<DesignFuncElementQueryModel>{

	/**
	 * 
	 *@描述：根据【功能代码+操作代码 】查询【自定义查询条件】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-22上午10:17:31
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncElementQueryModel> getFuncElementQueryList(String func_code,String opt_code);
	
	/**
	 * 
	 *@描述：根据查询区域对象查询该区域关联查询条件集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-14上午11:53:17
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<DesignFuncElementQueryFieldModel> getFuncElementQueryFieldList(DesignFuncElementQueryModel model);
	
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
	public List<Map<String,String>> getSQLParserColumnList(DesignFuncElementQueryModel model,boolean isFilter);
	
	/**
	 * 
	 * @description	： 根据关联的报表信息，从报表模板中解析出基础查询字段
	 * @author 		： kangzhidong
	 * @date 		：2015-6-9 下午04:36:32
	 * @param model
	 * @param isFilter
	 * @return
	 */
	public List<Map<String,String>> getReportParserColumnList(DesignFuncElementQueryModel model,boolean isFilter);

	/**
	 * 
	 *@描述：根据SQL查询数据结果集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-22上午11:48:14
	 *@param querySQL
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getFieldDataList(String querySQL);
	
}
