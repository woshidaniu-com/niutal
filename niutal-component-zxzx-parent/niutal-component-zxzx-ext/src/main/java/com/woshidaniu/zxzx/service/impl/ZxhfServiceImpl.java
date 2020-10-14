/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.zxzx.dao.daointerface.IZxhfDao;
import com.woshidaniu.zxzx.dao.entities.ZxhfModel;
import com.woshidaniu.zxzx.service.svcinterface.IZxhfService;

/**
 * @类名 ZxhfServiceImpl.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月25日 下午3:23:41
 * @功能描述 在线咨询-咨询回复
 * 
 */
@Service("zxzxZxhfService")
public class ZxhfServiceImpl extends BaseServiceImpl<ZxhfModel, IZxhfDao>
		implements IZxhfService {

	
	@Override
	@Autowired
	@Qualifier("zxzxZxhfDao")
	public void setDao(IZxhfDao dao) {
		this.dao = dao;
	}
}
