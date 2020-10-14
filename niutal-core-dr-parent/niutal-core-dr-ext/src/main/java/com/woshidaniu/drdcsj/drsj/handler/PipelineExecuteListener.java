/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler;

/**
 * Pipeline的执行监听器
 * @author 		：康康（1571）
 */
public interface PipelineExecuteListener {

	/**
	 * @description	： 执行前
	 * @param handlerPipeline
	 */
	void beforeExecute(final HandlerPipeline handlerPipeline);
	
	/**
	 * @description	： 执行后
	 * @param handlerPipeline
	 */
	void afterExecute(final HandlerPipeline handlerPipeline);
}
