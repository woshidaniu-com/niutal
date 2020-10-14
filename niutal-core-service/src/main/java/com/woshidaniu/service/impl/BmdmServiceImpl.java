package com.woshidaniu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.cache.CacheName;
import com.woshidaniu.common.datarange.DataItem;
import com.woshidaniu.common.datarange.DataItemService;
import com.woshidaniu.common.datarange.DataRangeService;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dao.daointerface.IBmdmDao;
import com.woshidaniu.dao.daointerface.IYhglDao;
import com.woshidaniu.dao.entities.BmdmModel;
import com.woshidaniu.format.pinyin4j.PingYinUtils;
import com.woshidaniu.service.svcinterface.IBmdmService;
import com.woshidaniu.taglibs.data.TagDataProvider;

import net.sf.json.JSONObject;

@Service("bmdmService")
public class BmdmServiceImpl extends BaseServiceImpl<BmdmModel , IBmdmDao> implements IBmdmService,TagDataProvider,DataItemService,DataRangeService {

	@Resource
	private IBmdmDao dao;

	@Resource
	private IYhglDao yhglDao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	
	public List<BmdmModel> queryModel(Map<String, String> map){
		return dao.queryModel(map);
	}
	
	
	@Cacheable(value=CacheName.CACHE_BASIC,key="'bmdm_getModelList'")
	public List<BmdmModel> getModelList(BmdmModel model) {
		return dao.getModelList(model);
	}
	
	public List<BmdmModel> getPagedList(BmdmModel model) {
		return dao.getPagedList(model);
	}
	
	public List<Map<String, String>> getModelMapList(Map<String,String> params){
		return dao.getModelMapList(params);
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.taglibs.data.TagDataProvider#getDataList(net.sf.json.JSONObject)
	 */
	public List<?> getDataList(JSONObject json) {
		BmdmModel model = null;
		return getModelList(model);
	}


	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.common.datarange.DataItemService#getDataItemList()
	 */
	public List<DataItem> getDataItemList() {
		BmdmModel model = null;
		List<BmdmModel> list = getModelList(model);
		List<DataItem> itemList = new ArrayList<DataItem>();

		for (BmdmModel bmdm : list) {
			DataItem item = new DataItem();
			item.setKey(bmdm.getBmdm_id());
			item.setValue(bmdm.getBmmc());
			item.setPinyin(PingYinUtils.converterToSpell(bmdm.getBmmc()).substring(0, 1));
			itemList.add(item);
		}

		return itemList;
	}


	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.common.datarange.DataRangeService#getDataRangeList(com.woshidaniu.common.log.User)
	 */
	public List<String> getDataRangeList(User user) {
		return yhglDao.getYhsjfwList(user.getYhm(), "xydm");
	}
	
	
	
	
	
	
	
	
	
	
}
