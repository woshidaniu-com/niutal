package com.woshidaniu.export.service.utils;

import java.util.Comparator;

import com.woshidaniu.dao.entities.ExportConfigModel;

/**
 * 导出字段排序
 * @author Penghui.Qu
 *
 */
public class ExportComparator implements Comparator<ExportConfigModel>{

	/**
	 * 按显示顺序排序
	 */
	public int compare(ExportConfigModel oldModel, ExportConfigModel newModel) {
		try{
			return Integer.valueOf(oldModel.getXssx()) > Integer.valueOf(newModel.getXssx()) ? 1 : -1;
		}catch (Exception e) {
			return 0;
		}
	}
	
}