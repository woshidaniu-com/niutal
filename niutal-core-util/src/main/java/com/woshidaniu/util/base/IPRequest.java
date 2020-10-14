package com.woshidaniu.util.base;

import javax.servlet.http.HttpServletRequest;

/**
 * @deprecated
 * @see com.woshidaniu.web.utils.WebRequestUtils
 */
@Deprecated
public class IPRequest {

	// 获取客户端IP地址，支持代理服务器
	public static String getIpAddress(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		String localIP = "127.0.0.1";

		if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

}
