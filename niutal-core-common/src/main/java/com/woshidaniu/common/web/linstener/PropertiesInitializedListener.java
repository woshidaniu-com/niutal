package com.woshidaniu.common.web.linstener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.Parameters;

/**
 * 
 * @className: PropertiesInitializedListener
 * @description: Properties文件加载监听；Properties加载文件 并进行缓存
 * @author : kangzhidong
 * @date : 下午05:12:06 2015-6-5
 * @modify by:
 * @modify date :
 * @modify description :
 */
public class PropertiesInitializedListener implements ServletContextListener {
	
	protected static Logger LOG = LoggerFactory.getLogger(PropertiesInitializedListener.class);
	
	public void contextInitialized(ServletContextEvent event) {
		try {

			//初始化系统参数,web.xml内参数至全局参数取值对象
			Parameters.initialize(event.getServletContext());
			
			LOG.info("开始加载classpath目录下的 *.properties资源文件!");
			//初始加载资源配置文件
			MessageUtil.loadProperties();
			LOG.info("资源文件加载成功!");
			
		} catch (Exception e) {
			LOG.error("资源文件加载失败",e);
		}
		
		
	}

	public void contextDestroyed(ServletContextEvent event) {
		
	}
	
}
