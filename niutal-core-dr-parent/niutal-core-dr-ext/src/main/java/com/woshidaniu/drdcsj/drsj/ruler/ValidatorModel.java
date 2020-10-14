/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.woshidaniu.drdcsj.drsj.comm.ImportErrorMessage;

public class ValidatorModel {
	// 错误信息
	private ImportErrorMessage errorMessage;
	// 当前数据
	private Map<String, List<String>> dataSource;
	// 更新的数据
	private Map<String, List<String>> dataForUpdate;
	// 新增的数据
	private Map<String, List<String>> dataForInsert;
	//数据插入表示，U：更新，I：插入
	private Map<String,String> dataOperationFlags;
	
	public ValidatorModel() {
		super();
	}
	/**
	 * 初始化
	 * @param errorMessage
	 * @param dataSource
	 */
	public ValidatorModel(ImportErrorMessage errorMessage, Map<String, List<String>> dataSource){
		this.dataSource = dataSource;
		this.errorMessage = errorMessage;
		dataForUpdate = new HashMap<String, List<String>>();
		dataForInsert = new HashMap<String, List<String>>();
		dataOperationFlags = new HashMap<String,String>();
		Set<String> keySet = dataSource.keySet();
		for (String key : keySet) {
			List<String> list = dataSource.get(key).subList(0, 1);
			List<String> copy1 = new ArrayList<String>(list);
			List<String> copy2 = new ArrayList<String>(list);
			dataForUpdate.put(key, copy1);
			dataForInsert.put(key, copy2);
		}
	}
	/**
	 * 转移行数据
	 * @param index
	 * @param target
	 */
	public void transferData(int index, String target){
		Set<String> keySet = dataSource.keySet();
		for (String key : keySet) {
			List<String> list = dataSource.get(key);
			if(StringUtils.equals("U", target)){
				this.dataForUpdate.get(key).add(list.get(index));
			}else if(StringUtils.equals("I", target)){
				this.dataForInsert.get(key).add(list.get(index));
			}
		}
	}
	public ImportErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ImportErrorMessage errorMessage) {
		if (null == this.errorMessage) {
			this.errorMessage = errorMessage;
		} else {
			this.errorMessage.putAll(errorMessage);
		}
	}

	public Map<String, List<String>> getDataSource() {
		return dataSource;
	}

	public void setDataSource(Map<String, List<String>> dataSource) {
		this.dataSource = dataSource;
	}

	public Map<String, List<String>> getDataForUpdate() {
		return dataForUpdate;
	}

	public void setDataForUpdate(Map<String, List<String>> dataForUpdate) {
		this.dataForUpdate = dataForUpdate;
	}

	public Map<String, List<String>> getDataForInsert() {
		return dataForInsert;
	}

	public void setDataForInsert(Map<String, List<String>> dataForInsert) {
		this.dataForInsert = dataForInsert;
	}

	public Map<String, String> getDataOperationFlags() {
		return dataOperationFlags;
	}

	public void setDataOperationFlags(Map<String, String> dataOperationFlags) {
		this.dataOperationFlags = dataOperationFlags;
	}
}
