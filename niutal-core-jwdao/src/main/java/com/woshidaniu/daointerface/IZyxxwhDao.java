package com.woshidaniu.daointerface;

import java.util.List;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.entities.ZyxxwhModel;

/**
 * 
 *@类名称:IZyxxwhDao.java
 *@类描述：专业信息维护接口
 *@创建人：wjy
 *@创建时间：2014-10-22 下午04:22:01
 *@版本号:v1.0
 */
public interface IZyxxwhDao extends BaseDao<ZyxxwhModel>{
	/**
	 * 
	 *@描述：获取学科门类集合
	 *@创建人:wjy
	 *@创建时间:2014-10-23上午11:54:06
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<ZyxxwhModel> getXkmlList();
	/**
	 * 
	 *@描述：获取层次集合
	 *@创建人:wjy
	 *@创建时间:2014-10-23上午11:54:43
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<ZyxxwhModel> getCcList();
	/**
	 * 
	 *@描述：是否停用
	 *@创建人:wjy
	 *@创建时间:2014-10-23下午03:45:11
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public int updateSfty(ZyxxwhModel model);
}
