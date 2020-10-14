package com.woshidaniu.globalweb.interceptor;

 

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
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.ZFtalParameter;
import com.woshidaniu.common.ZFtalParameters;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.utils.WebUtils;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;
import com.woshidaniu.util.request.WebRequestUtils;
import com.woshidaniu.web.WebContext;
import com.woshidaniu.web.core.http.HttpStatus;
@Deprecated
@SuppressWarnings("serial")
public class LoginUserChangedInterceptor extends BaseAbstractInterceptor {
	
	protected Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		LOG.debug("LoginUserChangedInterceptor..."); 
		//请求的request对象
		HttpServletRequest request = ServletActionContext.getRequest();
		//获取用户名
		String sessionUserName = WebUtils.getSessionUser(request);
		//检查参数
		if(BlankUtils.isBlank(sessionUserName)){
			return interceptRequired(invocation, "W-N000000-01001", new String[]{ ZFtalParameters.getGlobalString(ZFtalParameter.REQUEST_FUNC_USERKEY) });
		}
		//当前登录账户
		User user     = WebContext.getUser();
		//当前浏览器同一会话被其他用户登录，导致session变化
		if(! (sessionUserName.equals(user.getYhm()) || sessionUserName.equals(user.getYhlybid()))){
			
			//判断是否ajax请求
			boolean isAjaxRequest =   WebRequestUtils.isAjaxRequest(request);
			LOG.debug("isAjaxRequest:"+ isAjaxRequest);
			
			//判断是否ajax请求
			if( isAjaxRequest ){ 
				
				//响应的response对象
				HttpServletResponse response  =  ServletActionContext.getResponse();
				//HTTP Status 903（HTTP 会话用户变更）    -> 当前浏览器同一会话被其他用户登录，导致session变化，服务器可能返回此响应。
				response.setStatus(HttpStatus.SC_SESSION_USER_CHANGE);
				
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
				List<Object> args = new ArrayList<Object>();
				args.add(StringUtils.getSafeStr(user.getYhm(), user.getYhlybid()));
				invocation.getStack().set(Result.MESSAGE, TextProviderHelper.getText("W-N000000-01007", "", args , invocation.getStack()) );
				//invocation.getStack().set(Result.MESSAGE, MessageUtil.getText("W-N000000-01007",new Object[]{StringUtils.getSafeStr(user.getYhm(), user.getYhlybid())}));
				return Result.EX_WARN;
	        }
		}
        
        //执行下一个拦截器
		return invocation.invoke();
	}

}
