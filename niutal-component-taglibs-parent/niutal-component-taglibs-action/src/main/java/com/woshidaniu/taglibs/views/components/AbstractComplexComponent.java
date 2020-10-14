package com.woshidaniu.taglibs.views.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.util.ValueStack;

public abstract class AbstractComplexComponent extends Component  {

	protected Logger LOG = LoggerFactory.getLogger(AbstractComplexComponent.class);
	protected ApplicationContext applicationContext = null;
	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	
	public AbstractComplexComponent(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack);
		this.request = request;
		this.response = response;
		this.applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
	}
	
}
