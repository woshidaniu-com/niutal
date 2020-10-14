/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.handler.Handler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @author 康康（1571）
 * 最大长度验证
 */
/**public**/ final class MaxLengthExcelValidatorHandler implements Handler{
	
	private static final String ERROR_MAX_LENGTH = "[%s]列的值超过最大长度[%d]";

	@Override
	public void handle(final HandlerContext context,final List<ExcelRow> acceptRows,final List<ExcelRow> unacceptRows) {
		
		//缓存
		Map<String,Integer> cache = new HashMap<String,Integer>();
		
		for(ExcelRow currentRow : acceptRows) {
			for(ExcelCell cell : currentRow.getCells()) {
				String cellValue = cell.getExcelCellValue();
				DrlpzModel drlpz = cell.getDrlpzModel();
				//最大长度
				String zdcd = drlpz.getZdcd();
				String drl = drlpz.getDrl();
				
				Integer maxLength = cache.get(drl);
				if(maxLength == null) {
					if(StringUtils.isNotEmpty(zdcd)) {
						maxLength = Integer.valueOf(zdcd);
					}else {
						maxLength = Integer.MAX_VALUE;
					}
					cache.put(drl, maxLength);
				}
				
				//长度
				if(StringUtils.isNotEmpty(cellValue) && cellValue.length() > maxLength.intValue()) {
					String err = String.format(ERROR_MAX_LENGTH, cell.getExcelHeaderName(),maxLength.intValue());
					cell.setError(err);
				}
			}
		}
	}
}
