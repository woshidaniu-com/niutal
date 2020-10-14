/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.zxzx.dao.daointerface.ICjwtDao;
import com.woshidaniu.zxzx.dao.entities.CjwtModel;
import com.woshidaniu.zxzx.service.svcinterface.ICjwtService;

/**
 * @类名 CjwtServiceImpl.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月24日 下午2:00:06
 * @功能描述 在线咨询-常见问题
 * 
 */
 @Service("zxzxCjwtService")
public class CjwtServiceImpl extends BaseServiceImpl<CjwtModel, ICjwtDao> implements ICjwtService {

	@Override
	@Autowired
	@Qualifier("zxzxCjwtDao")
	public void setDao(ICjwtDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 根据咨询ID删除
	 * @param zxidList
	 * @return
	 */
	public boolean batchDeleteByZxid(List<String> zxidList){
		return dao.batchDeleteByZxid(zxidList) > 0;
	}
	
	/**
	 * web端调用
	 * @param model
	 * @return
	 */
	public List<CjwtModel> getPagedListWeb(CjwtModel model){
		return dao.getPagedListWeb(model);
	}

	@Override
	public List<CjwtModel> getListWeb(CjwtModel model) {
		return dao.getListWeb(model);
	}
}
