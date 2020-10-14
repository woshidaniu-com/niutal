package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.datarange.DataRangeService;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.IJsglDao;
import com.woshidaniu.dao.daointerface.IYhglDao;
import com.woshidaniu.dao.entities.YhglModel;
import com.woshidaniu.service.svcinterface.IYhglService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：用户管理Service
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月16日上午11:46:39
 */
@Service("yhglService")
public class YhglServiceImpl extends BaseServiceImpl<YhglModel, IYhglDao> implements IYhglService , DataRangeService {
	
	
	@Autowired
	private IYhglDao dao;	
	
	@Autowired
	private IJsglDao jsglDao;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}


	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IYhglService#insert(com.woshidaniu.dao.entities.YhglModel, java.lang.String[])
	 */
	public boolean insert(YhglModel model, String[] jsdms) {
		int count = dao.insert(model);//增加用户
		
		if (jsdms != null && jsdms.length > 0){
			dao.zjYhjsxx(model.getZgh(), jsdms);
		}
		
		return count > 0;
	}



	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IYhglService#update(com.woshidaniu.dao.entities.YhglModel, java.lang.String[])
	 */
	public boolean update(YhglModel model, String[] jsdms) {
		int count = dao.update(model);//修改用户
		//修改用户角色
		String zgh = model.getZgh();
		dao.scYhjsxx(new String[]{zgh});
		
		if (jsdms != null && jsdms.length > 0){
			dao.zjYhjsxx(model.getZgh(), jsdms);
		}
		return count > 0;
	}


	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IYhglService#scYhxx(java.lang.String[])
	 */
	public boolean scYhxx(String[] zgh)  {
		if (zgh == null || zgh.length == 0) return false;
		
		int count = dao.scYhxx(zgh);
	
		if (count > 0){
			dao.scYhjsxx(zgh);
			jsglDao.deleteYhEjqx();
		}
		return count  > 0;
	}


	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.common.datarange.DataRangeService#getDataRangeList(com.woshidaniu.common.log.User)
	 */
	public List<String> getDataRangeList(User user) {
		String[] yhbmArr = new String[]{user.getBmdm_id()};
		return Arrays.asList(yhbmArr);
	}


	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IYhglService#updateYhmm(java.lang.String[], java.lang.String)
	 */
	public boolean updateYhmm(String[] zghArr, String password) {
		if (zghArr == null || zghArr.length == 0) return false;
		return dao.updateYhmm(zghArr, password)  > 0;
	}


	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IYhglService#insertYhsjfw(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public boolean insertYhsjfw(String ids, String sjfw) {
		
		String[] zghArr = ids.split(",");
		
		JSONObject json = JSONObject.fromObject(sjfw);
		if(json.isEmpty()) {
			dao.deleteYhsjfw(zghArr);
			return true;
		}
		
		if (zghArr == null || zghArr.length == 0) return false;
		
		dao.deleteYhsjfw(zghArr);
		
		List<Map<String,String>> params = new ArrayList<Map<String,String>>();
		Iterator<String> sIterator = json.keys();  
		while(sIterator.hasNext()){  
		    String key = sIterator.next();  
		    JSONArray value = json.getJSONArray(key);
		    for (int i = 0 , j = value.size() ; i < j ; i ++){
		    	for (String zgh : zghArr){
		    		Map<String,String> param = new HashMap<String, String>();
			    	param.put("zgh", zgh);
			    	param.put("sjdm", key);
			    	param.put("sjid", value.getString(i));
			    	params.add(param);
		    	}
		    }
		} 
		return dao.insertYhsjfw(params) > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IYhglService#getSjfwByYh(java.lang.String)
	 */
	public List<Map<String, String>> getSjfwByYh(String zgh) {
		return dao.getSjfwByYh(zgh);
	}

	@Override
	public List<String> getYhsjfwList(String zgh, String sjdm) {
		return dao.getYhsjfwList(zgh, sjdm);
	}

	@Override
	public boolean updateYhmmBatch(String[] zghArr, String[] passwords) {
		int count = 0;
		for(int i=0;i<zghArr.length;i++) {
			String zgh = zghArr[i];
			String pass = passwords[i];
			count += this.dao.updateYhmm(new String[] {zgh},pass);			
		}
		return count > 0;
	}

	@Override
	public boolean updateCheckYhsjfw(String id, String sjdm,String value, boolean check) {
		if(check) {//选中，增加操作
			//先删除以前可能存在的
			this.dao.deleteCheckedYhsjfw(id,sjdm,value);
			boolean result = this.dao.insertCheckedYhsjfw(id,sjdm,value);
			return result;
		}else {//不选中，取消操作
			this.dao.deleteCheckedYhsjfw(id,sjdm,value);
			return true;
		}
	}
}
