package com.woshidaniu.service.impl;



import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.IJsglDao;
import com.woshidaniu.dao.daointerface.ILoginDao;
import com.woshidaniu.dao.entities.AncdModel;
import com.woshidaniu.dao.entities.EjqxModel;
import com.woshidaniu.dao.entities.GnqxModel;
import com.woshidaniu.dao.entities.JsglModel;
import com.woshidaniu.dao.entities.LoginModel;
import com.woshidaniu.dao.entities.Menu;
import com.woshidaniu.dao.entities.YhglModel;
import com.woshidaniu.service.svcinterface.IJsglService;



/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：角色管理
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月15日下午6:52:27
 */
@Service("jsglService")
public class JsglServiceImpl extends BaseServiceImpl<JsglModel, IJsglDao> implements IJsglService {
	
	private static final int BATCH_UPDATE_SIZE = 100;
	
	private static final String[] EMPTY_STRING_ARRAY = new String[0]; 
	
	@Resource
	private IJsglDao dao;	
	@Resource
	private ILoginDao loginDao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#getJscdList(java.lang.String , java.lang.String)
	 */
	public List<Menu> getGnqxByJsdm(String zgh , String jsdm) {
		LoginModel model = new LoginModel();
		model.setYhm(zgh);
		User user = loginDao.getUserInfo(model);
		
		if (user != null && user.getJsdms() != null && user.getJsdms().contains(jsdm)){
			return dao.getGnqxByJsdm(jsdm);
		} else {
			return dao.getGnqxByEjjs(jsdm, zgh);
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#getButtonList(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<AncdModel> getButtonList(String jsdm , String zgh, String gnmkdm) {
		
		LoginModel model = new LoginModel();
		model.setYhm(zgh);
		User user = loginDao.getUserInfo(model);
		
		if (user != null && user.getJsdms() != null && user.getJsdms().contains(jsdm)){
			return dao.getButtonList(jsdm, gnmkdm);
		} else {
			return dao.getEjqxButtonList(jsdm, gnmkdm, zgh);
		}
	}
	

	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#getJsxxListByZgh(java.lang.String)
	 */
	public List<JsglModel> getJsxxListByZgh(String zgh){
		
		List<JsglModel> yhjs = dao.getJsxxListByZgh(zgh);//用户角色
		List<JsglModel> ejjs = dao.getEjsqJsxxList(zgh);//二级授权角色
		
		Set<String> jsdms = new HashSet<String>();
		for (JsglModel jsxx : yhjs){
			jsdms.add(jsxx.getJsdm());
		}
		
		for (JsglModel model : ejjs){
			if (!jsdms.contains(model.getJsdm())){
				yhjs.add(model);
			}
		}
		return yhjs;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#deleteJsxxById(java.lang.String)
	 */
	public boolean deleteJsxxById(String jsdm) {
		
		int count = dao.deleteJsxx(jsdm);
		
		if (count > 0){
			dao.deleteJsyh(jsdm);//删除角色用户
			// 删除角色二级授权
			dao.deleteEjqxByJsdm(jsdm);
			dao.deleteJsgn(jsdm);//删除角色功能权限
			dao.deleteSjqx(jsdm);//删除角色数据权限
		}
		
		return count > 0;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#getPagedWfpyhList(com.woshidaniu.dao.entities.JsglModel)
	 */
	public List<YhglModel> getPagedWfpyhList(JsglModel model) {
		return dao.getPagedWfpyhList(model);
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#getPagedYfpyhList(com.woshidaniu.dao.entities.JsglModel)
	 */
	public List<YhglModel> getPagedYfpyhList(JsglModel model) {
		return dao.getPagedYfpyhList(model);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#insertYhfp(java.lang.String, java.lang.String[])
	 */
	public boolean insertYhfp(String jsdm, String[] zghs) {
		return dao.insertYhfp(jsdm, zghs) > 0;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#deleteYhfp(java.lang.String, java.lang.String[])
	 */
	public boolean deleteYhfp(String jsdm, String[] zghs) {
		return dao.deleteYhfp(jsdm, zghs) > 0;
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#getAllGnqxList()
	 */
	public List<Menu> getAllGnqxList() {
		return dao.getAllGnqxList();
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#insertGnqx(java.lang.String, java.lang.String[])
	 */
	public boolean insertGnqx(String jsdm, String[] gnczids) {
		
		//删除原来的功能权限
		dao.deleteGnqx(jsdm);
		
		if (gnczids != null && gnczids.length > 0){
			//插入功能权限
			int c = 0;
			
			//分批插入
			List<String> gnczidList = Arrays.asList(gnczids);
			
			List<List<?>> gnczidGroupList = CollectionUtils.splitList(gnczidList, BATCH_UPDATE_SIZE);
			for(List<?> list : gnczidGroupList) {
				String[] gnczidArray = list.toArray(EMPTY_STRING_ARRAY);
				c = c + dao.insertGnqx(jsdm, gnczidArray);
			}
			return c > 0;
		}else {
			//未指定任何功能权限
			return true;			
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#getButtonList(java.lang.String)
	 */
	public List<AncdModel> getButtonList(String jsdm) {
		return dao.getButtonList(jsdm, null);
	}


	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#getEjqxList(com.woshidaniu.dao.entities.EjqxModel)
	 */
	public List<AncdModel> getEjqxList(EjqxModel ejqxModel) {
		return dao.getEjqxList(ejqxModel);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#getPagedEjsqList(com.woshidaniu.dao.entities.EjqxModel)
	 */
	public List<YhglModel> getPagedEjsqList(EjqxModel model) {
		return dao.getPagedEjsqList(model);
	}


	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#insertEjsq(com.woshidaniu.dao.entities.EjqxModel)
	 */
	public boolean insertEjsq(EjqxModel model){
		
		//删除二级授权数据
		int count = dao.deleteEjqx(model);
		
		if (model.getGnczids() != null && model.getGnczids().length > 0){
			int c = dao.insertEjqx(model);
			return c > 0 ;
		}

		return count > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#getSjqxByJsdm(java.lang.String)
	 */
	public List<String> getSjqxByJsdm(String jsdm) {
		return dao.getSjqxByJsdm(jsdm);
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#insertSjqx(java.lang.String, java.lang.String[])
	 */
	public boolean insertSjqx(String jsdm, String[] gzids) {
		int count = dao.deleteSjqx(jsdm);
		
		if (gzids != null && gzids.length > 0){
			int c = dao.insertSjqx(jsdm, gzids);
			return c > 0;
		}
		
		return count > 0;
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#getGnqxRole(com.woshidaniu.dao.entities.GnqxModel)
	 */
	public List<JsglModel> getGnqxRole(GnqxModel model) {
		
		
		return dao.getPagedGnqxRole(model);
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#getGnqxUser(com.woshidaniu.dao.entities.GnqxModel)
	 */
	public List<YhglModel> getGnqxUser(GnqxModel model) {
		
		return dao.getPagedGnqxUser(model);
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IJsglService#getPagedEjqxUser(com.woshidaniu.dao.entities.GnqxModel)
	 */
	public List<YhglModel> getPagedEjqxUser(GnqxModel model) {
		
		return dao.getPagedEjqxUser(model);
	}



	
}
