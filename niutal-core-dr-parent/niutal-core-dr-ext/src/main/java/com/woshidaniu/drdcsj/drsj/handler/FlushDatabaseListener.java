/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler;

import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * 刷新数据库监听器
 * @author 		：康康（1571）
 */
public interface FlushDatabaseListener {
	
	/**
	 * @description	： 当插入一行数据之前
	 * @param context 上下文
	 * @param currentRow 当前行
	 */
	void beforeInsert(final HandlerContext context,final ExcelRow currentRow);
	
	/**
	 * @description	： 当插入一行数据之后
	 * @param context 上下文
	 * @param currentRow 当前行
	 */
	void afterInsert(final HandlerContext context,final ExcelRow currentRow);

	/**
	 * @description	： 当更新一条数据之前
	 * @param context 上下文
	 * @param currentRow 当前行
	 */
	void beforeUpdate(final HandlerContext context,final ExcelRow currentRow);
	
	/**
	 * @description	： 当更新一条数据之后
	 * @param context 上下文
	 * @param currentRow 当前行
	 */
	void afterUpdate(final HandlerContext context,final ExcelRow currentRow);
}
