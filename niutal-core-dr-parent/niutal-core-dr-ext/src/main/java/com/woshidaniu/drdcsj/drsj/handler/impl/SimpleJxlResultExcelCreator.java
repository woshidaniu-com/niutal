/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


/**
 * @author 康康（1571）
 * 简单jxl结果创建器 
 */
@Deprecated
class SimpleJxlResultExcelCreator extends SimpleResultExcelBase{

	protected static final Logger log = LoggerFactory.getLogger(SimpleJxlResultExcelCreator.class);

	/**
	 * @description	： 创建汇总结果文件
	 * 特性
	 * 1.定位错误cell
	 * 2.数据列宽度设置
	 * @param context
	 * @param unacceptRows
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static File createResultExcel(final HandlerContext context,final List<ExcelRow> unacceptRows,final File file) throws Exception{
		//TODO 抽取成类
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(file);
			WritableSheet sheet = book.createSheet(DefaultSheetName, 0);
			int rowIndex = -1;
			
			//表格头部行
			Iterator<ExcelRow> it_header = unacceptRows.iterator();
			if(it_header.hasNext()) {
				rowIndex ++;
				ExcelRow firstRow = it_header.next();
				List<ExcelCell> cells =  firstRow.getCells();
				int columnIndex = -1;
				for(ExcelCell c : cells) {
					columnIndex ++;
					Label label = new Label(columnIndex, rowIndex, c.getExcelHeaderName());
					
					//是否必填
					boolean mustInput = false;
					DrlpzModel drlpz = c.getDrlpzModel();
					if(ImportConfig._SFZJ_YES.equals(c.getDrlpzModel().getSfzj())) {
						mustInput = true;
					}
					if(!mustInput) {
						String crfs = context.getCrfs();
						int sfbtFlag = drlpz.getSfbtFlag();
						if(ImportConfig._CRFS_INSERT.equals(crfs)) {
							mustInput = (sfbtFlag & DrlpzModel.SFBT_ON_INSERT_FLAG) != 0;
						}else if(ImportConfig._CRFS_UPDATE.equals(crfs)) {
							mustInput = (sfbtFlag & DrlpzModel.SFBT_ON_UPDATE_FLAG) != 0;
						}else if(ImportConfig._CRFS_INSERT_UPDATE.equals(crfs)) {
							mustInput = ((sfbtFlag & DrlpzModel.SFBT_ON_INSERT_FLAG) != 0) ||((sfbtFlag & DrlpzModel.SFBT_ON_UPDATE_FLAG) != 0);
						}
					}
					WritableCellFormat format = createHeaderCellFormat(mustInput);
					if(format != null) {
						label.setCellFormat(format);							
					}
					sheet.addCell(label);
				}
				//提示列(若使用此文件继续导入，请删除此列)
				columnIndex ++;
				Label label = new Label(columnIndex, rowIndex, ErrorColumnTips);
				WritableCellFormat format = createTipsHeaderCellFormat();
				if(format != null) {
					label.setCellFormat(format);							
				}
				sheet.addCell(label);
				sheet.setColumnView(columnIndex,40);
			}
			
			ColumnAutoWithFix columnMaxWithFix = new ColumnAutoWithFix(defaultColumnSize,defaultErrorTipColumnSize);
			
			//表格的数据行
			Iterator<ExcelRow> it_data = unacceptRows.iterator();
			while(it_data.hasNext()) {
				rowIndex ++;
				ExcelRow row = it_data.next();
				List<ExcelCell> cells =  row.getCells();
				int columnIndex = -1;
				
				//所有cell的错误构成一行的错误
				String oneRowError = "";
				
				for(ExcelCell c : cells) {
					columnIndex ++;
					String error = c.getError();
					String excelOriginValue = c.getExcelOriginValue();
					
					//columnMaxWithTracer.trace(columnIndex, excelOriginValue);
					columnMaxWithFix.trace(columnIndex);
					
					if(StringUtils.isEmpty(error)) {//这个cell没有错误
						Label label = new Label(columnIndex, rowIndex, excelOriginValue);
						sheet.addCell(label);
					}else {//这个cell有错误
						if(StringUtils.isEmpty(oneRowError)) {
							oneRowError = error;
						}else {
							oneRowError =  oneRowError +"|" +error;
						}
						Label label = new Label(columnIndex, rowIndex, excelOriginValue);
						WritableCellFormat format = createErrorCellFormat();
						if(format != null) {
							label.setCellFormat(format);							
						}
						sheet.addCell(label);
					}
				}
				
				//渲染错误信息
				if(StringUtils.isNotEmpty(oneRowError)) {
					columnIndex ++;
					Label label = new Label(columnIndex, rowIndex, oneRowError);
					WritableCellFormat format = createErrorTipsCellFormat();
					if(format != null) {
						label.setCellFormat(format);							
					}
					sheet.addCell(label);
				}
				
				//表格列宽度调整
				columnMaxWithFix.fixColumnWidth(sheet);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// 写入数据并关闭文件
			if (book != null) {
				try {
					book.write();
					book.close();
				} catch (Exception e) {
					log.error("",e);
				}
			}
		}
		return file;
	}
	
	/**
	 * @description	： 创建普通表格头cell格式
	 * @param mustInput
	 * @return
	 */
	private static WritableCellFormat createHeaderCellFormat(boolean mustInput) {
		try {
			WritableCellFormat wcf = new WritableCellFormat();
			WritableFont wf = new WritableFont(WritableFont.ARIAL);
			wf.setBoldStyle(WritableFont.BOLD);
			wf.setPointSize(10);
			if(mustInput) {
				wf.setColour(Colour.RED);				
			}else{
				wf.setColour(Colour.WHITE);
			}
			wcf.setFont(wf);
			wcf.setAlignment(Alignment.CENTRE);
			wcf.setBackground(Colour.GREEN);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);   
			wcf.setWrap(false);
			return wcf;
		}catch (Exception e) {
			log.error("",e);
			return null;
		}
	}
	/**
	 * @description	： 创建普通表格头cell格式
	 * @return
	 */
	private static WritableCellFormat createTipsHeaderCellFormat() {
		try {
			WritableCellFormat wcf = new WritableCellFormat();
			WritableFont wf = new WritableFont(WritableFont.ARIAL);
			wf.setBoldStyle(WritableFont.BOLD);
			wf.setPointSize(10);
			wf.setColour(Colour.RED);
			wcf.setFont(wf);
			wcf.setAlignment(Alignment.CENTRE);
			wcf.setBackground(Colour.YELLOW);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);   
			wcf.setWrap(false);
			return wcf;
		}catch (Exception e) {
			log.error("",e);
			return null;
		}
	}
	/**
	 * @description	： 创建错误tips的cell的cell格式
	 * @return
	 */
	private static WritableCellFormat createErrorTipsCellFormat() {
		try {
			WritableCellFormat wcf = new WritableCellFormat();
			WritableFont wf = new WritableFont(WritableFont.ARIAL);
			wf.setBoldStyle(WritableFont.BOLD);
			wf.setPointSize(10);
			wf.setColour(Colour.RED);
			wcf.setFont(wf);
			wcf.setBackground(Colour.YELLOW);
			wcf.setAlignment(Alignment.LEFT);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);   
			wcf.setWrap(true);
			return wcf;
		}catch (Exception e) {
			log.error("",e);
			return null;
		}
	}
	/**
	 * @description	： 创建错误cell的cell格式
	 * @return
	 */
	private static WritableCellFormat createErrorCellFormat() {
		try {
			WritableCellFormat wcf = new WritableCellFormat();
			WritableFont wf = new WritableFont(WritableFont.ARIAL);
			wf.setBoldStyle(WritableFont.BOLD);
			wf.setPointSize(10);
			wcf.setFont(wf);
			wcf.setAlignment(Alignment.LEFT);
			wcf.setBackground(Colour.YELLOW);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);   
			wcf.setWrap(true);
			return wcf;
		}catch (Exception e) {
			log.error("",e);
			return null;
		}
	}
	
	private static class ColumnAutoWithFix{
		
		//默认列宽度
		private Integer defaultColumnSize;
		//错误提示列宽度
		private Integer errorTipColumnSize;
		
		private Map<Integer/*列号*/,Integer/*最大字符个数*/> map = new HashMap<Integer,Integer>();
		
		public ColumnAutoWithFix(Integer defaultColumnSize, Integer errorTipColumnSize) {
			this.defaultColumnSize = defaultColumnSize;
			this.errorTipColumnSize = errorTipColumnSize;
		}

		public void trace(Integer column) {
			map.put(column, defaultColumnSize);
		}
		//TODO
		public void trace(Integer column,String text) {
			Integer newSize = text.length();
			Integer oldMaxSize = map.get(column);
			if(oldMaxSize == null) {
				map.put(column, defaultColumnSize);
			}else {
				if(newSize > oldMaxSize) {
					map.put(column, newSize);
				}
			}
		}
		
		//TODO 如何准确设置每列的宽度，这个问题后续解决!!!
		public void fixColumnWidth(WritableSheet sheet) {
			int lastColumn = 0;
			Iterator<Entry<Integer, Integer>> it = map.entrySet().iterator();
			while(it.hasNext()) {
				Entry<Integer, Integer> e = it.next();
				Integer column = e.getKey();
				Integer maxSize = e.getValue();
				sheet.setColumnView(column.intValue(), maxSize.intValue());
				
				lastColumn = column.intValue();
			}
			
			//最后一列是错误列
			sheet.setColumnView(lastColumn, errorTipColumnSize.intValue());
		}
	}
}
