/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.sqlExpr;

import java.util.Set;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;

public class OracleOutputWithTagVisitor extends OracleOutputVisitor {

	private static final String TAG_START = "  /*权限条件开始*/  ";
	private static final String TAG_END = "  /*权限条件结束*/  ";

	private boolean showTag;

	private Set<SQLExpr> processedTagSQLExpr;// 追加tag的表达式

	public OracleOutputWithTagVisitor(Appendable appender, boolean showTag, Set<SQLExpr> processedTagSQLExpr) {
		super(appender);
		this.showTag = showTag;
		this.processedTagSQLExpr = processedTagSQLExpr;
	}

	@Override
	public boolean visit(SQLInListExpr x) {
		boolean printTag = processedTagSQLExpr != null && this.processedTagSQLExpr.contains(x);
		if(showTag && printTag) {
			this.print0(TAG_START);
		}
		boolean ret = super.visit(x);
		if(showTag && printTag) {
			this.print0(TAG_END);
		}
		return ret;
	}

}
