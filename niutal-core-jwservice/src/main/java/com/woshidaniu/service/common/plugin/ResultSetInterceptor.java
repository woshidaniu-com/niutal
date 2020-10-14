/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.plugin;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.query.ModelBase;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.beanutils.reflection.ReflectionUtils;

/**
 *@类名称:MapInterceptor.java
 *@类描述：处理map类型结果，转换大写key为小写
 *@创建人：kangzhidong
 *@创建时间：2014-8-27 上午11:43:44
 *@版本号:v1.0
 */
@Intercepts(@Signature(method = "handleResultSets", type = ResultSetHandler.class, args = { Statement.class }))
public class ResultSetInterceptor implements Interceptor {
	
	protected static Logger LOG = LoggerFactory.getLogger(ResultSetInterceptor.class);
	protected static String pageSqlId = "";  //分页Id,mapper.xml中需要拦截的ID(正则匹配)
	
	@SuppressWarnings("rawtypes")
	public Object intercept(Invocation invocation) throws Throwable {
		// 通过invocation获取代理的目标对象
		Object target = invocation.getTarget();
		// 暂时ResultSetHandler只有FastResultSetHandler这一种实现
		if (target instanceof DefaultResultSetHandler) {
			//获取ResultSetHandler的实现
			DefaultResultSetHandler resultSetHandler = (DefaultResultSetHandler) target;
			// 利用反射获取到FastResultSetHandler的mappedStatement属性，从而获取到MappedStatement；
			MappedStatement mappedStatement = (MappedStatement) ReflectionUtils.getField("mappedStatement",resultSetHandler);
			// 获取到当前的Statement
			Statement stmt = (Statement) invocation.getArgs()[0];
			// 利用反射获取到FastResultSetHandler的ParameterHandler属性，从而获取到ParameterObject；
			ParameterHandler parameterHandler = (ParameterHandler) ReflectionUtils.getField("parameterHandler",resultSetHandler);
			Object parameterObject = parameterHandler.getParameterObject();
			List<Object> list = resultSetHandler.handleResultSets(stmt);
			//拦截分页的结果处理
			if(mappedStatement.getId().matches(pageSqlId)){ 
				boolean pageable = true;
				QueryModel pageModel = null;
				if(parameterObject instanceof QueryModel){	//参数就是Page实体
					pageModel = (QueryModel) parameterObject;
					pageModel.setEntityOrField(true);	 //
					//pageModel.setTotalResult(count);
				}else{	
					//是否分页的标记
					Field pageableField = ReflectionUtils.getAccessibleField(parameterObject,"pageable");
					if(pageableField!=null){
						pageable = pageableField.getBoolean(parameterObject);
					}else{
						LOG.error(parameterObject.getClass().getName()+"不存在 pageable 属性！");
					}
					if(pageable != false){
						//参数为某个实体，该实体拥有Page属性
						Field pageField = ReflectionUtils.getAccessibleField(parameterObject,"queryModel");
						if(pageField!=null){
							pageModel = (QueryModel) ReflectionUtils.getField("queryModel",parameterObject);
							if(pageModel==null){
								pageModel = new QueryModel();
							}
							pageModel.setEntityOrField(false); //见com.flf.entity.Page.entityOrField 注释
							Field field = ReflectionUtils.getAccessibleField(parameterObject,"queryModel");
							field.set(parameterObject, pageModel);
						}else{
							LOG.error(parameterObject.getClass().getName()+"不存在 queryModel 属性！");
						}
					}
				}
				if(!BlankUtils.isBlank(list) && pageable != false){
					int total = 0;
					/*for (Object row : list) {
						if(row instanceof BaseMap){
							total = Math.max(Integer.parseInt(((BaseMap)row).get("totalresult").toString()), total);
						}else if(row instanceof Map){
							total = Math.max(Integer.parseInt(((Map)row).get("TOTALRESULT").toString()), total);
						}else if(row instanceof ModelBase){// row.getClass().isAssignableFrom(ModelBase.class)
							total = Math.max(Integer.parseInt(((ModelBase)row).getTotalResult()), total);
						}
					}*/
					Object row = list.get(0);
					if(row instanceof BaseMap){
						total = Integer.parseInt(((BaseMap)row).get("totalresult").toString());
					}else if(row instanceof Map){
						total = Integer.parseInt(((Map)row).get("TOTALRESULT").toString());
					}else if(row instanceof ModelBase){// row.getClass().isAssignableFrom(ModelBase.class)
						total = Integer.parseInt(((ModelBase)row).getTotalResult());
					}
					pageModel.setTotalResult(total);
				}
			}
			return list;
		}
		// 如果没有进行拦截处理，则执行默认逻辑
		return invocation.proceed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	public Object plugin(Object obj) {
		return Plugin.wrap(obj, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	public void setProperties(Properties props) {
		pageSqlId = props.getProperty("pageSqlId");
		if (StringUtils.isEmpty(pageSqlId)) {
			pageSqlId = ".*getPaged*.*";
		}
	}

}