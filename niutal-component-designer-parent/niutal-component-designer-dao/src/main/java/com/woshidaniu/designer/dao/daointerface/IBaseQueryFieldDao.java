package com.woshidaniu.designer.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.designer.dao.entities.BaseQueryFieldModel;

/**
 * 
 *@类名称:IBaseQueryFieldDao.java
 *@类描述：系统可自定义查询字段信息dao
 *@创建人：kangzhidong
 *@创建时间：2015-4-20 下午02:41:07
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IBaseQueryFieldDao extends BaseDao<BaseQueryFieldModel>{

	/**
	 * 
	 *@描述：查询【基础字段信息】被使用次数 
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-5下午07:33:00
	 *@param t
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public int getUseCount(BaseQueryFieldModel model);
	
	/**
	 * 
	 *@描述：根据【功能代码】查询可用的基础查询条件集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-15上午11:52:05
	 *@param func_code
	 *@param opt_code
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<BaseQueryFieldModel> getFuncModelList(@Param("func_code")String func_code);
	
}
