/**
 * 
 */
package com.woshidaniu.search;

import java.util.ArrayList;
import java.util.List;


import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.util.base.StringUtil;

/**
 * 高级查询配置对象
 * 
 * @author 小康
 *
 */
public class SuperSearchConfig {

	/*
	 * 配置标识
	 */
	private String configKey;
	
	/*
	 * 数据库配置列表
	 */
	private List<SearchConfigModel> searchConfigList = new ArrayList<SearchConfigModel>();

	/*
	 * 获取其中某个配置对象
	 */
	public SearchConfigModel getSearchConfigItemById(String configId){
		if(StringUtil.isBlank(configId))
			return null;
		
		if(searchConfigList == null || searchConfigList.size() == 0)
			return null;
		for (SearchConfigModel iterable_element : searchConfigList) {
			String id = iterable_element.getId();
			if(StringUtil.equals(id, configId))
				return iterable_element;
		}
		return null;
	}
	
	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public List<SearchConfigModel> getSearchConfigList() {
		return searchConfigList;
	}

	public void setSearchConfigList(List<SearchConfigModel> searchConfigList) {
		this.searchConfigList = searchConfigList;
	}
}
