package com.woshidaniu.common.excel.impl;

import com.woshidaniu.common.excel.template.ATemplateBuilder;

/**
 * 操作页面的基本属性
 * @author Administrator
 *
 */
public class ExcelModel {
	//临时文件路径
	public String tempFilePath="";
	//临时文件名称
	public String tempFileName="";
	//设置默认数据
	private String sheetName="sheet1";//工作簿名称
	//Excel格式模板
	private ATemplateBuilder templateBuilder=null;
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getTempFilePath() {
		return tempFilePath;
	}
	public void setTempFilePath(String tempFilePath) {
		this.tempFilePath = tempFilePath;
	}
	public String getTempFileName() {
		return tempFileName;
	}
	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
	}
	public ATemplateBuilder getTemplateBuilder() {
		return templateBuilder;
	}
	public void setTemplateBuilder(ATemplateBuilder templateBuilder) {
		this.templateBuilder = templateBuilder;
	}
}
