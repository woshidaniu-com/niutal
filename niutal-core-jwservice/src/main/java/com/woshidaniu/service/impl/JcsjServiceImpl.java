package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.daointerface.IJcsjDao;
import com.woshidaniu.entities.JcsjModel;
import com.woshidaniu.service.common.impl.CommonBaseServiceImpl;
import com.woshidaniu.service.svcinterface.IJcsjService;
import com.woshidaniu.util.local.LocaleUtils;

/**
 * 
* 
* 类名称：JcsjServiceImpl 
* 类描述： 基础数据业务处理实现类
* 创建人：xucy 
* 创建时间：2012-4-13 下午01:44:39 
* 修改人：xucy 
* 修改时间：2012-4-13 下午01:44:39 
* 修改备注： 
* @version 
*
 */
@After
@Logger(model="N0105",business="N010501")
@Service("jcsjService")
public class JcsjServiceImpl extends CommonBaseServiceImpl<JcsjModel, IJcsjDao> implements IJcsjService  {
	
	@Resource
	private IJcsjDao dao;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);

	}
	//查询基础数据列表(不分页用于导出)
	public List<JcsjModel> cxJcsjList(JcsjModel model){
		return dao.cxJcsjList(model);
	}
	
	@Comment(recordSQL=true)
	public boolean insert(JcsjModel model) {
		return dao.insert(model) > 0;
	}
	
	@Comment(recordSQL=true)
	public boolean update(JcsjModel model) {
		return dao.update(model) > 0;
	}
	
	//删除基础数据
	@Comment(recordSQL=true)
	public boolean scJcsj(JcsjModel model) {
		List<JcsjModel> list = new ArrayList<JcsjModel>();
		if(!BlankUtils.isBlank(model.getPkValue())){
			String[] pks = model.getPkValue().split(",");
			for (int i = 0; i < pks.length; i++) {
				JcsjModel jcsjmodel = new JcsjModel();
				jcsjmodel.setPkValue(pks[i]);
				list.add(jcsjmodel);
			}
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("list", list);
		int result = dao.batchDelete(param);
		/*if(!BlankUtils.isBlank(cache)){
			String lx = null;
			for (JcsjModel jcsjModel : list) {
				lx = jcsjModel.getPkValue().split("_")[0];
				//清除同类型基础数据的缓存
				String autoKey1 = CacheKeyUtils.genKey(getClass(), "getJcsjList" ,lx );
				cache.delete(autoKey1);
			}
		}*/
		return result > 0 ? true : false;
	}
	
	//根据类型代码查询基础数据
	public List<JcsjModel> getJcsjList(String lx){
		List<JcsjModel> list = null;
		/*if(!BlankUtils.isBlank(cache)){
			String autoKey = CacheKeyUtils.genKey(getClass(), "getJcsjList" , lx );
			Object object = cache.get(autoKey);
			if(!BlankUtils.isBlank(object)){
				list =  (List) object;
			}else{
				//缓存过期重新查询
				list = dao.getJcsjList(LocaleUtils.getLocaleKey(),lx);
				//缓存一周
				cache.set(autoKey,ICacheClient.CACHE_EXP_FOREVER,list);
			}
		}else{
			list = dao.getJcsjList(LocaleUtils.getLocaleKey(),lx);
		}*/
		list = dao.getJcsjList(LocaleUtils.getLocaleKey(),lx);
		//dao.getXqList(null);
		return list;
	}
	
	//查询学期List
	public List<JcsjModel> getXqList(String syxq){
		return dao.getXqList(null);
	}
	 
}
