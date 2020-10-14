/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.sqlExpr;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import com.alibaba.druid.util.JdbcUtils;

/**
 * @author 		：康康（1571）
 * @description	： 默认的sql构建器
 */
public class DataRangeHitSqlBuilder{
	
	private List<ConditionSqlExprBuilder> conditionSqlExprBuilders;
	
	private Set<SQLExpr> processedTagSQLExpr;// 已经标记tag的表达式，避免重复标记
	
	private boolean showTag;
	
	private String sql;
	private String resultSql;
	
	public DataRangeHitSqlBuilder(String sql,List<ConditionSqlExprBuilder> conditionSqlExprBuilders,boolean showTag) {
		this.sql = sql;
		this.conditionSqlExprBuilders = conditionSqlExprBuilders;
		this.processedTagSQLExpr = new HashSet<SQLExpr>();
		this.showTag = showTag;
	}

	public String buildSql() {

		List<SQLStatement> stmtList = SQLUtils.parseStatements(this.sql, JdbcUtils.ORACLE);
		
		for(ConditionSqlExprBuilder cseb:conditionSqlExprBuilders) {
			
			for (SQLStatement stmt: stmtList) {
				
				if (stmt instanceof SQLSelectStatement) {
					
					SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) stmt;
					
					// 追加where条件
					SqlVisitor vister = new SqlVisitor(cseb,processedTagSQLExpr);
					sqlSelectStatement.accept(vister);
				}
			}
			// 追加结果到buffer中
			StringBuffer resultSqlBuffer = new StringBuffer();
			for (SQLStatement stmt: stmtList) {
				OracleOutputVisitor oracleOutputVisitor = new OracleOutputWithTagVisitor(resultSqlBuffer,showTag,processedTagSQLExpr);
				//保持原来的sql样式
				oracleOutputVisitor.setPrettyFormat(false);
				stmt.accept(oracleOutputVisitor);
			}
			this.resultSql = resultSqlBuffer.toString();
		}
		
		return this.resultSql;
	}
}
