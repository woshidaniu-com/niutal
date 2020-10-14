package com.woshidaniu.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dao.entities.CsszModel;
import com.woshidaniu.entities.PairModel;

/**
 * 
* 
* 类名称：XtszDao 
* 类描述： 系统维护实现
* 创建人：qph 
* 创建时间：2012-4-20
* 修改备注： 
*
 */
@Repository("csszDao")
public interface ICsszDao extends BaseDao<CsszModel>{


	
	/**
	 * 
	 *@描述：根据字段来源字段值的SQL查询数据
	 *@创建人:kangzhidong
	 *@创建时间:2015-1-15下午02:36:53
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param zdlySQL
	 *@return
	 */
	public List<PairModel> getZdsjList(@Param(value="zdlySQL")String zdlySQL);
	
	
}