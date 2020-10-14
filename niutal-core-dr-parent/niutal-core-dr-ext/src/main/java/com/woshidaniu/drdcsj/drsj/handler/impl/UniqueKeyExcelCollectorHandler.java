/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.List;

import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.handler.AbstractHandler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @author 康康（1571）
 * 采集唯一键
 */
/**public**/final class UniqueKeyExcelCollectorHandler extends AbstractHandler{

	@Override
	protected boolean accept(HandlerContext context, ExcelRow currentRow, List<ExcelRow> allRows) {
		List<ExcelCell> cells = currentRow.getCells();
		for(ExcelCell cell : cells) {
			DrlpzModel drlpz = cell.getDrlpzModel();
			String sfwy = drlpz.getSfwy();
			if(ImportConfig._SFWY_YES.equals(sfwy)) {
				currentRow.getUniqueKeys().add(cell);
			}
		}
		//始终返回true
		return true;
	}
}
