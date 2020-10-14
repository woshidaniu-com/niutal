package com.woshidaniu.designer.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.designer.dao.entities.DesignFuncDataModel;

/**
 * 
 *@类名称: IDesignFuncDataService.java
 *@类描述：功能数据查询Service接口
 *@创建人：kangzhidong
 *@创建时间：2015-5-4 下午03:48:43
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignFuncDataService extends BaseService<DesignFuncDataModel>{
	/**
	 * 
	 *@描述：分页查询【功能数据】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-5上午09:24:48
	 *@param data_sql
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getPagedFuncDataList(DesignFuncDataModel model);
	
	/**
	 * 
	 *@描述：查询【功能数据】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-5上午09:24:30
	 *@param data_sql
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getFuncDataList(DesignFuncDataModel model);
	
	/**
	 * 
	 *@描述：查询【报表数据】信息
	 *@创建人:kangzhidong
	 *@创建时间:Aug 12, 201510:44:55 AM
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getReportDataList(DesignFuncDataModel model,List<PairModel> jqGridColumnList);
	
	/**
	 * 
	 *@描述：测试SQL是否可以执行
	 *@创建人:kangzhidong
	 *@创建时间:Aug 10, 20152:37:19 PM
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getFuncDataListForTest(DesignFuncDataModel model);
	
	
}
