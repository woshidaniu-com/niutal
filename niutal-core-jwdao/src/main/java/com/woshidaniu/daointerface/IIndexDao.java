package com.woshidaniu.daointerface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.log.User;
import com.woshidaniu.entities.AncdModel;
import com.woshidaniu.entities.IndexModel;

/**
 * 
* 
* 类名称：IndexDao 
* 类描述： 主页面dao
* 创建人：yjd 
* 创建时间：2012-05-09 上午10:50:04 
* 修改备注： 
* @version 
*
 */
public interface IIndexDao extends BaseDao<IndexModel>{
	
	/**
	 * 方法描述: 查询用户角色
	 * 参数 @param model
	 * 参数 @return 
	 * 返回类型 List<HashMap<String,String>> 返回类型
	 */
	public List<HashMap<String, String>> cxJsxxLb(User user) ;
	
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
	public List<AncdModel> cxFkgnList(@Param(value="localeKey")String localeKey);
	
	/**
	 * 
	 *@描述：获取用户首页显示信息
	 *@创建人:wjy
	 *@创建时间:2014-9-15下午05:22:06
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public Map<String,String> getYhxxIndex(Map<String,String> map);
	
	/**
	 * 
	 *@描述：查询教师是否有照片
	 *@创建人:wjy
	 *@创建时间:2014-9-16下午02:17:06
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param map
	 *@return
	 */
	public int getCountJzgzp(@Param(value="jgh") String jgh);
	/**
	 * 
	 *@描述：查询学生是否有入学前照片
	 *@创建人:wjy
	 *@创建时间:2014-9-16下午02:41:43
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param xh
	 *@return
	 */
	public int getCountXsRxqzp(@Param(value="xh") String xh);
	/**
	 * 
	 *@描述：查询学生是否有入学后照片
	 *@创建人:wjy
	 *@创建时间:2014-9-16下午02:42:15
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param xh
	 *@return
	 */
	public int getCountXsRxhzp(@Param(value="xh") String xh);
	 
	/**
	 *@描述：根据jsdm查询角色类型代码
	 *@创建人:zou
	 *@创建时间:2016-4-8
	 *@param jsdm
	 *@return
	 */
	public String getJslxdm(@Param(value="jsdm") String jsdm);
}