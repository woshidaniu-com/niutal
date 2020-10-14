package com.woshidaniu.common.aop.aware;


/**
 * 
 *@类名称	: BeforeAware.java
 *@类描述	：Aop 前置通知要调用的方法接口
 *@创建人	：kangzhidong
 *@创建时间	：Mar 21, 2016 12:17:24 PM
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
public interface BeforeAware {

	/**
	 * 
	 *@描述		：Aop 前置通知调用的方法
	 *@创建人	: kangzhidong
	 *@创建时间	: Mar 21, 201612:17:30 PM
	 *@param invocation
	 *@throws Exception
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public abstract void doBefore(Invocation invocation)  throws Exception;
	
}
