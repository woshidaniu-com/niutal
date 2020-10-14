package com.woshidaniu.jcsj.service.svcinterface;


import java.util.List;

import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.jcsj.dao.entities.XydmModel;

public interface IXydmService extends BaseService<XydmModel> {

	 public String scXydm(String ids);
	 
	 public List<XydmModel> cxXydmList() ;
	 
	 /**
	  * 分页查询
	  * @param model
	  * @return
	  */
	 public QueryModel getQueryResult(XydmModel model);
}
