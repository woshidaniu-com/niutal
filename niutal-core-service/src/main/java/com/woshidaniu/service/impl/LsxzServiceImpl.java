package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.ILsxzDao;
import com.woshidaniu.dao.entities.LsxzModel;
import com.woshidaniu.service.svcinterface.ILsxzService;

/**
 * 选择老师信息
 * @author Jiangdong.Yi
 *
 */
@Service("lsxzService")
public class LsxzServiceImpl extends BaseServiceImpl<LsxzModel, ILsxzDao>
		implements ILsxzService {
	
	@Resource
	private ILsxzDao dao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	
	/**
	 * 查询老师信息列表
	 * @param String
	 * @return
	 */
	public List<LsxzModel> getModelList(String ids) {
		if (!StringUtils.isEmpty(ids)) {
			String[] pks = ids.split(",");
			
			HashMap<String, Object> hashMap=new HashMap<String, Object>();
			List<LsxzModel> list = new ArrayList<LsxzModel>();
			LsxzModel lsxz=null;
			for (int i = 0; i < pks.length; i++) {
				lsxz = new LsxzModel();
				lsxz.setZgh(pks[i]);
				list.add(lsxz);
			}
			hashMap.put("list", list);
			return dao.getModelList(hashMap);
		}
		return null;
	}

	/**
	 * 获取老师信息列表
	 * @param model
	 * @return
	 */
	public List<LsxzModel> getLsxxList(LsxzModel model) {
		return dao.getLsxxList(model);
	}

	/**
	 * 获取老师选择根据职工号
	 * @param zgh
	 * @return
	 */
	public LsxzModel getLsxzByZgh(String zgh) {
		if(StringUtils.isEmpty(zgh)){
			return null;
		}
		LsxzModel model=new LsxzModel();
		model.setZgh(zgh);
		return dao.getModel(model);
	}
	
	
	/**
	 * 获取老师信息列表【2015.10.16模糊查询】
	 * @param model
	 * @return
	 */
	public List<Map<String, String>> likeQueryLsxxList(HashMap<String, Object> map) {
		return dao.likeQueryLsxxList(map);
	}
	
}
