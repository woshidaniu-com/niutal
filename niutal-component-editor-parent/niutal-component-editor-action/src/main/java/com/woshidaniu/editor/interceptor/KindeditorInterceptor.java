package com.woshidaniu.editor.interceptor;

 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.util.TextProviderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.factory.SessionFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;
/**
 *@类名称		： KindeditorInterceptor.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：Jun 3, 2016 5:57:01 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@SuppressWarnings("serial")
public class KindeditorInterceptor extends BaseAbstractInterceptor {

	protected Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		LOG.debug("KindeditorInterceptor...");
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		//请求的request对象
		//HttpServletRequest request = ServletActionContext.getRequest();
		//用户的session对象
		HttpSession session = SessionFactory.getSession();
		
		//当前登录账户
		User user     = SessionFactory.getUser();
		//判断是否会话过期或者注销
		if (null == user) {
			result.put("error", 1);
			result.put("message",MessageUtil.getText("W-N000000-01012"));
        	invocation.getStack().set(Result.DATA, result );
			return Result.DATA;
		}
				
		//获取本次登录中前次使用角色和本次登录角色信息，在拦截器中会用于判断角色切换后的权限判断
		String role_pre_key = Parameters.getGlobalString(Parameter.SESSION_ROLE_PRE_KEY);
        String pre_role = StringUtils.getSafeStr(session.getAttribute(role_pre_key));
        String login_role = StringUtils.getSafeStr(session.getAttribute(Parameters.getGlobalString(Parameter.SESSION_ROLE_KEY)));
        //判断本次登录中前次使用角色和本次登录角色是否相同;并设置上次登录角色
        if(!BlankUtils.isBlank(login_role) && !BlankUtils.isBlank(login_role) && !pre_role.equals(login_role)){
        	//重新设置登录用户的登录角色信息，在拦截器中会用于判断角色切换后的权限判断
        	session.setAttribute(role_pre_key, login_role);
        	result.put("error", 1);
			result.put("message",TextProviderHelper.getText("W-N000000-01009", "", new ArrayList<Object>() , invocation.getStack()));
        	invocation.getStack().set(Result.DATA, result );
			return Result.DATA;
        }
        
		//执行下一个拦截器
		return invocation.invoke();
	}

}
