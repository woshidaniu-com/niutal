package com.woshidaniu.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.IMbglDao;
import com.woshidaniu.dao.entities.PWModel;
import com.woshidaniu.globalweb.PWUtils;
import com.woshidaniu.service.svcinterface.IMbglService;

/**
 * @className: MbglServiceImpl
 * @description: 模板管理实现类
 * @author : Hanyc
 * @date: 2018-12-03 09:28:59
 * @version V1.0
 */
@Service
public class MbglServiceImpl extends BaseServiceImpl<PWModel, BaseDao<PWModel>> implements IMbglService {

	@Resource
	protected IMbglDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}

	/**
	 * @description: 新增
	 * @author : Hanyc
	 * @date : 2018-12-04 16:33:49
	 * @param model
	 * @param request
	 * @return
	 */
	@Override
	public boolean insert(PWModel model, MultipartHttpServletRequest request){
		PWUtils.initDrpath(model, request);
		return dao.insert(model) > 0;
	}

	/**
	 * @description: 更新
	 * @author : Hanyc
	 * @date : 2018-12-04 16:33:49
	 * @param model
	 * @param request
	 * @return
	 */
	@Override
	public boolean update(PWModel model, HttpServletRequest request){
		//假设修改的时候有重新上传PW模版
		if(request instanceof MultipartHttpServletRequest){
			MultipartHttpServletRequest req = (MultipartHttpServletRequest)request;
			PWModel m = dao.getModel(model);
			model.setExpath(m.getDrpath());
			PWUtils.initDrpath(model, req);
		}
		return dao.update(model) > 0;
	}

	/**
	 * @description: TODO
	 * @author : Hanyc
	 * @date : 2018-12-13 09:33:03
	 * @param model
	 * @return
	 */
	@Override
	public File getModelFile(PWModel model){
		model = dao.getModel(model);
		if(StringUtils.isNotEmpty(model.getDrpath())){
			return new File(PWUtils.getRealPath(model.getDrpath()));
		}
		return null;
	}

	@Override
	public List<PWModel> getList(PWModel model){
		return dao.getList(model);
	}
}