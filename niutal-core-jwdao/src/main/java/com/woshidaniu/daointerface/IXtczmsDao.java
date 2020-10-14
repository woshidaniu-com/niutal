package com.woshidaniu.daointerface;


import java.util.List;

import com.woshidaniu.entities.PairModel;
import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.entities.XtczmsModel;

/**
 * 
 *@类名称:IXtczmsDao.java
 *@类描述：系统操作描述dao接口
 *@创建人：kangzhidong
 *@创建时间：2015-3-5 下午05:20:33
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IXtczmsDao extends BaseDao<XtczmsModel>{
	
	/**
	 * 
	 *@描述：获得【系统操作描述表】中所有进行不重复处理后的功能模块代码信息List<PairModel>集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-5下午05:48:27
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<PairModel> getGnmkdmPairList(@Param(value="localeKey")String localeKey);
	
	/**
	 * 
	 *@描述：获得【系统操作描述表】中所有进行不重复处理后的操作代码信息List<PairModel>集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-5下午05:48:32
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<PairModel> getCzdmPairList(@Param(value="localeKey")String localeKey);
	
	
	/**
	 * 
	 *@描述：得到所有系统操作描述的 功能模块代码+操作代码  集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-9下午04:04:52
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<String>  getXtczmsList();
	
}