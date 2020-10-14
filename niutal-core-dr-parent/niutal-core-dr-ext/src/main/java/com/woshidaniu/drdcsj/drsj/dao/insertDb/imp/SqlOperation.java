/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.insertDb.imp;

/**
 * 封装sql导入时的配置数据
 * @author xiaokang
 */
public class SqlOperation {

	private String[] update;
	
	private String[] insert;

	public SqlOperation(String[] update, String[] insert) {
		this.update = update;
		this.insert = insert;
	}

	public String[] getUpdate() {
		return update;
	}

	public void setUpdate(String[] update) {
		this.update = update;
	}

	public String[] getInsert() {
		return insert;
	}

	public void setInsert(String[] insert) {
		this.insert = insert;
	}
}
