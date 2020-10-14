package com.woshidaniu.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.BmdmModel;


public interface IBmdmService extends BaseService<BmdmModel>{
	/**
	 * 
	* 方法描述: 根据开课学院,查询部门列表
	* 参数 @return 参数说明
	* 返回类型  List<BmdmModel>  返回类型
	*/
	public List<BmdmModel> queryModel(Map<String,String> map) ;
	
	/**
	 * 
	* 方法描述: 查询部门代码不分页
	* 参数 @return 参数说明
	* 返回类型  List<BmdmModel>  返回类型
	*/
	public List<BmdmModel> getModelList(BmdmModel model);
	
	/**
	 * 分页查询
	 * @param t
	 * @return
	 */
	public List<BmdmModel> getPagedList(BmdmModel model);
	
	/**
	 * 查詢所有部門信息
	 * @param params
	 * @return
	 */
	public List<Map<String, String>> getModelMapList(Map<String,String> params);
}
