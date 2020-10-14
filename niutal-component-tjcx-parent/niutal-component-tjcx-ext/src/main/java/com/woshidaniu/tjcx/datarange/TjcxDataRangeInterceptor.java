package com.woshidaniu.tjcx.datarange;

import java.sql.Connection;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.datarange.DataRangeItem;
import com.woshidaniu.common.datarange.QueryType;
import com.woshidaniu.orm.mybatis.interceptor.meta.MetaStatementHandler;
import com.woshidaniu.service.common.sqlplugin.AbstractDataRangeInterceptor;
import com.woshidaniu.tjcx.TjcxThreadLocalUtil;
import com.woshidaniu.tjcx.dao.entites.YsjModel;

/**
 * 
 * @author zhidong.kang
 * 
 * @desc 实现统计查询的数据范围控制
 *
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }), })
public class TjcxDataRangeInterceptor extends AbstractDataRangeInterceptor {

	static final String MAPPED_STATEMENT_PRIFIX = "com.woshidaniu.tjcx.dao.daointerface";

	static final Logger logger = LoggerFactory.getLogger(TjcxDataRangeInterceptor.class);

	// SELECT * FROM XSXXB A WHERE {}
	// {}里面会根据 SJFWPZ解析成对应SQL语句
	static final String PLACEHOLDER = "{}";

	@Override
	protected DataRangeItem obtainDataRangeItem(MetaStatementHandler metaStatementHandler) {
		MappedStatement mappedStatement = metaStatementHandler.getMappedStatement();
		String stmtId = mappedStatement.getId();

		if (!StringUtils.startsWith(stmtId, MAPPED_STATEMENT_PRIFIX)) {
			return null;
		}

		YsjModel ysjThreadLocal = TjcxThreadLocalUtil.getYsjThreadLocal();
		if (null != ysjThreadLocal && StringUtils.contains(ysjThreadLocal.getSql(), PLACEHOLDER)) {
			return doParseJSONConfig(ysjThreadLocal.getSjfwpz());
		}
		return null;
	}

	// @DataRange(info = "{TEACHER_SZBM:'JGDM'}", dataIds = { "teacher" } )
	private DataRangeItem doParseJSONConfig(String sjfwpz) {
		DataRangeItem item = new DataRangeItem();

		JSONObject parseObject = JSON.parseObject(sjfwpz);
		String type = parseObject.getString("type");
		String[] dataIds = StringUtils.tokenizeToStringArray(parseObject.getString("dataIds"), ",");
		String info = parseObject.getString("info");

		item.setDataIds(dataIds);
		item.setInfo(info);
		item.setType(QueryType.valueOf(type));
		return item;
	}

	@Override
	protected String handleBoundSql(BoundSql boundSql, String generateDataRangeSql) {
		return StringUtils.replace(boundSql.getSql(), PLACEHOLDER, generateDataRangeSql);
	}

	@Override
	protected void afterInvocationProcess(Invocation invocation) {
		TjcxThreadLocalUtil.clear();
	}

	@Override
	protected void beforeInvocationProcess(Invocation invocation) {
	}

}
