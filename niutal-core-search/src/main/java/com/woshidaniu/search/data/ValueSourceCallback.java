/**
 * 
 */
package com.woshidaniu.search.data;

import java.util.List;

import com.woshidaniu.search.dao.entities.SourceDataItem;

/**
 * 用户自定义数据接口
 * 
 * @author 小康
 *
 */
public interface ValueSourceCallback {

	/**
	 * 用户实现
	 * 约定 
	 * @return
	 */
	List<SourceDataItem> getData();
	
}
