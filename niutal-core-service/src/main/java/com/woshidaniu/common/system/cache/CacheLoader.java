package com.woshidaniu.common.system.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.system.emum.BaseGBDataEnum;
import com.woshidaniu.dao.daointerface.BaseGbBasicDataDao;
import com.woshidaniu.dao.daointerface.BasicDataDao;
import com.woshidaniu.dao.entities.BaseGBModel;
import com.woshidaniu.dao.entities.BasicDataModel;
import com.woshidaniu.dao.entities.GBMzModel;
import com.woshidaniu.dao.entities.GBXbModel;
import com.woshidaniu.dao.entities.GBXlModel;
import com.woshidaniu.dao.entities.GBXwModel;
import com.woshidaniu.dao.entities.GBXzqModel;
import com.woshidaniu.dao.entities.GBZzmmModel;

import net.sf.ehcache.Element;

/**
 * 缓存相关装载,获取,更新
 * 
 * Create on 2013-7-5 下午04:52:44
 * 
 * All CopyRight By http://www.woshidaniu.com/ AT 2013 Version 1.0
 * 
 * @author : HJL [718]
 */
public class CacheLoader {

	private static Logger	logger	= LoggerFactory.getLogger(CacheLoader.class.getName());

	/**
	 * 全局加载缓存到磁盘 void
	 */
	public static void addCache() {
		BasicDataDao basicDataDao = (BasicDataDao) ServiceFactory.getService(BasicDataDao.class);
		List<BasicDataModel> basicDataModelList = basicDataDao.getBasicDataModelList();
		Set<String> typeSet = new HashSet<String>();
		for (BasicDataModel basicModel : basicDataModelList) {
			typeSet.add(basicModel.getLx());
		}

		for (String type : typeSet) {
			List<CacheOption> optionList = new ArrayList<CacheOption>();
			for (BasicDataModel basicModel : basicDataModelList) {
				if (StringUtils.equals(type, basicModel.getLx())) {
					CacheOption option = new CacheOption();
					option.setKey(basicModel.getDm());
					option.setValue(basicModel.getMc());
					optionList.add(option);
				}
			}
			CacheManagerLoader.getInstance().addElement(type, optionList);
		}
	}

	public static void addGbCache() {
		BaseGbBasicDataDao baseGbBasicDataDao = (BaseGbBasicDataDao) ServiceFactory.getService(BaseGbBasicDataDao.class);

		List<GBMzModel> mingZhuModelList = baseGbBasicDataDao.getMingZhuModelList();
		processMZ(mingZhuModelList);

		List<GBXbModel> xingBieModelList = baseGbBasicDataDao.getXingBieModelList();
		processXB(xingBieModelList);

		List<GBXlModel> xueLiModelList = baseGbBasicDataDao.getXueLiModelList();
		processXL(xueLiModelList);

		List<GBXwModel> xueWeiModelList = baseGbBasicDataDao.getXueWeiModelList();
		processXW(xueWeiModelList);

		List<GBZzmmModel> zhengZhiMianMaoModelList = baseGbBasicDataDao.getZhengZhiMianMaoModelList();
		processZzmm(zhengZhiMianMaoModelList);
		
		List<GBXzqModel> xingZhengQuModelList = baseGbBasicDataDao.getXingZhengQuModelList();
		processXzq(xingZhengQuModelList);
	}


	/**
	 * 高级查询
	 */
//	public static void addGjcxCache() {
//		logger.info("高级查询缓存开始...");
//		try {
//			ISearchService superSearchService = (ISearchService) ServiceFactory
//					.getService("superSearchService");
//			HashMap<String, HashMap<String, String>> gjcxMap =  superSearchService.getBuildSelectHtml(OptionUtil.GJCX_CXBZ);
//			processGjcx(gjcxMap);
//		} catch (Exception e) {
//			logger.error("缓存-高级查询报错：" + e.getMessage());
//		}
//		logger.info("高级查询缓存结束...");		
//	}
	
	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            ：缓存KEY
	 * @return List<CacheOption>
	 */
	@SuppressWarnings("unchecked")
	public static List<CacheOption> getCache(String key) {
		Element element = CacheManagerLoader.getInstance().getElement(key);
		if(element!=null){
    		Object objectValue = element.getObjectValue();
    		if (null != objectValue) {
    			List<CacheOption> cacheOptionList = (List<CacheOption>) objectValue;
    			return cacheOptionList;
    		}
		}
		return new ArrayList<CacheOption>();
	}

	/**
	 * 更新缓存
	 * 
	 * @param key
	 * @param value
	 *            void
	 */
	public static void updateCache(String key, Object value) {
		CacheManagerLoader.getInstance().updateElement(key, value);
	}

	private static void processMZ(List<GBMzModel> mingZhuModelList) {
		logger.info("民族缓存开始...");
		CacheManagerLoader.getInstance().addElement(BaseGBDataEnum.MING_ZHU.getValue(), mingZhuModelList);
		logger.info("民族缓存结束...");
	}

	private static void processXB(List<GBXbModel> xingBieModelList) {
		logger.info("性别缓存开始...");
		CacheManagerLoader.getInstance().addElement(BaseGBDataEnum.XING_BIE.getValue(), xingBieModelList);
		logger.info("性别缓存结束...");
	}

	private static void processXL(List<GBXlModel> xueLiModelList) {
		logger.info("学历缓存开始...");
		CacheManagerLoader.getInstance().addElement(BaseGBDataEnum.XUE_LI.getValue(), xueLiModelList);
		logger.info("学历缓存结束...");
	}

	private static void processXW(List<GBXwModel> xueWeiModelList) {
		logger.info("学位缓存开始...");
		CacheManagerLoader.getInstance().addElement(BaseGBDataEnum.XUE_WEI.getValue(), xueWeiModelList);
		logger.info("学位缓存结束...");
	}

	private static void processZzmm(List<GBZzmmModel> zhengZhiMianMaoModelList) {
		logger.info("政治面貌缓存开始...");
		CacheManagerLoader.getInstance().addElement(BaseGBDataEnum.ZHENG_ZHI_MIAN_MIAO.getValue(), zhengZhiMianMaoModelList);
		logger.info("政治面貌缓存结束...");
	}
	
	private static void processXzq(List<GBXzqModel> xingZhengQuModelList) {
		logger.info("行政区缓存开始...");
		CacheManagerLoader.getInstance().addElement(BaseGBDataEnum.XING_ZHENG_QU.getValue(), xingZhengQuModelList);
		logger.info("行政区缓存结束...");
	}

//	private static void processGjcx(HashMap<String,HashMap<String,String>> gjcxMap) {
//		CacheManagerLoader.getInstance().addElement(
//				BaseGBDataEnum.GJCX_HTML.getValue(),
//				gjcxMap);
//	}
	
	/**
	 * 得到民族缓存
	 * 
	 * @param key
	 * @return List<GBMzModel>
	 */
	@SuppressWarnings("unchecked")
	private static List<GBMzModel> getGBMzCache(String key) {
		Element element = CacheManagerLoader.getInstance().getElement(key);
		Object objectValue = element.getObjectValue();
		if (null != null) {
			List<GBMzModel> cacheMzmodelList = (List<GBMzModel>) objectValue;
			return cacheMzmodelList;
		}
		return new ArrayList<GBMzModel>();
	}

	/**
	 * 得到性别缓存
	 * 
	 * @param key
	 * @return List<GBXbModel>
	 */
	@SuppressWarnings("unchecked")
	private static List<GBXbModel> getGBXbCache(String key) {
		Element element = CacheManagerLoader.getInstance().getElement(key);
		Object objectValue = element.getObjectValue();
		if (null != objectValue) {
			List<GBXbModel> cacheXbmodelList = (List<GBXbModel>) objectValue;
			return cacheXbmodelList;
		}
		return new ArrayList<GBXbModel>();
	}

	/**
	 * 得到民族缓存
	 * 
	 * @param key
	 * @return List<GBXlModel>
	 */
	@SuppressWarnings("unchecked")
	private static List<GBXlModel> getGBXlCache(String key) {
		Element element = CacheManagerLoader.getInstance().getElement(key);
		Object objectValue = element.getObjectValue();
		if (null != objectValue) {
			List<GBXlModel> cacheXlmodelList = (List<GBXlModel>) objectValue;
			return cacheXlmodelList;
		}
		return new ArrayList<GBXlModel>();
	}

	/**
	 * 得到学位缓存
	 * 
	 * @param key
	 * @return List<GBXwModel>
	 */
	@SuppressWarnings("unchecked")
	private static List<GBXwModel> getGBXwCache(String key) {
		Element element = CacheManagerLoader.getInstance().getElement(key);
		Object objectValue = element.getObjectValue();
		if (null != objectValue) {
			List<GBXwModel> cacheXwmodelList = (List<GBXwModel>) objectValue;
			return cacheXwmodelList;
		}
		return new ArrayList<GBXwModel>();
	}

	/**
	 * 得到政治面貌缓存
	 * 
	 * @param key
	 * @return List<GBZzmmModel>
	 */
	@SuppressWarnings("unchecked")
	private static List<GBZzmmModel> getGBZzmmCache(String key) {
		Element element = CacheManagerLoader.getInstance().getElement(key);
		Object objectValue = element.getObjectValue();
		if (null != objectValue) {
			List<GBZzmmModel> cacheZzmmModelList = (List<GBZzmmModel>) objectValue;
			return cacheZzmmModelList;
		}
		return new ArrayList<GBZzmmModel>();
	}

	/**
	 * 判断该KEY是否在国标枚举中
	 * 
	 * @param key
	 * @return boolean
	 */
	public static boolean isBaseGBEnum(String key) {
		if (StringUtils.isBlank(key))
			return false;
		BaseGBDataEnum[] values = BaseGBDataEnum.values();
		for (BaseGBDataEnum gbValue : values) {
			if (StringUtils.equals(key, gbValue.getValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 得到国标缓存
	 * @param key
	 * @return List<? extends BaseGBModel>
	 */
	public static List<? extends BaseGBModel> getGBCache(String key) {
		if (!isBaseGBEnum(key))
			return new ArrayList<BaseGBModel>();
		if (StringUtils.equals(key, BaseGBDataEnum.MING_ZHU.getValue())) {
			return getGBMzCache(key);
		} else if (StringUtils.equals(key, BaseGBDataEnum.XING_BIE.getValue())) {
			return getGBXbCache(key);
		} else if (StringUtils.equals(key, BaseGBDataEnum.XUE_LI.getValue())) {
			return getGBXlCache(key);
		} else if (StringUtils.equals(key, BaseGBDataEnum.XUE_WEI.getValue())) {
			return getGBXwCache(key);
		} else {
			return getGBZzmmCache(key);
		}
	}
	
	/**
	 * 得到高级查询缓存html
	 * @param key
	 * @return
	 */
//	public static HashMap<String, HashMap<String, String>> getGjcxCache() {
//		Element element = CacheManagerLoader.getInstance().getElement(BaseGBDataEnum.GJCX_HTML.getValue());
//		Object objectValue = element.getObjectValue();
//		
//		HashMap<String, HashMap<String, String>> gjcxMap = null;
//		
//		if (null != objectValue) {
//			gjcxMap = (HashMap) objectValue;
//		}else{
//			gjcxMap = new HashMap<String, HashMap<String,String>>();
//		}
//		return gjcxMap;
//	}

}
