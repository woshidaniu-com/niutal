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
 * 主键收集验证器，先收集主键列，然后开始验证主键
 * 这个应该放到 验证主键 的验证器前面
 */
public final class PrimaryKeyExcelCollectorValidatorHandler extends AbstractHandler{

	@Override
	protected boolean accept(final HandlerContext context,final ExcelRow currentRow,final List<ExcelRow> allRows) {
		
		List<ExcelCell> cells = currentRow.getCells();
		for(ExcelCell cell : cells) {
			DrlpzModel drlpz = cell.getDrlpzModel();
			String sfzj = drlpz.getSfzj();
			if(ImportConfig._SFZJ_YES.equals(sfzj)) {
				currentRow.getPrimaryKeys().add(cell);
			}
		}
		//始终返回true
		return true;
	}
}
