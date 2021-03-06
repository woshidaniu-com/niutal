package com.woshidaniu.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.ZyxxwhModel;


/**
 * 
 *@类名称:IZyxxwhService.java
 *@类描述：专业信息维护
 *@创建人：wjy
 *@创建时间：2014-10-22 下午04:24:14
 *@版本号:v1.0
 */
public interface IZyxxwhService extends BaseService<ZyxxwhModel>{
	/**
	 * 
	 *@描述：学科门类集合
	 *@创建人:wjy
	 *@创建时间:2014-10-23上午11:55:50
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<ZyxxwhModel> getXkmlList();
	/**
	 * 
	 *@描述：层次集合
	 *@创建人:wjy
	 *@创建时间:2014-10-23上午11:56:14
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
	public void updateSfty(ZyxxwhModel model);
}
