package com.woshidaniu.taglibs.views.tags;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.taglibs.views.components.I18nComponent;

/**
 * @see com.woshidaniu.taglibs.views.components.I18nComponent
 */
@SuppressWarnings("serial")
public class I18nTag extends ComponentTagSupport {
	
	// 模块名称：通常指功能模块代码；多个可用,分割
 	protected String modules;
 	// 国际化资源路径表达式
 	protected String expression;
 	// 国际化数据提供者对象名称：用于从Spring上下文获取该对象实例
 	protected String provider;
 	
    public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        return new I18nComponent(stack, request, response);
    }

    protected void populateParams() {
        super.populateParams();
        I18nComponent uibean = (I18nComponent)component;
		uibean.setModules(getModules());
		uibean.setExpression(getExpression());
		uibean.setProvider(getProvider());
    }

	public String getModules() {
		return modules;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public String getProvider() {
		return provider;
	}
	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
    
}
