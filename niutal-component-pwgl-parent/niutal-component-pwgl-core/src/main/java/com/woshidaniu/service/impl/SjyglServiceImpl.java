package com.woshidaniu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.ISjyglDao;
import com.woshidaniu.dao.entities.PWModel;
import com.woshidaniu.dao.entities.SjbzModel;
import com.woshidaniu.service.svcinterface.ISjyglService;

/**
 * @className: SjyglServiceImpl
 * @description: 数据源管理实现类
 * @author : Hanyc
 * @date: 2018-12-03 09:29:10
 * @version V1.0
 */
@Service
public class SjyglServiceImpl extends BaseServiceImpl<PWModel, BaseDao<PWModel>> implements ISjyglService {

	@Resource
	protected ISjyglDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}

	/**
	 * @description: 获取未选中的字段
	 * @author : Hanyc
	 * @date : 2018-12-03 10:16:54
	 * @param model
	 * @return
	 */
	@Override
	public List<PWModel> getRestColsList(PWModel model){
		return dao.getRestColsList(model);
	}

	/**
	 * @description: 获取数据源列表
	 * @author : Hanyc
	 * @date : 2018-12-03 13:50:16
	 * @return
	 */
	@Override
	public List<SjbzModel> getSjyList(){
		return dao.getSjyList();
	}

	/**
	 * @description: 根据模块代码获取输出的字段信息
	 * @author : Hanyc
	 * @date : 2018-12-05 17:18:48
	 * @return
	 */
	@Override
	public PWModel getModelByMkdm(PWModel model){
		return dao.getModelByMkdm(model);
	}
}