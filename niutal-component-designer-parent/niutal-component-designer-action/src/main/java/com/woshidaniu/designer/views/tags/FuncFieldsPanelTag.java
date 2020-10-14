package com.woshidaniu.designer.views.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.plus.views.jsp.ui.AbstractStrutsUITag;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.designer.views.components.FuncFieldsPanel;
import com.woshidaniu.util.base.StringUtil;

@SuppressWarnings("serial")
public class FuncFieldsPanelTag extends AbstractStrutsUITag {

	// 功能类型 1:增加;2:修改;3:查看
	protected String func_type;
	// 显示模式   1：横排页签 ，2：竖排伸缩
	protected String display_mode;
	// 对象值栈取值key
	protected String stackKey;
	// 数据提供者对象名称：用于从Spring上下文获取该对象实例
 	protected String provider;
 	
	/**
	 * 返回该Tag中的UIBean
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		return new FuncFieldsPanel(stack, request, response);
	}
	
	/**
	 * populateParams()初始化参数，一般用来初始化UIBean(Component)。
	 */
	@Override
	protected void populateParams() {
		super.populateParams();
		FuncFieldsPanel uibean = (FuncFieldsPanel)component;
		uibean.setFunc_type(getFunc_type());
		uibean.setDisplay_mode(StringUtil.getSafeStr(getDisplay_mode(), "1"));
		uibean.setStackKey(getStackKey());
		uibean.setProvider(getProvider());
	}

	/**
	 * @return the func_type
	 */
	public String getFunc_type() {
		return func_type;
	}

	/**
	 * @param funcType the func_type to set
	 */
	public void setFunc_type(String funcType) {
		func_type = funcType;
	}

	/**
	 * @return the display_mode
	 */
	public String getDisplay_mode() {
		return display_mode;
	}

	/**
	 * @param displayMode the display_mode to set
	 */
	public void setDisplay_mode(String displayMode) {
		display_mode = displayMode;
	}

	/**
	 * @return the stackKey
	 */
	public String getStackKey() {
		return stackKey;
	}

	/**
	 * @param stackKey the stackKey to set
	 */
	public void setStackKey(String stackKey) {
		this.stackKey = stackKey;
	}
	
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public String getProvider() {
		return provider;
	}
	
}
