package com.woshidaniu.designer.views.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.plus.views.jsp.ui.AbstractStrutsUITag;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.designer.views.components.FuncResources;

@SuppressWarnings("serial")
public class FuncResourcesTag extends AbstractStrutsUITag {
	
	/**
	 * 返回该Tag中的UIBean
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		return new FuncResources(stack, request, response);
	}
	
	/**
	 * populateParams()初始化参数，一般用来初始化UIBean(Component)。
	 */
	@Override
	protected void populateParams() {
		super.populateParams();
	}

	
}
