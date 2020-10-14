package com.woshidaniu.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.datarange.DataRangeService;
import com.woshidaniu.common.log.User;
import com.woshidaniu.jcsj.dao.daointerface.IBjdmDao;
import com.woshidaniu.service.svcinterface.IBjdmService;

@Service("bjdmService")
public class BjdmServiceImpl implements IBjdmService , DataRangeService {

	@Resource
	private IBjdmDao dao;

	public IBjdmDao getDao() {
		return dao;
	}

	public void setDao(IBjdmDao dao) {
		this.dao = dao;
	}

	public List<String> getDataRangeList(User user) {
		String[] bjdmArr = new String[]{"456","789"};
		return Arrays.asList(bjdmArr);
	}

	 

}
