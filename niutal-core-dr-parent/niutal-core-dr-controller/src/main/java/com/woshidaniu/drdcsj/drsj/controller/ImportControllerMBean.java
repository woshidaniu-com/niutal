/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.controller;

public interface ImportControllerMBean {

	boolean isDownloadChineseTemplateFileName();

	void setDownloadChineseTemplateFileName(boolean isDownloadChineseTemplateFileName);

	boolean isDownloadChineseErrorFileName();

	void setDownloadChineseErrorFileName(boolean isDownloadChineseErrorFileName);
	
	String getVersion();
	
	void setVersion(String version);
}
