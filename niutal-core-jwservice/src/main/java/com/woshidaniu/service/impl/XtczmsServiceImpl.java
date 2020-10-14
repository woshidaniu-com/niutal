package com.woshidaniu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.daointerface.IXtczmsDao;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.entities.XtczmsModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IXtczmsService;
import com.woshidaniu.util.local.LocaleUtils;

/**
 * 
 *@类名称:XtczmsServiceImpl.java
 *@类描述：系统操作描述service接口实现
 *@创建人：kangzhidong
 *@创建时间：2015-3-5 下午05:17:43
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model="N01",business="N000003")
@Service("xtczmsService")
public class XtczmsServiceImpl extends CommonBaseServiceImpl<XtczmsModel, IXtczmsDao> implements IXtczmsService {
	
	@Resource
	private IXtczmsDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);

	}
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
	public List<PairModel> getGnmkdmPairList(){
		return dao.getGnmkdmPairList(LocaleUtils.getLocaleKey());
	}
	
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
	public List<PairModel> getCzdmPairList(){
		return dao.getCzdmPairList(LocaleUtils.getLocaleKey());
	}
	
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
	public XtczmsModel getXtczms(String gnmkdm,String czdm){
		return this.getModel(new XtczmsModel(gnmkdm,czdm));
	}
	
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
	public List<String>  getXtczmsList(){
		List<String> list = null;
		//去除掉缓存
		list = getDao().getXtczmsList();
		return list;
	}
	
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
	public boolean isHasXtczms(String gnmkdmKey,String czdm){
		//查询受到时间控制的 功能模块代码+操作代码 组合 的集合
		List<String> controlList = this.getXtczmsList();
		if(!BlankUtils.isBlank(controlList) &&  controlList.contains(gnmkdmKey + czdm)){
			return true;
		}
		return false;
	}
	
	public XtczmsModel getModel(XtczmsModel model){
		return dao.getModel(model);
	}

	@Comment
	public void updateXtczms(XtczmsModel model){
		dao.update(model);
	}
	
	@Override
	public boolean isCacheSupport() {
		return true;
	}
	
}
