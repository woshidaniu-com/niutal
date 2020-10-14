package com.woshidaniu.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.entities.XtczmsModel;

/**
 * 
 *@类名称:IXtczmsService.java
 *@类描述：系统操作描述service接口
 *@创建人：kangzhidong
 *@创建时间：2015-3-5 下午05:15:52
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public interface IXtczmsService extends BaseService<XtczmsModel>{
	
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
	public List<PairModel> getGnmkdmPairList();
	
	/**
	 * 
	 *@描述：获得【数据批量修改配置表】中所有进行不重复处理后的操作代码信息List<PairModel>集合
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-5下午05:48:32
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public List<PairModel> getCzdmPairList();
	
	/**
	 * 
	 *@描述：根据功能模块代码+操作代码查询对应的操作描述信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-9上午11:31:54
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param gnmkdm
	 *@param czdm
	 *@return
	 */
	public XtczmsModel getXtczms(String gnmkdm,String czdm);
	
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
	
	/**
	 * 
	 *@描述：判断指定功能代码+操作代码对应的功能是否有操作描述
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-9下午04:05:15
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param gnmkdmKey
	 *@param czdm
	 *@return
	 */
	public boolean isHasXtczms(String gnmkdmKey,String czdm);
	
	/**
	 * 
	 *@描述：更新系统操作描述信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-5下午05:18:22
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param model
	 */
	public void updateXtczms(XtczmsModel model);
	
}
