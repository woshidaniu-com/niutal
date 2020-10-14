/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.excel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.handler.IdObject;

/**
 * @author 		：康康（1571）
 * Excel的行数据
 */
public class ExcelRow extends IdObject implements Comparable<ExcelRow>{
	
	private final Logger log = LoggerFactory.getLogger(ExcelRow.class);
	
	//是否是插入
	private boolean isInsert = false;
	//是否是更新
	private boolean isUpdate = false;
	//本行索引
	private int index;
	//主键列的数据
	private List<ExcelCell> primaryKeys = new ArrayList<ExcelCell>();
	//所有列的数据(主键列 + 非主键列)
	private List<ExcelCell> cells = new LinkedList<ExcelCell>();
	//所有的唯一性键
	private List<ExcelCell> uniqueKeys = new ArrayList<ExcelCell>();
	
	public ExcelRow(int index,List<ExcelCell> cells) {
		this.index = index;
		this.cells = cells;
	}
	/**
	 * @description	： 获得这一行的所有单元格数据
	 * @return
	 */
	public List<ExcelCell> getCells() {
		return cells;
	}

	public void setCells(List<ExcelCell> cells) {
		this.cells = cells;
	}

	/**
	 * @description	： 这一行是否要对其执行插入操作
	 * @return
	 */
	public boolean isInsert() {
		return isInsert;
	}

	public void setInsert(boolean isInsert) {
		this.isInsert = isInsert;
	}

	/**
	 * @description	： 这一行是否要对其执行更新操作
	 * @return
	 */
	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	/**
	 * @description	： 获得这一行的主键列的所有单元格 
	 * @return
	 */
	public List<ExcelCell> getPrimaryKeys() {
		return primaryKeys;
	}
	
	public void setPrimaryKeys(List<ExcelCell> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}
	
	public List<ExcelCell> getUniqueKeys() {
		return uniqueKeys;
	}
	
	public void setUniqueKeys(List<ExcelCell> uniqueKeys) {
		this.uniqueKeys = uniqueKeys;
	}

	/**
	 * @description	： 获得这一行在原始Excel的行号
	 * @return
	 */
	public int getIndex() {
		return index;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ValidateRow[\n");
		stringBuilder//
			.append(" index=").append(this.index)//
			.append(" id=").append(this.getId())//
			.append(" isInsert=").append(this.isInsert)//
			.append(" isUpdate=").append(this.isUpdate).append(",\n");
		stringBuilder.append(" primaryKeys=\n");
		for(ExcelCell cell : this.primaryKeys) {
			stringBuilder.append("  ").append(cell).append(",\n");
		}
		stringBuilder.append(" cells=\n");
		for(ExcelCell cell : cells) {
			stringBuilder.append("  ").append(cell).append(",\n");
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	@Override
	public int compareTo(ExcelRow o) {
		return this.index - o.getIndex();
	}
}
