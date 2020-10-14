/**
 * 
 */
package com.woshidaniu.globalweb.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：拦截strus请求：
 *   	  http请求只能执行标注特定注解的方法
 *   class com.woshidaniu.globalweb.interceptor.ActionMethodInvokeInterceptor
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月9日下午5:35:41
 */
public class ActionMethodInvokeInterceptor extends MethodFilterInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5926004894375841782L;
	
	private static final Logger log = LoggerFactory.getLogger(ActionMethodInvokeInterceptor.class);

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.MethodFilterInterceptor#doIntercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		Object actionObject = invocation.getAction();
		String methodName = invocation.getProxy().getMethod();
		if(log.isDebugEnabled()){
			log.debug("ActionMethodInvokeInterceptor intercept Action: {}, Method: {}.", new Object[]{actionObject,methodName});
		}
		Method method = actionObject.getClass().getMethod(methodName, null);
		HttpServletRequest request = ServletActionContext.getRequest();
		String httpMethod = request.getMethod().toLowerCase();
		Annotation[] annotations = method.getAnnotations();
		boolean allowed = false;
		boolean exsit = false;
		//如果没有注解，默认表示全部方法都可以调用
		if(annotations == null || annotations.length == 0){
			return invocation.invoke();
		}else{
			for (Annotation annotation : annotations) {
				if(annotation instanceof POST){
					exsit = true;
					POST postAnnotation = (POST)annotation;
					if(postAnnotation.value().equals(httpMethod)){
						allowed = true;
						break;
					}
				}
				if(annotation instanceof GET){
					exsit = true;
					GET getAnnotation = (GET)annotation;
					if(getAnnotation.value().equals(httpMethod)){
						allowed = true;
						break;
					}
				}
			}
		}
		
		if(allowed || (!exsit)){
			//表示有相关注解，并且匹配,或者没有相关注解
			return invocation.invoke();
		}else{
			//表示存在相关注解，但是不匹配
			return "error";
		}
	}

}
