/**
 * 
 */
package com.woshidaniu.search.data.factory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.data.query.AbstractValueSourceQuerier;
import com.woshidaniu.search.data.query.ClassMethodQuerier;
import com.woshidaniu.search.data.query.DBQuerier;


/**
 * 数据抓取工厂类
 * 
 * @author 小康
 * 
 */
public class ValueSourceQuerierFactory {

	static final Logger LOG = LoggerFactory.getLogger(ValueSourceQuerierFactory.class);
	//从方法返回
	static final String VALUE_SOURCE_METHOD = "method";
	//从数据表返回
	static final String VALUE_SOURCE_TABLE = "table";
	//从数据视图返回
	static final String VALUE_SOURCE_VIEW = "view";
	// 直接赋值
	static final String VALUE_SOURCE_DATA = "data";
	
	// 为实现每个处理器只存在一个实例
	private static Map<String, AbstractValueSourceQuerier> dataSourceFetcherObjects = Collections
			.synchronizedMap(new HashMap<String, AbstractValueSourceQuerier>());

	/**
	 * 
	 * @param method
	 * @param args
	 * @return
	 */
	public static AbstractValueSourceQuerier getInstance(String method , Object[] args){
		LOG.debug("AbstractValueSourceQuerier.getInstance({})", new String[]{method});
		if (method == null) {
			return null;
		} else {
			AbstractValueSourceQuerier querier = null;
			//if (querier == null) {		
				Class<? extends AbstractValueSourceQuerier> clazz = getClazz(method);	
				try {
					if(clazz != null){
						querier = ConstructorUtils.invokeConstructor(clazz, args , new Class[]{SearchConfigModel.class , String.class , String.class});
						dataSourceFetcherObjects.put(method, querier);
					}
				} catch (Exception e) {
					e.printStackTrace();
					querier = null;
				}
			//}
			return querier;
		}
	}
	
	
	private static Class<? extends AbstractValueSourceQuerier> getClazz(String method) {
		if(StringUtils.equals(method, VALUE_SOURCE_METHOD)){
			return ClassMethodQuerier.class;
		}else if(StringUtils.equals(method, VALUE_SOURCE_TABLE) || StringUtils.equals(method, VALUE_SOURCE_VIEW)){
			return DBQuerier.class;
		}
		return null;
	}


	/**
	 * 
	 * @param method
	 * @param args
	 * @return
	 */
	public static AbstractValueSourceQuerier getInstance(Class<? extends AbstractValueSourceQuerier> method){
		if (method == null) {
			return null;
		} else {
			AbstractValueSourceQuerier querier = dataSourceFetcherObjects.get(method
					.getName());
			if (querier == null) {
				try {
					Class.forName(method.getName());
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
					return null;
				}
				try {
					querier = ConstructorUtils.invokeConstructor(method, null);
					dataSourceFetcherObjects.put(method.getName(), querier);
				} catch (Exception e) {
					e.printStackTrace();
					querier = null;
				}
			}
			return querier;
		}
	}
}
