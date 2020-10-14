package com.woshidaniu.common.system.cache;

import java.util.ArrayList;
import java.util.List;

import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.system.emum.BaseGBDataEnum;
import com.woshidaniu.dao.entities.BaseGBModel;

/**
 * 暴露功能，让开发人员调用
 * 
 * Create on 2013-7-5 下午03:07:32
 * 
 * All CopyRight By http://www.woshidaniu.com/ AT 2013 Version 1.0
 * 
 * @author : HJL [718]
 */
public class CacheLogic {

	public static final String	TY_FULL_NAME	= "中国共产主义青年团团员";
	
	public static final String	QZ_FULL_NAME	= "群众";

	/**
	 * 获取缓存
	 * 
	 * @param key
	 * @return List<CacheOption>
	 */
	public static List<CacheOption> getCache(String key) {
		return CacheLoader.getCache(key);
	}

	/**
	 * 更新缓存
	 * 
	 * @param key
	 * @param value
	 *            void
	 */
	public static void updateCache(String key, Object value) {
		CacheLoader.updateCache(key, value);
	}

	/**
	 * 得到国标缓存
	 * 
	 * @param key
	 *            ：缓存KEY
	 * @return List<? extends BaseGBModel>
	 */
	public static List<? extends BaseGBModel> getGBCache(String key) {
		List<? extends BaseGBModel> gbCache = CacheLoader.getGBCache(key);
		return CollectionUtils.isEmpty(gbCache) ? new ArrayList<BaseGBModel>() : gbCache;
	}

	public static String getOptionValue(String key, String optionKey) {
		List<CacheOption> cache = getCache(key);
		for (CacheOption co : cache) {
			if (StringUtils.equalsIgnoreCase(co.getKey(), optionKey)) {
				return co.getValue();
			}
		}
		return StringUtils.EMPTY;
	}

	public static String getGbZzmm(String mc) {
		List<? extends BaseGBModel> gbCache = getGBCache(BaseGBDataEnum.ZHENG_ZHI_MIAN_MIAO.getValue());
		for (BaseGBModel model : gbCache) {
			if (StringUtils.equalsIgnoreCase(model.getMc(), mc)) {
				return model.getDm();
			}
		}
		return StringUtils.EMPTY;
	}
	
	/**
	 * 得到高级查询缓存html
	 * @param key
	 * @return
	 */
//	public static HashMap<String, HashMap<String, String>> getGjcxCache() {
//		return CacheLoader.getGjcxCache();
//	}

}
