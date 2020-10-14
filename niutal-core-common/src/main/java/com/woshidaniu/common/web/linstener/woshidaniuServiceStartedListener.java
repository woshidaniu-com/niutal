package com.woshidaniu.common.web.linstener;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.context.WebContext;



/**
 * 
 *@类名称:SpringServiceListener.java
 *@类描述：应用系统监听
 *@创建人：kangzhidong
 *@创建时间：2015-3-31 上午11:46:33
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public class woshidaniuServiceStartedListener implements ServletContextListener {
	
	private  Logger LOG = LoggerFactory.getLogger(this.getClass());
	private String serviceName = null;
	
	public woshidaniuServiceStartedListener() {
		super();
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceName() {
		return serviceName;
	}
	
	/**
	 * 系统初始化
	 */
	public void contextInitialized(ServletContextEvent event) {
		final ServletContext application = event.getServletContext();
		//ServiceFactory.springContext = WebApplicationContextUtils.getWebApplicationContext(application);
		this.setServiceName(MessageUtil.getLocaleText(Locale.CHINA,"system.title") + "系统");
		
		try {
			WebContext.bindServletContext(application);
			LOG.info(getServiceName() + "启动成功,已可以访问!");
		} catch (Exception e) {
			LOG.error(getServiceName() + "启动失败",e);
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		LOG.info(getServiceName() + "停止");
	}
	
}
