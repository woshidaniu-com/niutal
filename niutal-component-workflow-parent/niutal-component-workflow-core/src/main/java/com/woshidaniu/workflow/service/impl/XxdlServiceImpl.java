package com.woshidaniu.workflow.service.impl;

import java.util.List;

import com.woshidaniu.workflow.dao.IXxdlDao;
import com.woshidaniu.workflow.model.XxdlModel;
import com.woshidaniu.workflow.service.IXxdlService;

/**
 * 
 * 类描述：消息队列实现类
 *
 * @version: 1.0
 * @author: jyy
 * @since: 2017-8-23 10:42:47
 */
public class XxdlServiceImpl implements IXxdlService {
	private IXxdlDao xxdlDao;

	public IXxdlDao getXxdlDao() {
		return xxdlDao;
	}

	public void setXxdlDao(IXxdlDao xxdlDao) {
		this.xxdlDao = xxdlDao;
	}

	@Override
	public void addXxdl(XxdlModel xxdlModel) {
		xxdlDao.insert(xxdlModel);
	}
	
	@Override
	public List<XxdlModel> selectXxdlxxByxxzH(XxdlModel xxdlModel) {
		return xxdlDao.selectXxdlxxByxxzH(xxdlModel);
	}
	
	
	@Override
	public void deleteXxdl(XxdlModel xxdlModel) {
		xxdlDao.deleteXxdl(xxdlModel);
	}
	
	
	@Override
	public void deleteSqrXxdl(XxdlModel xxdlModel) {
		xxdlDao.deleteSqrXxdl(xxdlModel);
	}
	
	@Override
	public void deleteXxdlBySqr(XxdlModel xxdlModel) {
		xxdlDao.deleteXxdlBySqr(xxdlModel);
	}
	
	@Override
	public void updateXxdlclzt(XxdlModel xxdlModel) {
		xxdlDao.updateXxdlclzt(xxdlModel);
	}

}
