/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.file.impl;

public interface AutoCleanableTempFileServiceMBean {
	
	long getScheduleCount();

	long getCreateCount();
	
	long getScheduleDeleteCount();
	
	long dumpLeaveOverCount();
	
	String dumpLeaveOverTotalSize();
	
	int forceClean();
	
	void cleanScheduleCount();
}
