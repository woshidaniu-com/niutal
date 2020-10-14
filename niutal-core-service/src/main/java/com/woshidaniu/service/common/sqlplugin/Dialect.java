package com.woshidaniu.service.common.sqlplugin;

import com.woshidaniu.common.query.QueryModel;

public abstract class Dialect {

	public static enum Type {
		MYSQL, ORACLE
	}

	public abstract String generatePageSql(String sql, QueryModel page);

}