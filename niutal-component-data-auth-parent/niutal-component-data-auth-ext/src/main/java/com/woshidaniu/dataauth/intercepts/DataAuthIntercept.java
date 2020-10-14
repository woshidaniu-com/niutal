package com.woshidaniu.dataauth.intercepts;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;

import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dataauth.dao.entities.DataRule;
import com.woshidaniu.dataauth.service.impl.DataAuthServiceImpl;
import com.woshidaniu.dataauth.service.svcinterface.DataAuthService;
import com.woshidaniu.orm.mybatis.interceptor.AbstractInterceptorAdapter;
import com.woshidaniu.orm.mybatis.interceptor.meta.MetaStatementHandler;
import com.woshidaniu.orm.mybatis.utils.MetaObjectUtils;
import com.woshidaniu.web.WebContext;

/**
 * 
 * @description : 数据权限拦截器
 * @author : 小康康(1505)
 */
@Intercepts({
	@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class })
})
public class DataAuthIntercept extends AbstractInterceptorAdapter{
	/**
	 * @description : 占位信息说明
	 * 	yhm--当前用户名
	 * 	jsdm--当前角色
	 * 	jsdms--多角色时角色代码字符串
	 * 	items--用户自定义的选项字符值串
	 * @author : 小康康(1505)
	 * @date : 2018年11月26日 下午2:24:21
	 * @param u 当前用户信息
	 * @param r 权限规则
	 * @param sql 源SQL
	 * @param mapperId
	 * @return
	 */
	private String replaceSql(User u, DataRule r, String sql, String mapperId) {
		if(StringUtils.isNoneBlank(r.getMethodRegexs())) {
			String[] regexs = r.getMethodRegexs().split(",");
			for (String regex : regexs) {
				if(Pattern.matches(regex, mapperId)) {
					//替换源SQL
					if(StringUtils.isNoneBlank(r.getReplaceRegexs()) && StringUtils.isNoneBlank(r.getReplaceSqls())) {
						String[] regexArr = r.getReplaceRegexs().split(",");
						String[] sqlArr = r.getReplaceSqls().split(",");
						for (int i=0;i<regexArr.length;i++) {
							String regexStr = regexArr[i].replaceAll("~", ",");
							String sqlStr = sqlArr[i].replaceAll("~", ",");
							sql = sql.replaceAll(regexStr, sqlStr);
						}
					}
					//为SQL增加前缀
					if(StringUtils.isNoneBlank(r.getPrepositionSql())) {
						sql = r.getPrepositionSql() + sql;
					}
					//为SQL增加后缀
					if(StringUtils.isNoneBlank(r.getPostpositionSql())) {
						sql += r.getPostpositionSql();
					}
					//占位信息替换
					sql = sql.replaceAll("\\{yhm\\}", "'"+u.getYhm()+"'");
					sql = sql.replaceAll("\\{jsdm\\}", "'"+u.getJsdm()+"'");
					String jsdms = "'";
					for(String jsdm : u.getJsdms()) {jsdms += "','" + jsdm;}
					jsdms += "'";
					sql = sql.replaceAll("\\{jsdms\\}", jsdms);
					sql = sql.replaceAll("\\{items\\}", r.getItemValues() != null ? r.getItemValues() : "'default'");
					return sql;
				}
			}
		}
		return null;
	}
	
	public String replaceSql(String mapperId, String sql, DataAuthService dataAuthService) {
		User user = WebContext.getUser();
		//当前用户为空则不替换
		if(user == null) {
			return sql;
		}
		//替换用户所包含的规则
		List<DataRule> list = dataAuthService.listByUser(user.getYhm(), user.getJsdm());
		if(list != null) {
			for (DataRule r : list) {
				String s = replaceSql(user, r, sql, mapperId);
				if(s != null) {return s;}
			}
		}
		//替换当前角色所包含的规则
		list = dataAuthService.listByRole(Arrays.asList(user.getJsdm()));
		if(list != null) {
			for (DataRule r : list) {
				String s = replaceSql(user, r, sql, mapperId);
				if(s != null) {
					return s;
				}
			}
		}
		return sql;
	}
	
	@Override
	public Object doStatementIntercept(Invocation invocation, StatementHandler statementHandler,MetaStatementHandler metaStatementHandler) throws Throwable {
		MappedStatement mappedStatement = metaStatementHandler.getMappedStatement();
		String id = mappedStatement.getId();
		BoundSql boundSql = metaStatementHandler.getBoundSql();
		MetaObject metaBoundSql = MetaObjectUtils.forObject(boundSql);
		String originalSQL = (String) metaBoundSql.getValue("sql");
		
		//不是查询语句不做处理
		if(!originalSQL.trim().toLowerCase().startsWith("select")) {
			return invocation.proceed();
		}
		
		//拦截器自己用到的SQL不做处理
		if(id.endsWith("DataAuthDao.listByRole") || id.endsWith("DataAuthDao.listByUser")) {
			return invocation.proceed();
		}
		
		String replacedSql = replaceSql(mappedStatement.getId(), originalSQL, ServiceFactory.getService(DataAuthServiceImpl.class));
		ReflectionUtils.setValueByFieldName(boundSql, "sql",replacedSql);
		return invocation.proceed();
	}
	
	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
	}

	@Override
	public void setInterceptProperties(Properties properties) {
		
	}

}
