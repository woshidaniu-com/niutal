package com.woshidaniu.service.svcinterface;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.XqxxwhModel;

public interface IXqxxwhService extends BaseService<XqxxwhModel> {
	/**
	 * 
	 *@描述：删除校区信息
	 *@创建人:zzh
	 *@创建时间2016-3-31
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void deleteXqxx(XqxxwhModel model);
}
