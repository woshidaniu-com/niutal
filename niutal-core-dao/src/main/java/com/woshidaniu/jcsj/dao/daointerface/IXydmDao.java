package com.woshidaniu.jcsj.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.jcsj.dao.entities.XydmModel;

@Repository(value="zfxgXydmDao")
public interface IXydmDao extends BaseDao<XydmModel> {

	public List<XydmModel> cxXydmList();
	
	
	public int getQueryCount(XydmModel model);
	
	
	public List<XydmModel> getQueryResult(XydmModel model);
}
