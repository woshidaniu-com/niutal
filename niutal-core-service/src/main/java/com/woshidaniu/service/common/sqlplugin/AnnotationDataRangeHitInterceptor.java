/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.sqlplugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang3.StringUtils;
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

import com.woshidaniu.common.datarange.DataRangeHit;
import com.woshidaniu.common.datarange.DataRangeHitPair;
import com.woshidaniu.common.datarange.DataRangeService;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.daointerface.IJsglDao;
import com.woshidaniu.dao.entities.SjzygzModel;
import com.woshidaniu.orm.mybatis.interceptor.AbstractInterceptorAdapter;
import com.woshidaniu.orm.mybatis.interceptor.meta.MetaStatementHandler;
import com.woshidaniu.orm.mybatis.utils.MetaObjectUtils;
import com.woshidaniu.service.common.sqlExpr.ConditionSqlExprBuilder;
import com.woshidaniu.service.common.sqlExpr.DataRangeHitSqlBuilder;
import com.woshidaniu.service.common.sqlExpr.WhereInSqlExprBuilder;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.WebContext;

/**
 * @author 	康康（1571）
 * 
 * DataRangeHit注解的数据范围拦截器
 * 
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }), })
public class AnnotationDataRangeHitInterceptor extends AbstractInterceptorAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(AnnotationDataRangeHitInterceptor.class);
	//是否显示权限标签提示
	private boolean showTag = false;
	{
		String val = MessageUtil.getText("niutal.dataRangeHit.showTag");
		this.showTag = StringUtils.isNotEmpty(val) ? Boolean.parseBoolean(val) : this.showTag;
	}
	//已经处理过的MappedStatement和DataRange
	//TODO 待优化点,记录处理过的Method
	//private Map<MappedStatement,DataRangeHit> processedMappedStatementToDateRagneMapping = new HashMap<MappedStatement,DataRangeHit>();
	
	@Override
	public Object doStatementIntercept(Invocation invocation,StatementHandler statementHandler,MetaStatementHandler metaStatementHandler) throws Throwable {
		
		MappedStatement mappedStatement = metaStatementHandler.getMappedStatement();
		SqlCommandType type = mappedStatement.getSqlCommandType();
		// 查询类型
		boolean isSelect = SqlCommandType.SELECT.equals(type);
		if (!isSelect) {
			return invocation.proceed();
		}else {
			BoundSql boundSql = statementHandler.getBoundSql();
			MetaObject metaBoundSql = MetaObjectUtils.forObject(boundSql);
			String sql = boundSql.getSql();
			//取Annotation信息
			Method method = this.doGetTargetMethod(mappedStatement);
			DataRangeHit dataRangeHit = this.doGetTargetAnnotation(method);
			if(dataRangeHit != null) {
				this.check(dataRangeHit,method);
				this.maybeDoAppendCondition(dataRangeHit,sql,metaBoundSql);
			}
			// 将执行权交给下一个拦截器  
			return invocation.proceed();
		}
	}
	
	private void maybeDoAppendCondition(DataRangeHit dataRangeHit,String sql, MetaObject metaBoundSql) {
		
		DataRangeHitPair[] pairs = dataRangeHit.hits();
		
		//最多也就2个注解算是比较多的了!!!
		List<ConditionSqlExprBuilder> conditionSqlExprBuilders = new ArrayList<ConditionSqlExprBuilder>(2);
		for (DataRangeHitPair pair: pairs) {
			ConditionSqlExprBuilder builder = this.createSqlExprBuilder(pair,sql,metaBoundSql);
			if (builder != null) {
				conditionSqlExprBuilders.add(builder);
			}
		}
		if(conditionSqlExprBuilders.isEmpty()) {
			return;
		}
		DataRangeHitSqlBuilder builder = new DataRangeHitSqlBuilder(sql, conditionSqlExprBuilders,this.showTag);
		String newSql = builder.buildSql();
		metaBoundSql.setValue("sql", newSql);
	}
	//检查注解有效性
	private void check(DataRangeHit dataRangeHit,Method method) {
		DataRangeHitPair[] pairs = dataRangeHit.hits();
		for(DataRangeHitPair pair: pairs) {
			
			String gzid = pair.gzid();
			String zyid = pair.zyId();
			String table = pair.table();
			String column = pair.column();
			
			if(StringUtils.isBlank(gzid) || StringUtils.isBlank(zyid) || StringUtils.isBlank(table) ||StringUtils.isBlank(column)) {
				String name = method.getName();
				throw new IllegalArgumentException("方法"+ name +"上的DataRangeHit注解中DataRangePair中的gzid,zyid,table,column都不能为空");
			}
		}
	}

	private ConditionSqlExprBuilder createSqlExprBuilder(DataRangeHitPair pair, String sql, MetaObject metaBoundSql) {

		// 通过当前用户，取出角色数据范围
		String gzid = pair.gzid();
		String zyid = pair.zyId();
		String table = pair.table();
		String column = pair.column();
		
		User user = WebContext.getUser();
		String jsdm = user.getJsdm();
		
		IJsglDao jsglDao = ServiceFactory.getService(IJsglDao.class);
		List<SjzygzModel> sjzyList = jsglDao.getSjzyList(jsdm, new String[] {zyid});// 角色规则
		if(sjzyList.isEmpty()) {
			return null;
		}else {
			// 数据范围SQL解析
			for (SjzygzModel model : sjzyList) {
				String gzid_database = model.getGzid();
				if (gzid.equals(gzid_database)) {// 拦截方法所支持的规则
					
					DataRangeService service = (DataRangeService) ServiceFactory.getService(model.getGztgz());
					List<String> sjfwList = service.getDataRangeList(user);
					if(sjfwList.isEmpty()) {
						return null;
					}else {
						String[] values = new String[sjfwList.size()];
						values = sjfwList.toArray(values);
						ConditionSqlExprBuilder conditionSqlExprBuilder = new WhereInSqlExprBuilder(table,column, values, false);
						return conditionSqlExprBuilder;
					}
				}
			}
			return null;
		}
	}
	
	private Method doGetTargetMethod(MappedStatement mappedStatement) {
		String mappedStatementId = mappedStatement.getId();
		int index = mappedStatementId.lastIndexOf(".");
		String className = mappedStatementId.substring(0, index);
		String methodName = mappedStatementId.substring(index + 1);
		Method resultMethod = this.getSingleMethod(className, methodName);
		return resultMethod;
	}
	
	private DataRangeHit doGetTargetAnnotation(Method method) {
		if(method != null) {
			Annotation a = method.getAnnotation(DataRangeHit.class);
			if(a != null && a instanceof DataRangeHit) {
				DataRangeHit dataRangeHit = (DataRangeHit)a;
				return dataRangeHit;
			}else {
				return null;
			}
		}else {
			return null;
		}
	}
	
	private Method getSingleMethod(String className,String methodName) {
		
		try {
			Class<?> clazz = ClassUtils.getClass(className);
			while(clazz != null) {
				Method[] methods = clazz.getMethods();
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
			log.error("根据类名["+ className +"]获得方法["+ methodName +"]异常",e);
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
