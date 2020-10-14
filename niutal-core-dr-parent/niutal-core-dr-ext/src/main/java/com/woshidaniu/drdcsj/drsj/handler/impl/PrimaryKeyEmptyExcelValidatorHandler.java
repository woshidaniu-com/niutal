/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.drdcsj.drsj.handler.Handler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;


/**
 * @author 康康（1571）
 * excel内主键列空值验证器
 */
/**public**/ final class PrimaryKeyEmptyExcelValidatorHandler implements Handler{
	
	private static final Logger log = LoggerFactory.getLogger(PrimaryKeyEmptyExcelValidatorHandler.class);
	
	private static final String DEFAULT_ERROR = "主键列不能为空";
	
	@Override
	public void handle(final HandlerContext context,final List<ExcelRow> acceptRows,final List<ExcelRow> unacceptRows) {

		Iterator<ExcelRow> it = acceptRows.iterator();
		while(it.hasNext()) {
			ExcelRow currentRow = it.next();
			List<ExcelCell> primaryKeys = currentRow.getPrimaryKeys();
			if(primaryKeys.isEmpty()) {
				if(log.isTraceEnabled()) {
					log.trace("第{}行[id:{}]没有主键",currentRow.getIndex(),currentRow.getId());				
				}
			}else {
				for(ExcelCell cell : primaryKeys) {
					if(StringUtils.isEmpty(cell.getExcelCellValue())) {
						if(log.isTraceEnabled()) {
							log.trace("Excel行[rowId={},rowIndex={}],ExcelCell[cellId={},cellIndex={}]数据错误:{}",currentRow.getId(),currentRow.getIndex(),cell.getId(),cell.getIndex(),DEFAULT_ERROR);						
						}
						cell.setError(DEFAULT_ERROR);
					}
				}
			}
		}
	}
}
