package com.woshidaniu.monitor.api.servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woshidaniu.common.ZFtalParameter;
import com.woshidaniu.common.ZFtalParameters;
import com.woshidaniu.common.log.User;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;
import com.woshidaniu.web.monitor.DruidStatViewServlet;
import com.woshidaniu.web.utils.LocaleUtils;

public class ZFDruidStatViewServlet extends DruidStatViewServlet {
	
	public void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
		Locale locale = LocaleUtils.interceptLocale(httpRequest);
		User user = (User) httpRequest.getSession().getAttribute(Parameters.getString(getServletName() ,Parameter.SESSION_USER_KEY));
		if (user == null) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + ZFtalParameters.getGlobalString(ZFtalParameter.LOGIN_FORWARD_URL) + "?language=" + locale.toString());
			return;
		}
		if (!(user.isMonitor())) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + ZFtalParameters.getGlobalString(ZFtalParameter.LOGIN_HOMEPAGE_URL) + "?language=" + locale.toString() + "?jsdm=" + user.getJsdm());
			return;
		}	 
		super.service(httpRequest, httpResponse);
	 }
	 
}
