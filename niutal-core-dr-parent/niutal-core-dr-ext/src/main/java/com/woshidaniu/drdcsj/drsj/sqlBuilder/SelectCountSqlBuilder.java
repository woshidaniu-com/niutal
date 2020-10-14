/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.sqlBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SelectCountSqlBuilder implements SafeSqlBuilder{
	
	private String targetTable;
	private Map<String,String> whereMap = new LinkedHashMap<String,String>();
	private Map<String,String> whereNotMap = new LinkedHashMap<String,String>();
	
	public SelectCountSqlBuilder() {
		
	}
	
	public SelectCountSqlBuilder selectCount(String targetTable) {
		this.targetTable = targetTable;
		return this;
	}
	
	public SelectCountSqlBuilder where(String column,String value) {
		this.whereMap.put(column, value);
		return this;
	}
	
	public SelectCountSqlBuilder whereNot(String column,String value) {
		this.whereNotMap.put(column, value);
		return this;
	}

	@Override
	public String getSql() {
		
		StringBuilder builder = new StringBuilder("select count(*) from ").append(targetTable).append(" ");
		boolean first = true;
		Iterator<String> it_where_map = whereMap.keySet().iterator();
		while(it_where_map.hasNext()) {
			String column = it_where_map.next();
			if(first) {
				builder.append(" where ").append(column).append(" = ? ");
				first = false;
			}else {
				builder.append(" and ").append(column).append(" = ? ");
			}
		}
		Iterator<String> it_where_not_map = whereNotMap.keySet().iterator();
		while(it_where_not_map.hasNext()) {
			String column = it_where_not_map.next();
			if(first) {
				builder.append(" where ").append(column).append(" <> ? ");
				first = false;
			}else {
				builder.append(" and ").append(column).append(" <> ? ");
			}
		}
		String sql = builder.toString();
		this.checkSql(sql);
		return sql;
	}

	@Override
	public List<String> getParams() {
		int s1 = whereMap.size();
		int s2 = whereNotMap.size();
		List<String> params = new ArrayList<String>(s1 + s2);
		params.addAll(whereMap.values());
		params.addAll(whereNotMap.values());
		return params;
	}

	@Override
	public void checkSql(String sql) {
		if(whereMap.isEmpty() && whereNotMap.isEmpty()) {
			throw new IllegalStateException("sql["+ sql +"]缺乏where条件");
		}
	}
	
	public static void main(String[] args) {
		SelectCountSqlBuilder selectCountSqlBuilder = new SelectCountSqlBuilder();
		selectCountSqlBuilder.selectCount("t_user").whereNot("name", "weiwei").whereNot("age", "1").where("xh", "110");
		
		System.out.println(selectCountSqlBuilder.getSql());
		System.out.println(selectCountSqlBuilder.getParams());
	}
}
