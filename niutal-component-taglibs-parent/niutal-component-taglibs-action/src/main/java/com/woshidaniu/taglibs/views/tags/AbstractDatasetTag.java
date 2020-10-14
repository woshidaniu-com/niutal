package com.woshidaniu.taglibs.views.tags;

import org.apache.struts2.plus.views.jsp.ui.AbstractStrutsUITag;

import com.woshidaniu.taglibs.views.components.AbstractDatasetStrutsUIBean;

@SuppressWarnings("serial")
public abstract class AbstractDatasetTag extends AbstractStrutsUITag {

	// 原js文件url,相对于web根目录的路径
	protected String fileURL;
	// 原js文件是否是v5样式
	protected String vstyle;
	// 指定进行国际化的key
	protected String i18nKeys;
	
	/**
	 * populateParams()初始化参数，一般用来初始化UIBean(Component)。
	 */
	@Override
	protected void populateParams() {
		super.populateParams();
		AbstractDatasetStrutsUIBean uibean = (AbstractDatasetStrutsUIBean)component;
		//uibean.setVstyle(BooleanUtils.parse(getVstyle()));
		//uibean.setFileURL(getFileURL());
		//uibean.setI18nKeys(getI18nKeys());
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
