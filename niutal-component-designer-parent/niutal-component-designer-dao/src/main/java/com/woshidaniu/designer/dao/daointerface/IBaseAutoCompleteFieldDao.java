package com.woshidaniu.designer.dao.daointerface;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.designer.dao.entities.BaseAutoCompleteFieldModel;

/**
 * 
 *@类名称: IBaseAutoCompleteFieldDao.java
 *@类描述：系统可自定义自动完成字段信息操作dao
 *@创建人：kangzhidong
 *@创建时间：2015-4-24 下午03:52:01
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IBaseAutoCompleteFieldDao extends BaseDao<BaseAutoCompleteFieldModel>{
	
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
	public int getUseCount(BaseAutoCompleteFieldModel t);
	
}
