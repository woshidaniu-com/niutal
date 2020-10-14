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
import com.woshidaniu.dao.daointerface.IYhglDao;
import com.woshidaniu.dao.daointerface.IZydmDao;
import com.woshidaniu.dao.entities.ZydmModel;
import com.woshidaniu.format.pinyin4j.PingYinUtils;
import com.woshidaniu.service.svcinterface.IZydmService;

@Service("zydmService")
public class ZydmServiceImpl extends BaseServiceImpl<ZydmModel , IZydmDao> implements IZydmService , DataItemService , DataRangeService {

	@Resource
	private IZydmDao dao;

	@Resource
	private IYhglDao yhglDao;
	
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IZydmService#queryModel(java.util.Map)
	 */
	public List<ZydmModel> queryModel(Map<String, String> map) {
		return dao.queryModel(map);
	}

	

	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.common.service.BaseServiceImpl#getModelList(java.lang.String[])
	 */
	@Cacheable(value=CacheName.CACHE_BASIC, key="zydm_getModelList")
	public List<ZydmModel> getModelList(String... str) {
		
		return dao.getModelList(str);
	}



	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.common.datarange.DataItemService#getDataItemList()
	 */
	public List<DataItem> getDataItemList() {
		List<ZydmModel> list = getModelList();
		List<DataItem> itemList = new ArrayList<DataItem>();

		for (ZydmModel zydm : list) {
			DataItem item = new DataItem();
			item.setKey(zydm.getZydm_id());
			item.setValue(zydm.getZymc());
			item.setPinyin(PingYinUtils.converterToSpell(zydm.getZymc()).substring(0, 1));
			itemList.add(item);
		}
		return itemList;
	}



	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.common.datarange.DataRangeService#getDataRangeList(com.woshidaniu.common.log.User)
	 */
	public List<String> getDataRangeList(User user) {
		return yhglDao.getYhsjfwList(user.getYhm(), "zydm");
	}
}
