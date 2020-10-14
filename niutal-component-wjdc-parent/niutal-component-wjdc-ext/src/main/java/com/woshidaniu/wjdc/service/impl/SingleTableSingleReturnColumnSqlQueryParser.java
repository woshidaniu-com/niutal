/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service.impl;

import java.util.List;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectQueryBlock;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectTableReference;
import com.alibaba.druid.util.JdbcUtils;

/**
 * @description	： 单一表查询解析器,解析得到表名称
 * @author 		：康康（1571）
 */
public class SingleTableSingleReturnColumnSqlQueryParser {
	
	private String sql;
	private String table;
	private String column;

	public SingleTableSingleReturnColumnSqlQueryParser(String sql) {
		super();
		this.sql = sql;
		this.doParse();
	}
	
	public String getTable() {
		return this.table;
	}
	
	public String getColumn() {
		return this.column;
	}

	private void doParse() {
		
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
			SQLExpr sqlItemExpr = sqlSelectItem.getExpr();
			SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) sqlItemExpr;
			String column = sqlIdentifierExpr.getName();
			this.column = column;
			
			SQLTableSource sqlTableSource = oracleSelectQueryBlock.getFrom();
			if(sqlTableSource instanceof OracleSelectTableReference) {
				OracleSelectTableReference reference = (OracleSelectTableReference)sqlTableSource;
				SQLExpr sqlExpr = reference.getExpr();
				if(sqlExpr instanceof SQLIdentifierExpr) {
					SQLIdentifierExpr expr = (SQLIdentifierExpr)sqlExpr;
					String name = expr.getName();
					this.table = name;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		String sql = "select xh from niutal_xtgl_xsxxb";
		SingleTableSingleReturnColumnSqlQueryParser singleTableSqlQueryParser = new SingleTableSingleReturnColumnSqlQueryParser(sql);
		System.out.println(singleTableSqlQueryParser.getTable());
		System.out.println(singleTableSqlQueryParser.getColumn());
	}
}
