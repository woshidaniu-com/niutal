package com.woshidaniu.util.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.util.local.LocaleUtils;
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
public class BaseDataReader {

	private static SAXReader saxReader = new SAXReader();   
	private static final String baseDataKey = "niutal.basicdata.file";
	private static final String bigcomboKey = "bigcombo.setupfile";
	
	protected static Map<String,Map<String,List<BaseData>>> baseDataOptionList = new HashMap<String,Map<String,List<BaseData>>>();
	protected static Map<String,Map<String,List<HashMap<String,String>>>> mapOptionList = new HashMap<String,Map<String,List<HashMap<String,String>>>>();
	protected static Map<String,Document> documentMap = new HashMap<String,Document>();
	protected static Logger LOG = LoggerFactory.getLogger(BaseDataReader.class);
	
	private BaseDataReader(){
		
	}
	
 
	public static Document getDoc(String locale){
		try {
			if(documentMap.get(locale) == null){
				////File classPath = ResourceUtils.getFile("classpath:\\");
				documentMap.put(locale, Dom4jUtils.read(locale,  MessageUtil.getText(baseDataKey)));
			}
			return documentMap.get(locale);
		}catch(Exception e){
			LOG.error("Load Basicdata Error",e);
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
	public static List<HashMap<String,String>> getCachedOptionList(String id){
		String locale = LocaleUtils.getLocaleKey();
		Map<String, List<HashMap<String, String>>>  localeOptionMap = mapOptionList.get(locale);
		if(localeOptionMap == null){
			localeOptionMap =  new HashMap<String,List<HashMap<String,String>>>(); 
		}
		List<HashMap<String,String>> list = localeOptionMap.get(id);
		if(list == null){
			list = getOptionMapList(id);
		}
		localeOptionMap.put(id, list);
		mapOptionList.put(locale, localeOptionMap);
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
	@SuppressWarnings({ "unchecked" })
	public static List<HashMap<String,String>> getOptionMapList(String id){
		Element root = BaseDataReader.getDoc(LocaleUtils.getLocaleKey()).getRootElement();
		List<Element> dataList = root.elements("data");
		List<HashMap<String,String>> optionList = new ArrayList<HashMap<String,String>>();
		for (int i = 0 ; i < dataList.size() ; i++) {
			Element element = dataList.get(i);
			if (id.equalsIgnoreCase(element.attributeValue("id"))) {
				List<Element> options = element.elements("option");
				HashMap<String,String> option = null;
				for (int j = 0 ; j < options.size() ; j++) {
					option = new HashMap<String,String>();
					option.put("key", options.get(j).attributeValue("key"));
					option.put("value", options.get(j).getText());
					optionList.add(option);
				}
				break;
			}
		}
		return optionList;
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
		if(list == null || list.size()==0){
			list = getBaseDataList(id);
			if(list != null && list.size()>0){
				localeOptionMap.put(id, list);
				baseDataOptionList.put(locale, localeOptionMap);
			}
		}
		/*System.out.println("getCachedBaseDataList:id"+id);
		if(list.size()>0){
		  System.out.println("getCachedBaseDataList:list.size()"+list.size());
			for(BaseData baseData:list){
				System.out.println(baseData.getKey());
				System.out.println(baseData.getValue());
			}
		}*/
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
		Element root = BaseDataReader.getDoc(LocaleUtils.getLocaleKey()).getRootElement();
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
		/*System.out.println("ggetBaseDataList:list"+id);
		if(list.size()>0){
		System.out.println("ggetBaseDataList:list.size()"+list.size());
			for(BaseData baseData:list){
				System.out.println(baseData.getKey());
				System.out.println(baseData.getValue());
			}
		}*/
		return list;
	}
	
	private static Document getBigComboDocument(){
		String file = BaseDataReader.class.getResource(MessageUtil.getText(bigcomboKey)).getFile();
		try {
			Document document = saxReader.read(file);
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String,String> getBigComboSelect(final String id){
		
		Element root = getBigComboDocument().getRootElement();
		List<Element> dataList = root.elements("data");
		HashMap<String,String> optionMap = new HashMap<String, String>();
		for (int i = 0 ; i < dataList.size() ; i++) {
			if (id.equalsIgnoreCase(dataList.get(i).attributeValue("id"))) {
				List<Element> options = dataList.get(i).elements("option");
				
				for (int j = 0 ; j < options.size() ; j++) {
					optionMap.put(options.get(j).attributeValue("key"), options.get(j).getText());
				}
				break;
			}
		}
		return optionMap;
	}
	
	public static void main(String[] args) {
		
		getBaseDataList("isNot");
		 
	}
}
