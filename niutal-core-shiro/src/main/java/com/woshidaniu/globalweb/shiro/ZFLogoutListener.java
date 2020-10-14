/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.globalweb.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;

import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.common.log.BusinessLogModel;
import com.woshidaniu.common.log.BusinessType;
import com.woshidaniu.common.log.User;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.shiro.filter.impl.DefaultLogoutListener;
import com.woshidaniu.web.utils.WebRequestUtils;

public class ZFLogoutListener extends DefaultLogoutListener {

	@Override
	public void beforeLogout(Subject subject, ServletRequest request, ServletResponse response) {
		if(!subject.isAuthenticated()){
			return;
		}
		User loginUser = (User) subject.getPrincipals().getPrimaryPrincipal();
		String czlx = BusinessType.LOGOUT.getKey();
		BusinessLogModel log = new BusinessLogModel();
		log.setCzlx(czlx);
		log.setCzr(loginUser.getYhm());
		log.setCzrq(DateUtils.format(DateUtils.DATE_FORMAT_TWO));
		log.setIpdz(WebRequestUtils.getRemoteAddr((HttpServletRequest) request));
		log.setZjm(WebRequestUtils.getRemoteAddr((HttpServletRequest) request));
		LogEngineImpl.getInstance().log(log);
	}
	
}
