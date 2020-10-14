package com.woshidaniu.designer.dao.daointerface;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.designer.dao.entities.BaseReportDetailModel;

/**
 * 
 *@类名称: IBaseReportDetailDao.java
 *@类描述：高级报表基本信息表Dao:指自定义报表的基本信息
 *@创建人：kangzhidong
 *@创建时间：2015-6-3 上午10:23:57
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IBaseReportDetailDao extends BaseDao<BaseReportDetailModel>{

	/**
	 * 
	 * @description	： 根据report_guid查询报表基础信息
	 * @author 		： kangzhidong
	 * @date 		：2015-6-9 下午04:30:36
	 * @param report_guid
	 * @return
	 */
	public BaseReportDetailModel getReportDetailModel(@Param("report_guid")String report_guid);
	
	/**
	 * 
	 *@描述：查询【高级报表基本信息】被使用次数 
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-3上午11:02:03
	 *@param t
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int getUseCount(BaseReportDetailModel model);
	
}
