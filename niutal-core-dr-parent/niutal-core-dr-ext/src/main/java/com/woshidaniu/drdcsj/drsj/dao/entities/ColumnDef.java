/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.entities;

/**
 * @author xiaokang
 * 字段定义
 */
public class ColumnDef {
		
	private String tableName;
	
	private String columnName;
	
	private String dataType;
	
	private String dataLength;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataLength() {
		return dataLength;
	}

	public void setDataLength(String dataLength) {
		this.dataLength = dataLength;
	}

	@Override
	public String toString() {
		return "ColumnDef [tableName=" + tableName + ", columnName=" + columnName + ", dataType=" + dataType
				+ ", dataLength=" + dataLength + "]";
	}
}
