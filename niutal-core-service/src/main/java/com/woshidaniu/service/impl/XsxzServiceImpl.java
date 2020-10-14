package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.IXsxzDao;
import com.woshidaniu.dao.entities.XsxzModel;
import com.woshidaniu.service.svcinterface.IXsxzService;

/**
 * 选择学生信息
 * @author Jiangdong.Yi
 *
 */
@Service("xsxzService")
public class XsxzServiceImpl extends BaseServiceImpl<XsxzModel, IXsxzDao>
		implements IXsxzService {
	
	@Resource
	private IXsxzDao dao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	
	/**
	 * 查询学生信息列表
	 * @param String
	 * @return
	 */
	public List<XsxzModel> getModelList(String ids) {
		if (!StringUtils.isEmpty(ids)) {
			String[] pks = ids.split(",");
			
			HashMap<String, Object> hashMap=new HashMap<String, Object>();
			List<XsxzModel> list = new ArrayList<XsxzModel>();
			XsxzModel xsxz=null;
			for (int i = 0; i < pks.length; i++) {
				xsxz = new XsxzModel();
				xsxz.setXh(pks[i]);
				list.add(xsxz);
			}
			hashMap.put("list", list);
			return dao.getModelList(hashMap);
		}
		return null;
	}
	
}
