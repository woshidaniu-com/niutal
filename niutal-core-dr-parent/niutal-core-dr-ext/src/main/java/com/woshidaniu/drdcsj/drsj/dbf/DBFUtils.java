/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dbf;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class DBFUtils {

	/**
	 * 根据ExcelFile 获取 数据列表 简单版
	 * 
	 * @param file
	 *            excel文件对象
	 * @return
	 */
	public static Map<String, List<String>> readerDBF(File file) {
		if (file == null) {
			throw new IllegalArgumentException("找不到文件!");
		}
		// 创建返回数据集
		Map<String, List<String>> data = new LinkedHashMap<String, List<String>>();
		
		try {
			DBFReader dbfreader = new DBFReader(new FileInputStream(file));
			for (int b = 0; b < dbfreader.getFieldCount(); b++) {
				String fieldName = dbfreader.getField(b).getName();
				List<String> valList = new ArrayList<String>();
				for (int i = 0; dbfreader.hasNextRecord(); i++) {
					String[] aobj = dbfreader.nextRecordString();
					valList.add(aobj[b]);
				}
				data.put(fieldName, valList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
}
