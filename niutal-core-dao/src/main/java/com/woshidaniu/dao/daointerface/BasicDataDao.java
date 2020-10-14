/**
 * 2013 2013-7-5 下午01:43:54
 * 
 * author : HJL 718
 */
package com.woshidaniu.dao.daointerface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.woshidaniu.dao.entities.BasicDataModel;

/**
 * Create on 2013-7-5 下午01:43:54 
 * 
 * All CopyRight By http://www.woshidaniu.com/ AT 2013  Version 1.0 
 *
 * @author : HJL [718] 
 */
@Repository("basicDataDao")
public interface BasicDataDao {
	/**
	 * 得到基础数据
	 * @return List<CacheOption>
	 */
	public List<BasicDataModel> getBasicDataModelList();
	     
}
