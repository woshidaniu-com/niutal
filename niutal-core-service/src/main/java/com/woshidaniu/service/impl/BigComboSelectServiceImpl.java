package com.woshidaniu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.cache.CacheName;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.common.system.cache.CacheNames;
import com.woshidaniu.dao.daointerface.IBigComboSelectDao;
import com.woshidaniu.dao.entities.BigComboSelectModel;
import com.woshidaniu.service.svcinterface.IBigComboSelectService;

@Service("bigComboSelectService")
public class BigComboSelectServiceImpl extends
		BaseServiceImpl<BigComboSelectModel, IBigComboSelectDao> implements IBigComboSelectService,CacheNames {

	private Map<String,String> zxsMap = null;
	
	@Resource
	private IBigComboSelectDao dao;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
	    super.setDao(dao);   
	}
	
	
	public void setZxsMap(Map<String, String> zxsMap) {
		this.zxsMap = zxsMap;
	}
	
	public Map<String, String> getZxsMap() {
		return zxsMap;
	}
	
	/**
	 * 查询学院首字母
	 * @param hashMap
	 * @return
	 */
	@Override
	@Cacheable(value=CacheName.CACHE_OTHER,key="'getAllXYInitial'")
	public List<BigComboSelectModel> getAllXYInitial() {
		return dao.getAllXYInitial();
	}

	@Override
	@Cacheable(value=CacheName.CACHE_OTHER,key="#initial + 'getXYDataByInitial'")
	public List<BigComboSelectModel> getXYDataByInitial(String initial) {
		BigComboSelectModel bcs = new BigComboSelectModel();
		bcs.setInitialname(initial);
		return dao.getXYDataByInitial(bcs);
	}
	
	/**
	 * 查询下拉菜单首字母
	 * @param hashMap
	 * @return
	 */
	@Override
	@Cacheable(value=CacheName.CACHE_OTHER,key="'getBigComboInitial'+#table+#name+#parentName+#parentId+#secondparentName+#secondParentId")
	public List<BigComboSelectModel> getBigComboInitial(String name, String table,String parentName,String parentId,String secondparentName, String secondParentId) {
		BigComboSelectModel bcs = new BigComboSelectModel();
		bcs.setSelectName(name);
		bcs.setSelectTable(table);
		bcs.setParentName(parentName);
		bcs.setParentId(parentId);
		bcs.setSecondparentName(secondparentName);
		bcs.setSecondParentId(secondParentId);
		return dao.getBigComboInitial(bcs);
	}

	@Override
	@Cacheable(value=CacheName.CACHE_OTHER,key="'getBigComboDataByInitial'+#initial+#id+#name+#table+#parentName+#parentId+#secondparentName+#secondParentId")
	public List<BigComboSelectModel> getBigComboDataByInitial(String id, String name, String table, String initial,String parentName,String parentId,String secondparentName, String secondParentId) {
		BigComboSelectModel bcs = new BigComboSelectModel();
		bcs.setSelectId(id);
		bcs.setSelectName(name);
		bcs.setSelectTable(table);
		bcs.setInitialname(initial);
		bcs.setParentName(parentName);
		bcs.setParentId(parentId);
		bcs.setSecondparentName(secondparentName);
		bcs.setSecondParentId(secondParentId);
		return dao.getBigComboDataByInitial(bcs);
	}
	
	@Override
	@Cacheable(value=CacheName.CACHE_OTHER,key="'getBigComboDataWithoutInitial'+#id+#name+#table+#parentName+#parentId+#secondparentName+#secondParentId")
	public List<BigComboSelectModel> getBigComboDataWithoutInitial(String id, String name, String table,String parentName,String parentId,String secondparentName, String secondParentId) {
		BigComboSelectModel bcs = new BigComboSelectModel();
		bcs.setSelectId(id);
		bcs.setSelectName(name);
		bcs.setSelectTable(table);
		bcs.setParentName(parentName);
		bcs.setParentId(parentId);
		bcs.setSecondparentName(secondparentName);
		bcs.setSecondParentId(secondParentId);
		return dao.getBigComboDataWithoutInitial(bcs);
	}

	@Cacheable(value=CacheName.CACHE_OTHER,key="'getBigComboDataShengList'")
	public List<BigComboSelectModel> getShengList() throws Exception {
		return dao.getShengList();
	}
	
	@Cacheable(value=CacheName.CACHE_OTHER,key="'getBigComboDataShiList'+#sheng")
	public List<BigComboSelectModel> getShiList(String sheng) throws Exception {
		//直接市调不同的方法查询下级列表
		if(zxsMap.containsKey(sheng)){
			return dao.getZxsqList(sheng);
		}
		
		return dao.getShiList(sheng);
	}
	
	@Cacheable(value=CacheName.CACHE_OTHER,key="'getBigComboDataXianList'+#shi")
	public List<BigComboSelectModel> getXianList(String shi) throws Exception {
		return dao.getXianList(shi);
	}
}
