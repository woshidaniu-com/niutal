/**
 * 
 */
package com.woshidaniu.search.service.svcinterface;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.woshidaniu.search.dao.entities.InitialLabelCount;
import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SearchItemModel;


/**
 * 高级查询数据查询服务接口
 * 
 * @author xiaokang
 *
 */
public interface IDataQueryService {
	
	static final String VALUE_SOURCE_SEPARATOR = "_";
	
	/**
	 * 获取高级查询初始化数据<br>
	 * 
	 * 迭代查询配置列表，根据配置获取初始化数据，<br>
	 * 	只有valueSource字段配置不为空的时候，调用想用的数据源获取数据<br>
	 * 	数据源的获取有以下几种途径<br>
	 *   	<b>1.根据一个方法获取<br>
	 * 		<b>2.根据一个数据表获取<br>
	 * 		<b>3.根据一个视图获取<br>
	 * 		<b>4.根据一个资源文件获取<br>
	 * 		<b>5.用户直接在配置的时候指定，直接解析<br>
	 * @param configModel
	 * @param dataSourceModel
	 * @return
	 */
	List<SearchItemModel> queryInitialData(List<SearchConfigModel> configList);
	
	/**
	 *  获取高级查询初始化数据<br>
	 *  
	 *  带联动数据
	 * @param configList
	 * @param linkageQueryData
	 * @return
	 */
	List<SearchItemModel> queryInitialData(List<SearchConfigModel> configList , Map<String, Collection<String>> linkageQueryData);
	
	/**
	 * 获取更多数据
	 * @param cibfigModel
	 * @return
	 */
	SearchItemModel queryMoreData(SearchConfigModel configModel , Map<String, Collection<String>> linkageQueryData);
	
	/**
	 * 获取更多数据 首字母搜索模式
	 * @param configModel
	 * @return
	 */
	SearchItemModel queryMoreDataWithInitalLabel(SearchConfigModel configModel , String initalLabelValue ,  Map<String, Collection<String>> linkageQueryData);
	
	/**
	 * 获取首字母总计
	 * @param configModel
	 * @return
	 */
	List<InitialLabelCount> queryIntialLabelCount(SearchConfigModel configModel, Map<String, Collection<String>> linkageQueryData);
	
	/**
	 * 
	 * @param configModel
	 * @param linkageQueryData
	 * @return
	 */
	List<SearchItemModel> getLinkageDataListMap(SearchConfigModel configModel , Map<String, Collection<String>> linkageQueryData);
	
	/**
	 * 获取默认条件数据
	 * @param configList
	 * @param defaultCondition
	 * @return
	 */
	List<SearchItemModel> queryDefaultConditionlData(List<SearchConfigModel> configList, Map<String , Collection<String>> defaultCondition);
	
}
