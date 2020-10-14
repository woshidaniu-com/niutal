package com.woshidaniu.common.system.loader;

import org.springframework.context.ApplicationContext;

/**
 * 系统平台类
 * 
 * Create on 2013-7-5 上午09:22:57 
 * 
 * All CopyRight By http://www.woshidaniu.com/ AT 2013  Version 1.0 
 *
 * @author : HJL [718]
 */
public class Platform {
	
	private static Platform	ourInstance	= new Platform();

	public static Platform getInstance() {
		return ourInstance;
	}

	private Platform() {
	}

	/** Spring applicationContext **/
	private ApplicationContext	applicationContext	= null;

	/** 平台部署的根物理路径 **/
	private String				WebPath;

	/**
	 * 根据BeanName获得Bean
	 * 
	 * @param beanName
	 *            sping配置的id
	 * @return 符合条件的bean
	 */
	public Object getBean(String beanName) {
		return this.applicationContext.getBean(beanName);
	}

	/**
	 * 获得平台部署的根物理路径
	 * 
	 * @return 平台根物理路径
	 */
	public String getWebPath() {
		return WebPath;
	}

	/**
	 * 设置平台根物理路径
	 * 
	 * @param webPath
	 *            平台根物理路径
	 */
	public void setWebPath(String webPath) {
		if (null == this.WebPath)
			WebPath = webPath;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		if (null == this.applicationContext)
			this.applicationContext = applicationContext;
	}
}
