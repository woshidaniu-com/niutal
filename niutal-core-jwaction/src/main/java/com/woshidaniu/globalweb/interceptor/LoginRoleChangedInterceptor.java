package com.woshidaniu.globalweb.interceptor;

 

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.TextProviderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.factory.SessionFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.utils.WebUtils;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;
import com.woshidaniu.util.request.WebRequestUtils;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;
import com.woshidaniu.web.WebContext;
import com.woshidaniu.web.core.http.HttpStatus;
/**
 * 
 *@类名称:LoginRoleChangedInterceptor.java
 *@类描述：用于过滤客户端请求判断是否拥有访问权限的过滤器链之【<b>角色切换控制</b>】；检查同浏览器不同角色切换后权限变化问题</br>
 *@创建人：kangzhidong
 *@创建时间：Oct 20, 2015 9:58:10 AM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@Deprecated
@SuppressWarnings("serial")
public class LoginRoleChangedInterceptor extends BaseAbstractInterceptor {

	protected Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		LOG.debug("LoginRoleChangedInterceptor..."); 
		//请求的request对象
		HttpServletRequest request = ServletActionContext.getRequest();
		//用户的session对象
		HttpSession session = SessionFactory.getSession();
		//获取本次登录中前次使用角色和本次登录角色信息，在拦截器中会用于判断角色切换后的权限判断
		String role_pre_key = Parameters.getGlobalString(Parameter.SESSION_ROLE_PRE_KEY);
        String pre_role = StringUtils.getSafeStr(session.getAttribute(role_pre_key));
        String login_role = StringUtils.getSafeStr(session.getAttribute(Parameters.getGlobalString(Parameter.SESSION_ROLE_KEY)));
        //判断本次登录中前次使用角色和本次登录角色是否相同;并设置上次登录角色
        if(!BlankUtils.isBlank(login_role) && !BlankUtils.isBlank(login_role) && !pre_role.equals(login_role)){
        	//重新设置登录用户的登录角色信息，在拦截器中会用于判断角色切换后的权限判断
        	session.setAttribute(role_pre_key, login_role);
        	
        	//获取功能模块代码
    		String gnmkdm = WebUtils.getFuncCode(request);
    		//参数检查
    		if(BlankUtils.isBlank(gnmkdm)){
    			return interceptRequired(invocation,"W-N000000-01001",new String[]{ "gnmkdm" });
    		}
    		
    		//当前登录账户
    		User user     = WebContext.getUser();
    		//当前用户没有任何功能权限
    		if(BlankUtils.isBlank(user.getJsgnmkdmList())){
    			return interceptRequired(invocation, "W-N000000-01008", new String[]{StringUtils.getSafeStr(user.getYhm(), user.getYhlybid())});
    		}

    		//表示切换后的角色无当前功能页面的权限
    		if(!"index".equalsIgnoreCase(gnmkdm) && !(user.getJsgnmkdmList().contains(gnmkdm))){
    			
    			//判断是否ajax请求
    			boolean isAjaxRequest =   WebRequestUtils.isAjaxRequest(request);
    			LOG.debug("isAjaxRequest:"+ isAjaxRequest);
    			
    			 //判断是否ajax请求
    			if( isAjaxRequest ){ 
    				
    				//响应的response对象
    				HttpServletResponse response  =  ServletActionContext.getResponse();
    				//HTTP Status 902（未授权） ->请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。
            		response.setStatus(HttpStatus.SC_NON_ACCESS);
            		
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
    	        	invocation.getStack().set(Result.MESSAGE, TextProviderHelper.getText("W-N000000-01009", "", new ArrayList<Object>() , invocation.getStack()));
    	        	//invocation.getStack().set(Result.MESSAGE, MessageUtil.getText("W-N000000-01009"));
    				return Result.EX_WARN;
    	        }
    		}
        }
        
		//执行下一个拦截器
		return invocation.invoke();
	}

}
