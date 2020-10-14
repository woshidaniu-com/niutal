/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.daointerface.IImportDao;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.handler.Handler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @author 康康（1571）
 * 准备环境
 * 1.把null换成""
 * 2.把" xxx"换成"xxx"
 * 3.备份到originExcelValue
 */
/**public**/ final class PrepareHandler implements Handler{
	
	private static final Logger log = LoggerFactory.getLogger(PrepareHandler.class);
	
	@Override
	public void handle(final HandlerContext context,final List<ExcelRow> acceptRows,final List<ExcelRow> unacceptRows) {

		List<String> selectColumn = context.getSelectColumns();
		
		IImportDao importDao = context.getSqlSession().getMapper(IImportDao.class);
		List<DrlpzModel> drlpzModels = importDao.getDrlpzModelList(selectColumn);
		
		//必须在excel中存在的表头名称
		Set<String> selectExcelHeaderSet = new HashSet<String>();			
		for(DrlpzModel drlpzModel : drlpzModels) {
			selectExcelHeaderSet.add(drlpzModel.getDrlmc());
		}
		
		for(ExcelRow currentRow :acceptRows) {
			List<ExcelCell> cells = currentRow.getCells();
			Iterator<ExcelCell> it = cells.iterator();
			while(it.hasNext()) {
				ExcelCell cell = it.next();
				String excelHeaderName = cell.getExcelHeaderName();
				
				//必须在excel中存在的列
				if(selectExcelHeaderSet.contains(excelHeaderName)) {
					String excelValue = cell.getExcelCellValue();
					if(excelValue == null) {
						cell.setExcelCellValue("");
						cell.setExcelOriginValue("");
					}else {
						String trimed = excelValue.trim();
						cell.setExcelCellValue(trimed);
						cell.setExcelOriginValue(trimed);
					}
				}else {
					//移除不必要的excel列
					it.remove();
					if(log.isTraceEnabled()) {
						log.trace("存在非未必要的列[{}]",excelHeaderName);						
					}
				}
			}
		}
	}
}
