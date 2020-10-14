package com.woshidaniu.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dao.entities.XtszModel;

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
	 * <p>方法说明：获取学校代码<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月24日下午2:45:51<p>
	 */
	public String getXXDM();
	
	/**
	 * 
	 * <p>方法说明：获取学校名称<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年6月24日下午2:46:04<p>
	 */
	public String getXXMC();
	
	/**
	  * 
	 * 方法描述: 提供查询学年列表
	 * @param params
	 * @return list
	 * @throws java.lang.Exception
	  */
	@SuppressWarnings("rawtypes")
	public List cxXnlb(String... params) ;
	
	
	
	/**
	 * 学年列表
	 * @return
	 */
	public List<Map<String,String>> getXnList() ;
	
	
	/**
	 * 年度列表
	 * @return
	 */
	public List<Map<String,String>> getNdList() ;
	
	
	
	/**
	  * 
	 * 方法描述: 提供查询年度 列表
	 * @param params
	 * @return list
	 * @throws java.lang.Exception
	  */
	public List<?> cxNdlb(String... params) ;
}
