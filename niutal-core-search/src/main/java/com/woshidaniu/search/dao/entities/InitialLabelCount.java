package com.woshidaniu.search.dao.entities;

public class InitialLabelCount {

	private String columnInitialValue;
	
	private Integer columnInitialLabelCount;

	public String getColumnInitialValue() {
		return columnInitialValue;
	}

	public void setColumnInitialValue(String columnInitialValue) {
		this.columnInitialValue = columnInitialValue;
	}

	public Integer getColumnInitialLabelCount() {
		return columnInitialLabelCount;
	}

	public void setColumnInitialLabelCount(Integer columnInitialLabelCount) {
		this.columnInitialLabelCount = columnInitialLabelCount;
	}

	public InitialLabelCount() {
		super();
	}

	public InitialLabelCount(String columnInitialValue,
			Integer columnInitialLabelCount) {
		super();
		this.columnInitialValue = columnInitialValue;
		this.columnInitialLabelCount = columnInitialLabelCount;
	}
	
	
	
}
