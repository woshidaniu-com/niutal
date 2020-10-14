package com.woshidaniu.common.excel.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.woshidaniu.common.excel.template.ATemplateBuilder;

/**
 * 定义Excel操作具体实现类
 * 
 * @author Jiangdong.Yi
 * 
 */
public class ExcelImpl implements IExcel {
	// 声明ExcelModel
	private ExcelModel excelModel = null;

	public ExcelImpl() {
		// 初始化默认格式
		excelModel = new ExcelModel();
	}

	public ExcelImpl(ExcelModel excelModel) {
		if(excelModel != null){
			// 初始化格式
			this.excelModel = excelModel;
		}else{
			// 初始化默认格式
			excelModel = new ExcelModel();
		}
	}
	
	/**
	 * 根据ExcelFile 获取 数据列表(增加流支持)
	 */
	public List<String[]> getDataList(InputStream is) throws Exception {
		if (is == null) {
			throw new IllegalArgumentException("找不到文件!");
		}
		// 工作表
		Workbook book = null;
		// 工作簿
		Sheet sheet = null;
		// 创建返回数据集
		List<String[]> resultList = new ArrayList<String[]>();
		try {
			// 获取文件工作表
			book = Workbook.getWorkbook(is);
			// 获得第一个工作表对象
			sheet = book.getSheet(0);
			// 得到第一列第一行的单元格
			// 得到列数
			int columnum = sheet.getColumns();
			// 得到行数
			int rownum = sheet.getRows();
			// 单元格
			Cell cell = null;
			// 去空白行标示
			boolean isNullData = true;

			// 创建返回结果
			String[] rows = null;
			// 循环进行读写
			for (int i = 0; i < rownum; i++) {
				isNullData = true;
				rows = new String[columnum];
				for (int j = 0; j < columnum; j++) {
					cell = sheet.getCell(j, i);
					rows[j] = cell.getContents();
					if (rows[j] != null && !"".equals(rows[j].trim())) {
						isNullData = false;
					}
				}
				if (!isNullData) {
					resultList.add(rows);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (book != null) {
				book.close();
			}
		}
		return resultList;
	}

	/**
	 * 根据ExcelFile 获取 数据列表 简单版
	 * 
	 * @param file
	 * @return
	 */
	public List<String[]> getDataList(File file) throws Exception {
		if (file == null) {
			throw new IllegalArgumentException("找不到文件!");
		}
		// 工作表
		Workbook book = null;
		// 工作簿
		Sheet sheet = null;
		// 创建返回数据集
		List<String[]> resultList = new ArrayList<String[]>();
		try {
			// 获取文件工作表
			book = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			sheet = book.getSheet(0);
			// 得到第一列第一行的单元格
			// 得到列数
			int columnum = sheet.getColumns();
			// 得到行数
			int rownum = sheet.getRows();
			// 单元格
			Cell cell = null;
			// 去空白行标示
			boolean isNullData=true;

			// 创建返回结果
			String[] rows = null;
			// 循环进行读写
			for (int i = 0; i < rownum; i++) {
				isNullData=true;
				rows = new String[columnum];
				for (int j = 0; j < columnum; j++) {
					cell = sheet.getCell(j, i);
					rows[j] = cell.getContents();
					if(rows[j] != null && !"".equals(rows[j].trim())){
						isNullData=false;
					}
				}
				if(!isNullData){
					resultList.add(rows);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(book != null){
				book.close();
			}
		}
		return resultList;
	}
	
	/**
	 * 根据ExcelFile 获取 数据列表  
	 * @param file  Excel文件
	 * @param columnum  读取列数
	 * @return
	 * @throws Exception
	 */
	public List<String[]> getDataList(File file, int columnum) throws Exception {
		if (file == null) {
			throw new IllegalArgumentException("找不到文件!");
		}
		if(columnum == 0){
			return null;
		}
		// 工作表
		Workbook book = null;
		// 工作簿
		Sheet sheet = null;
		// 创建返回数据集
		List<String[]> resultList = new ArrayList<String[]>();
		try {
			// 获取文件工作表
			book = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			sheet = book.getSheet(0);
			// 得到第一列第一行的单元格
			// 得到行数
			int rownum = sheet.getRows();
			// 单元格
			Cell cell = null;
			// 去空白行标示
			boolean isNullData=true;

			// 创建返回结果
			String[] rows = null;
			// 循环进行读写
			for (int i = 0; i < rownum; i++) {
				isNullData=true;
				rows = new String[columnum];
				for (int j = 0; j < columnum; j++) {
					cell = sheet.getCell(j, i);
					rows[j] = cell.getContents();
					if(rows[j] != null && !"".equals(rows[j].trim())){
						isNullData=false;
					}
				}
				if(!isNullData){
					resultList.add(rows);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(book != null){
				book.close();
			}
		}
		return null;
	}

	/**
	 * 根据数据列表 获取ExcelFile 简单版
	 * 
	 * @param dataList
	 * @return
	 */
	public File getExcel(List<String[]> dataList,String filePath) throws Exception {
		if (dataList == null || dataList.size() == 0) {
			throw new IllegalArgumentException("数据为空，不能创建Excel!");
		}
		
		//创建Excel文件
		File file = cearetExcelFile(filePath);
		return cearetExcel(dataList,file);
	}
	
	/**
	 * 创建Excel文件
	 * @param dataList
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private File cearetExcel(List<String[]> dataList,File file) throws Exception{
		if (dataList == null || dataList.size() == 0 || file == null) {
			return file;
		}
		// 打开文件
		WritableWorkbook book = null;
		// 生成名为工作表
		WritableSheet sheet = null;
		// 前冲表格内容单元格内容
		Label label = null;
		// 获取工作表名称
		String sheetName = excelModel.getSheetName();
		try {
			book = Workbook.createWorkbook(file);
			sheet = book.createSheet(sheetName, 0);

			String[] rows = null;
			for (int i = 0; i < dataList.size(); i++) {
				rows = dataList.get(i);
				if (rows != null) {
					for (int j = 0; j < rows.length; j++) {
						label = new Label(j, i, rows[j]);
						// 将定义好的单元格添加到工作表中
						sheet.addCell(label);

					}
				}
			}
			//设置模板
			if(excelModel.getTemplateBuilder() != null){
				ATemplateBuilder templateBuilder=excelModel.getTemplateBuilder();
				//设置工作表
				templateBuilder.setSheet(sheet);
				excelModel.getTemplateBuilder().getExcelTemplate();
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			// 写入数据并关闭文件
			if (book != null) {
				book.write();
				book.close();
			}
		}
		return file;
	}
	
	/**
	 * 创建Excel文件
	 * @param path
	 * @return
	 */
	private File cearetExcelFile(String filePath){
		//导出文件存放 的临时目录
		File tempFile = new File(filePath);
		
		if(! tempFile.exists()) {
			makeDir(tempFile.getParentFile());
		}
		return tempFile;
	}
	
	/**
	 * 创建文件目录
	 * @param dir
	 */
	private static void makeDir(File file) {
		if(! file.getParentFile().exists()) {
			makeDir(file.getParentFile());
		}
		file.mkdir();
	}

}
