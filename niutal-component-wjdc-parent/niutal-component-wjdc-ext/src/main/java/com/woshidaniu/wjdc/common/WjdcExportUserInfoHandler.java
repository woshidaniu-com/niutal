/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.common;

/**
 * @description	： 问卷调查用户信息Handler
 * @author 		：康康（1571）
 * @date		： 2019年6月20日 下午2:41:08
 * @version 	V1.0
 */
public interface WjdcExportUserInfoHandler {

	/**
	 * @description	： 根据答卷人id获得答卷人姓名
	 * @param djrid
	 * @return
	 */
	public String getXm(String djrid);
	
	/**
	 * @description	： 根据答卷人id获得答卷人部门名称
	 * @param djrid
	 * @return
	 */
	public String getBmmc(String djrid);
}
