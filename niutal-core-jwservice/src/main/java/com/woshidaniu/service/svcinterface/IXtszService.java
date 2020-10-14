package com.woshidaniu.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.XtszModel;



/**
 * 
* 
* 类名称：XtszService 
* 类描述： 系统设置
* 创建人：qph 
* 创建时间：2012-4-20
* 修改备注： 
*
 */
public interface IXtszService extends BaseService<XtszModel>{
	
	
	/**
	  * 
	 * 方法描述: 提供查询学年列表
	 * @param params
	 * @return list
	 * @throws java.lang.Exception
	  */
	public List cxXnlb(String[][] params) ;
	
	
	
	/**
	  * 
	 * 方法描述: 提供查询年度 列表
	 * @param params
	 * @return list
	 * @throws java.lang.Exception
	  */
	public List cxNdlb(String[][] params) ;
	/**
	 * 
	 *@描述：修改  学校信息设置
	 *@创建人:majun
	 *@创建时间:2014-6-23上午10:17:03
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 *@return
	 */
	public boolean xgXxxxsz(XtszModel model);
	
	/**
	 * 
	 *@描述：更新系统设置
	 *@创建人:wjy
	 *@创建时间:2014-10-8下午06:30:41
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void updateXtsz(XtszModel model);

	public String getValue(String key);
	
	public Map<String, String> getValues(String ssmk);
	/**
	 * 
	 *@描述		：更新系统参数设置
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年7月5日上午11:57:11
	 *@param key
	 *@param value
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public void setValue(String key, String value);
	
	public void refreshCache();
	
}
