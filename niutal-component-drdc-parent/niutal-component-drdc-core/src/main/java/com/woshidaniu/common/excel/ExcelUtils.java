package com.woshidaniu.common.excel;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.woshidaniu.common.excel.impl.ExcelImpl;
import com.woshidaniu.common.excel.impl.ExcelModel;
import com.woshidaniu.common.excel.impl.IExcel;
import com.woshidaniu.common.excel.template.ATemplateBuilder;

/**
 * Excel操作工具
 * 
 * @author Jiangdong.Yi
 * 
 */
public class ExcelUtils {

	/**
	 * 创建简单版的Excel文件  
	 * @param dataList 数据列表
	 * @param filePath 文件路径包括文件的实体名称
	 * @return 返回Excel文件
	 * @throws Exception
	 */
	public static File createExcel(List<String[]> dataList, String filePath) throws Exception {
		if (dataList == null || filePath == null || "".equals(filePath)) {
			return null;
		}
		
		IExcel excel = new ExcelImpl();
		File file = null;
		file = excel.getExcel(dataList, filePath);
		return file;
	}
	
	/**
	 * 创建简单版的Excel文件  
	 * @param dataList 数据列表
	 * @param filePath 文件路径包括文件的实体名称
	 * @param templateBuilder 导入模板
	 * @return 返回Excel文件
	 * @throws Exception
	 */
	public static File createExcel(List<String[]> dataList, String filePath,
			ATemplateBuilder templateBuilder) throws Exception {
		if (dataList == null || filePath == null || "".equals(filePath)) {
			return null;
		}
		//设置模板
		ExcelModel excelModel =new ExcelModel();
		excelModel.setTemplateBuilder(templateBuilder);
		
		// 加入个性化格式生成Excel
		IExcel excel = new ExcelImpl(excelModel);
		File file = null;
		file = excel.getExcel(dataList, filePath);
		return file;
	}

	/**
	 * 根据Excel获取Excle文件数据
	 * 
	 * @param file Excel文件
	 * @return
	 */
	public static List<String[]> getDataList(File file) throws Exception {
		if (file == null) {
			return null;
		}
		IExcel excel = new ExcelImpl();
		List<String[]> list = null;
		list = excel.getDataList(file);
		return list;
	}
	
	public static List<String[]> getDataList(InputStream is) throws Exception {
		if (is == null) {
			return null;
		}
		IExcel excel = new ExcelImpl();
		List<String[]> list = null;
		list = excel.getDataList(is);
		return list;
	}
}
