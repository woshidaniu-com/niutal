/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.insertDb.imp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.drdcsj.drsj.comm.ImportUtil;
import com.woshidaniu.drdcsj.drsj.dao.daointerface.IImportDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.CrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.insertDb.IArrayInsert;
import com.woshidaniu.drdcsj.drsj.dao.insertDb.IInsertDb;
import com.woshidaniu.drdcsj.drsj.ruler.ValidatorModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 982 张昌路
 * @author xiaokang
 * 
 * 通过配置执行对应的插入,插入方式支持:
 * 
 * map[配置格式-map:servicename/实际实现类路径]
 * array[配置格式-array:servicename/实际实现类路径]
 * sql[配置格式-sql:{"insert":["insert tablename(id,name) values(#id,#name)"],"update":["update tablename set name=#name where id=#id"]}
 */
public class ConfigInsert implements IInsertDb {
	
	private static final Logger log = LoggerFactory.getLogger(ConfigInsert.class);
	//数组格式
	private final String _INSERT_ARRAY = "array";
	//map格式
	private final String _INSERT_MAP = "map";
	//sql 格式
	private final String _INSERT_SQL = "sql";
	//class格式
	private final String _INSERT_CLASS = "class";
	//具体配置的插入器
	private final Object excutor;
	//导入模块代码
	private String drmkdm;
	
	private IImportDao importDao;
	
	public ConfigInsert(String drmkdm,IImportDao importDao) {
		this.drmkdm = drmkdm;
		this.importDao = importDao;
		this.excutor = this.getConfigExecutor(drmkdm);
	}

	@Override
	public Integer[] batchAddOrUpdate(ValidatorModel vm,DrpzModel drpzModel, List<DrlpzModel> drlpzList) {
		Integer[] num = null;
		if (this.excutor instanceof IInsertDb) {
			num = ((IInsertDb) this.excutor).batchAddOrUpdate(vm, drpzModel, drlpzList);
		} else if (this.excutor instanceof IArrayInsert) {
			String[][] list = ImportUtil.convertMap2Arr(vm.getDataSource(), drlpzList);
			num = ((IArrayInsert) this.excutor).batchAddOrUpdate(list, drpzModel,drlpzList);
		} else {
			throw new RuntimeException("[import-->insert:]"+ this.excutor.getClass().getName()+ " must implements IInsertDb or IArrayInsert");
		}
		return num;
	}

	@Override
	public int batchInsertData(ValidatorModel vm,DrpzModel drpzModel, List<DrlpzModel> drlpzList) {
		int num = -1;
		if (this.excutor instanceof IInsertDb) {
			num = ((IInsertDb) this.excutor).batchInsertData(vm, drpzModel, drlpzList);
		} else if (this.excutor instanceof IArrayInsert) {
			String[][] list = ImportUtil.convertMap2Arr(vm.getDataSource(), drlpzList);
			num = ((IArrayInsert) this.excutor).batchInsertData(list, drpzModel,drlpzList);
		} else {
			throw new RuntimeException("[import-->insert:]"	+ this.excutor.getClass().getName()+ " must implements IInsertDb or IArrayInsert");
		}
		return num;
	}

	@Override
	public int batchUpdateData(ValidatorModel vm, DrpzModel drpzModel,List<DrlpzModel> drlpzList) {
		int num = -1;
		if (this.excutor instanceof IInsertDb) {
			num = ((IInsertDb) this.excutor).batchUpdateData(vm, drpzModel, drlpzList);
		} else if (this.excutor instanceof IArrayInsert) {
			String[][] list = ImportUtil.convertMap2Arr(vm.getDataSource(), drlpzList);
			num = ((IArrayInsert) this.excutor).batchUpdate(list, drpzModel, drlpzList);
		} else {
			throw new RuntimeException("[import-->insert:]"	+ this.excutor.getClass().getName()+ " must implements IInsertDb or IArrayInsert");
		}
		return num;
	}

	private Object getConfigExecutor(String drmkdm) {
		Object obj = null;
		// 获取插入配置
		CrpzModel crpzModel = this.importDao.getCrpzModel(drmkdm);
		if(null == crpzModel){
			return null;
		}
		String crpz = crpzModel.getCrpz();
		if (StringUtils.isEmpty(crpz)) {
			return null;
		}
		String pz[] = StringUtils.split(crpz, ":", 2);
		if (pz.length != 2) {
			return null;
		}
		String type = pz[0];//类型
		String config = pz[1];//额外的配置
		String lowerCaseType = type.toLowerCase();
		
		if (_INSERT_ARRAY.equals(lowerCaseType)) {// array格式
			try {
				obj = ServiceFactory.getService(config);
			} catch (NoSuchBeanDefinitionException e1) {
				log.error("",e1);
			}
			if (obj == null) {
				try {
					//如果带有参数
					if(StringUtils.containsAny(config,'(',')')){
						String args = StringUtils.substringBetween(config, "(", ")");
						String clazz = StringUtils.substringBefore(config, "(");	
						obj = ConstructorUtils.invokeConstructor(Class.forName(clazz), args);
					}else{
						obj = Class.forName(config).newInstance();
					}	
				} catch (Exception e) {
					log.warn("",e);
					return null;
				}
			}
		} else if (_INSERT_MAP.equals(lowerCaseType)) {// map格式
			try {
				obj = ServiceFactory.getService(config);
			} catch (NoSuchBeanDefinitionException e1) {
				log.error("",e1);
			}
			if (obj == null) {
				try {
					obj = Class.forName(config).newInstance();
				} catch (Exception e) {
					log.warn("",e);
					return null;
				}
			}
		} else if (_INSERT_SQL.equals(lowerCaseType)) {//sql模式
			JSONObject sqlConfigJsonObject = JSONObject.fromObject(config);
			JSONArray insertJsonArray = sqlConfigJsonObject.getJSONArray("insert");
			JSONArray updateJsonArray = sqlConfigJsonObject.getJSONArray("update");
			String[] insertArray = new String[insertJsonArray.size()];
			String[] updateArray = new String[updateJsonArray.size()];
			for (int i = 0; i < insertJsonArray.size(); i++) {
				insertArray[i] = insertJsonArray.get(i).toString();
			}
			for (int i = 0; i < updateJsonArray.size(); i++) {
				updateArray[i] = updateJsonArray.get(i).toString();
			}
			obj = new SqlInsert(new SqlOperation(updateArray, insertArray));
		}else {
			log.warn("发现没有预定义的类型:[{}]和配置[{}]",type,config);
		}
		return obj;
	}

	public Object getExecutor() {
		return this.excutor;
	}
}
