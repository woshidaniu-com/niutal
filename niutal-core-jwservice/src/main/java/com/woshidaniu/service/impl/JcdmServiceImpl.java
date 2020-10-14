package com.woshidaniu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.daointerface.IJcdmDao;
import com.woshidaniu.entities.JcdmModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IJcdmService;
@After
@Logger(model="N01",business="N000000")
@Service
public class JcdmServiceImpl  extends CommonBaseServiceImpl<JcdmModel,IJcdmDao> implements IJcdmService {


	@Resource
	private  IJcdmDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);

	}
	
	public BaseMap getObject(JcdmModel model) {
		return dao.getObject(model);
	}
	
	@Comment(recordSQL=true)
	public boolean insert(JcdmModel model) {
		return dao.insert(model) > 0;
	}
	
	@Comment(recordSQL=true)
	public boolean update(JcdmModel model) {
		return dao.update(model) > 0;
	}
	
	@Comment(recordSQL=true)
	public boolean handle(JcdmModel model) {
		int result = dao.handle(model); 
		return result > 0 ? true : false;
	}
	
	@Comment(recordSQL=true)
	public boolean delete(JcdmModel model) {
		dao.delete(model);
		return true;
	}
	
	public List<Map<String, String>> getValidateList(JcdmModel model) {
		return dao.getValidateList(model);
	}
 
	public List<HashMap<String, String>> getList(String QuerySql) {
		return dao.getList(QuerySql);
	}
	
	public String getNowDate(String dataFormat) {
		return dao.getNowDate(dataFormat);
	}
}
