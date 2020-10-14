package com.woshidaniu.daointerface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.entities.PairModel;
import org.apache.ibatis.annotations.Param;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.entities.XtszModel;

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
public interface IXtszDao extends BaseDao<XtszModel>{


	/***
	 * 
	 *@描述：修改  学校信息设置
	 *@创建人:majun
	 *@创建时间:2014-6-23上午10:15:14
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public int xgXxxxsz(XtszModel model);
	/***
	 * 
	 *@描述：查询系统设置项值
	 *@创建人:majun
	 *@创建时间:2014-6-24下午08:20:05
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param 
	 *@return
	 */
	public List<Map<String,String>> cxXtszxz();
	
	/**
	 *@描述：快速查询指定的系统设置项值
	 *@创建人:icetor
	 *@创建时间:2016-1-13下午05:33:05
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param zdm
	 *@return
	 */
	public String cxXtszxzWithZdm(String zdm);
	
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
	
	/**
	 * 
	 * <p>方法说明：单个查询系统设置<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年10月19日下午2:57:48<p>
	 * @param zdm
	 * @return
	 */
	public String getValue(String zdm);
	
	
}