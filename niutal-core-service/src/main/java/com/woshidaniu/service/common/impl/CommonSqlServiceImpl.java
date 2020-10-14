package com.woshidaniu.service.common.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.dao.daointerface.ICommonSqlDao;
import com.woshidaniu.dao.entities.BjdmModel;
import com.woshidaniu.dao.entities.BmdmModel;
import com.woshidaniu.dao.entities.NjdmModel;
import com.woshidaniu.dao.entities.ZydmModel;
import com.woshidaniu.service.common.ICommonSqlService;

/**
 * 
 * @author Administrator
 *
 */
@Service("commonSqlService")
public class CommonSqlServiceImpl implements ICommonSqlService {

	@Resource
	private ICommonSqlDao dao;

	@Override
	public List<BmdmModel> queryAllXy() throws Exception {
		return dao.queryAllXy();
	}

	@Override
	public List<ZydmModel> queryAllZy() throws Exception {
		return dao.queryAllZy();

	}

	@Override
	public List<NjdmModel> queryAllNj(Integer njnum) throws Exception {
		return dao.queryAllNj(njnum);
	}

	@Override
	public List<BjdmModel> queryAllBj() throws Exception {
		return dao.queryAllBj();
	}


	public ICommonSqlDao getDao() {
		return dao;
	}

	public void setDao(ICommonSqlDao dao) {
		this.dao = dao;
	}

	@Override
	public List<ZydmModel> queryZydm(Map map) throws Exception {
		return dao.queryZydm(map);
	}

	@Override
	public List<BjdmModel> queryBjdm(BjdmModel model) throws Exception {
		return dao.queryBjdm(model);
	}
}
