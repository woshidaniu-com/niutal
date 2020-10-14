package com.woshidaniu.search.data.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.search.dao.entities.InitialLabelCount;
import com.woshidaniu.search.dao.entities.LinkageQueryModel;
import com.woshidaniu.search.dao.entities.SearchConfigModel;
import com.woshidaniu.search.dao.entities.SourceDataItem;
import com.woshidaniu.search.service.SearchConfigCache;
import com.woshidaniu.util.base.StringUtil;
/**
 * 数据通过具体方法查询
 * 
 * @author xiaokang
 *
 */
public class ClassMethodQuerier extends AbstractValueSourceQuerier {

	static final Logger logger = LoggerFactory.getLogger(ClassMethodQuerier.class);
	
	/*
	 * 配置缓存服务
	 */
	private  SearchConfigCache configCache = (SearchConfigCache) 
											ServiceFactory.getService("searchConfigCache");	
	
	@Override
	public List<SourceDataItem> queryDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SourceDataItem> queryInitData() {
		if(StringUtils.isBlank(getSource())){
			return null;
		}
		//字段配置
		SearchConfigModel config = getConfig();
		//最大查询数
		Integer maxRows = NumberUtils.createInteger(config.getMaxRows());
		//数据对象
		List<SourceDataItem> queryInitDataByConfig = new ArrayList<SourceDataItem>();
		
		String methodInfo = getSource().replace("method_", "");
		String[] infoArray = methodInfo.split("#");

		if (infoArray.length < 2) {
			throw new RuntimeException("高级查询初始化数据:非法参数");
		}
		//实例id 必填 需要在spring中配置
		String beanId = infoArray[0];
		//方法名 必填
		String methodName = infoArray[1];
		//参数 可选
		String param = infoArray.length == 3 ? infoArray[2] : null;
		try {
			// 获取对象实例
			Object o = ServiceFactory.getService(beanId);
			Class<?> t = o.getClass();

			List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

			logger.debug("高级查询数据初始化,调用目标方法:{},{},{}", new String[]{beanId , methodName , param});
			
			if (StringUtil.isNull(param)) {
				list = (List<HashMap<String, String>>) t.getMethod(methodName).invoke(o);
			} else {
				list = (List<HashMap<String, String>>) t.getMethod(methodName, new Class[] { String.class }).invoke(o,param);
			}
			//做一次排序
			if(list!=null){
				Collections.sort(list, new Comparator<HashMap<String, String>>() {
					@Override
					public int compare(HashMap<String, String> o1,
							HashMap<String, String> o2) {
						String mc1 = StringUtils.isBlank(o1.get("mc")) ? o1.get("MC") : o1.get("mc") ;
						String mc2 = StringUtils.isBlank(o2.get("mc")) ? o2.get("MC") : o2.get("mc");
						return mc1.compareToIgnoreCase(mc2);
					}
				});
			}
			//封装数据
			if(list != null || list.size() > 0){
				int count = 0;
				for (HashMap<String, String> map : list) {
					if(++count > maxRows){
						break;
					}
					String dm = map.get("dm");
					String mc = map.get("mc");
					SourceDataItem item = new SourceDataItem();
					if (StringUtil.isEmpty(dm)) {
						item.setColumnKeyValue(map.get("DM"));
					} else {
						item.setColumnKeyValue(dm);
					}
					if (StringUtil.isEmpty(mc)) {
						item.setColumnLabelValue(map.get("MC"));
						item.setColumnInitialValue(StringUtil.getFirstPinYin(map.get("MC")));
					} else {
						item.setColumnLabelValue(mc);
						item.setColumnInitialValue(StringUtil.getFirstPinYin(mc));
					}
					queryInitDataByConfig.add(item);
				}
			}
			//return convertMap(list);
		} catch (Exception e) {
			//queryInitDataByConfig = null;
			e.printStackTrace();
			//throw new RuntimeException("高级查询初始化数据:调用目标实例方法失败!" , e);
		}
		return queryInitDataByConfig;
	}

	@Override
	public Integer getDataTotalCount() {
		Integer dataTotalCount = 0;
		String methodInfo = getSource().replace("method_", "");
		String[] infoArray = methodInfo.split("#");
		if (infoArray.length < 2) {
			throw new RuntimeException("高级查询初始化数据:非法参数");
		}
		//实例id 必填 需要在spring中配置
		String beanId = infoArray[0];
		//方法名 必填
		String methodName = infoArray[1];
		//参数 可选
		String param = infoArray.length == 3 ? infoArray[2] : null;
		try {
			// 获取对象实例
			Object o = ServiceFactory.getService(beanId);
			Class<?> t = o.getClass();
			List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
			
			logger.debug("高级查询获取数据总量,调用目标方法:{},{},{}", new String[]{beanId , methodName , param});
			
			if (StringUtil.isNull(param)) {
				list = (List<HashMap<String, String>>) t.getMethod(methodName).invoke(o);
			} else {
				list = (List<HashMap<String, String>>) t.getMethod(methodName, new Class[] { String.class }).invoke(o,param);
			}
			if(list != null){
				dataTotalCount = list.size();
			}
		} catch (Exception e) {
			//dataTotalCount = 0;
			e.printStackTrace();
			//throw new RuntimeException("高级查询初始化数据:调用目标实例方法失败!" , e);
		}
		return dataTotalCount;
	}

	@Override
	public List<SourceDataItem> queryMoreData(List<LinkageQueryModel> linkageQueryData) {
		if(StringUtils.isBlank(getSource())){
			return null;
		}
		//字段配置
		SearchConfigModel config = getConfig();
		//最大查询数
		Integer maxRows = NumberUtils.createInteger(config.getMaxRows());
		//数据对象
		List<SourceDataItem> queryInitDataByConfig = new ArrayList<SourceDataItem>();
		
		String methodInfo = getSource().replace("method_", "");
		String[] infoArray = methodInfo.split("#");

		if (infoArray.length < 2) {
			throw new RuntimeException("高级查询初始化数据:非法参数");
		}
		//实例id 必填 需要在spring中配置
		String beanId = infoArray[0];
		//方法名 必填
		String methodName = infoArray[1];
		//参数 可选
		String param = infoArray.length == 3 ? infoArray[2] : null;
		try {
			// 获取对象实例
			Object o = ServiceFactory.getService(beanId);
			Class<?> t = o.getClass();

			List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

			logger.debug("高级查询获取更多数据,调用目标方法:{},{},{}", new String[]{beanId , methodName , param});
			
			if (StringUtil.isNull(param)) {
				list = (List<HashMap<String, String>>) t.getMethod(methodName).invoke(o);
			} else {
				list = (List<HashMap<String, String>>) t.getMethod(methodName, new Class[] { String.class }).invoke(o,param);
			}
			//做一次排序
			if(list!=null){
				Collections.sort(list, new Comparator<HashMap<String, String>>() {
					@Override
					public int compare(HashMap<String, String> o1,
							HashMap<String, String> o2) {
						String mc1 = StringUtils.isBlank(o1.get("mc")) ? o1.get("MC") : o1.get("mc") ;
						String mc2 = StringUtils.isBlank(o2.get("mc")) ? o2.get("MC") : o2.get("mc");
						return mc1.compareToIgnoreCase(mc2);
					}
				});
			}
			//封装数据
			if(list != null || list.size() > 0){
				int count = 0;
				for (HashMap<String, String> map : list) {
					if(++count <= maxRows){
						continue;
					}
					String dm = map.get("dm");
					String mc = map.get("mc");
					SourceDataItem item = new SourceDataItem();
					if (StringUtil.isEmpty(dm)) {
						item.setColumnKeyValue(map.get("DM"));
					} else {
						item.setColumnKeyValue(dm);
					}
					if (StringUtil.isEmpty(mc)) {
						item.setColumnLabelValue(map.get("MC"));
						item.setColumnInitialValue(StringUtil.getFirstPinYin(map.get("MC")));
					} else {
						item.setColumnLabelValue(mc);
						item.setColumnInitialValue(StringUtil.getFirstPinYin(mc));
					}
					queryInitDataByConfig.add(item);
				}
			}
			//return convertMap(list);
		} catch (Exception e) {
			//queryInitDataByConfig = null;
			e.printStackTrace();
			//throw new RuntimeException("高级查询初始化数据:调用目标实例方法失败!" , e);
		}
		return queryInitDataByConfig;
	}

	@Override
	public List<InitialLabelCount> getInitalLabelCount(List<LinkageQueryModel> queryParam ) {
		List<InitialLabelCount> ret = new ArrayList<InitialLabelCount>();
		Map<String , Integer> countMap = new TreeMap<String, Integer>();
		if(StringUtils.isBlank(getSource())){
			return null;
		}
		//字段配置
		SearchConfigModel config = getConfig();
		//最大查询数
		Integer maxRows = NumberUtils.createInteger(config.getMaxRows());
		//数据对象
		List<SourceDataItem> queryInitDataByConfig = new ArrayList<SourceDataItem>();
		
		String methodInfo = getSource().replace("method_", "");
		String[] infoArray = methodInfo.split("#");

		if (infoArray.length < 2) {
			throw new RuntimeException("高级查询初始化数据:非法参数");
		}
		//实例id 必填 需要在spring中配置
		String beanId = infoArray[0];
		//方法名 必填
		String methodName = infoArray[1];
		//参数 可选
		String param = infoArray.length == 3 ? infoArray[2] : null;
		try {
			// 获取对象实例
			Object o = ServiceFactory.getService(beanId);
			Class<?> t = o.getClass();
			List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
			logger.debug("高级查询获取更多数据,调用目标方法:{},{},{}", new String[]{beanId , methodName , param});
			if (StringUtil.isNull(param)) {
				list = (List<HashMap<String, String>>) t.getMethod(methodName).invoke(o);
			} else {
				list = (List<HashMap<String, String>>) t.getMethod(methodName, new Class[] { String.class }).invoke(o,param);
			}
			//做一次排序
			if(list!=null){
				Collections.sort(list, new Comparator<HashMap<String, String>>() {
					@Override
					public int compare(HashMap<String, String> o1,
							HashMap<String, String> o2) {
						String mc1 = StringUtils.isBlank(o1.get("mc")) ? o1.get("MC") : o1.get("mc") ;
						String mc2 = StringUtils.isBlank(o2.get("mc")) ? o2.get("MC") : o2.get("mc");
						return mc1.compareToIgnoreCase(mc2);
					}
				});
			}
			//封装数据
			if(list != null || list.size() > 0){
				int count = 0;
				for (HashMap<String, String> map : list) {
					if(++count <= maxRows){
						continue;
					}
					String mc = map.get("mc");
					String firstPinYin = StringUtil.getFirstPinYin(StringUtil.isEmpty(mc) ? map.get("MC") : mc);
					if(countMap.containsKey(firstPinYin)){
						countMap.put(firstPinYin, (countMap.get(firstPinYin) + 1));
					}else{
						countMap.put(firstPinYin, 1);
					}
				}
			}
			
			Set<String> keySet = countMap.keySet();
			
			for (String string : keySet) {
				Integer integer = countMap.get(string);
				InitialLabelCount count = new InitialLabelCount();
				count.setColumnInitialLabelCount(integer);
				count.setColumnInitialValue(string);
				ret.add(count);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public List<SourceDataItem> queryMoreDataWithInitalLabel(List<LinkageQueryModel> linkageQueryData) {
		if(StringUtils.isBlank(getSource())){
			return null;
		}
		//字段配置
		SearchConfigModel config = getConfig();
		//最大查询数
		Integer maxRows = NumberUtils.createInteger(config.getMaxRows());
		//数据对象
		List<SourceDataItem> queryInitDataByConfig = new ArrayList<SourceDataItem>();
		
		String methodInfo = getSource().replace("method_", "");
		String[] infoArray = methodInfo.split("#");

		if (infoArray.length < 2) {
			throw new RuntimeException("高级查询初始化数据:非法参数");
		}
		//实例id 必填 需要在spring中配置
		String beanId = infoArray[0];
		//方法名 必填
		String methodName = infoArray[1];
		//参数 可选
		String param = infoArray.length == 3 ? infoArray[2] : null;
		try {
			// 获取对象实例
			Object o = ServiceFactory.getService(beanId);
			Class<?> t = o.getClass();

			List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

			logger.debug("高级查询获取更多数据,调用目标方法:{},{},{}", new String[]{beanId , methodName , param});
			
			if (StringUtil.isNull(param)) {
				list = (List<HashMap<String, String>>) t.getMethod(methodName).invoke(o);
			} else {
				list = (List<HashMap<String, String>>) t.getMethod(methodName, new Class[] { String.class }).invoke(o,param);
			}
			//做一次排序
			if(list!=null){
				Collections.sort(list, new Comparator<HashMap<String, String>>() {
					@Override
					public int compare(HashMap<String, String> o1,
							HashMap<String, String> o2) {
						String mc1 = StringUtils.isBlank(o1.get("mc")) ? o1.get("MC") : o1.get("mc") ;
						String mc2 = StringUtils.isBlank(o2.get("mc")) ? o2.get("MC") : o2.get("mc");
						return mc1.compareToIgnoreCase(mc2);
					}
				});
			}
			//封装数据
			if(list != null || list.size() > 0){
				int count = 0;
				for (HashMap<String, String> map : list) {
					if(++count <= maxRows){
						continue;
					}
					String dm = StringUtil.isEmpty(map.get("dm")) ? map.get("DM"): map.get("dm");
					String mc =StringUtil.isEmpty( map.get("mc")) ? map.get("MC"): map.get("mc");
					String firstPinYin = StringUtil.getFirstPinYin(mc);
					if(StringUtil.equalsIgnoreCase(firstPinYin, getInitalLabelValue())){
						SourceDataItem item = new SourceDataItem();
						item.setColumnKeyValue(dm);
						item.setColumnLabelValue(mc);
						item.setColumnInitialValue(firstPinYin);
						queryInitDataByConfig.add(item);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryInitDataByConfig;
	}

	@Override
	public List<SourceDataItem> queryInitData(
			List<LinkageQueryModel> linkageQueryData) {
		return this.queryInitData();
	}

	@Override
	public Integer getDataTotalCount(List<LinkageQueryModel> linkageQueryData) {
		return this.getDataTotalCount();
	}
	
	//===============================================================//
	
	
	public SearchConfigCache getConfigCache() {
		return configCache;
	}

	public void setConfigCache(SearchConfigCache configCache) {
		this.configCache = configCache;
	}

	public ClassMethodQuerier() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassMethodQuerier(String method, String source) {
		super(method, source);
		// TODO Auto-generated constructor stub
	}

	public ClassMethodQuerier(SearchConfigModel config, String method, String source) {
		super(config, method, source);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<SourceDataItem> getDefaultConditionData(
			List<String> defaultConditionData) {
		if(StringUtils.isBlank(getSource())){
			return null;
		}
		//字段配置
		SearchConfigModel config = getConfig();
		//最大查询数
		Integer maxRows = NumberUtils.createInteger(config.getMaxRows());
		//数据对象
		List<SourceDataItem> queryDefaultData = new ArrayList<SourceDataItem>();
		
		String methodInfo = getSource().replace("method_", "");
		String[] infoArray = methodInfo.split("#");

		if (infoArray.length < 2) {
			throw new RuntimeException("高级查询初始化数据:非法参数");
		}
		//实例id 必填 需要在spring中配置
		String beanId = infoArray[0];
		//方法名 必填
		String methodName = infoArray[1];
		//参数 可选
		String param = infoArray.length == 3 ? infoArray[2] : null;
		try {
			// 获取对象实例
			Object o = ServiceFactory.getService(beanId);
			Class<?> t = o.getClass();

			List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

			logger.debug("高级查询获取更多数据,调用目标方法:{},{},{}", new String[]{beanId , methodName , param});
			
			if (StringUtil.isNull(param)) {
				list = (List<HashMap<String, String>>) t.getMethod(methodName).invoke(o);
			} else {
				list = (List<HashMap<String, String>>) t.getMethod(methodName, new Class[] { String.class }).invoke(o,param);
			}
			//做一次排序
			if(list!=null){
				Collections.sort(list, new Comparator<HashMap<String, String>>() {
					@Override
					public int compare(HashMap<String, String> o1,
							HashMap<String, String> o2) {
						String mc1 = StringUtils.isBlank(o1.get("mc")) ? o1.get("MC") : o1.get("mc") ;
						String mc2 = StringUtils.isBlank(o2.get("mc")) ? o2.get("MC") : o2.get("mc");
						return mc1.compareToIgnoreCase(mc2);
					}
				});
			}
			//封装数据
			if(list != null || list.size() > 0){
				for (String key : defaultConditionData) {
					for (HashMap<String, String> map : list) {
						String dm = StringUtil.isEmpty(map.get("dm")) ? map.get("DM"): map.get("dm");
						String mc =StringUtil.isEmpty( map.get("mc")) ? map.get("MC"): map.get("mc");
						String firstPinYin = StringUtil.getFirstPinYin(mc);
						
						if(StringUtils.equals(key, dm)){
							SourceDataItem item = new SourceDataItem();
							item.setColumnKeyValue(dm);
							item.setColumnLabelValue(mc);
							item.setColumnInitialValue(firstPinYin);
							queryDefaultData.add(item);
							continue;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryDefaultData;
	}
	
	
}
