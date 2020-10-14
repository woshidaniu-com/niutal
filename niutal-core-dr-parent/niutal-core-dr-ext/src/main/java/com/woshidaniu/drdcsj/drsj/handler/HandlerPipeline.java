/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler;

/**
 * @author 康康（1571）
 * 流水线管道对象,内部包含各种handler,用来处理数据
 * handler可以灵活的定义自己的业务,如
 * 1.验证数据有效性
 * 2.写数据到数据库
 * 3.添加额外的数据列，数据行
 * 4.打印日志
 * 5.最数据进行转换
 * 6.对数据进行分析汇总
 */
public interface HandlerPipeline {

	/**
	 * @description	： 尾部追加handler
	 * @param name handler的名称
	 * @param handler
	 */
	void addHandler(String name,Handler handler);
	
	/**
	 * @description	： 获得handler
	 * @param name handler的名称
	 * @return
	 */
	Handler getHandler(String name);

	/**
	 * @description	： 尾部追加监听器
	 * @param listener
	 */
	void addListener(PipelineExecuteListener listener);
	
	/**
	 * @description	： 执行
	 */
	void execute();
}
