package com.woshidaniu.service.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.daointerface.ICommonQueryDao;
import com.woshidaniu.daointerface.IEmailSettingDao;
import com.woshidaniu.entities.CommonModel;
import com.woshidaniu.service.common.ICommonQueryService;

/**
 * 
 *@类名称	: CommonQueryServiceImpl.java
 *@类描述	：与业务无关的公共基础查询service接口实现
 *@创建人	：kangzhidong
 *@创建时间	：2016年4月20日 下午1:11:18
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0	
 */
@Service("commonQueryService")
public class CommonQueryServiceImpl extends BaseServiceImpl<CommonModel, ICommonQueryDao>  implements ICommonQueryService,InitializingBean {
	
	@Resource
	protected ICommonQueryDao queryDao;
	@Resource
	protected IEmailSettingDao settingDao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(queryDao);
	}
	
	public String getSysGuid() {
		return getQueryDao().getSysGuid();
	}
	
	@Override
	public String getDatabaseTime() {
		return getQueryDao().getDatabaseTime("yyyy-MM-dd HH24:mi:ss");
	}

	@Override
	public String getDatabaseTime(String dataFormat) {
		return getQueryDao().getDatabaseTime(dataFormat);
	}

	@Override
	public List<Map<String,String>> getResultList(String tableName,String tabCol,String tabColV) {
		
		List<Map<String,String>> tableList = null;
		/**
		//缓存服务器开启
		if(isCacheStart()){
			String autoKey = CacheKeyGen.genKeyForMap(getClass(), "getList",tableName,map);
			Object object = cacheClient.get(autoKey);
			if(!BlankUtil.isBlank(object)){
				tableList =  (List<Map<String, String>>) object;
			}else{
				//缓存过期重新查询
				tableList = getQueryDao().cxSelectList(tableName, key, value);;
				//永久缓存，实际上最大只有一个月
				cacheClient.set(autoKey, tableList);
				//缓存当前缓存对应的key
				cacheClient.storeAutoKey(getClass(), "getList", autoKey);
			}
		}else{
			tableList = getQueryDao().getSelectList(tableName, key, value);
		}*/
		tableList = getQueryDao().getResultList(tableName, tabCol, tabColV);
		return tableList;
	}
	
	@Override
	public List<BaseMap> getTableList(String tableName,String tabCol,String tabColV) {
		
		List<BaseMap> tableList = null;
		/**
		//缓存服务器开启
		if(isCacheStart()){
			String autoKey = CacheKeyGen.genKeyForMap(getClass(), "getList",tableName,map);
			Object object = cacheClient.get(autoKey);
			if(!BlankUtil.isBlank(object)){
				tableList =  (List<Map<String, String>>) object;
			}else{
				//缓存过期重新查询
				tableList = getQueryDao().cxSelectList(tableName, key, value);;
				//永久缓存，实际上最大只有一个月
				cacheClient.set(autoKey, tableList);
				//缓存当前缓存对应的key
				cacheClient.storeAutoKey(getClass(), "getList", autoKey);
			}
		}else{
			tableList = getQueryDao().getSelectList(tableName, key, value);
		}*/
		tableList = getQueryDao().getSelectList(tableName, tabCol, tabColV);
		return tableList;
	}

	@Override
	public List<BaseMap> getTableList(String tableName,String[] columns, String sort, String order) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("columns", StringUtils.join(columns,","));
		map.put("sort", sort);
		map.put("order", order);
		/*//cacheClient.flushAll();
		List<BaseMap>  tableList = null;
		//缓存服务器开启
		if(isCacheStart()){
			String autoKey = CacheKeyGen.genKeyForMap(getClass(), "getTableList",tableName,map);
			Object object = cacheClient.get(autoKey);
			if(!BlankUtil.isBlank(object)){
				tableList =  (List<BaseMap>) object;
			}else{
				//缓存过期重新查询
				tableList = getQueryDao().cxTableList(map);
				//永久缓存，实际上最大只有一个月
				cacheClient.set(autoKey, tableList);
				//缓存当前缓存对应的key
				cacheClient.storeAutoKey(getClass(), "getTableList", autoKey);
			}
		}else{
			tableList = getQueryDao().getTableList(map);
		}*/
		return getQueryDao().getTableList(map);
	}
	
	@Override
	public List<BaseMap> getPagedTableList(String tableName, String tabCol,String tabColV) {
		return getQueryDao().getPagedSelectList(tableName,tabCol,tabColV);
	}

	@Override
	public List<BaseMap> getPagedTableList(String tableName, String[] columns, String sort, String order) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("columns", StringUtils.join(columns,","));
		map.put("sort", sort);
		map.put("order", order);
		return getQueryDao().getPagedTableList(map);
	}
	
	
	/**
	 * 
	 *@描述		：获取允许的邮箱后缀集合
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 21, 201612:03:57 PM
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public List<BaseMap> getEmailPostfixList(){
		return getSettingDao().getEmailPostfixList();
	}
	
	public ICommonQueryDao getQueryDao() {
		return queryDao;
	}

	public void setQueryDao(ICommonQueryDao queryDao) {
		this.queryDao = queryDao;
	}

	public IEmailSettingDao getSettingDao() {
		return settingDao;
	}

	public void setSettingDao(IEmailSettingDao settingDao) {
		this.settingDao = settingDao;
	}
	
}
