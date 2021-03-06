package com.woshidaniu.taglibs.views.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.plus.views.jsp.ui.AbstractStrutsUITag;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.taglibs.views.components.NavtabsComponent;

@SuppressWarnings("serial")
public class NavtabsTag extends AbstractStrutsUITag {

	/**
	 * 返回该Tag中的UIBean
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		return new NavtabsComponent(stack, request, response);
	}
 
}
