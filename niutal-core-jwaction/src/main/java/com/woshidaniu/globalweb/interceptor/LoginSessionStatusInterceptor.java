package com.woshidaniu.globalweb.interceptor;

 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.log.User;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;
import com.woshidaniu.util.request.WebRequestUtils;
import com.woshidaniu.web.WebContext;
import com.woshidaniu.web.core.http.HttpStatus;
/**
 * 
 *@类名称:LoginSessionStatusInterceptor.java
 *@类描述：登录会话状态拦截器：检测会话中User对象是否为空
 *@创建人：kangzhidong
 *@创建时间：Oct 20, 2015 10:53:00 AM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@Deprecated
@SuppressWarnings("serial")
public class LoginSessionStatusInterceptor extends BaseAbstractInterceptor {
	
	protected Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		LOG.debug("LoginSessionStatusInterceptor..."); 
		//请求的request对象
		HttpServletRequest request = ServletActionContext.getRequest();
		//当前登录账户
		User user     = WebContext.getUser();
		//判断是否会话过期或者注销
		if (null == user) {

			//判断是否ajax请求
			boolean isAjaxRequest =   WebRequestUtils.isAjaxRequest(request);
			LOG.debug("isAjaxRequest:"+ isAjaxRequest);
			
			//判断是否ajax请求
			if( isAjaxRequest ){
				
				//响应的response对象
				HttpServletResponse response  =  ServletActionContext.getResponse();
				//表示会话过期或者注销【session timeout 】
				response.setStatus(HttpStatus.SC_SESSION_TIMEOUT);
				
				// 获取doType参数
				String doType = request.getParameter("doType");
				LOG.debug("doType:"+ doType);
				
				if(doType!=null && doType.equalsIgnoreCase("query")){
        			model.getQueryModel().setItems(empty);
        			invocation.getStack().set(Result.DATA, model.getQueryModel());
        		}else{
        			invocation.getStack().set(Result.DATA, model);
        		}
				return Result.DATA;
				
	        }else{  
	            //不是ajax请求，则直接跳转页面
        		String requestPath   = ServletActionContext.getRequest().getServletPath();
    		    if(!requestPath.startsWith("/xtgl/index_sessionOut.html")){
    		    	invocation.getStack().set("tzurl", "xtgl/index_sessionOut.html");
    		        return Result.EX_SESSION_OUT;
    		    }else{
    		        return Action.LOGIN;
    		    }
	        }
		}
		//执行下一个拦截器
		return invocation.invoke();
	}

}
