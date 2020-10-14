package com.woshidaniu.designer.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.util.local.LocaleUtils;
import com.woshidaniu.util.xml.BaseData;
import com.woshidaniu.xmlhub.utils.Dom4jUtils;

/**
 * 
 *@类名称:BaseDataReader.java
 *@类描述：通用基础数据集配置，增加国际化支持
 *@创建人：kangzhidong
 *@创建时间：Feb 15, 2016 2:50:13 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public class FuncXMLDataUtils {
	

	private static Map<String,Map<String,List<BaseData>>> baseDataOptionList = new HashMap<String,Map<String,List<BaseData>>>();
	private static Map<String,Document> documentMap = new HashMap<String,Document>();
	
	private FuncXMLDataUtils(){
		
	}
	private static final Logger log = LoggerFactory.getLogger(FuncXMLDataUtils.class);
	
 
	public static Document getDoc(String locale){
		try {
			URL  url = 	Thread.currentThread().getContextClassLoader().getResource("");
			if(documentMap.get(locale) == null){
				////File classPath = ResourceUtils.getFile("classpath:\\");
				documentMap.put(locale, Dom4jUtils.read(locale, url.getPath() + "/conf/comm/designData.xml"));
			}
			return documentMap.get(locale);
		}catch(Exception e){
			log.error("Load Basicdata Error",e);
		}  
		return null;
	}
	
	/**
	 * 
	 *@描述：xml中读取下拉列表选项；优先从缓存中获取
	 *@创建人:kangzhidong
	 *@创建时间:Dec 15, 20159:07:53 AM
	 *@param id  baseData.xml中data节点的id
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public static List<BaseData> getCachedBaseDataList(String id){
		String locale = LocaleUtils.getLocaleKey();
		Map<String,List<BaseData>>  localeOptionMap = baseDataOptionList.get(locale);
		if(localeOptionMap == null){
			localeOptionMap =  new HashMap<String,List<BaseData>>(); 
		}
		List<BaseData> list = localeOptionMap.get(id);
		if(list == null){
			list = getBaseDataList(id);
		}
		localeOptionMap.put(id, list);
		baseDataOptionList.put(locale, localeOptionMap);
		return list;
	}
	
	/**
	 * 
	 *@描述：xml中读取下拉列表选项
	 *@创建人:kangzhidong
	 *@创建时间:Dec 15, 20159:07:53 AM
	 *@param id  baseData.xml中data节点的id
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	@SuppressWarnings("unchecked")
	public static List<BaseData> getBaseDataList(String id){
		List<BaseData> list = null;
		Element root = FuncXMLDataUtils.getDoc(LocaleUtils.getLocaleKey()).getRootElement();
		List<Element> dataList = root.elements("data");
		for (int i = 0 ; i < dataList.size() ; i++) {
			Element element = dataList.get(i);
			if (id.equalsIgnoreCase(element.attributeValue("id"))) {
				List<Element> options = element.elements("option");
				BaseData data = null;
				list = new ArrayList<BaseData>();
				for (int j = 0 ; j < options.size() ; j++) {
					data = new BaseData();
					data.setKey(options.get(j).attributeValue("key"));
					data.setValue(options.get(j).getText());
					list.add(data);
				}
				break;
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		
		getBaseDataList("isNot");
		 
	}
}
