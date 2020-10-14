/**
 * 
 */
package com.woshidaniu.service.common.sqlplugin;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;

import com.woshidaniu.common.datarange.DataRangeItem;
import com.woshidaniu.common.datarange.DataRangeService;
import com.woshidaniu.common.datarange.QueryType;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.daointerface.IJsglDao;
import com.woshidaniu.dao.entities.SjzygzModel;
import com.woshidaniu.orm.mybatis.interceptor.AbstractInterceptorAdapter;
import com.woshidaniu.orm.mybatis.interceptor.meta.MetaStatementHandler;
import com.woshidaniu.orm.mybatis.utils.MetaObjectUtils;
import com.woshidaniu.web.WebContext;

import net.sf.json.JSONObject;

/**
 * @author zhidong kang
 *
 */
public abstract class AbstractDataRangeInterceptor extends AbstractInterceptorAdapter {

	/**
	 * @desc 获取数据范围的定义对象
	 * @param metaStatementHandler
	 * @return
	 */
	protected abstract DataRangeItem obtainDataRangeItem(MetaStatementHandler metaStatementHandler);
	
	/**
	 * 处理最终执行的SQL语句
	 * @param boundSql
	 * @param generateDataRangeSql
	 * @return
	 */
	protected abstract String handleBoundSql(BoundSql boundSql, String generateDataRangeSql);
	
	/**
	 * 生成数据范围的sql语句
	 * @param dataRange
	 * @return
	 */
	protected StringBuilder generateDataRangeSql(DataRangeItem dataRange) {
		String[] dataIds = dataRange.getDataIds();
		String info = dataRange.getInfo();
		QueryType type = dataRange.getType();

		JSONObject jsonInfo = JSONObject.fromObject(info);

		// 通过当前用户，取出角色数据范围
		User user = WebContext.getUser();
		String jsdm = user.getJsdm();
		IJsglDao jsglDao = ServiceFactory.getService(IJsglDao.class);
		List<SjzygzModel> sjzyList = jsglDao.getSjzyList(jsdm, dataIds);// 角色规则
		StringBuilder sql = new StringBuilder();

		// 数据范围SQL解析
		for (SjzygzModel model : sjzyList) {
			if (jsonInfo.containsKey(model.getGzid())) {// 拦截方法所支持的规则

				String zdm = jsonInfo.getString(model.getGzid());// 字段名
				DataRangeService service = (DataRangeService) ServiceFactory.getService(model.getGztgz());
				List<String> sjfwList = service.getDataRangeList(user);
				StringBuilder format = new StringBuilder();

				if (sjfwList == null || sjfwList.size() == 0)
					continue;

				if (sql.length() > 0) {
					// 如果多条规则同时起效
					sql.append(" or ");
				}

				if (QueryType.OR.equals(type)) {// or 类型
					for (int i = 0, j = sjfwList.size(); i < j; i++) {
						format.append(zdm);
						format.append("='");
						format.append(sjfwList.get(i));
						format.append("'");
						if (i + 1 != j) {
							format.append(" or ");
						}
					}
				} else { // in 类型
					format.append(zdm);
					format.append(" in (");
					for (int i = 0, j = sjfwList.size(); i < j; i++) {
						format.append("'");
						format.append(sjfwList.get(i));
						format.append("'");
						if (i + 1 != j) {
							format.append(",");
						}
					}
					format.append(")");
				}

				sql.append(format.toString());
			}
		}

		return sql;
	}

	@Override
	public Object doStatementIntercept(Invocation invocation, StatementHandler statementHandler,
			MetaStatementHandler metaStatementHandler) throws Throwable {

		if (isRequireIntercept(invocation, statementHandler, metaStatementHandler)) {
			StringBuilder generateDataRangeSql = generateDataRangeSql(obtainDataRangeItem(metaStatementHandler));
			BoundSql boundSql = metaStatementHandler.getBoundSql();
			MetaObject metaBoundSql = MetaObjectUtils.forObject(boundSql);
			if (generateDataRangeSql.length() > 0) {
				metaBoundSql.setValue("sql", handleBoundSql(boundSql, generateDataRangeSql.toString()));
			}
		}
		// 将执行权交给下一个拦截器
		beforeInvocationProcess(invocation);
		
		Object proceed = invocation.proceed();
		
		afterInvocationProcess(invocation);
		
		return proceed;
	}

	protected abstract void afterInvocationProcess(Invocation invocation);
	protected abstract void beforeInvocationProcess(Invocation invocation);

	@Override
	protected boolean isRequireIntercept(Invocation invocation, StatementHandler statementHandler,
			MetaStatementHandler metaStatementHandler) {
		MappedStatement mappedStatement = metaStatementHandler.getMappedStatement();
		SqlCommandType type = mappedStatement.getSqlCommandType();

		// 查询类型
		boolean isSelect = SqlCommandType.SELECT.equals(type);
		if (!isSelect) {
			return false;
		}
		return obtainDataRangeItem(metaStatementHandler) != null;
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setInterceptProperties(Properties properties) {

	}

}
