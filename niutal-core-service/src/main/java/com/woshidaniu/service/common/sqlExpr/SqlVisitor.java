/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.sqlExpr;


import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectJoin;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectTableReference;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;

/**
 * @author ：康康（1571）
 * @description ： SqlVisitor
 */
public class SqlVisitor extends OracleSchemaStatVisitor {

	private static final Logger log = LoggerFactory.getLogger(SqlVisitor.class);
	
	private ConditionSqlExprBuilder conditionSqlExprBuilder;

	private Set<ProcessedPairKey> processed;// 已经处理过的集合，避免无限递归不退出
	
	private Set<SQLExpr> processedTagSQLExpr;//追加tag的表达式

	public SqlVisitor(ConditionSqlExprBuilder conditionSqlExprBuilder, Set<SQLExpr> processedTagSQLExpr) {
		this.processed = new HashSet<ProcessedPairKey>();
		this.conditionSqlExprBuilder = conditionSqlExprBuilder;
		this.processedTagSQLExpr = processedTagSQLExpr;
	}

	@Override
	public void endVisit(OracleSelectTableReference x) {

		SQLExpr sqlExpr = x.getExpr();
		if (sqlExpr instanceof SQLIdentifierExpr) {

			SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) sqlExpr;
			String tableName = sqlIdentifierExpr.getName();
			
			SQLObject sqlObject = x.getParent();
			ProcessedPairKey pairKey = new ProcessedPairKey(sqlObject, sqlIdentifierExpr);
			// 若已经处理过了，直接跳过
			if (processed.contains(pairKey)) {
				return;
			}
			// 如果发现匹配的表，就处理
			boolean accept = conditionSqlExprBuilder.accept(tableName);

			if (accept) {
				if (log.isTraceEnabled()) {
					log.trace("found target table[" + tableName + "]");
				}
				String tableNameAlias = x.getAlias();
				if (tableNameAlias == null) {
					tableNameAlias = tableName;
				} else {
					if (log.isTraceEnabled()) {
						log.trace("use table alias[" + tableNameAlias + "]");
					}
				}
				if (sqlObject instanceof SQLSelectQueryBlock) {// 父节点是查询块

					SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock) sqlObject;
					this.appendWhereCondition(sqlSelectQueryBlock, tableNameAlias);

				} else if (sqlObject instanceof OracleSelectJoin) {// 父节点是连接查询

					OracleSelectJoin oracleSelectJoin = (OracleSelectJoin) sqlObject;
					this.processOracleSelectJoin(oracleSelectJoin, tableNameAlias);

				} else {
					log.warn("未处理语句情况，请联系基础平台部开发人员协助处理!!!");
				}
				// 已经处理过的集合
				this.processed.add(pairKey);
			}
		}
	}

	private void processOracleSelectJoin(OracleSelectJoin oracleSelectJoin, String tableNameAlias) {

		SQLObject par = oracleSelectJoin.getParent();
		if (par instanceof SQLSelectQueryBlock) {

			SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock) par;
			this.appendWhereCondition(sqlSelectQueryBlock, tableNameAlias);

		} else if (par instanceof OracleSelectJoin) {

			OracleSelectJoin join = (OracleSelectJoin) par;
			this.processOracleSelectJoin(join, tableNameAlias);
		}
	}

	private void appendWhereCondition(SQLSelectQueryBlock sqlSelectQueryBlock, String tableNameAlias) {
		SQLExpr sqlExpr = conditionSqlExprBuilder.build(tableNameAlias);
		SQLExpr oldWhereSqlExpr =  sqlSelectQueryBlock.getWhere();
		if(oldWhereSqlExpr != null) {
			SQLExpr newWhereSqlExpr = new SQLBinaryOpExpr(oldWhereSqlExpr, SQLBinaryOperator.BooleanAnd,sqlExpr);			
			sqlSelectQueryBlock.setWhere(newWhereSqlExpr);
		}else {
			sqlSelectQueryBlock.setWhere(sqlExpr);
		}
		this.processedTagSQLExpr.add(sqlExpr);
	}
	
	//标识是否处理过的key
	private static class ProcessedPairKey{
		
		private SQLObject parent;
		private SQLIdentifierExpr sqlIdentifierExpr;
		public ProcessedPairKey(SQLObject parent, SQLIdentifierExpr sqlIdentifierExpr) {
			super();
			this.parent = parent;
			this.sqlIdentifierExpr = sqlIdentifierExpr;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((parent == null) ? 0 : parent.hashCode());
			result = prime * result + ((sqlIdentifierExpr == null) ? 0 : sqlIdentifierExpr.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ProcessedPairKey other = (ProcessedPairKey) obj;
			if (parent == null) {
				if (other.parent != null)
					return false;
			} else if (!parent.equals(other.parent))
				return false;
			if (sqlIdentifierExpr == null) {
				if (other.sqlIdentifierExpr != null)
					return false;
			} else if (!sqlIdentifierExpr.equals(other.sqlIdentifierExpr))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "PairKey [parent=" + parent + ", sqlIdentifierExpr=" + sqlIdentifierExpr + "]";
		}
	}
}