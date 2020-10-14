package com.woshidaniu.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.BmdmModel;


public interface IBmdmService extends BaseService<BmdmModel>{
	/**
	 * 
	* 方法描述: 根据开课学院,查询部门列表
	* 参数 @return 参数说明
	* 返回类型  List<BmdmModel>  返回类型
	*/
	public List<BmdmModel> queryModel(Map<String,String> map) ;
	
}
