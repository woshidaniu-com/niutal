/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.sqlExpr;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;

/**
 * @author 		：康康（1571）
 * @description	： 用于构建where in表达式
 */
public class WhereInSqlExprBuilder extends AbstractConditionSqlExprBuilder{
	

	private String columnName;

	private String[] arrayInValue;
	
	private boolean not;

	public WhereInSqlExprBuilder(String tableName,String columnName, String[] arrayInValue,boolean not) {
		super(tableName);
		this.columnName = columnName;
		this.arrayInValue = arrayInValue;
		this.not = not;
	}

	@Override
	public SQLExpr build(String tableNameAlias) {
		SQLExpr targetExpr = new SQLIdentifierExpr(tableNameAlias + "." + this.columnName);
		SQLInListExpr condition = new SQLInListExpr();
		condition.setExpr(targetExpr);
		List<SQLExpr> sqlExprList = new ArrayList<SQLExpr>();
		for (String v : arrayInValue) {
			SQLExpr se = new SQLCharExpr(v);
			sqlExprList.add(se);
		}
		condition.setTargetList(sqlExprList);
		condition.setNot(not);
		return condition;
	}
}