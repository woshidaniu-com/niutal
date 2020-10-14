package com.woshidaniu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.IXsbmDao;
import com.woshidaniu.dao.entities.SjbzModel;
import com.woshidaniu.dao.entities.XsbmModel;
import com.woshidaniu.service.svcinterface.IXsbmService;

/**
 * 年级、学院、层次、专业、班级加载实现
 * @author Penghui.Qu
 *
 */
@Service("xsbmService")
public class XsbmServiceImpl extends BaseServiceImpl<XsbmModel, IXsbmDao>
		implements IXsbmService {

	@Resource
	private IXsbmDao dao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	/**
	 * @see {@link com.woshidaniu.service.svcinterface.IXsbmService#getXyList()}
	 */
	public List<XsbmModel> getXyList() throws Exception {
		return dao.getXyList();
	}


	/**
	 * @see {@link com.woshidaniu.service.svcinterface.IXsbmService#getCcList()}
	 */
	public List<XsbmModel> getCcList() throws Exception {
		return dao.getCcList();
	}



	/**
	 * @see {@link com.woshidaniu.service.svcinterface.IXsbmService#getZyList(XsbmModel)}
	 */
	public List<XsbmModel> getZyList(XsbmModel t) throws Exception {
		return dao.getZyList(t);
	}


	/**
	 * @see {@link com.woshidaniu.service.svcinterface.IXsbmService#getBjList(XsbmModel)}
	 */
	public List<XsbmModel> getBjList(XsbmModel t) throws Exception {
		return dao.getBjList(t);
	}


	/**
	 * @see {@link com.woshidaniu.service.svcinterface.IXsbmService#getNjList()}
	 */
	public List<XsbmModel> getNjList() throws Exception {
		return dao.getNjList();
	}
	
	/**
	 * 根据学历层次加载学院列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<XsbmModel> getBmListByXlcc(XsbmModel t)throws Exception{
		return dao.getBmListByXlcc(t);
	}

	/**
	 * 根据学历层次加载学院列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SjbzModel> likeQueryBmListByXlcc(Map<String,Object>params) throws Exception {
		return dao.likeQueryBmListByXlcc(params);
	}

	/**
	 * 按学院加载专业 
	 * @param t
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SjbzModel> likeQueryZyList(Map<String, Object> params)
			throws Exception {
		return dao.likeQueryZyList(params);
	}

	/**
	 * 模糊查询加载班级列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SjbzModel> likeQueryBjList(Map<String, Object> params)
			throws Exception {
		return dao.likeQueryBjList(params);
	}
}
