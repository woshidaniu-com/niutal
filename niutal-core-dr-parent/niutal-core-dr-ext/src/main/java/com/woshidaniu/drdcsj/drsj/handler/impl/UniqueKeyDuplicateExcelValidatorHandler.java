/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.handler.AbstractHandler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @author 康康（1571）
 * 验证Excel内唯一键数据,当前行跟Excel内所有其他行进行比较
 */
/**public**/ final class UniqueKeyDuplicateExcelValidatorHandler extends AbstractHandler{

	private static final Logger log = LoggerFactory.getLogger(PrimaryKeyDuplicateExcelValidatorHandler.class);
	
	private String errorTemplate = "Excel中的唯一键列(%s)有重复值(%s)";
	
	private String errorTemplate_empty = "Excel中的唯一键列(%s)是不能填写空白值";
	
	private String traceLogTemplate = "第%s行[id:%s]没有没有唯一键";

	@Override
	protected boolean accept(final HandlerContext context,final ExcelRow currentRow,final List<ExcelRow> allRows) {
		List<ExcelCell> uniqueKeys = currentRow.getUniqueKeys();
		if(uniqueKeys.isEmpty()) {
			if(log.isTraceEnabled()) {
				String logMsg = String.format(traceLogTemplate, currentRow.getIndex(),currentRow.getId());
				log.trace(logMsg);			
			}
			return true;
		}
		
		long currentRowId = currentRow.getId();
		for (ExcelCell currentRowUk : uniqueKeys) {
			
			boolean hasError = false;
			
			//唯一键必填
			if(StringUtils.isNotEmpty(currentRowUk.getExcelCellValue())) {
				for (ExcelRow r : allRows) {
					long rowId = r.getId();
					if (currentRowId != rowId) {
						
						// 其他行唯一键
						List<ExcelCell> otherRowExcelUks = r.getUniqueKeys();
						ExcelCell otherRowUk = this.getTargetUk(currentRowUk, otherRowExcelUks);
						// 唯一键相同，不允许
						if (currentRowUk.getExcelCellValue().equals(otherRowUk.getExcelCellValue())) {
							
							String reasonKey = currentRowUk.getExcelHeaderName();
							String reasonValue = currentRowUk.getExcelCellValue();
							
							String error = String.format(errorTemplate, reasonKey, reasonValue);
							currentRowUk.setError(error);
							
							hasError = true;
							break;
						}else {
							//nothing to do
						}
					}else {
						//nothing to do
					}
				}
			}else {
				hasError = true;
				String error = String.format(errorTemplate_empty, currentRowUk.getExcelHeaderName());
				currentRowUk.setError(error);
			}
			
			if (hasError) {
				if (log.isTraceEnabled()) {
					log.trace("Excel行[rowId={},index={}],ExcelCell[cellId={},cellIndex={}]数据错误:{}", currentRow.getId(), currentRow.getIndex(), currentRowUk.getId(), currentRowUk.getIndex(), currentRowUk.getError());
				}
				break;
			}
		}
		return true;
	}

	private ExcelCell getTargetUk(ExcelCell currentRowUk, List<ExcelCell> otherRowExcelUks) {
		for(ExcelCell cell : otherRowExcelUks) {
			if(cell.getExcelHeaderName().equals(currentRowUk.getExcelHeaderName())) {
				return cell;
			}
		}
		return null;
	}
}
