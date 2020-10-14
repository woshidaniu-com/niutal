package com.woshidaniu.service.common.plugin;

import java.util.Properties;

import javax.annotation.Resource;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.orm.mybatis.interceptor.AbstractInterceptorAdapter;
import com.woshidaniu.orm.mybatis.interceptor.meta.MetaExecutor;
import com.woshidaniu.service.common.aspect.CommonLogAspect;

@Intercepts({ 
	@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
	@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })
})
public class LOGAspectInterceptor extends AbstractInterceptorAdapter {
	
	//日志拦截切面处理对象
	@Resource(name = "commonLogAspect" )
	protected CommonLogAspect logAspect = null;
	
	@Override
	public Object doExecutorIntercept(Invocation invocation,Executor executorProxy, MetaExecutor metaExecutor) throws Throwable {
		try {
			// 将执行权交给下一个拦截器  
			return invocation.proceed();
		} finally{
			MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
			Object parameterObject = (Object) invocation.getArgs()[1];
			BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
			//获得Service接口
			if(BlankUtils.isBlank(logAspect)){
				Object tmp = ServiceFactory.getService("commonLogAspect");
				if(!BlankUtils.isBlank(tmp)){
					logAspect = (CommonLogAspect)tmp;
				}
			}
    		if(!BlankUtils.isBlank(logAspect)){
    			//调用SQL日志记录：记录与否在方法中进行判断
    			logAspect.doAfterStatement(mappedStatement, boundSql);
    		}
		}
	}

	@Override
	public void setInterceptProperties(Properties properties) {
		
	}
	
	@Override
	public Object plugin(Object target) {
		//这里因为只拦截执行结果，所有仅对Executor代理对象进行包装
		if (target instanceof Executor) {  
            return Plugin.wrap(target, this);  
        } else {  
            return target;  
        }
	}

	public CommonLogAspect getLogAspect() {
		return logAspect;
	}

	public void setLogAspect(CommonLogAspect logAspect) {
		this.logAspect = logAspect;
	}
	
}
