/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.sqlExpr;
import com.alibaba.druid.sql.ast.SQLExpr;

/**
 * @author 		：康康（1571）
 * @description	： 条件语句sql表达式构建器
 */
public interface ConditionSqlExprBuilder {
	
	/**
	 * @description	： 是否处理这个表
	 * @param tableName 表名称
	 * @return
	 */
	public boolean accept(String tableName);

	/**
	 * 
	 * @description	： 构建sql表达式
	 * @param tableNameAlias 表别名
	 * @return
	 */
	public SQLExpr build(String tableNameAlias);
}