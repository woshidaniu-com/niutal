package com.woshidaniu.daointerface;

import java.util.List;

import com.woshidaniu.entities.PairModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.entities.PlxgModel;
/**
 * 
 *@类名称:IPlxgDao.java
 *@类描述：数据批量修改dao接口
 *@创建人：kangzhidong
 *@创建时间：2015-4-10 下午12:37:17
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@Component("plxgDao")
public interface IPlxgDao  extends BaseDao<PlxgModel>{

	/**
	 * 
	 *@描述：获得【数据批量修改配置表】中所有进行不重复处理后的功能模块代码信息List<PairModel>集合
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
	 *@描述：得到启用的功能数据批量修改配置信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-13下午02:00:01
	 *@param model
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public List<PlxgModel> getBatchModelList(PlxgModel model);
	
}
