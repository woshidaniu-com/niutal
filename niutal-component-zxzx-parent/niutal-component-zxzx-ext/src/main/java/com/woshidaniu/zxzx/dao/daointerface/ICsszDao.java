package com.woshidaniu.zxzx.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.zxzx.dao.entities.CsszModel;

@Repository("zxzxCsszDao")
public interface ICsszDao extends BaseDao<CsszModel>{
	
	/**
	 * 删除
	 * @return
	 */
	int deleteAll();
	
	/**
	 * 批量新增
	 * @param modelList
	 * @return
	 */
	int batchInsert(List<CsszModel> modelList);
	
	
}