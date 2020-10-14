package com.woshidaniu.service.common.sqlplugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.ClassUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.datarange.DataRange;
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
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：数据范围拦截器
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月22日下午3:16:39
 * 
 * @deprecated
 * @see com.woshidaniu.service.common.sqlplugin.AnnotationDataRangeInterceptor
 */

@Deprecated
@Intercepts( { 
	@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) ,
})
public class DataRangeInterceptor extends AbstractInterceptorAdapter{

	private static final Logger log = LoggerFactory.getLogger(DataRangeInterceptor.class);
	
	//方法名称到DataRange注解的map
	private Map<String,DataRange> methodNameToDataRangeMap = new HashMap<String,DataRange>();
	
	//处理的历史集合
	private Set<String> processedMethodHistory = new HashSet<String>();
	
	@Override
	protected boolean isRequireIntercept(Invocation invocation,StatementHandler statementHandler,MetaStatementHandler metaStatementHandler) {
		MappedStatement mappedStatement = metaStatementHandler.getMappedStatement();
		SqlCommandType type = mappedStatement.getSqlCommandType();
		
		//查询类型
		boolean isSelect = SqlCommandType.SELECT.equals(type);
		if(!isSelect) {
			return false;
		}
		DataRange dataRange = this.getTargetAnnotation(metaStatementHandler);
		return dataRange != null;
	}
	
	@Override
	public Object doStatementIntercept(Invocation invocation,StatementHandler statementHandler,MetaStatementHandler metaStatementHandler) throws Throwable {
		
		if (isRequireIntercept(invocation, statementHandler, metaStatementHandler)){
			
			BoundSql boundSql = metaStatementHandler.getBoundSql();
			MetaObject metaBoundSql = MetaObjectUtils.forObject(boundSql);
			String orgSql = boundSql.getSql();
			//取Annotation信息
			DataRange dataRange = this.getTargetAnnotation(metaStatementHandler);
			String[] dataIds = dataRange.dataIds();
			String info = dataRange.info();
			QueryType type = dataRange.type();
			JSONObject jsonInfo = JSONObject.fromObject(info);
			
			//通过当前用户，取出角色数据范围
			User user = WebContext.getUser();
			String jsdm = user.getJsdm();
			IJsglDao jsglDao = ServiceFactory.getService(IJsglDao.class);
			List<SjzygzModel> sjzyList = jsglDao.getSjzyList(jsdm,dataIds);//角色规则
			StringBuilder sql = new StringBuilder();
			
			//数据范围SQL解析
			for (SjzygzModel model : sjzyList){
				if (jsonInfo.containsKey(model.getGzid())){//拦截方法所支持的规则
					
					String zdm = jsonInfo.getString(model.getGzid());//字段名
					DataRangeService service = (DataRangeService) ServiceFactory.getService(model.getGztgz());
					List<String> sjfwList = service.getDataRangeList(user);
					StringBuilder format = new StringBuilder();
					
					if (sjfwList == null || sjfwList.size() == 0) continue;
					
					if (sql.length() > 0){
						//如果多条规则同时起效
						sql.append(" or ");
					}
					
					if (QueryType.OR.equals(type)){//or 类型
						for (int i = 0 ,j = sjfwList.size() ; i < j; i++){
							format.append(zdm);
							format.append("='");
							format.append(sjfwList.get(i));
							format.append("'");
							if (i+1 != j){
								format.append(" or ");
							}
						}
					} else { //in 类型
						format.append(zdm);
						format.append(" in (");
						for (int i = 0 ,j = sjfwList.size() ; i < j; i++){
							format.append("'");
							format.append(sjfwList.get(i));
							format.append("'");
							if (i+1 != j){
								format.append(",");
							}
						}
						format.append(")");
					}
					
					sql.append(format.toString());
				}
			}
			if (sql.length() > 0){
				//将最终生成SQL交给mybatis
				String finalSql = orgSql+" and ("+sql.toString()+")";
				metaBoundSql.setValue("sql", finalSql);
			}
		}
		// 将执行权交给下一个拦截器  
		return invocation.proceed();
	}

	protected DataRange getTargetAnnotation(MetaStatementHandler metaStatementHandler) {
		MappedStatement mappedStatement = metaStatementHandler.getMappedStatement();
		String mappedStatementId = mappedStatement.getId();
		int index = mappedStatementId.lastIndexOf(".");
		String className = mappedStatementId.substring(0, index);
		String methodName = mappedStatementId.substring(index + 1);
		return this.doGetTargetAnnotation(className,methodName);
	}
	
	protected String methodKey(String className, String methodName) {
		return className + "."+methodName;
	}
	
	protected DataRange doGetTargetAnnotation(String className, String methodName) {
		String methodKey = methodKey(className, methodName);
		
		boolean processBefore = this.processedMethodHistory.contains(methodKey);
		
		if(processBefore) {//以前处理过
			if(log.isDebugEnabled()) {
				DataRange  processedDataRange = this.methodNameToDataRangeMap.get(methodKey);
				log.debug("process methodKey["+ methodKey +"]already , return dataRagne from methodNameToDataRangeMap is " + processedDataRange);
				return processedDataRange;
			}
		}else {//以前没有处理过，现在处理
			if(log.isDebugEnabled()) {
				log.debug("not process methodKey["+ methodKey +"] , parse class ");
			}
			Method m = this.getSingleMethod(className,methodName);
			this.processedMethodHistory.add(methodKey);
			if(m != null) {
				Annotation a = m.getAnnotation(DataRange.class);
				DataRange dr = null;
				if(a != null && a instanceof DataRange) {
					dr = (DataRange)a;
					this.methodNameToDataRangeMap.put(methodKey, dr);
				}
				if(log.isDebugEnabled()) {
					log.debug("parse form class get DataRange:"+dr);
				}
				return dr;
			}
		}
		return null;
	}
	
	protected Method getSingleMethod(String className,String methodName) {
		
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
