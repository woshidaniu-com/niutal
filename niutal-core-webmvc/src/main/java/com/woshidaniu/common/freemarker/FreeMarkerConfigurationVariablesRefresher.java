/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.freemarker;

import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * 
 * @className	： FreeMarkerVariablesRefresher
 * @description	： FreeMarker变量刷新器
 * @author 		：康康（1571）
 * @date		： 2018年6月20日 上午11:09:10
 * @version 	V1.0
 */
public interface FreeMarkerConfigurationVariablesRefresher {
	
	/**
	 * @description	： 设置config
	 * @author 		： 康康（1571）
	 * @date 		：2018年6月20日 上午11:09:32
	 * @param config
	 */
	public void setConfiguration(Configuration config);

	/**
	 * 
	 * @description	： 刷新config里面的变量
	 * @author 		： 康康（1571）
	 * @date 		：2018年6月20日 上午11:09:47
	 */
	public void refresh() throws IOException, TemplateException;
	
}
