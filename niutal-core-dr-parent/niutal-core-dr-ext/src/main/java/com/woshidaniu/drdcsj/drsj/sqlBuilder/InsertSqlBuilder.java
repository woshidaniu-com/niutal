/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.sqlBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 康康（1571）
 * 插入语句构建器
 */
public class InsertSqlBuilder implements SafeSqlBuilder{

	private String targetTable;
	
	private Map<String,String> valuesMap = new LinkedHashMap<String,String>();
	
	public InsertSqlBuilder() {
	}
	
	public InsertSqlBuilder insert(String targetTable) {
		this.targetTable = targetTable;
		return this;
	}
	
	public InsertSqlBuilder value(String column,String value) {
		valuesMap.put(column, value);
		return this;
	}
	
	public void checkSql(String sql) {
		if(valuesMap.isEmpty()) {
			throw new IllegalStateException("sql["+ sql +"]缺乏value参数");
		}
	}
	
	@Override
	public String getSql() {
		StringBuilder builder = new StringBuilder("insert into ").append(targetTable);
		boolean first = true;
		Iterator<String> it_set_map = valuesMap.keySet().iterator();
		while(it_set_map.hasNext()) {
			String column = it_set_map.next();
			if(first) {
				builder.append(" ( ").append(column).append(" ");
				first = false;
			}else {
				builder.append(" , ").append(column);
			}
		}
		builder.append(")values(");
		
		first = true;
		for(int i = 0;i<valuesMap.size();i++) {
			if(first) {
				builder.append("?");
				first = false;
			}else {
				builder.append(",?");
			}
		}
		builder.append(")");
		
		String sql = builder.toString();
		this.checkSql(sql);
		return sql;
	}

	@Override
	public List<String> getParams() {
		int size = valuesMap.size();
		List<String> params = new ArrayList<String>(size);
		params.addAll(valuesMap.values());
		return params;
	}
	
	public static void main(String[] args) {
		InsertSqlBuilder insertSqlBuilder = new InsertSqlBuilder();
		insertSqlBuilder//
		.insert("t_user")
		.value("name", "zhidong");
		
		String sql = insertSqlBuilder.getSql();
		System.out.println(sql);
		System.out.println(insertSqlBuilder.getParams());
	}
}
