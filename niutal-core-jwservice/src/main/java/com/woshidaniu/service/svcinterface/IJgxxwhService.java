package com.woshidaniu.service.svcinterface;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.JgxxwhModel;

public interface IJgxxwhService extends BaseService<JgxxwhModel>{
	/**
	 * 
	 *@描述：删除机构信息
	 *@创建人:wjy
	 *@创建时间:2015-6-8下午07:02:30
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void deleteJgxx(JgxxwhModel model);
}
