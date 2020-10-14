/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.LinkedList;
import java.util.List;

import com.woshidaniu.drdcsj.drsj.handler.AbstractHandler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @author 康康（1571）
 * 
 * 刷新数据到数据库的handler
 */
/**public**/ abstract class FlushDatabaseHandler extends AbstractHandler{

	static String key_successInsertRows = "successInsertRows"; 
	static String key_failInsertRows = "failInsertRows"; 
	static String key_successUpdateRows = "successUpdateRows"; 
	static String key_failUpdateRows = "failUpdateRows";

	/**
	 * 配置key,用于跳过框架内原始的插入操作
	 */
	public static final String CONFIG_KEY_SIKP_ORIGINAL_INSERT = FlushDatabaseHandler.class.getName()+".CONFIG_KEY_SIKP_ORIGINAL_INSERT";
	/**
	 * 配置key,用于跳过框架原始的更新操作
	 */
	public static final String CONFIG_KEY_SIKP_ORIGINAL_UPDATE = FlushDatabaseHandler.class.getName()+".CONFIG_KEY_SIKP_ORIGINAL_UPDATE";
	
	protected final List<ExcelRow> successInsertRows = new LinkedList<ExcelRow>();
	
	protected final  List<ExcelRow> failInsertRows = new LinkedList<ExcelRow>();
	
	protected final  List<ExcelRow> successUpdateRows = new LinkedList<ExcelRow>();
	
	protected final List<ExcelRow> failUpdateRows = new LinkedList<ExcelRow>();
	
	@Override
	protected void doHandleAfter(final HandlerContext context,final List<ExcelRow> acceptRows,final List<ExcelRow> unacceptRows) {
		context.putAttr(key_successInsertRows, successInsertRows);
		context.putAttr(key_failInsertRows, failInsertRows);
		context.putAttr(key_successUpdateRows, successUpdateRows);
		context.putAttr(key_failUpdateRows, failUpdateRows);
	}
	
	@Override
	protected boolean accept(final HandlerContext context,final ExcelRow currentRow,final List<ExcelRow> allRows){
		boolean accept = this.doAccept(context,currentRow,allRows);
		if(accept) {
			if(currentRow.isInsert()) {
				this.successInsertRows.add(currentRow);
			}
			if(currentRow.isUpdate()) {
				this.successUpdateRows.add(currentRow);
			}
		}else {
			if(currentRow.isInsert()) {
				this.failInsertRows.add(currentRow);
			}
			if(currentRow.isUpdate()) {
				this.failUpdateRows.add(currentRow);
			}
		}
		return accept;
	}

	protected abstract boolean doAccept(final HandlerContext context,final ExcelRow currentRow,final List<ExcelRow> allRows);
}
