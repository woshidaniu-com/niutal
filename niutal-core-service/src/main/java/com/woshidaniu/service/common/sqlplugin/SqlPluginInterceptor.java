package com.woshidaniu.service.common.sqlplugin;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.orm.mybatis.utils.BoundSQLUtils;

@Intercepts( { 
	@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }), 
	@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) 
})
public class SqlPluginInterceptor implements Interceptor {

	private static final transient Logger log = LoggerFactory.getLogger(SqlPluginInterceptor.class);
	// 数据库方言
	private static final Dialect dialect = new OracleDialect();
	
	private static String pageSqlId; // 分页Id,mapper.xml中需要拦截的ID(正则匹配)
	private static final String sqlName = "sql";
	private static final String delegateName = "delegate";
	private static final String statementName = "mappedStatement";
	private static final String pageQueryName = "queryModel";
	
	
	@Override
	public Object intercept(Invocation ivk) throws Throwable {
		if (!(ivk.getTarget() instanceof RoutingStatementHandler)) {
			return ivk.proceed();
		}

		RoutingStatementHandler handler = (RoutingStatementHandler) ivk.getTarget();
		BaseStatementHandler baseHandler = (BaseStatementHandler) ReflectionUtils.getValueByFieldName(handler, delegateName);
		MappedStatement statement = (MappedStatement) ReflectionUtils.getValueByFieldName(baseHandler, statementName);
		Connection connection = (Connection) ivk.getArgs()[0];
		BoundSql boundSql = baseHandler.getBoundSql();
		
		String sql = boundSql.getSql();
		String statementId = statement.getId();
		

		if (statementId.matches(pageSqlId)) { // 拦截需要分页的SQL
			// 分页SQL<select>中parameterType属性对应的实体参数
			Object parameter = boundSql.getParameterObject();
			if (parameter == null) {
				return ivk.proceed();
			}

			String countSql = "select count(0) as c from (" + sql + ")"; // 记录统计

			PreparedStatement countStmt = connection.prepareStatement(countSql);
			BoundSql newBoundSql = new BoundSql(statement.getConfiguration(),
					countSql, boundSql.getParameterMappings(), parameter);

//			setParameters(countStmt, statement, newBoundSql, parameter);
			BoundSQLUtils.setBoundSql(boundSql, newBoundSql);
			ParameterHandler parameterHandler = new DefaultParameterHandler(statement, boundSql.getParameterObject(), newBoundSql);
    		// 通过parameterHandler给PreparedStatement对象设置参数
			parameterHandler.setParameters(countStmt);
			
			// 执行count SQl
			ResultSet rs = countStmt.executeQuery();
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}

			try {
				// 参数为某个实体，该实体拥有Page属性
				QueryModel pageModel = (QueryModel) ReflectionUtils
						.getValueByFieldName(parameter, pageQueryName);
				if (pageModel == null) {
					pageModel = new QueryModel();
				}
				pageModel.setEntityOrField(false);
				pageModel.setTotalResult(count);

				Field field = ReflectionUtils.getFieldByFieldName(parameter,
						pageQueryName);
				field.set(parameter, pageModel);

				sql = dialect.generatePageSql(sql, pageModel);

			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						log.error("", e);
					}
				}
				if (countStmt != null) {
					try {
						countStmt.close();
					} catch (SQLException e) {
						log.error("", e);
					}
				}
			}
		}
		// 将分页sql语句反射回BoundSql.
		ReflectionUtils.setValueByFieldName(boundSql, sqlName, sql);

		return ivk.proceed();
	}

	private void setParameters(PreparedStatement ps,
			MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {

		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

		if (parameterMappings == null) {
			return;
		}

		Configuration configuration = mappedStatement.getConfiguration();
		TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
		MetaObject metaObject = null;

		if (parameterObject != null) {
			metaObject = configuration.newMetaObject(parameterObject);
		}

		for (int i = 0; i < parameterMappings.size(); i++) {
			ParameterMapping parameterMapping = parameterMappings.get(i);

			if (parameterMapping.getMode() == ParameterMode.OUT) {
				continue;
			}

			Object value = null;

			String propertyName = parameterMapping.getProperty();
			PropertyTokenizer prop = new PropertyTokenizer(propertyName);

			if (parameterObject == null) {
				value = null;
			} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				value = parameterObject;
			} else if (boundSql.hasAdditionalParameter(propertyName)) {
				value = boundSql.getAdditionalParameter(propertyName);
			} else if (propertyName.startsWith("__frch_") && boundSql.hasAdditionalParameter(prop.getName())) {
				value = boundSql.getAdditionalParameter(prop.getName());
				if (value != null) {
					value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
				}
			} else {
				value = metaObject == null ? null : metaObject.getValue(propertyName);
			}

			TypeHandler typeHandler = (TypeHandler) parameterMapping.getTypeHandler();
			if (typeHandler != null) {
				typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
			}
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties p) {
		pageSqlId = p.getProperty("pageSqlId");
	}

}