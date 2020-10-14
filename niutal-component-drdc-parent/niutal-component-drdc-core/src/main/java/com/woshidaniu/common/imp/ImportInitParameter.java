package com.woshidaniu.common.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.service.svcinterface.IImportService;

/**
 * 导入功能初始化参数
 * <li>该类只为导入功能提供导入参数初始化服务<li>
 * @author JiangDong.Yi
 *
 */
public class ImportInitParameter {
	//导入数据源
	private Map<String, Object> importDataSource=null;
	//参数分割符
	private final String IMPORT_INFO_SPLIT_SYMBAL="-";
	
	//初始化字符串长度
	private final String RULE_STRINGLENGTHRULE="StringLengthRule";
	//导入数据唯一性验证
	private final String RULE_IMPORTUNIQUERULE="ImportUniqueRule";
	//外键验证
	private final String RULE_IMPORTFOREIGNKEYRULE="ImportForeignKeyRule";
	//
	private final String RULE_IMPORTEXTENDRULE = "ImportExtendRule";
	
	public ImportInitParameter(Map<String, Object> importDataSource){
		this.importDataSource = importDataSource;
	}
	
	/**
	 * 获取初始化参数数组
	 * @param className
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public Object[] getInitParameterArray(String className, String parame) {
		if(StringUtils.isEmpty(className)){
			return null;
		}
		//判断初始化类   待优化
		if(RULE_STRINGLENGTHRULE.equals(className)){
			return getStringLengthRule(parame);
		}else if(RULE_IMPORTUNIQUERULE.equals(className)){
			return getImportUniqueRule(parame);
		}else if(RULE_IMPORTFOREIGNKEYRULE.equals(className)){
			return getImportForeignKeyRule(parame);
		}else if(RULE_IMPORTEXTENDRULE.equals(className)){
			return getImportExtendRule(className);
		}
		return null;
	}
	
	/**
	 * 验证数据长度 初始化参数
	 * @param str
	 * @return
	 */
	private Integer[] getStringLengthRule(String parame){
		if(StringUtils.isEmpty(parame)){
			return null;
		}
		String[] parames=parame.split(IMPORT_INFO_SPLIT_SYMBAL);
		Integer[] ints=new Integer[parames.length];
		for (int i = 0; i < parames.length; i++) {
			ints[i] = Integer.valueOf(parames[i]);
		}
		return ints;
	}
	
	/**
	 * 导入验证数据唯一性初始化参数
	 * <li>仅支持单列数据唯一性验证</li>
	 * @param parame 唯一键约束表名（或视图）_唯一键对应字段名_导入数据的映射字段名称
	 * @return
	 */
	private Object[] getImportUniqueRule(String parame){
		if(StringUtils.isEmpty(parame)){
			return null;
		}
		IImportService importService = (IImportService)ServiceFactory.getService("importService");
		//取出参数
		String[] parames=parame.split(IMPORT_INFO_SPLIT_SYMBAL);
		//此处写死长度是因为唯一性验证只支持  单列唯一性验证，不考虑多列唯一性
		if(parames.length != 2){
			return null;
		}
		Object[] objs=new Object[parames.length];
		
		//设置验证数值，根据表名和列名
		HashMap<String, String> vParame=new HashMap<String, String>();
		vParame.put("tableName", parames[0]);
		vParame.put("columnName", parames[1]);
		
		//获取待验证的数据
		Map<String, Object> ds =  importDataSource;
		HashMap<String, String> dataSource = formatImportUniqueRuleParame((String[])ds.get(parame));
		
		//将验证数值转换成  hashMap格式，便于验证
		HashMap<String, String> vData = importService.getValidateValueMap(vParame);
		//设置参数
		objs[0] = dataSource;
		objs[1] = vData;
		return objs;
	}
	
	/**
	 * 导入验证数据 外键初始化参数
	 * @param parame
	 * @return
	 */
	private Object[] getImportForeignKeyRule(String parame){
		if(StringUtils.isEmpty(parame)){
			return null;
		}
		//此处写死是因为验证导入验证规则只提供一个参数的验证
		Object[] objs=new Object[1];
		IImportService importService = (IImportService)ServiceFactory.getService("importService");
		//取出参数
		String[] parames=parame.split(IMPORT_INFO_SPLIT_SYMBAL);
		if(parames.length != 3){
			return null;
		}
		//设置验证数值，根据表名和列名
		HashMap<String, String> vParame=new HashMap<String, String>();
		String columnName="";
		String tableName="";
		for (int i = 0; i < parames.length; i++) {
			if(i == 0){
				tableName=parames[i];
			}else{
				if(!StringUtils.isEmpty(columnName)){
					columnName = columnName + "," +parames[i];
				}else{
					columnName = parames[i];
				}
			}
			
		}
		vParame.put("tableName", tableName);
		vParame.put("columnName", columnName);
		
		//将验证数值转换成  hashMap格式，便于验证
		List<HashMap<String, String>> vDataList = importService.getValidateValueList(vParame);
		//设置参数
		objs[0] = formatForeignKeyRuleParame(vDataList, columnName.split(","));
		
		return objs;
	}
	
	/**
	 * 导入验证数据唯一性初始化参数
	 * <li>仅支持单列数据唯一性验证</li>
	 * @param parame 唯一键约束表名（或视图）_唯一键对应字段名_导入数据的映射字段名称
	 * @return
	 */
	private Object[] getImportExtendRule(String parame){
		if(StringUtils.isEmpty(parame)){
			return null;
		}
		Object[] objs=new Object[1];
		
		//将验证数值转换成  hashMap格式，便于验证
		HashMap<String, String> vData = new HashMap<String, String>();
		vData.put("data", parame);
		//设置参数
		objs[0] = vData;
		return objs;
	}
	
	
	/***********************************辅助方法**************************************/
	/**
	 * 格式化导入唯一规则参数
	 * @param str
	 * @return
	 */
	private HashMap<String, String> formatImportUniqueRuleParame(String[] str){
		if(str == null){
			return null;
		}
		HashMap<String, String> hashMap =new HashMap<String, String>();
		HashMap<String, String> hashMapTemp =new HashMap<String, String>();
		//验证重复的数据加入hashMap中.
		for (int i = 0; i < str.length; i++) {
			if(hashMapTemp.containsKey(str[i])){
				hashMap.put(str[i], str[i]);
			}
			hashMapTemp.put(str[i], str[i]);
		}
		return hashMap;
	}
	
	/**
	 * 格式化外建规则参数
	 * @param vDataList
	 * @param parame
	 * @return
	 */
	private HashMap<String, String> formatForeignKeyRuleParame(List<HashMap<String, String>> vDataList,
			String[] parame){
		if(vDataList == null || parame == null){
			return null;
		}
		HashMap<String, String> parameData = null;
		//将list，HashMap，转换成  hashmap，hashmap
		parameData = new HashMap<String, String>();
		for (int i = 0; i < vDataList.size(); i++) {
			if(parame.length == 1){
				parameData.put(vDataList.get(i).get(parame[0].toUpperCase()), 
						vDataList.get(i).get(parame[0].toUpperCase()));
			}else if(parame.length == 2){
				parameData.put(vDataList.get(i).get(parame[0].toUpperCase()), 
						vDataList.get(i).get(parame[1].toUpperCase()));
			}
				
		}
		
		return parameData;
	}
	
}
