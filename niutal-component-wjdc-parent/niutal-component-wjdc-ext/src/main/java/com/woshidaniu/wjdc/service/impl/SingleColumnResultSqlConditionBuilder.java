/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service.impl;

import java.util.List;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectQueryBlock;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import com.alibaba.druid.util.JdbcUtils;

class SingleColumnResultSqlConditionBuilder {

	private String sql;
	private String conditionValue;

	public SingleColumnResultSqlConditionBuilder(String sql, String conditionValue) {
		super();
		this.sql = sql;
		this.conditionValue = conditionValue;
	}
 
	public String build() {
		List<SQLStatement> stmtList = SQLUtils.parseStatements(this.sql, JdbcUtils.ORACLE);
		if (stmtList.size() > 1) {
			throw new IllegalArgumentException("sql:" + this.sql + ",只能是一条语句语句");
		}
		SQLStatement singleOneStme = stmtList.get(0);
		if (!(singleOneStme instanceof SQLSelectStatement)) {
			throw new IllegalArgumentException("sql:" + this.sql + ",只能查询语句");
		}

		SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) singleOneStme;
		SQLSelect sqlSelect = sqlSelectStatement.getSelect();
		SQLSelectQuery sqlSelectQuery = sqlSelect.getQuery();
		
		//是查询语句块
		if (sqlSelectQuery instanceof OracleSelectQueryBlock) {
			OracleSelectQueryBlock oracleSelectQueryBlock = (OracleSelectQueryBlock)sqlSelectQuery;
			List<SQLSelectItem> sqlSelectItems = oracleSelectQueryBlock.getSelectList();
			if (sqlSelectItems.size() > 1) {
				throw new IllegalArgumentException("sql:" + this.sql + ",只能返回单列");
			}
			SQLSelectItem sqlSelectItem = sqlSelectItems.get(0);
			SQLExpr sqlExpr = sqlSelectItem.getExpr();
			if (!(sqlExpr instanceof SQLIdentifierExpr)) {
				throw new IllegalArgumentException("sql:" + this.sql + ",返回的单列只能是标识符");
			}
			SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) sqlExpr;
			String column = sqlIdentifierExpr.getName();

			// 构建额外的where条件
			SQLExpr oldWhereSqlExpr = oracleSelectQueryBlock.getWhere();
			
			SQLExpr left = new SQLIdentifierExpr(column);
			SQLExpr right = new SQLCharExpr(conditionValue);
			SQLBinaryOpExpr condition = new SQLBinaryOpExpr(left, SQLBinaryOperator.Equality, right);

			// 追加where条件
			if (oldWhereSqlExpr != null) {
				SQLExpr newWhereSqlExpr = new SQLBinaryOpExpr(oldWhereSqlExpr, SQLBinaryOperator.BooleanAnd, condition);
				oracleSelectQueryBlock.setWhere(newWhereSqlExpr);
			} else {

				oracleSelectQueryBlock.setWhere(condition);
			}
		}
		// 追加结果到buffer中
		StringBuffer resultSqlBuffer = new StringBuffer();
		
		OracleOutputVisitor oracleOutputVisitor = new OracleOutputVisitor(resultSqlBuffer);
		oracleOutputVisitor.setPrettyFormat(false);
		singleOneStme.accept(oracleOutputVisitor);

		String resultsql = resultSqlBuffer.toString();
		return resultsql;
	}
}