package com.woshidaniu.service.svcinterface;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.RzglModel;

public interface ILogService extends BaseService<RzglModel>  {

	/**
	 * 
	 *@描述：往成绩数据库插入操作日志
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-11下午05:01:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void insertSQL(RzglModel model);
	
	/**
	 * 
	 *@描述：
	 *@创建人:kangzhidong
	 *@创建时间:2014-10-13上午10:14:24
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public RzglModel getSQLModel(RzglModel model);
	
}
