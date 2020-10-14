/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.wjdc.dao.daointerface.IYwgnDao;
import com.woshidaniu.wjdc.dao.entites.YwgnModel;
import com.woshidaniu.wjdc.service.svcinterface.IYwgnService;

/**
 * @author Penghui.Qu(445)
 * 功能实现
 * 
 * @author ：康康（1571）
 * 整理，优化
 * 
 * */
@Service("ywgnService")
public class YwgnServiceImpl implements IYwgnService {

	@Resource
	private IYwgnDao ywgnDao;
	
	public List<YwgnModel> getYwgnList() {
		return ywgnDao.getYwgnList();
	}
	
	@Override
	public List<YwgnModel> getAllYwgnList() {
		return ywgnDao.getAllYwgnList();
	}

}
