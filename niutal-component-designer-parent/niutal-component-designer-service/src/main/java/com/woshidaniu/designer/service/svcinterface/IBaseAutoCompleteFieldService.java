package com.woshidaniu.designer.service.svcinterface;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.designer.dao.entities.BaseAutoCompleteFieldModel;


/**
 * 
 *@类名称: IBaseAutoCompleteFieldService.java
 *@类描述：系统可自定义自动完成字段信息操作service
 *@创建人：kangzhidong
 *@创建时间：2015-4-24 下午04:02:26
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IBaseAutoCompleteFieldService extends BaseService<BaseAutoCompleteFieldModel>{
	
	/**
	 * 
	 *@描述：查询【可自动完成字段】被使用次数 
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-5下午07:33:00
	 *@param t
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int getUseCount(BaseAutoCompleteFieldModel model);
	
}
