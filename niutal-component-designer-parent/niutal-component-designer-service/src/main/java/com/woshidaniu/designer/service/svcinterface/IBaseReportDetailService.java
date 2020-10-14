package com.woshidaniu.designer.service.svcinterface;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.designer.dao.entities.BaseReportDetailModel;

/**
 * 
 *@类名称: IBaseReportDetailService.java
 *@类描述：高级报表基本信息表service接口
 *@创建人：kangzhidong
 *@创建时间：2015-6-3 下午07:56:00
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IBaseReportDetailService extends BaseService<BaseReportDetailModel>{
	
	/**
	 * 
	 * @description	： 根据report_guid查询报表基础信息
	 * @author 		： kangzhidong
	 * @date 		：2015-6-9 下午04:30:36
	 * @param report_guid
	 * @return
	 */
	public BaseReportDetailModel getReportDetailModel(String report_guid);
	
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
