/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.sqlExpr;

/**
 * @author 		：康康（1571）
 * @description	： 抽象条件sql表达式构建器
 */
public abstract class AbstractConditionSqlExprBuilder implements ConditionSqlExprBuilder{
	
	private String tableName;
	
	public AbstractConditionSqlExprBuilder(String tableName) {
		this.tableName = tableName;
	}

	public boolean accept(String tableName) {
		return tableName.equals(this.tableName);
	}

}