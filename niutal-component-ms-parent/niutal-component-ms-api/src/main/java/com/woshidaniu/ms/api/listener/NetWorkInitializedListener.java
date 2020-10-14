package com.woshidaniu.ms.api.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.enhanced.listener.SpringContextInitializedListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class NetWorkInitializedListener implements ServletContextListener {

	protected static Logger LOG = LoggerFactory.getLogger(SpringContextInitializedListener.class);
	
	 //获取spring注入的bean对象
	protected WebApplicationContext applicationContext;
	 
	public void contextInitialized(ServletContextEvent event) {
		this.applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
		this.applicationContext.publishEvent(event);
	}

	public void contextDestroyed(ServletContextEvent event) {
		LOG.info("SpringContext destroyed .");
	}
	
}

