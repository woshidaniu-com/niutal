package com.woshidaniu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.daointerface.IGnmkDao;
import com.woshidaniu.entities.GnmkModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IGnmkService;

/**
 * 
 * 
 * 类名称：GnmkServiceImpl 类描述：功能模块service实现类 创建人：huangxp 创建时间：2012-6-28
 * 
 * @version
 * 
 */
@Service
public class GnmkServiceImpl extends CommonBaseServiceImpl<GnmkModel, IGnmkDao>
		implements IGnmkService {

	@Resource
	private IGnmkDao dao;
	
	public List<GnmkModel> cxUrlqx(GnmkModel model) {
		return dao.cxUrlqx(model);
	}

}
