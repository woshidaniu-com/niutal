package com.woshidaniu.ws.utils;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination; 

public class IpAddressInterceptor extends AbstractSoapInterceptor {

	public IpAddressInterceptor(){
		super(Phase.RECEIVE);

	}
	
	@Override
	public void handleMessage(SoapMessage message) throws Fault {
			Exception e = new Exception("禁止访问" ) ;
			HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
			//当接口响应时间较长导致超时，会自动重新请求，此时request会为null
			if(request == null){
				return ;
			}
			String clientIP = request.getRemoteAddr() ;
			
			//PropertiesUtil propertiesUtil = new PropertiesUtil("allowedIp.properties") ;
			Properties properties;
			try {
				properties = PropertiesUtils.loadProperties("allowedIp.properties");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				throw new Fault(e1);
			}
			//properties.get("allowedIp").split(",") ;
			String str = (String)properties.get("allowedIp") ;
			String[] allowedIpStr = str.split(",") ;
			int len = allowedIpStr.length ;
			boolean flag = false;
			for(int i=0; i<len ;i++){
				if( !allowedIpStr[i].equals(clientIP)){
					flag = true ;
				}
			}
			if(!flag)
				throw new Fault(e);
			
			//System.out.println(allowedIpStr);
			

	}

}
