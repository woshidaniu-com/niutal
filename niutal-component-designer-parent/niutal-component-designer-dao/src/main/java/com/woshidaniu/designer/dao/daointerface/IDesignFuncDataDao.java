package com.woshidaniu.designer.dao.daointerface;

import java.util.List;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.designer.dao.entities.DesignFuncDataModel;

/**
 * 
 *@类名称: IDesignFuncDataDao.java
 *@类描述：功能数据查询Dao接口
 *@创建人：kangzhidong
 *@创建时间：2015-5-4 下午03:51:14
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IDesignFuncDataDao extends BaseDao<DesignFuncDataModel>{

	/**
	 * 
	 *@描述：根据SQL分页查询【功能数据】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-5上午09:24:48
	 *@param data_sql
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getPagedFuncDataByScope(DesignFuncDataModel model);
	
	/**
	 * 
	 *@描述：根据SQL查询【功能数据】信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-5上午09:24:30
	 *@param data_sql
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseMap> getFuncDataByScope(DesignFuncDataModel model);
	
}

