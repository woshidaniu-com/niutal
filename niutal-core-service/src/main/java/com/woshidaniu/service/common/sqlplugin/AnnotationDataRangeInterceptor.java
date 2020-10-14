/**
 * 
 */
package com.woshidaniu.service.common.sqlplugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.datarange.DataRange;
import com.woshidaniu.common.datarange.DataRangeItem;
import com.woshidaniu.orm.mybatis.interceptor.meta.MetaStatementHandler;

/**
 * @author zhidong kang
 * @desc 基于方法注解的数据范围拦截器
 * 
 * @author zhidong
 * @desc 优化缓存DataRange,最多只找一次Method
 *
 */
@Intercepts( { 
	@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) ,
})
public class AnnotationDataRangeInterceptor extends AbstractDataRangeInterceptor{

	private static final Logger log = LoggerFactory.getLogger(AnnotationDataRangeInterceptor.class);
	//已经处理过的MappedStatement和DataRange
	private Map<MappedStatement,DataRange> processedMappedStatementToDateRagneMapping = new HashMap<MappedStatement,DataRange>();

	@Override
	protected synchronized DataRangeItem obtainDataRangeItem(MetaStatementHandler metaStatementHandler) {
		
		MappedStatement mappedStatement = metaStatementHandler.getMappedStatement();
		
		if(this.processedMappedStatementToDateRagneMapping.containsKey(mappedStatement)) {
			DataRange dataRange = this.processedMappedStatementToDateRagneMapping.get(mappedStatement);
			if(dataRange != null) {
				DataRangeItem item = new DataRangeItem();
				item.setDataIds(dataRange.dataIds());
				item.setInfo(dataRange.info());
				item.setType(dataRange.type());
				return item;
			}else {
				return null;
			}
		}else {
			DataRange dataRange = getTargetAnnotation(mappedStatement);
			this.processedMappedStatementToDateRagneMapping.put(mappedStatement,dataRange);
			if(dataRange == null){
				return null;
			}else {
				DataRangeItem item = new DataRangeItem();
				item.setDataIds(dataRange.dataIds());
				item.setInfo(dataRange.info());
				item.setType(dataRange.type());
				return item;
			}
		}
	}

	@Override
	protected String handleBoundSql(BoundSql boundSql, String generateDataRangeSql)  {
		return boundSql.getSql() + " and (" + generateDataRangeSql + ")";
	}
	
	//======================================private methods=====================================//
	private DataRange getTargetAnnotation(MappedStatement mappedStatement) {
		
		String mappedStatementId = mappedStatement.getId();
		int index = mappedStatementId.lastIndexOf(".");
		String className = mappedStatementId.substring(0, index);
		String methodName = mappedStatementId.substring(index + 1);
		return this.doGetTargetAnnotation(className,methodName);
	}
	
	private DataRange doGetTargetAnnotation(String className, String methodName) {
		Method m = this.getSingleMethod(className,methodName);
		
		if(m != null) {
			Annotation a = m.getAnnotation(DataRange.class);
			DataRange dr = null;
			if(a != null && a instanceof DataRange) {
				dr = (DataRange)a;
			}
			return dr;
		}else {
			return null;
		}
	}
	
	private Method getSingleMethod(String className,String methodName) {
		
		try {
			Class<?> clazz = ClassUtils.getClass(className);
			while(clazz != null) {
				Method[] methods = clazz.getDeclaredMethods();
				for(Method m:methods) {
					String name = m.getName();
					if(methodName.equals(name)) {
						return m;
					}
				}	
				clazz = clazz.getSuperclass();
			}
			return null;
		}catch (Exception e) {
			log.error("解析类["+className+"]的方法["+ methodName +"]异常",e);
			return null;
		}
	}
	//======================================private methods=====================================//

	@Override
	protected void afterInvocationProcess(Invocation invocation) {
	}

	@Override
	protected void beforeInvocationProcess(Invocation invocation) {
	}

}
