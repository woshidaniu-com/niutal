package com.woshidaniu.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.AncdModel;
import com.woshidaniu.entities.IndexModel;

public interface IIndexService extends BaseService<IndexModel> {
	
	
	public List<String> cxJsxxLb(User user) ;
	
	/**
	 * 
	 *@描述：查询用户功能List
	 *@创建人:majun
	 *@创建时间:2014-7-9下午06:51:46
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public List<AncdModel> cxYhgnList(AncdModel model);

	/**
	 *@描述：查询访客功能list
	 *@创建人:"huangrz"
	 *@创建时间:2014-8-14上午11:04:10
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<AncdModel> cxFkgnList();
	
	/**
	 * 
	 *@描述：获取用户首页显示信息
	 *@创建人:wjy
	 *@创建时间:2014-9-15下午05:32:04
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public Map<String,String> getYhxxIndex(Map<String,String> map);
	
	/**
	 * 
	 *@描述：查询是否有教师照片
	 *@创建人:wjy
	 *@创建时间:2014-9-16下午02:27:12
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param jgh
	 *@return
	 */
	public boolean hasJzgzp(String jgh);
	/**
	 * 
	 *@描述：查询学生是否有入学前照片
	 *@创建人:wjy
	 *@创建时间:2014-9-16下午02:43:07
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param xh
	 *@return
	 */
	public boolean hasXsRxqzp(String xh);
	/**
	 * 
	 *@描述：查询学生是否有入学后照片
	 *@创建人:wjy
	 *@创建时间:2014-9-16下午02:43:22
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param xh
	 *@return
	 */
	public boolean hasXsRxhzp(String xh); 
	
	/**
	 *@描述：根据jsdm查询角色类型代码
	 *@创建人:zou
	 *@创建时间:2016-4-8
	 *@param jsdm
	 *@return
	 */
	public String getJslxdm(String jsdm);
}
