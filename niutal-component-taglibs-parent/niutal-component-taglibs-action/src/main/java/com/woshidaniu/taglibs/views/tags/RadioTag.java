package com.woshidaniu.taglibs.views.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.taglibs.views.components.RadioComponent;

@SuppressWarnings("serial")
public class RadioTag extends AbstractDatasetTag {

	// 原js文件url,相对于web根目录的路径
	protected String fileURL;
	// 原js文件是否是v5样式
	protected String vstyle;
	// 指定进行国际化的key
	protected String i18nKeys;
	/**
	 * 返回该Tag中的UIBean
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		return new RadioComponent(stack, request, response);
	}
	
	/**
	 * populateParams()初始化参数，一般用来初始化UIBean(Component)。
	 */
	@Override
	protected void populateParams() {
		super.populateParams();
		RadioComponent uibean = (RadioComponent)component;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}
	
	public String getVstyle() {
		return vstyle;
	}

	public void setVstyle(String vstyle) {
		this.vstyle = vstyle;
	}

	public String getI18nKeys() {
		return i18nKeys;
	}

	public void setI18nKeys(String i18nKeys) {
		this.i18nKeys = i18nKeys;
	}
 
}
