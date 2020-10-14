/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.excel;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.handler.IdObject;

/**
 * @author 康康（1571）
 * ExcelCell
 */
public class ExcelCell extends IdObject{
	//cell的索引(在原始Excel的列)
	private int index;
	//导入列配置
	private DrlpzModel drlpzModel;
	//excel的表头
	private String excelHeaderName;
	//excel中cell的原始值(用于汇总excel错误xls文件)
	private String excelOriginValue;
	//excel中cell的格式化后的值
	private String excelCellValue;
	//这个cell的错误信息
	private String error;
	//忽略数据库操作
	private boolean ignore = false;

	public DrlpzModel getDrlpzModel() {
		return drlpzModel;
	}
	public void setDrlpzModel(DrlpzModel drlpzModel) {
		this.drlpzModel = drlpzModel;
	}
	public String getExcelHeaderName() {
		return excelHeaderName;
	}
	public void setExcelHeaderName(String excelHeaderName) {
		this.excelHeaderName = excelHeaderName;
	}
	public String getExcelCellValue() {
		return excelCellValue;
	}
	public void setExcelCellValue(String excelCellValue) {
		this.excelCellValue = excelCellValue;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getExcelOriginValue() {
		return excelOriginValue;
	}
	public void setExcelOriginValue(String excelOriginValue) {
		this.excelOriginValue = excelOriginValue;
	}
	public void setIgnore(boolean ignore){
		this.ignore = ignore;
	}
	public boolean isIgnore(){
		return this.ignore;
	}
	@Override
	public String toString() {
		return "ExcelCell [index=" + index + ", drlpzModel=" + drlpzModel + ", excelHeaderName=" + excelHeaderName
				+ ", excelOriginValue=" + excelOriginValue + ", excelCellValue=" + excelCellValue + ", error=" + error
				+",ignore="+ignore
				+ "]";
	}
}
