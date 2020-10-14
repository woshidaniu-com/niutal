package com.woshidaniu.jcsj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.jcsj.dao.daointerface.IXydmDao;
import com.woshidaniu.jcsj.dao.entities.XydmModel;
import com.woshidaniu.jcsj.service.svcinterface.IXydmService;
import com.woshidaniu.util.base.MessageUtil;

/**
 * 学院代码维护
 * @author Administrator
 *
 */
@Service(value="zfxgXydmService")
public class XydmServiceImpl extends BaseServiceImpl<XydmModel, IXydmDao> implements
		IXydmService {

	private static final String SPLIT_CHAR = ",";
	
	@Resource
	private IXydmDao dao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.jcsj.service.svcinterface.IXydmService#scXydm(java.lang.String)
	 */
	public String scXydm(String ids) {
		if (StringUtils.isNull(ids)){
			throw new NullPointerException();
		}
		
		String[] bmdmidArr = ids.split(SPLIT_CHAR);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bmdm_id", bmdmidArr);
		
		int delCount = dao.batchDelete(map);
		
		if (delCount == bmdmidArr.length){
			return MessageUtil.getText("I99005");
		}
		
		if (delCount == 0){
			return MessageUtil.getText("I99006");
		}
		
		return MessageUtil.getText("I88001",new Object[]{delCount,bmdmidArr.length-delCount});
	}

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.jcsj.service.svcinterface.IXydmService#cxXydmList()
	 */
	public List<XydmModel> cxXydmList() {
		return dao.cxXydmList();
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.jcsj.service.svcinterface.IXydmService#getQueryResult(com.woshidaniu.jcsj.dao.entities.XydmModel)
	 */
	public QueryModel getQueryResult(XydmModel model){
		
		QueryModel queryModel = model.getQueryModel();
		
		int count = dao.getQueryCount(model);
		
		queryModel.setTotalResult(count);
		
		List<XydmModel> xydmList = dao.getQueryResult(model);
		queryModel.setItems(xydmList);
		
		return queryModel;
	}
}
