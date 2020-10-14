package com.woshidaniu.common.imp.validator.rules;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import com.woshidaniu.common.validator.rules.IValidateRule;

/**
 * 导入数据唯一验证规则。
 * <li>导入个性化验证，非通用验证</li>
 * <li>数据不是唯一的返回：false</li>
 * <li>数据是唯一的返回：true</li>
 * @author Jiangdong.Yi
 * 
 */
public class ImportUniqueRule implements IValidateRule {
	private String validateInfo;
	private final String defaultMessage = "数据已存在！";
	private HashMap<String, Object> excelSourceData=null;
	private HashMap<String, Object> oracleSourceData=null;
	
	public ImportUniqueRule(HashMap<String, Object> excelSourceData,
			HashMap<String, Object> oracleSourceData) {
		super();
		this.excelSourceData = excelSourceData;
		this.oracleSourceData = oracleSourceData;
	}
	public ImportUniqueRule() {
		super();
		this.oracleSourceData = new HashMap<String, Object>();
		this.excelSourceData = new HashMap<String, Object>();
	}

	@Override
	public String getValidateInfo() {
		return StringUtils.defaultIfEmpty(this.validateInfo, defaultMessage);
	}

	@Override
	public boolean validate(Object value) {
		if(value == null){
			return false;
		}
		
		//验证数据中是否唯一
		if(oracleSourceData.containsKey(value.toString())){
			this.validateInfo="数据库中存在相同数据！";
			return false;
		}
		//验证导入Excel中是否唯一
		if(excelSourceData.containsKey(value.toString())){
			this.validateInfo="导入数据中存在相同数据！";
			return false;
		}
		
		return true;
		
	}

	public void setValidateInfo(String validateInfo) {
		this.validateInfo = validateInfo;
	}
	
}
