/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.drdcsj.drsj.dao.daointerface.IImportDao;

public class DrfzQuery{
	
	private static final Logger log = LoggerFactory.getLogger(DrfzQuery.class);

	static final String TYPE_DB = "table";
	
	static final String TYPE_CLASS = "class";
	
	static final String TYPE_SQL = "sql";
	
	static final String TYPE_TEXT = "text";
	
	static final String TYPE_JSON = "json";
	
	static final String SEPARATE = "|";
	
	static final String SEPARATE_$ = "$";
	
	public static final String DM = "DM";
	
	public static final String MC = "MC";
	//方式
	private String type;
	//源
	private String source;

	public DrfzQuery(String type, String source) {
		this.type = type;
		this.source = source;
	}

	/**
	 * 查询配置类辅助信息
	 * @return
	 */
	public List<String> queryFzDrlData(){
		List<Map<String, String>> queryFzData = queryFzData();
		List<String> vals = new ArrayList<String>();
		
		if(queryFzData != null && queryFzData.size() > 0){
			for (Map<String, String> map : queryFzData) {
				vals.add(map.get(DM));
			}
		}
		return vals;
	}
	
	/**
	 * 查询辅助数据
	 * @return
	 */
	public List<Map<String , String>> queryFzData(){
		List<Map<String, String>> mcDmData = null;
		/*数据库查询方式*/
		if(StringUtils.equalsIgnoreCase(TYPE_DB, type)){
			StringBuffer sql = new StringBuffer("");
			String[] split = StringUtils.split(source, SEPARATE);
			String tableName = split[0];
			String dm = split[1];
			String mc = split[2];
			sql.append("SELECT ").append(mc).append(" " + MC + " , ")
			.append(dm).append(" " + DM + " FROM ").append(tableName)
			.append(" ORDER BY " + MC + " ");
			IImportDao dao = ServiceFactory.getService(IImportDao.class);
			mcDmData = dao.getFomartData(sql.toString());
		/*接口查询方式*/
		}else if(StringUtils.equalsIgnoreCase(TYPE_CLASS, type)){
			try {
				Object invokeConstructor = ConstructorUtils.invokeConstructor(ClassUtils.getClass(source), new Object[]{});
				if(invokeConstructor instanceof IDrFzData){
					IDrFzData sourceData = (IDrFzData) invokeConstructor;
					mcDmData = sourceData.data();
				}
			} catch (Exception e) {
				log.error("调用构造函数异常",e);
			} 
		}else if(StringUtils.equalsIgnoreCase(TYPE_SQL, type)){
			IImportDao dao = ServiceFactory.getService(IImportDao.class);
			mcDmData = dao.getFomartData(source);
		/*text:{dm:0,mc:女}|{dm:1,mc:男}*/
		}else if(StringUtils.equalsIgnoreCase(TYPE_JSON, type)){
			mcDmData = new ArrayList<Map<String,String>>();
			String[] spliteJson = StringUtils.split(source, SEPARATE);
			for (int i = 0; i < spliteJson.length; i++) {
				JSONObject jo = JSONObject.fromObject(spliteJson[i]);
				Object dm = jo.get("dm");
				Object mc = jo.get("mc");
				HashMap<String, String> mp = new HashMap<String, String>();
				mp.put(DM, dm.toString());
				mp.put(MC, mc.toString());
				mcDmData.add(mp);
			}
		}else if(StringUtils.equalsIgnoreCase(TYPE_TEXT, type)){
			mcDmData = new ArrayList<Map<String,String>>();
			String[] spliteJson = StringUtils.split(source, SEPARATE);
			for (int i = 0; i < spliteJson.length; i++) {
				String pair = StringUtils.trimToNull(spliteJson[i]);
				if(pair == null){
					continue;
				}
				String[] split = StringUtils.split(pair, SEPARATE_$, 2);
				String dm = split[0];
				String mc = split[1];
				HashMap<String, String> mp = new HashMap<String, String>();
				mp.put(DM, dm.toString());
				mp.put(MC, mc.toString());
				mcDmData.add(mp);
			}
		}else {
			log.warn("发现未定义的类型:{}",type);
		}
		return mcDmData;
	}
}
