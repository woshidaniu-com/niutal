package com.woshidaniu.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.IJsglDao;
import com.woshidaniu.dao.daointerface.ILoginDao;
import com.woshidaniu.dao.entities.JsglModel;
import com.woshidaniu.dao.entities.LoginModel;
import com.woshidaniu.security.algorithm.MD5Codec;
import com.woshidaniu.service.svcinterface.ILoginService;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：用户登录
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月17日下午2:36:21
 */
@Service("loginService")
public class LoginServiceImpl extends BaseServiceImpl<LoginModel, ILoginDao> implements ILoginService {
	
	@Resource
	private ILoginDao dao;	
	
	@Resource
	private IJsglDao jsglDao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.ILoginService#cxYhxx(com.woshidaniu.dao.entities.LoginModel)
	 */
	public User cxYhxx(LoginModel model){
		//密码不用空加密
		if (!StringUtils.isEmpty(model.getMm())){
			model.setMm(MD5Codec.encrypt(model.getMm()));
		}
		
		User user = dao.getUserInfo(model);
		if (user != null && User.UserType.STUDENT.toString().equals(user.getYhlx().toUpperCase())){
			//学生兼教师用户的情况
			user.getJsdms().add(User.UserType.STUDENT.toString().toLowerCase());
		}
		
		//设置当前角色
		if (user != null){
			String jsdm = user.getJsdm();
			List<String> jsdms = user.getJsdms();
			if(StringUtils.isEmpty(jsdm)) {
				if(CollectionUtils.isNotEmpty(jsdms)) {
					user.setJsdm(jsdms.get(0));					
				}
			}
		}
		
		//追加角色
		if (user != null){
			String jsdm = user.getJsdm();
			List<String> jsdms = user.getJsdms();
			List<String> list = this.appendJsdm(jsdms, Arrays.asList(jsdm));				
			user.setJsdms(list);
		}
		
		//追加二级授权角色
		if (user != null){
			//二级授权角色
			List<JsglModel> ejjsList = jsglDao.getEjsqJsxxList(user.getYhm());
			List<String> jsxxList = new ArrayList<String>();
			for (JsglModel jsxx : ejjsList){
				jsxxList.add(jsxx.getJsdm());
			}
			List<String> list = this.appendJsdm(user.getJsdms(), jsxxList);
			user.setJsdms(list);
			return user;
		}
		
		//教师用户登录失败，尝试学生登录
		user = dao.getStudentInfo(model);
		if (user != null){
			List<String> jsdms = new ArrayList<String>();
			jsdms.add(User.UserType.STUDENT.toString().toLowerCase());
			user.setJsdms(jsdms);
		}
		
		return user;
	}
	
	
	/**
	 * @description	：安全添加角色代码，避免重复的追加角色代码，过滤null和空字符串的角色，且只在最后面追新角色
	 * @author 		： 康康（1571）
	 * @date 		：2018年5月8日 下午5:09:10
	 */
	private List<String> appendJsdm(List<String> oldJsdms,List<String> newJsdms) {

		List<String> newAppendJsdms = new ArrayList<String>();
		
		if(CollectionUtils.isNotEmpty(newJsdms)) {
			for(String newJs : newJsdms) {
				if(StringUtils.isNotEmpty(newJs) && !oldJsdms.contains(newJs)) {
					newAppendJsdms.add(newJs);
				}
			}
		}
		
		List<String> resultJsdms = new ArrayList<String>();
		//原先的角色
		for(String js : oldJsdms) {
			resultJsdms.add(js);
		}
		//需新追加的角色
		for(String js : newAppendJsdms) {
			resultJsdms.add(js);
		}
		return resultJsdms;
	}
}
