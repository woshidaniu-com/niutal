package com.woshidaniu.common.filter;


import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author majun <br />
 *         描述：实现防XSS攻击 <br />
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {

	private static final Logger log = LoggerFactory.getLogger(XSSRequestWrapper.class);
	
	private AntiSamy antiSamy = null;
	
	public XSSRequestWrapper(AntiSamy antiSamy,HttpServletRequest request) {
		super(request);
		this.antiSamy = antiSamy;
	}

	@SuppressWarnings( { "unchecked" })
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> request_map = super.getParameterMap();
		Iterator iterator = request_map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry me = (Map.Entry) iterator.next();
			String[] values = (String[]) me.getValue();
			for (int i = 0; i < values.length; i++) {
				///System.out.println(values[i]);
				values[i] = xssClean(values[i]);
			}
		}
		return request_map;
	}

	private String xssClean(String taintedHTML) {
		if(antiSamy != null){
			try {
				CleanResults cr = antiSamy.scan(taintedHTML);
				String cleanHTML = cr.getCleanHTML();
				String escapeHtml = StringEscapeUtils.unescapeHtml4(cleanHTML);
	            String replaceAll = escapeHtml.replaceAll((antiSamy.scan("&nbsp;")).getCleanHTML(), "&nbsp;");
	            if(log.isDebugEnabled()){
	            	log.debug("["+taintedHTML+"]--["+replaceAll+"]");
	            }
				// 安全的HTML输出
				return replaceAll;
			} catch (ScanException e) {
				e.printStackTrace();
			} catch (PolicyException e) {
				e.printStackTrace();
			}
		}
		return taintedHTML;
	}

}
