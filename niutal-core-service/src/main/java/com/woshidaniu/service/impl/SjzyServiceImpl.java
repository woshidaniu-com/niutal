package com.woshidaniu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.ISjzyDao;
import com.woshidaniu.dao.entities.SjzyModel;
import com.woshidaniu.service.svcinterface.ISjzyService;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：数据资源Service
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月22日上午9:26:40
 */
@Service("sjzyService")
public class SjzyServiceImpl extends BaseServiceImpl<SjzyModel, ISjzyDao> implements ISjzyService {


	@Resource
	private ISjzyDao dao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
}
