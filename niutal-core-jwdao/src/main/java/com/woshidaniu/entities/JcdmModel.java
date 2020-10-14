package com.woshidaniu.entities;

import com.woshidaniu.common.query.ModelBase;

public class JcdmModel extends ModelBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String table;         //表名称
	private String primary_key;   //主键字段
	private String primary_value; //主键值
	private String table_fields;  //字段名称
	private String values;        //字段值
	private String pks[];         //数组
	private String validateSql;   //验证SQL
	private String QuerySql;      //查询SQL
	private String QueryWhere;    //查询where条件
	
	
	public String getQuerySql() {
		return QuerySql;
	}
	public void setQuerySql(String querySql) {
		QuerySql = querySql;
	}
	public String getValidateSql() {
		return validateSql;
	}
	public void setValidateSql(String validateSql) {
		this.validateSql = validateSql;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getPrimary_key() {
		return primary_key;
	}
	public void setPrimary_key(String primaryKey) {
		primary_key = primaryKey;
	}
	public String getPrimary_value() {
		return primary_value;
	}
	public void setPrimary_value(String primaryValue) {
		primary_value = primaryValue;
	}
	public String getTable_fields() {
		return table_fields;
	}
	public void setTable_fields(String tableFields) {
		table_fields = tableFields;
	}
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}
	public String[] getPks() {
		return pks;
	}
	public void setPks(String[] pks) {
		this.pks = pks;
	}
	public String getQueryWhere() {
		return QueryWhere;
	}
	public void setQueryWhere(String queryWhere) {
		QueryWhere = queryWhere;
	}
	
}
