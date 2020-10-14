/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.zxzx.ZxzxConstant;
import com.woshidaniu.zxzx.dao.daointerface.ICsszDao;
import com.woshidaniu.zxzx.dao.entities.CsszModel;
import com.woshidaniu.zxzx.service.svcinterface.ICsszService;

/**
 * @类名 CsszServiceImpl.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月19日 上午8:32:56
 * @功能描述 在线咨询-参数设置
 * 
 */
@Service("zxzxCsszService")
public class CsszServiceImpl implements ICsszService {

	protected static final String OPEN_STATE_OPEN = "open";
	
	protected static final String OPEN_STATE_CLOSE = "close";
	
	protected static final String OPEN_STATE_NO_AUTH = "no-auth";
	
	
	
	@Autowired
	@Qualifier("zxzxCsszDao")
	private ICsszDao csszDao;
	
	@Override
	public Map<String,Object> isOpen(boolean isLogin) {
		Map<String,Object> isOpenMap = new HashMap<String, Object>();
		Map<String, String> config = getConfig();
		isOpenMap.put("config", config);
		//如果数据库没有配置,默认不允许用户提交数据
		if(config == null || config.size() == 0){
			isOpenMap.put("isOpen", false);
			isOpenMap.put("state", OPEN_STATE_CLOSE);
			return isOpenMap;
		}
		String kg   = config.get(ZxzxConstant.CSSZ_KG_DM);
		String kssj = config.get(ZxzxConstant.CSSZ_KSSJ_DM);
		String jssj = config.get(ZxzxConstant.CSSZ_JSSJ_DM);
		String zxms = config.get(ZxzxConstant.CSSZ_ZXMS_DM);
		
		isOpenMap.put("kssj", kssj);
		isOpenMap.put("jssj", jssj);
		isOpenMap.put("kg", kg);
		isOpenMap.put("zxms", zxms);
		
		//未开启
		if("0".equals(kg)){
			isOpenMap.put("isOpen", false);
			isOpenMap.put("message", "ZXZX_CONGIF_1");
			isOpenMap.put("state", OPEN_STATE_CLOSE);
			return isOpenMap;
		}
		
		String currentDate = DateUtils.format(DateUtils.DATE_FORMAT_FOUR);
		//开启时间不为空
		if(kssj != null){
			if(currentDate.compareTo(kssj) < 0){
				isOpenMap.put("isOpen", false);
				isOpenMap.put("message", "ZXZX_CONGIF_1");
				isOpenMap.put("state", OPEN_STATE_CLOSE);
				return isOpenMap;
			}
		}
		//结束时间不为空
		if(jssj != null){
			if(currentDate.compareTo(jssj) > 0){
				isOpenMap.put("isOpen", false);
				isOpenMap.put("message", "ZXZX_CONGIF_1");
				isOpenMap.put("state", OPEN_STATE_CLOSE);
				return isOpenMap;
			}
		}
		
		//开启时间如果没有设置，表示没有限制
//		if((kssj == null && jssj == null)){
//			isOpenMap.put("isOpen", true);
//			isOpenMap.put("state", OPEN_STATE_OPEN);
//			return isOpenMap;
//		}
		
		//咨询模式
		if("1".equals(zxms) && (!isLogin)){
			isOpenMap.put("isOpen", false);
			isOpenMap.put("message", "ZXZX_CONGIF_2");
			isOpenMap.put("state", OPEN_STATE_NO_AUTH);
			return isOpenMap;
		}
		
		isOpenMap.put("isOpen", true);
		isOpenMap.put("state", OPEN_STATE_OPEN);
		return isOpenMap;
	}
	
	/* (non-Javadoc)
	 * @see com.woshidaniu.zxzx.service.svcinterface.ICsszService#getConfig()
	 */
	@Override
	public Map<String, String> getConfig() {
		CsszModel model = new CsszModel();
		List<CsszModel> modelList = csszDao.getModelList(model);
		if(modelList != null && modelList.size() > 0){
			Map<String, String> map = new HashMap<String, String>();
			for (CsszModel csszModel : modelList) {
				map.put(csszModel.getCsdm(), csszModel.getCsmc());
			}
			return map;
		}
		return new HashMap<String, String>();
	}

	/* (non-Javadoc)
	 * @see com.woshidaniu.zxzx.service.svcinterface.ICsszService#updateConfig(java.util.Map)
	 */
	@Override
	@Transactional
	public boolean updateConfig(Map<String, String> config) {
		//1.
		csszDao.deleteAll();
		//2.
		boolean result = Boolean.FALSE;
		if(config != null && config.size() > 0){
			
			List<CsszModel> modelList = new ArrayList<CsszModel>();
			Set<String> keySet = config.keySet();
			Iterator<String> iterator = keySet.iterator();
			while(iterator.hasNext()){
				String dm = iterator.next();
				String mc = config.get(dm);
				modelList.add(new CsszModel(dm,mc));
			}
			csszDao.batchInsert(modelList);
			result = true;
		}
		return result;
	}

	
	public ICsszDao getCsszDao() {
		return csszDao;
	}

	public void setCsszDao(ICsszDao csszDao) {
		this.csszDao = csszDao;
	}

}
