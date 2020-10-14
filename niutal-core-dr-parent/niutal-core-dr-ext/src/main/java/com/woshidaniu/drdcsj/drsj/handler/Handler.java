/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler;

import java.util.List;

import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @author 康康（1571）
 * pipeline中的handler,负责处理数据 
 */
public interface Handler {

	/**
	 * @description	：
	 *  处理数据
	 * 	三个参数加final修饰符表示在整个pipeline执行期间,这三个对象始终不会改变引用,
	 *  ParentPipeline和childPipeline的这三个参数都是一致的引用
	 * @param context 上下文
	 * @param acceptRows 已经被上一个handler处理且接受的数据行
	 * @param unacceptRows 已经被上一个handler处理,但没有被接受的数据行
	 */
	public void handle(final HandlerContext context,final List<ExcelRow> acceptRows,final List<ExcelRow> unacceptRows);	
	
}
