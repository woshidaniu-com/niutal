package com.woshidaniu.taglibs.views.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.plus.components.AbstractStrutsUIBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.util.base.MessageUtil;

public abstract class AbstractComplexStrutsUIBean extends AbstractStrutsUIBean  {

	protected Logger LOG = LoggerFactory.getLogger(AbstractComplexStrutsUIBean.class);
	protected ApplicationContext applicationContext = null;
	
	public AbstractComplexStrutsUIBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
		this.applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
	}
	
	/**
	 * setDefaultUITheme()方法默认是将配置文件中 struts.ui.theme 对应的值作为默认值；这里我们重写该方法使其始终为指定的字符，
	 * 在调用getTheme()如果标签未设置theme属性，该指定值将会作为默认值
	 * @see org.apache.struts2.components.UIBean#getTheme()
	 */
	@Override
	public void setDefaultUITheme(String theme) {
        this.defaultUITheme = "taglibs";
    }
	
	/**
	 * 覆写evaluateExtraParams（）方法，在UIBean初始化后会调用这个方法来初始化设定参数，
	 * 如addParameter方法，会在freemarker里的parameters里加入一个key value。
	 * 这里要注意findString，还有相关的findxxxx方法，它们是已经封装好了的解释ognl语法的工具，具体可看一下UIBean的api doc
	 */
	@Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();
		this.addParameter("jsVersion", MessageUtil.getText("niutal.jsVersion"));
		this.addParameter("cssVersion", MessageUtil.getText("niutal.cssVersion"));
    }
	
}
