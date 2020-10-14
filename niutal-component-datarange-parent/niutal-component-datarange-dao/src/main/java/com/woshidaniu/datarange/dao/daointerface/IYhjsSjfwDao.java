package com.woshidaniu.datarange.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.datarange.dao.entities.YhJssjfwModel;

/**
 * 
 *@类名称		： IYhsjfwglDao.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：Aug 25, 2016 3:26:46 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public interface IYhjsSjfwDao extends BaseDao<YhJssjfwModel>{
	
	/**
	 * 
	 *@描述		：增加用户数据范围信息:该方法在数据范围切面中被使用，当新增用户信息时会调用该方法
	 *@创建人		: kangzhidong
	 *@创建时间	: Aug 25, 20162:52:40 PM
	 *@param model
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public void zjYhsjfwxx(Map<String,Object> params);
	
	/**
	 * 
	 *@描述		：修改用户数据范围信息:该方法在数据范围切面中被使用，当修改用户信息时会调用该方法
	 *@创建人		: kangzhidong
	 *@创建时间	: Aug 25, 20162:51:55 PM
	 *@param model
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public void xgYhsjfwxx(Map<String,Object> params);
		
	/**
	 * 
	 *@描述		：删除用户数据范围信息:该方法在数据范围切面中被使用，当删除用户信息时会调用该方法
	 *@创建人		: kangzhidong
	 *@创建时间	: Aug 25, 20163:52:40 PM
	 *@param model
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public void scYhsjfwxx(Map<String,Object> params);
	
	/**
	 * 
	 *@描述		：增加用户角色数据范围信息：:该方法在数据范围切面中被使用，当给角色分配新用户时会调用该方法
	 *@创建人		: kangzhidong
	 *@创建时间	: Aug 25, 20163:47:53 PM
	 *@param jsyhsjfwList
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public void zjJsyhfpSjfwxx(Map<String,Object> params);
	
	public void scJsyhfpSjfwxx(Map<String,Object> params);
	
	/**
	 * 
	 * @description: 添加某角色下的某用户数据范围信息
	 * @author : kangzhidong
	 * @date : 2014-4-10
	 * @time : 下午01:55:46 
	 * @param model
	 * @return
	 */
	public int insertYhSjfw(YhJssjfwModel model);
	
	/**
	 * 
	 * @description: 删除某角色下的某用户数据范围信息
	 * @author : kangzhidong
	 * @date : 2014-4-9
	 * @time : 下午04:49:56 
	 * @param sjfwModel
	 * @return
	 */
	public int deleteYhSjfw(YhJssjfwModel model);
	
	/**
	 * 
	 * @description: 删除某角色下的某用户数据范围组，数据范围信息
	 * @author :kangzhidong
	 * @date : 2014-4-9
	 * @time : 下午04:49:56 
	 * @param sjfwModel
	 * @return
	 */
	public int deleteYhSjfwz(YhJssjfwModel model);
	
	/**
	 * 
	 * @description: 修改某角色下的某用户数据范围信息
	 * @author : kangzhidong
	 * @date : 2014-4-9
	 * @time : 下午04:50:28 
	 * @param sjfwModel
	 * @return
	 */
	public int updateYhSjfw(YhJssjfwModel model);
	
	
	/**
	 * 
	 * @description: 根据角色代码和用户id查询某角色下的某用户数据范围信息
	 * @author : kangzhidong
	 * @date : 2014-4-9
	 * @time : 下午04:58:50 
	 * @param model
	 * @return
	 */
	public List<YhJssjfwModel> getYhSjfwList(YhJssjfwModel model);
	
	
	/**
	 * 
	 * @description: 查询将要保存的数据范围信息在数据库中已经存储的记录
	 * @author : kangzhidong
	 * @date : 2014-5-29
	 * @time : 下午08:35:53 
	 * @param model
	 * @return
	 */
	public List<YhJssjfwModel> getYhSjfwListToSave (YhJssjfwModel model);
	
}
