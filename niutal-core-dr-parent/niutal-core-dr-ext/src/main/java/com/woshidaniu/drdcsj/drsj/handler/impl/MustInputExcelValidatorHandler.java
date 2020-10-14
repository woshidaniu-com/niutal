/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.handler.AbstractHandler;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @author 康康（1571） 必填字段验证
 */
/**public**/ final class MustInputExcelValidatorHandler extends AbstractHandler {
	
	private static final Logger log = LoggerFactory.getLogger(MustInputExcelValidatorHandler.class);

	private static final String ERROR_NOTNULL = "[%s]列的值不能为空";

	@Override
	protected boolean accept(final HandlerContext context,final ExcelRow currentRow,final List<ExcelRow> allRows) {
		// 插入方式
		final String crfs = context.getCrfs();

		if (ImportConfig._CRFS_INSERT.equals(crfs)) {// 如果是插入
			List<ExcelCell> cells = currentRow.getCells();
			for (ExcelCell cell : cells) {
				String cellValue = cell.getExcelCellValue();
				DrlpzModel drlpz = cell.getDrlpzModel();
				int sfbtFlag = drlpz.getSfbtFlag();

				String sfzj = drlpz.getSfzj();
				if (!ImportConfig._SFZJ_YES.equals(sfzj)) {// 不是主键
					if ((DrlpzModel.SFBT_ON_INSERT_FLAG & sfbtFlag) != 0) {// 插入时必填
						if (StringUtils.isEmpty(cellValue)) {// 是空值
							String error = String.format(ERROR_NOTNULL, cell.getExcelHeaderName());
							if(log.isTraceEnabled()) {
								log.trace("Excel行[rowId={},rowIndex={}],ExcelCell[cellId={},index={}]数据错误:{}",currentRow.getId(),currentRow.getIndex(),cell.getId(),cell.getIndex(),error);								
							}
							cell.setError(error);
						}
					}
				}
			}
			return true;
		} else if (ImportConfig._CRFS_UPDATE.equals(crfs)) {// 如果是更新
			List<ExcelCell> cells = currentRow.getCells();
			for (ExcelCell cell : cells) {
				String cellValue = cell.getExcelCellValue();
				DrlpzModel drlpz = cell.getDrlpzModel();
				int sfbtFlag = drlpz.getSfbtFlag();

				// 主键不能为空，且数据库中必须已经存在
				String sfzj = drlpz.getSfzj();
				if (!ImportConfig._SFZJ_YES.equals(sfzj)) {// 不是主键
					if ((DrlpzModel.SFBT_ON_UPDATE_FLAG & sfbtFlag) != 0) {// 更新时必填
						if (StringUtils.isEmpty(cellValue)) {// 是空值
							String error = String.format(ERROR_NOTNULL, cell.getExcelHeaderName());
							if(log.isTraceEnabled()) {
								log.trace("Excel行[rowId={},rowIndex={}],ExcelCell[cellId={},index={}]数据错误:{}",currentRow.getId(),currentRow.getIndex(),cell.getId(),cell.getIndex(),error);								
							}
							cell.setError(error);
						}
					}
				}
			}
			return true;
		} else if (ImportConfig._CRFS_INSERT_UPDATE.equals(crfs)) {// 如果是插入和更新
			List<ExcelCell> cells = currentRow.getCells();
			for (ExcelCell cell : cells) {
				String cellValue = cell.getExcelCellValue();
				DrlpzModel drlpz = cell.getDrlpzModel();
				int sfbtFlag = drlpz.getSfbtFlag();

				// 主键不能为空
				String sfzj = drlpz.getSfzj();
				if (!ImportConfig._SFZJ_YES.equals(sfzj)) {// 不是主键
					if (currentRow.isInsert()) {// 当此行是插入
						if ((DrlpzModel.SFBT_ON_INSERT_FLAG & sfbtFlag) != 0) {// 插入时必填
							if (StringUtils.isEmpty(cellValue)) {// 是空值
								String error = String.format(ERROR_NOTNULL, cell.getExcelHeaderName());
								if(log.isTraceEnabled()) {
									log.trace("Excel行[rowId={},rowIndex={}],ExcelCell[cellId={},index={}]数据错误:{}",currentRow.getId(),currentRow.getIndex(),cell.getId(),cell.getIndex(),error);									
								}
								cell.setError(error);
							}
						}
					}else if (currentRow.isUpdate()) {// 当此行是更新
						if ((DrlpzModel.SFBT_ON_UPDATE_FLAG & sfbtFlag) != 0) {// 更新时必填
							if (StringUtils.isEmpty(cellValue)) {// 是空值
								String error = String.format(ERROR_NOTNULL, cell.getExcelHeaderName());
								if(log.isTraceEnabled()) {
									log.trace("Excel行[rowId={},rowIndex={}],ExcelCell[cellId={},index={}]数据错误:{}",currentRow.getId(),currentRow.getIndex(),cell.getId(),cell.getIndex(),error);									
								}
								cell.setError(error);
							}
						}
					}else {
						//
					}
				} else {
					//
				}
			}
			return true;
		} else {
			log.error("插入方式未定义crfs[{}]", crfs);
			return false;
		}
	}
}
