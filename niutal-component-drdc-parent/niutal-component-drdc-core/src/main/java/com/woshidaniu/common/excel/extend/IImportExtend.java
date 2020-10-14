package com.woshidaniu.common.excel.extend;

import java.util.Map;

import com.woshidaniu.dao.entities.ImportModel;

public interface IImportExtend {
	/**
	 * 格式化导入数据信息
	 * @param model 导入model对象
	 * @param vReslut  原导入数据信息
	 * @return
	 */
	public Map<String, Object> fomartForExtend(ImportModel model,
			Map<String, Object> vReslut);
}
