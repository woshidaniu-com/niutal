/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.formatter.FormatResult;
import com.woshidaniu.drdcsj.drsj.formatter.FormatterFactory;
import com.woshidaniu.drdcsj.drsj.formatter.ImportFormatter;
import com.woshidaniu.drdcsj.drsj.formatter.imp.CSqlFormatter;
import com.woshidaniu.drdcsj.drsj.handler.AbstractHandler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;


/**
 * @author 康康（1571）
 * 组合Formator验证
 */
/**public**/ final class CompositieFormatorValidatorHandler extends AbstractHandler{
	
	private static final Logger log = LoggerFactory.getLogger(CompositieFormatorValidatorHandler.class);
	
	private FormatterFactory formatter;
	
	public CompositieFormatorValidatorHandler(FormatterFactory  formatterFactory) {
		this.formatter = formatterFactory;
	}

	@Override
	protected boolean accept(final HandlerContext context,final ExcelRow currentRow,final List<ExcelRow> allRows) {
		
		List<ExcelCell> cells = currentRow.getCells();
		for(ExcelCell cell : cells) {
			DrlpzModel drlpzModel = cell.getDrlpzModel();
			
			//当excel这个单元格没有填写任何值 且 是非必填的，那么就不需要格式化
			if(StringUtils.isEmpty(cell.getExcelCellValue()) && drlpzModel.getSfbtFlag() == DrlpzModel.SFBT_NOT_SET_FLAG) {
				continue;
			}
			//配置的字符串
			String lsjgsh = drlpzModel.getLsjgsh();
			ImportFormatter formatter = this.formatter.getFormatter(lsjgsh);
			
			if(formatter instanceof CSqlFormatter) {
				CSqlFormatter csqlFormatter = (CSqlFormatter)formatter;
				
				Map<String,String> currentRowValue = this.getCurrentRowValue(currentRow);
				FormatResult formatResult = csqlFormatter.format(drlpzModel, currentRowValue, lsjgsh);
				if(formatResult.hasError()) {
					String error = formatResult.getError();
					if(log.isTraceEnabled()) {
						log.trace("Excel行[rowId={},rowIndex={}],ExcelCell[cellId={},cellIndex={}]数据错误:{}",currentRow.getId(),currentRow.getIndex(),cell.getId(),cell.getIndex(),error);						
					}
					cell.setError(error);
				}else {
					cell.setExcelCellValue(formatResult.getResult());
				}
			}else {
				FormatResult formatResult = formatter.format(cell.getDrlpzModel(), cell.getExcelCellValue());
				if(formatResult.hasError()) {
					String error = formatResult.getError();
					if(log.isTraceEnabled()) {
						log.trace("Excel行[rowId={},rowIndex={}],ExcelCell[cellId={},cellIndex={}]数据错误:{}",currentRow.getId(),currentRow.getIndex(),cell.getId(),cell.getIndex(),error);						
					}
					cell.setError(error);
				}else {
					cell.setExcelCellValue(formatResult.getResult());
				}
			}
		}
		return true;
	}

	private Map<String, String> getCurrentRowValue(ExcelRow row) {
		
		List<ExcelCell> cells = row.getCells();
		
		//初始化到指定大小,避免扩容浪费时间和控件
		int initMapSize = cells.size() * 5;
		Map<String, String> result = new HashMap<String,String>(initMapSize);
		
		for(ExcelCell cell : cells) {
			
			//导入列名称
			String drlmc = cell.getDrlpzModel().getDrlmc();
			//导入列,英文
			String drl = cell.getDrlpzModel().getDrl().toLowerCase();
			//导入列,英文大写
			String drlUpperCase = drl.toUpperCase();
			
			//导入列配置id,英文
			String drlpzid = cell.getDrlpzModel().getDrlpzid().toLowerCase();
			//导入列配置id,英文大写
			String drlpzidUpperCase = drlpzid.toUpperCase();
			
			String value = cell.getExcelCellValue();
			
			result.put(drlmc,value);
			result.put(drl, value);
			result.put(drlUpperCase,value);
			result.put(drlpzid,value);
			result.put(drlpzidUpperCase,value);
			
		}
		return result;
	}
}