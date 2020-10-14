package com.woshidaniu.service.common.plugin;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basemodel.BaseModel;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.beanutils.reflection.AnnotationUtils;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.orm.mybatis.annotation.Pagination;
import com.woshidaniu.orm.mybatis.interceptor.AbstractInterceptorAdapter;
import com.woshidaniu.orm.mybatis.interceptor.meta.MetaResultSetHandler;
import com.woshidaniu.orm.mybatis.interceptor.meta.MetaStatementHandler;
import com.woshidaniu.orm.mybatis.utils.BoundSQLUtils;
import com.woshidaniu.orm.mybatis.utils.MetaObjectUtils;
import com.woshidaniu.orm.mybatis.utils.MyBatisSQLUtils;
import com.woshidaniu.orm.mybatis.utils.ParameterUtils;
import com.woshidaniu.service.utils.MybatisUtils;

/**
 * 
 *@类名称		： PartitionPaginationInterceptor.java
 *@类描述		：通过拦截<code>StatementHandler</code>的<code>prepare</code>方法，
 *               重写mybatis的SQL语句，实现物理分页;处理使用开窗函数获取分页数据中记录总数解析
 *@创建人		：kangzhidong
 *@创建时间	：Aug 24, 2016 4:59:32 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Intercepts( { 
	@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) ,
	@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
	@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class })
})
public class PartitionPaginationInterceptor extends AbstractInterceptorAdapter {

	protected static Logger LOG = LoggerFactory.getLogger(PartitionPaginationInterceptor.class);
	/* 默认拦截分页方法的正则表达式 */
	protected final String DEFAULT_PAGINATION_METHOD_REGEXP = ".*Paged*.*"; 
	
	@Override
	protected boolean isRequireIntercept(Invocation invocation,StatementHandler statementHandler,MetaStatementHandler metaStatementHandler) {
		// 通过反射获取到当前MappedStatement
		MappedStatement mappedStatement = metaStatementHandler.getMappedStatement();
		// 获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
		BoundSql boundSql = metaStatementHandler.getBoundSql();
		//参数对象
		Object parameterObject = boundSql.getParameterObject();
		//是否可分页
		boolean pageable = ParameterUtils.isPageable(parameterObject);
		// 拦截需要分页的SQL语句。通过MappedStatement的ID匹配，默认重写以包含Paged字符的ID
		return (mappedStatement.getId().matches(this.getMethodRegexp()) && pageable) || 
				AnnotationUtils.hasAnnotation(invocation.getMethod(), Pagination.class);
	}
	
	@Override
	public Object doStatementIntercept(Invocation invocation,StatementHandler statementHandler,MetaStatementHandler metaStatementHandler) throws Throwable {
		
		//检查是否需要进行拦截处理
		if (isRequireIntercept(invocation, statementHandler, metaStatementHandler)) {
			
			// 获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
			BoundSql boundSql = metaStatementHandler.getBoundSql();
			// 参数对象
			Object parameterObject = boundSql.getParameterObject();
			// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
			if(parameterObject == null){
				LOG.error("parameterObject 未实例化！");
			}else{
				
				// 通过反射获取到当前RoutingStatementHandler对象的delegate属性
				MappedStatement mappedStatement = metaStatementHandler.getMappedStatement();
				
				MetaObject metaBoundSql = MetaObjectUtils.forObject(boundSql);
				
				//获取分页参数对象
				QueryModel queryModel = (QueryModel)ParameterUtils.getQueryModel(parameterObject);
				//如果参数对象不为空，则进行记录数查询
				if(queryModel != null){
					// 对原始SQL进行分页SQL和总数SQL的处理
					String originalSQL = (String) metaBoundSql.getValue("sql");
					if(LOG.isInfoEnabled()){
						LOG.info("********************************分页原始SQL start****************************");
						//为了日志输出原SQL，只有这个用处
						String firstSql = 	MyBatisSQLUtils.getRunSQL(mappedStatement, parameterObject ,false);
						LOG.info("原始分页SQL===>"+firstSql);
						LOG.info("********************************分页原始SQL end*****************************");
					}
					boolean pageable = ParameterUtils.isPageable(parameterObject);
					// 获得物理分页SQL
					//String finalSQL = dialectObject.getOnceLimitSQL(originalSQL,queryModel.getOffset(), queryModel.getLimit());
					// 对原始SQL进行分页SQL和总数SQL的处理
					String finalSQL = MybatisUtils.buildPageSQL(mappedStatement, boundSql, originalSQL, (com.woshidaniu.common.query.QueryModel) queryModel , pageable, getDialect());
					//复制双倍参数
					List<ParameterMapping> originalParams =  boundSql.getParameterMappings();//获取传入参数
					List<ParameterMapping> finalParams =  new ArrayList<ParameterMapping>();//将参数复制2次
					for(int pi =0; pi <2; pi++){
						for (ParameterMapping originalParam : originalParams) {
							finalParams.add(originalParam);
						}
					}
					metaBoundSql.setValue("parameterMappings", finalParams);
					/*
					Field field = boundSql.getClass().getDeclaredField("parameterMappings");//复制后的参数重新通过反射赋值方式到参数上
					field.setAccessible(true);//可访问私有变量。 
					field.set(boundSql, finalParams);
					field.setAccessible(false);*/
					// 将处理后的物理分页sql重新写入作为执行SQL
					metaBoundSql.setValue("sql", finalSQL);
					
					//获取分页信息:从mybats源码可以看出默认情况mybats会在方法中设为RowBounds.DEFAULT
					RowBounds rowBounds = metaStatementHandler.getRowBounds();
					MetaObject metaRowBounds = MetaObjectUtils.forObject(rowBounds);
					
					// 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
					metaRowBounds.setValue("offset",RowBounds.NO_ROW_OFFSET);
					metaRowBounds.setValue("limit",RowBounds.NO_ROW_LIMIT);
					
					if (LOG.isDebugEnabled()) {
						LOG.debug(" Pagination SQL : "+ statementHandler.getBoundSql().getSql());
					}
				}
			}
			/*解决 MyBatis 物理分页foreach 参数失效 */
	        BoundSQLUtils.setBoundSql(boundSql, boundSql);
			
		}
		
		// 将执行权交给下一个拦截器  
		return invocation.proceed();
	}

	@Override
	protected boolean isRequireIntercept(Invocation invocation, ResultSetHandler resultSetHandler, MetaResultSetHandler metaResultSetHandler) {
		// 利用反射获取到FastResultSetHandler的mappedStatement属性，从而获取到MappedStatement；
		MappedStatement mappedStatement = metaResultSetHandler.getMappedStatement();
		// 利用反射获取到FastResultSetHandler的ParameterHandler属性，从而获取到ParameterObject；
		ParameterHandler parameterHandler = metaResultSetHandler.getParameterHandler();
		//参数对象
		Object parameterObject = parameterHandler.getParameterObject();
		//是否可分页
		boolean pageable = ParameterUtils.isPageable(parameterObject);
		//拦截分页的结果处理
		return (mappedStatement.getId().matches(this.getMethodRegexp()) && pageable) || 
				AnnotationUtils.hasAnnotation(invocation.getMethod(), Pagination.class);
	}
	
	
	@Override
	public Object doResultSetIntercept(Invocation invocation,ResultSetHandler resultSetHandler,MetaResultSetHandler metaResultSetHandler) throws Throwable{
		
		//检查是否需要进行拦截处理
		if (isRequireIntercept(invocation, resultSetHandler, metaResultSetHandler)) {
			
			// 获取到当前的Statement
			Statement stmt = (Statement) invocation.getArgs()[0];
			// 利用反射获取到FastResultSetHandler的ParameterHandler属性，从而获取到ParameterObject；
			ParameterHandler parameterHandler = metaResultSetHandler.getParameterHandler();
			//参数对象
			Object parameterObject = parameterHandler.getParameterObject();
			//获取分页参数对象
			QueryModel queryModel = (QueryModel)ParameterUtils.getQueryModel(parameterObject);
			if(queryModel == null ){
				queryModel = new QueryModel();
			}
			//结果集
			List<Object> resultList = resultSetHandler.handleResultSets(stmt);
			if(!BlankUtils.isBlank(resultList)){
				int total = 0;
				Object rowData = resultList.get(0);
				if(rowData instanceof BaseMap){
					total = Integer.parseInt(((BaseMap)rowData).getTotalResult().toString());
					Object totalresult = ((BaseMap)rowData).get("totalresult");
					if(total == 0 && totalresult != null){
						total = Integer.parseInt(totalresult.toString());
					}
				}else if(rowData instanceof BaseModel){
					total = Integer.parseInt(((BaseModel)rowData).getTotalResult());
				}else{
					//参数为某个实体，该实体拥有queryModel属性或者包含QueryModel类型对象
					MetaObject metaObject = MetaObjectUtils.forObject(rowData);
					if(metaObject.hasGetter("totalresult")){
						total = Integer.parseInt(metaObject.getValue("totalresult").toString());
					}else if(metaObject.hasGetter("TOTALRESULT")){
						total = Integer.parseInt(metaObject.getValue("TOTALRESULT").toString());
					}
				}
				queryModel.setTotalCount(total);
				queryModel.setTotalResult(total);
			}
			//将计算过总记录数和总页数的queryModel对象重新设置回原对象中
			ParameterUtils.setQueryModel(parameterObject,queryModel);
			return resultList;
			
		}
		// 将执行权交给下一个拦截器 
		return invocation.proceed();
	}
	
	@Override
	public void setInterceptProperties(Properties properties) {
		if(BlankUtils.isBlank(getMethodRegexp())){
			setMethodRegexp(DEFAULT_PAGINATION_METHOD_REGEXP);
			LOG.warn("Property methodRegexp is not setted,use default '{0}' !", DEFAULT_PAGINATION_METHOD_REGEXP);
		}
	}
	
	@Override
	public Object plugin(Object target) {
       return Plugin.wrap(target, this);  
	}

}
