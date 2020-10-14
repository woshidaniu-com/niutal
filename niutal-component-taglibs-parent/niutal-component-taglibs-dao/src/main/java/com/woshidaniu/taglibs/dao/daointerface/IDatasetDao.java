package com.woshidaniu.taglibs.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.taglibs.dao.entities.DatasetModel;

/**
 * 
 *@类名称		： IDatasetDao.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：Jun 15, 2016 11:12:56 AM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public interface IDatasetDao extends BaseDao<DatasetModel>{
 
	/**
	 * 
	 *@描述		：
	 *@创建人		: kangzhidong
	 *@创建时间	: Jun 15, 201611:13:14 AM
	 *@param dataset_key
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<DatasetModel> getDatasetList(@Param("dataset_key")String dataset_key);
	
}
