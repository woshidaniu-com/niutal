package com.woshidaniu.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.daointerface.IRzglDao;
import com.woshidaniu.entities.RzglModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IRzglService;

/**
 * 
* 
* 类名称：RzglServiceImpl 
* 类描述：日志管理实现
* 创建人：qph 
* 创建时间：2012-4-20
* 修改备注： 
*
 */
@After
@Logger(model="N01",business="N010901")
@Service
public class RzglServiceImpl extends CommonBaseServiceImpl<RzglModel,IRzglDao> implements IRzglService {
	
	@Resource
	private  IRzglDao dao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.afterPropertiesSet();
	    super.setDao(dao);   
	}
	
	@Comment
	public List<RzglModel> getPagedList(RzglModel model) {
		List<RzglModel> list = dao.getPagedList(model);
		if(!BlankUtils.isBlank(list)){
			for (RzglModel rzglModel : list) {
				rzglModel.setCzms(rzglModel.getCzms().replaceAll("<br/>", ""));
			}
		}
		return list;
	}
	
	@Comment
	public RzglModel getModel(RzglModel model) {
		return dao.getModel(model);
	}
	 
}