package com.woshidaniu.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.woshidaniu.httpclient.utils.HttpURLConnectionUtils;

import junit.framework.TestCase;

public class TicketLoginTest extends TestCase{
	
	String charset = "UTF-8";
	
	public void test222() {
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("appid", "4d4441774d44413d");
			
			String baseURL = "http://10.71.33.192:8080/niutal-web/api/ticket_access_token.html";
			String resultString = HttpURLConnectionUtils.getResultWithGet(baseURL, paramsMap, charset);
			System.out.println(resultString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
