package com.woshidaniu.taglibs.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.taglibs.dao.entities.DatasetModel;

/**
 * 
 *@类名称		：IDatasetService.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：2016年5月13日 上午11:54:50
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public interface IDatasetService extends BaseService<DatasetModel>{

	/**
	 * 
	 *@描述		：
	 *@创建人		: kangzhidong
	 *@创建时间	: 2016年5月13日上午11:59:26
	 *@param datatype 	： 数据来源类型
	 *@param key	  	：用于取值的key，可能basedata.xml中的ID,也可能是
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<DatasetModel> getModelList(String datatype,String key);
	
}
