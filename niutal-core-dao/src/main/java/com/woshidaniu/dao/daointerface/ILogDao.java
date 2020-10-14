package com.woshidaniu.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.log.BusinessLogModel;

@Repository("logDao")
public interface ILogDao {
	
	public void insert(BusinessLogModel model);
	
	public void batchInsert(List<BusinessLogModel> models);
}
