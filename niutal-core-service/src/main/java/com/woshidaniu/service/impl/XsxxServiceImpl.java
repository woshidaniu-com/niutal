package com.woshidaniu.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.woshidaniu.beanutils.BeanUtils;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.IXsxxDao;
import com.woshidaniu.dao.entities.XsxxModel;
import com.woshidaniu.security.algorithm.DesBase64Codec;
import com.woshidaniu.service.svcinterface.IXsxxService;
import com.woshidaniu.util.base.MessageUtil;


@Service("xsxxService")
public class XsxxServiceImpl extends BaseServiceImpl<XsxxModel,IXsxxDao> implements
		IXsxxService {

	@Resource
	private IXsxxDao dao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	public XsxxModel getStudent(String yhm,String mm){
		DesBase64Codec encrypt = new DesBase64Codec();
		XsxxModel xsxxModel = new XsxxModel();
		if (!StringUtils.isEmpty(mm)){
			xsxxModel.setMm(encrypt.encrypt(mm));
		}
		xsxxModel.setXh(yhm);
		
		return dao.getModel(xsxxModel);
	}

	/**
	 * 同步学生密码
	 * @param model
	 * @return
	 */
	public boolean initXsmm(XsxxModel model) throws Exception {
		if(model == null){
			return false;
		}
		XsxxModel xsxx=new XsxxModel();
		BeanUtils.copyProperties(xsxx, model);
		//初始化学生密码000000
		xsxx.setMm(MessageUtil.getText("xsxx_cshmm"));
		int row =dao.updateXsmm(model);
		if(row == 0){
			row = dao.insertXsmm(xsxx);
		}
		return row > 0;
	}

	/**
	 * 初始化学生信息
	 * @param model
	 * @return
	 */
	public boolean synXsxx(XsxxModel model) {
		if(model == null){
			return false;
		}
		int row = dao.update(model);
		if(row == 0){
			row = dao.insert(model);
		}
		return row > 0;
	}

	/**
	 * 删除学生密码
	 * @param xhs 学号集字符串，例：xh,xh,xh
	 * @return
	 */
	public boolean scXsmm(String xhs) {
		if(StringUtils.isEmpty(xhs)){
			return false;
		}
		String[] xh=xhs.split(",");
		List<XsxxModel> list=new ArrayList<XsxxModel>();
		XsxxModel xsxx=null;
		for (int i = 0; i < xh.length; i++) {
			xsxx=new XsxxModel();
			xsxx.setXh(xh[i]);
			list.add(xsxx);
		}
		int row = dao.batchDeleteXsmm(list);
		if(row > 0 ){
			return true;
		}else{
			
			return false;
		}
	}

	/**
	 * 删除学生信息
	 * @param xhs 学号集字符串，例：xh,xh,xh
	 * @return
	 */
	public boolean scXsxx(String xhs) {
		if(StringUtils.isEmpty(xhs)){
			return false;
		}
		String[] xh=xhs.split(",");
		List<XsxxModel> list=new ArrayList<XsxxModel>();
		XsxxModel xsxx=null;
		for (int i = 0; i < xh.length; i++) {
			xsxx=new XsxxModel();
			xsxx.setXh(xh[i]);
			list.add(xsxx);
		}
		int row = dao.batchDelete(list);
		if(row > 0 ){
			return true;
		}else{
			
			return false;
		}
	}
	
	
}
