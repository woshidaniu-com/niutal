package com.woshidaniu.common.excel.impl;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 定义Excel操作接口
 * @author Jiangdong.Yi
 * 依赖jxl
 */
public interface IExcel {
	
	/**
	 * 根据ExcelFile 获取 数据列表  简单版
	 * @param file
	 * @return
	 */
	public List<String[]> getDataList(File file) throws Exception;
	
	public List<String[]> getDataList(InputStream is) throws Exception;
	
	/**
	 * 根据ExcelFile 获取 数据列表  
	 * @param file  Excel文件
	 * @param columnum  读取列数
	 * @return
	 * @throws Exception
	 */
	public List<String[]> getDataList(File file,int columnum) throws Exception;
	
	/**
	 * 根据数据列表 获取ExcelFile 简单版
	 * @param dataList
	 * @return
	 */
	public File getExcel(List<String[]> dataList,String filePath) throws Exception;
}
