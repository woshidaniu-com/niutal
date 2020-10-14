/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.List;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.drdcsj.drsj.handler.AbstractHandler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @description	： 检查所有的Cell是否有错误
 * @author 		：康康（1571）
 */
/**public**/final class AwaysCheckAllCellErrorHandler extends AbstractHandler{

	@Override
	protected boolean accept(HandlerContext context, ExcelRow currentRow, List<ExcelRow> allRows) {
		for(ExcelCell cell : currentRow.getCells()) {
			if((!cell.isIgnore())&&StringUtils.isNotEmpty(cell.getError())) {
				//这里必须返回false !!!
				return false;
			}
		}
		return true;
	}
}
