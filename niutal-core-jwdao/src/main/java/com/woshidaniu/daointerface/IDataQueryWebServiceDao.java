package com.woshidaniu.daointerface;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.woshidaniu.entities.webservice.DbsyModel;
import com.woshidaniu.entities.webservice.JskbModel;
import com.woshidaniu.entities.webservice.ParamModel;
import com.woshidaniu.entities.webservice.TzggModel;
import com.woshidaniu.entities.webservice.XscjModel;
import com.woshidaniu.entities.webservice.XskbModel;

/**
 *@类名称:IWebServiceDao.java
 *@类描述：
 *@创建人："huangrz"
 *@创建时间：2014-8-28 上午10:09:45
 *@版本号:v1.0
 */
public interface IDataQueryWebServiceDao{
	
	/**
	 *@描述：查询学生成绩
	 *@创建人:"huangrz"
	 *@创建时间:2014-8-28上午10:24:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param param
	 *@return
	 */
	public List<XscjModel> getXscjList(ParamModel param);
	
	/**
	 *@描述：查询通知公告
	 *@创建人:"huangrz"
	 *@创建时间:2014-8-28上午10:24:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param param
	 *@return
	 */
	public List<TzggModel> getTzggList(ParamModel param);
	
	/**
	 *@描述：查询学生课表
	 *@创建人:"huangrz"
	 *@创建时间:2014-9-1下午08:27:29
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param param
	 *@return
	 */
	public List<XskbModel> getXskbList(ParamModel param);
	
	/**
	 *@描述：查询教师课表
	 *@创建人:"huangrz"
	 *@创建时间:2014-9-1下午08:27:29
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param param
	 *@return
	 */
	public List<JskbModel> getJskbList(ParamModel param);
	
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
	public int countJzgzp(@Param(value="jgh") String jgh);
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
	public int countXsRxqzp(@Param(value="xh") String xh);
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
	public int countXsRxhzp(@Param(value="xh") String xh);
	/**
	 * 
	 *@描述：根据学号获取xh_id
	 *@创建人:wjy
	 *@创建时间:2014-9-16下午03:53:50
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param xh
	 *@return
	 */
	public String getXh_id(@Param(value="xh") String xh);
	
	/**
	 *@描述：待办事宜
	 *@创建人:"huangrz"
	 *@创建时间:2014-9-22上午11:18:38
	 *@param param
	 *@return
	 */
	public List<DbsyModel> getDbsyListByScope(ParamModel param);
}
