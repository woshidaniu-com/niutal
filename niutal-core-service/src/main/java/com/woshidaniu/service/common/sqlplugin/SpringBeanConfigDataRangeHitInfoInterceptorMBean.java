/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.service.common.sqlplugin;

import java.util.concurrent.atomic.AtomicLong;

public interface SpringBeanConfigDataRangeHitInfoInterceptorMBean {

	boolean isEnable();
	
	void setEnable(boolean enable);
	
	AtomicLong getHitCount();

	void setHitCount(AtomicLong hitCount);

	AtomicLong getInterceptCount();

	void setInterceptCount(AtomicLong interceptCount);
	
	boolean isShowTag();

	void setShowTag(boolean showTag);
}
