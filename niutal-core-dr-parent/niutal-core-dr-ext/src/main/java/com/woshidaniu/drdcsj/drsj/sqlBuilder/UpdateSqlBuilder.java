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
 * 更新语句构建器
 */
public class UpdateSqlBuilder implements SafeSqlBuilder{
	
	private String targetTable;
	
	private Map<String,String> setMap = new LinkedHashMap<String,String>();
	private Map<String,String> whereMap = new LinkedHashMap<String,String>();
	
	public UpdateSqlBuilder() {
	}
	
	public UpdateSqlBuilder update(String targetTable) {
		this.targetTable = targetTable;
		return this;
	}
	
	public UpdateSqlBuilder set(String column,String newValue) {
		setMap.put(column, newValue);
		return this;
	}
	
	public UpdateSqlBuilder where(String column,String value) {
		whereMap.put(column, value);
		return this;
	}
	
	public void checkSql(String sql) {
		if(whereMap.isEmpty()) {
			throw new IllegalStateException("sql["+ sql +"]缺乏where条件");
		}
		if(setMap.isEmpty()) {
			throw new IllegalStateException("sql["+ sql +"]缺乏set参数条件");
		}
	}
	
	@Override
	public String getSql() {
		StringBuilder builder = new StringBuilder("update ").append(targetTable).append(" ");
		boolean first = true;
		Iterator<String> it_set_map = setMap.keySet().iterator();
		while(it_set_map.hasNext()) {
			String column = it_set_map.next();
			if(first) {
				builder.append(" set ").append(column).append(" = ? ");
				first = false;
			}else {
				builder.append(" , ").append(column).append(" = ? ");
			}
		}
		
		first = true;
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
		
		String sql = builder.toString();
		this.checkSql(sql);
		return sql;
	}

	@Override
	public List<String> getParams() {
		int size = setMap.size() + whereMap.size();
		List<String> params = new ArrayList<String>(size);
		params.addAll(setMap.values());
		params.addAll(whereMap.values());
		return params;
	}
	
	public static void main(String[] args) {
		UpdateSqlBuilder updateSqlBuilder = new UpdateSqlBuilder();
		updateSqlBuilder//
		.update("t_user")
		.set("name", "zhidong")
		.set("age", "19")
		.where("id", "1")
		.where("uid", "2");
		
		String sql = updateSqlBuilder.getSql();
		System.out.println(sql);
		System.out.println(updateSqlBuilder.getParams());
	}
}
