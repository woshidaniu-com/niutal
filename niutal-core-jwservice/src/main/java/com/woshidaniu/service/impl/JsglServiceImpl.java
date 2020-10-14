package com.woshidaniu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.daointerface.IJsglDao;
import com.woshidaniu.daointerface.IYhglDao;
import com.woshidaniu.entities.JsglModel;
import com.woshidaniu.entities.YhglModel;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IJsglService;

/**
 * 
 * @类名称:JsglServiceImpl.java
 * @类描述：角色管理业务实现类
 * @创建人：kangzhidong
 * @创建时间：2015-1-9 下午06:45:14
 * @版本号:v1.0
 */
@After
@Logger(model = "N0109", business = "N010901")
@Service
public class JsglServiceImpl extends CommonBaseServiceImpl<JsglModel, IJsglDao> implements IJsglService {

	@Resource
	private IJsglDao dao;
	// 用户管理dao接口
	@Resource
	private IYhglDao yhglDao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}


	// 增加角色信息
	@Comment
	public void zjJsxx(JsglModel model) {
		dao.zjJsxx(model);
	}

	// 增加子角色信息
	@Comment
	public void zjSubJsxx(JsglModel model) {
		dao.zjJsxx(model);
	}

	// 修改角色信息
	@Comment
	public void xgJsxx(JsglModel model) {
		dao.xgJsxx(model);
	}

	// 删除角色信息
	@Comment(statements = { ".*sc.*" })
	public void scJsxx(JsglModel model) {
		// 删除角色信息
		// 删除角色权限信息,由于是关系删除，可能存在删除0条数据，不作判断
		dao.scJsxx(model);
	}

	@Comment(unstatements = { ".*get.*" })
	public void zjJsyhfpxx(JsglModel model) {
		// 有数据增加
		if (!BlankUtils.isBlank(model.getYhm_list())) {
			// 用户名集合：为记录日志准备的参数
			model.setYhm(StringUtils.join(model.getYhm_list(), ","));
			dao.zjJsyhfpxx(model);
		}
	}

	@Comment(unstatements = { ".*get.*" })
	public void scJsyhfpxx(JsglModel model) {
		// 有数据删除
		if (!BlankUtils.isBlank(model.getYhm_list())) {
			dao.scJsyhfpxx(model);
		}
	}

	public JsglModel cxJsmcByJsdm(JsglModel model) {
		return dao.cxJsmcByJsdm(model);
	}

	public List<JsglModel> cxJsxxListByYhm(String yhm) {
		return dao.cxJsxxListByYhm(yhm);
	}

	public List<JsglModel> cxKczjsxxList(YhglModel model) {
		return dao.cxKczjsxxList(model);
	}

	// 查询管理员角色列表
	public List<JsglModel> getAdminTreeGridModelList(JsglModel model) {
		model.setJsgybj("1");
		List<JsglModel> resulList = dao.getAdminTreeGridModelList(model);
		// for (JsglModel jsglModel : resulList) {
		// jsglModel.setExpanded(false);
		// 转换级别数字为中文数字
		// String cn_number =
		// NumberUtils.numberArab2CN(Integer.valueOf(jsglModel.getJsjb()));
		// jsglModel.setJsjbmc(cn_number);
		// }
		return resulList;
	}

	// 查询角色列表
	public List<JsglModel> getTreeGridModelList(JsglModel model) {
		model.setJsgybj("1");
		List<JsglModel> resulList = dao.getTreeGridModelList(model);
		// for (JsglModel jsglModel : resulList) {
		// jsglModel.setExpanded(false);
		// 转换级别数字为中文数字
		// String cn_number =
		// NumberUtils.numberArab2CN(Integer.valueOf(jsglModel.getJsjb()));
		// jsglModel.setJsjbmc(cn_number);
		// }
		return resulList;
	}

	@Override
	public List<YhglModel> getPagedListWfpYh(JsglModel model) {
		return dao.getPagedListWfpYhByScope(model);
	}

	@Override
	public List<YhglModel> getPagedListYfpYh(JsglModel model) {
		return dao.getPagedListYfpYhByScope(model);
	}

	// 查询所有一级功能模块列表
	public List<JsglModel> getGnmkYj(JsglModel model) {
		return dao.getGnmkYj(model);
	}

	public boolean getYhEjsq(String jsdm) {
		String sfejsq = dao.getYhEjsq(jsdm);
		return "1".equalsIgnoreCase(sfejsq);
	}

	public IYhglDao getYhglDao() {
		return yhglDao;
	}

	public void setYhglDao(IYhglDao yhglDao) {
		this.yhglDao = yhglDao;
	}
 

	public IJsglDao getDao() {
		return dao;
	}

	public void setDao(IJsglDao dao) {
		this.dao = dao;
	}

}
