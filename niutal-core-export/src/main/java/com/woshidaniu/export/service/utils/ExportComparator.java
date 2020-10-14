/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.service.utils;

import com.woshidaniu.dao.entities.ExportConfigModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * @author Penghui.Qu
 * 导出字段排序
 */
public class ExportComparator implements Comparator<ExportConfigModel>{
	
	private static final Logger log = LoggerFactory.getLogger(ExportComparator.class);

	/**
	 * @description	： 按显示顺序排序
	 * @param oldModel
	 * @param newModel
	 * @return
	 */
	public int compare(ExportConfigModel oldModel, ExportConfigModel newModel) {
		try{
			return Integer.valueOf(oldModel.getXssx()) > Integer.valueOf(newModel.getXssx()) ? 1 : -1;
		}catch (Exception e) {
			log.error("",e);
			return 0;
		}
	}
}