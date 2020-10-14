package com.woshidaniu.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.IRzglDao;
import com.woshidaniu.dao.entities.RzglModel;
import com.woshidaniu.service.svcinterface.IRzglService;

/**
 * 
* 
* 类名称：RzglServiceImpl 
* 类描述：日志管理实现
* 创建人：qph 
* 创建时间：2012-4-20
* 修改备注： 
*
 */
@Service("rzglService")
public class RzglServiceImpl extends BaseServiceImpl<RzglModel,IRzglDao> implements IRzglService {
	

	@Resource
	private IRzglDao dao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	/*private RzglDao dao;

	
	public RzglDao getDaoBase() {
		return dao;
	}
	
	public void setDao(RzglDao dao) {
		this.dao = dao;
	}*/

	/**
	 * @see  {@link com.woshidaniu.comp.xtgl.rzgl.service.RzglService#cxRz(RzglModel)}.
	 *//*
	public List<RzglModel> cxRz(RzglModel model) throws Exception {
		
		return dao.getPagedList(model);
	}

	*//**
	 * @see  {@link com.woshidaniu.comp.xtgl.rzgl.service.RzglService#cxDtrz(String)}.
	 *//*
	public RzglModel cxDtrz(String czbh) throws Exception {
	
		return (RzglModel) dao.getModel(czbh);
	}*/
}