package com.woshidaniu.designer.dao.daointerface;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.designer.dao.entities.DesignReportModel;

/**
 * 
 *@类名称: IDesignReportDao.java
 *@类描述：自定义高级报表设计表Dao:指定自定义功能与高级报表关联关系和报表查询条件
 *@创建人：kangzhidong
 *@创建时间：2015-6-3 上午11:05:05
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignReportDao extends BaseDao<DesignReportModel>{

	/**
	 * 
	 *@描述：获得功能绑定的高级报表信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3上午11:05:34
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public DesignReportModel getFuncReportModel(@Param("func_guid")String func_guid);
	
}
