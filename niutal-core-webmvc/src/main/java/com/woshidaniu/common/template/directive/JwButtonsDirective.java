package com.woshidaniu.common.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.woshidaniu.common.log.User;
import com.woshidaniu.entities.AncdModel;
import com.woshidaniu.service.svcinterface.IAncdService;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 *@类名称		： JwButtonsDirective.java
 *@类描述		：Freemarker工具--加载功能按钮(教务专用)
 *@创建人		：kangzhidong
 *@创建时间	：2017年5月24日 上午9:57:00
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class JwButtonsDirective implements TemplateDirectiveModel {

	private IAncdService ancdService;
	
	/**
	 * 
	 *@描述		： 兼容SpringMVC和Struts2的上下文获取Request对象方法
	 *@创建人		: kangzhidong
	 *@创建时间	: Feb 8, 201711:24:03 AM
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public HttpServletRequest getRequest() {
		ServletRequestAttributes requestAttributes = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		if(requestAttributes != null){
			return requestAttributes.getRequest();
		}
		return null;
	}
	
	/**
	 * 取当前会话用户信息
	 * @return User
	 */
	public User getUser() {
		HttpSession session = getRequest().getSession();
		return (User) session.getAttribute(Parameters.getGlobalString(Parameter.SESSION_USER_KEY));
	}

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		
		TemplateModel paramValue = (TemplateModel) params.get("gnmkdm");
		String gnmkdm = ((SimpleScalar)paramValue).getAsString();
		
		User user = getUser();
		List<AncdModel> ancdList = ancdService.cxButtonsList(user, gnmkdm);
		
		TemplateModel template = new BeansWrapperBuilder(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).build().wrap(ancdList);
		
		env.setVariable("ancdList",template);
		
		if (body != null) {  
            body.render(env.getOut());  
        } 
	}

	public IAncdService getAncdService() {
		return ancdService;
	}

	public void setAncdService(IAncdService ancdService) {
		this.ancdService = ancdService;
	}

}
