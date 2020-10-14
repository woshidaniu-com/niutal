/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.entities.YhglModel;
import com.woshidaniu.zxzx.dao.daointerface.IYhbkDao;
import com.woshidaniu.zxzx.dao.entities.YhbkModel;
import com.woshidaniu.zxzx.service.svcinterface.IYhbkService;

/**
 * @类名 YhbkServiceImpl.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月23日 下午6:01:10
 * @功能描述 在线咨询-用户板块
 * 
 */
@Service("zxzxYhbkService")
public class YhbkServiceImpl extends BaseServiceImpl<YhbkModel, IYhbkDao> implements IYhbkService {

	@Override
	@Autowired
	@Qualifier("zxzxYhbkDao")
	public void setDao(IYhbkDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 获取用户版块代码列表
	 * @param zgh
	 * @return
	 */
	@Override
	public List<String> getYhbkdmList(String zgh){
		return dao.getYhbkdmList(zgh);
	}
	
	@Override
	public boolean batchInsert(List<YhbkModel> modelList){
		return dao.batchInsert(modelList) > 0;
	}
	
	@Override
	public List<YhglModel> getPagedListYfpYhxx(YhbkModel model) {
		return dao.getPagedListYfpYhxx(model);
	}

	@Override
	public List<YhglModel> getPagedListWfpYhxx(YhbkModel model) {
		return dao.getPagedListWfpYhxx(model);
	}

	@Override
	public List<YhbkModel> getYhbkList(String zgh) {
		return dao.getYhbkList(zgh);
	}

}
