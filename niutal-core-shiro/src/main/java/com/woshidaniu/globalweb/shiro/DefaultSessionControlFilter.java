package com.woshidaniu.globalweb.shiro;

import com.woshidaniu.common.log.User;
import com.woshidaniu.shiro.filter.session.SessionControlFilter;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   <br>说明：TODO
 *	 <br>class：com.woshidaniu.globalweb.shiro.DefaultSessionControlFilter.java
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class DefaultSessionControlFilter extends SessionControlFilter {

	/* (non-Javadoc)
	 * @see com.woshidaniu.shiro.filter.session.SessionControlFilter#getSessionControlCacheKey(java.lang.Object)
	 */
	@Override
	protected String getSessionControlCacheKey(Object principal) {
		return ((User)principal).getYhm();
	}

}
