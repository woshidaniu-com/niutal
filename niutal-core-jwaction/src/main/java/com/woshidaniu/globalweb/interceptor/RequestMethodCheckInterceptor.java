package com.woshidaniu.globalweb.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.TextProviderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;
import com.woshidaniu.util.request.WebRequestUtils;
import com.woshidaniu.web.core.http.HttpStatus;

/**
 * 
 *@类名称:RequestMethodCheckInterceptor.java
 *@类描述：请求方法检查拦截器：检查http请求对应的Action对象和方法是否存在
 *@创建人：kangzhidong
 *@创建时间：Oct 30, 2015 3:48:35 PM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class RequestMethodCheckInterceptor extends BaseAbstractInterceptor {

	protected Logger LOG	= LoggerFactory.getLogger(getClass());
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		LOG.debug("RequestMethodInterceptor..."); 
		// 获取request请求对象
		HttpServletRequest request = ServletActionContext.getRequest();
		//响应的response对象
		HttpServletResponse response  =  ServletActionContext.getResponse();
		// 获取doType参数
		String doType = request.getParameter("doType");
		LOG.debug("doType:"+ doType);
		
		//判断是否ajax请求
		boolean isAjaxRequest =   WebRequestUtils.isAjaxRequest(request);
		LOG.debug("isAjaxRequest:"+ isAjaxRequest);
		
		//处理请求的Action对象类型
		Object actionObject = invocation.getProxy().getAction();
		//Action不存在
		if(BlankUtils.isBlank(actionObject)){
			
			// 判断是否ajax请求
			if( isAjaxRequest ){ 
				//HTTP Status 909（HTTP Action未定义）    -> 处理请求的Action对象未定义，则服务器可能返回此响应。
				response.setStatus(HttpStatus.SC_ACTION_UNDEFINED);
				
				if(doType!=null && doType.equalsIgnoreCase("query")){
        			model.getQueryModel().setItems(empty);
        			invocation.getStack().set(Result.DATA, model.getQueryModel());
        		}else{
        			invocation.getStack().set(Result.DATA, model);
        		}
				return Result.DATA;
			}else{
				List<Object> args = new ArrayList<Object>();
				args.add(invocation.getProxy().getActionName());
				invocation.getStack().set(Result.MESSAGE, TextProviderHelper.getText("W-N000000-01010", "", args , invocation.getStack()));
				//invocation.getStack().set(Result.MESSAGE, MessageUtil.getText("W-N000000-01010",new Object[]{invocation.getProxy().getActionName()}));
				return Result.EX_WARN;
			}
		}
		//action 将要执行的方法名称
		String methodName = invocation.getProxy().getMethod();
		//action 将要执行的方法对象
		Method method = ReflectionUtils.getMethod(actionObject.getClass(), methodName);
		//方法不存在
		if(BlankUtils.isBlank(method)){
			
			// 判断是否ajax请求
			if( isAjaxRequest ){ 
				
				//HTTP Status 910（HTTP 方法未定义）    -> 请求的后台方法未定义，则服务器可能返回此响应。
				response.setStatus(HttpStatus.SC_METHOD_UNDEFINED);
				
				if(doType!=null && doType.equalsIgnoreCase("query")){
        			model.getQueryModel().setItems(empty);
        			invocation.getStack().set(Result.DATA, model.getQueryModel());
        		}else{
        			invocation.getStack().set(Result.DATA, model);
        		}
				return Result.DATA;
			}else{
				List<Object> args = new ArrayList<Object>();
				args.add(methodName);
				args.add(actionObject.getClass().getSimpleName());
				invocation.getStack().set(Result.MESSAGE, TextProviderHelper.getText("W-N000000-01011", "", args , invocation.getStack()));
				//invocation.getStack().set(Result.MESSAGE, MessageUtil.getText("W-N000000-01011",new Object[]{methodName,actionObject.getClass().getSimpleName()}));
				return Result.EX_WARN;
			}
		}
		//执行下一个拦截器
		return invocation.invoke();
		 
	}
 
}
