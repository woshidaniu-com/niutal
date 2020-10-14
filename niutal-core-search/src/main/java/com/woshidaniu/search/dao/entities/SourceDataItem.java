/**
 * 
 */
package com.woshidaniu.search.dao.entities;

/**
 * 数据对象
 * 
 * @author 小康
 *
 */
public  class SourceDataItem {
	//代码值
	private String columnKeyValue;
	//标签值
	private String columnLabelValue;
	
	private String columnInitialValue;
	
	
	public SourceDataItem() {
		super();
	}
	public SourceDataItem(String columnKeyValue, String columnLabelValue) {
		super();
		this.columnKeyValue = columnKeyValue;
		this.columnLabelValue = columnLabelValue;
	}
	public SourceDataItem(String columnKeyValue, String columnLabelValue,
			String columnInitialValue) {
		super();
		this.columnKeyValue = columnKeyValue;
		this.columnLabelValue = columnLabelValue;
		this.columnInitialValue = columnInitialValue;
	}
	public String getColumnKeyValue() {
		return columnKeyValue;
	}
	public void setColumnKeyValue(String columnKeyValue) {
		this.columnKeyValue = columnKeyValue;
	}
	public String getColumnLabelValue() {
		return columnLabelValue;
	}
	public void setColumnLabelValue(String columnLabelValue) {
		this.columnLabelValue = columnLabelValue;
	}
	public String getColumnInitialValue() {
		return columnInitialValue;
	}
	public void setColumnInitialValue(String columnInitialValue) {
		this.columnInitialValue = columnInitialValue;
	}
}
