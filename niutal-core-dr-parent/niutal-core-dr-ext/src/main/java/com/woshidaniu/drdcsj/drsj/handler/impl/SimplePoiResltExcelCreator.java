/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.excel.DrfzQuery;
import com.woshidaniu.drdcsj.drsj.handler.HandlerContext;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelCell;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;

/**
 * @author 康康（1571）
 * 简单poi结果创建器,带有下拉列表选项
 */
/**public**/ final class SimplePoiResltExcelCreator extends SimpleResultExcelBase{
	
	protected static final Logger log = LoggerFactory.getLogger(SimplePoiResltExcelCreator.class);
	//每列宽度
	private static final int columnWidth = 20;
	
	private Map<Drawing<?>,CellStyle> cellStyleDataCellCacheForError = new HashMap<Drawing<?>,CellStyle>();
	private Map<Drawing<?>,CellStyle> cellStyleDateCellCacheForNotError = new HashMap<Drawing<?>,CellStyle>();
	
	private Map<Drawing<?>,CellStyle> cellStyleHeaderCellCacheForMustInput = new HashMap<Drawing<?>,CellStyle>();
	private Map<Drawing<?>,CellStyle> cellStyleHeaderCellCacheForNotMustInput = new HashMap<Drawing<?>,CellStyle>();

	/**
	 * 创建汇总结果文件
	 * 特性
	 * 1.定位错误cell
	 * 2.数据列宽度设置
	 * @param context
	 * @param unacceptRows
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public File createResultExcel(final HandlerContext context,final List<ExcelRow> unacceptRows,final File file) throws Exception{
		
		//下拉选项的数据
		Map<String, List<Map<String, String>>> fzData = (Map<String, List<Map<String, String>>>) context.getAttr(RenderDrpzl2ExcelCellHandler.FZ_DATA_KEY);
		
		Workbook book = null;
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(file);
			book = new HSSFWorkbook();
			Sheet sheet = book.createSheet(DefaultSheetName);
			
			this.createHelpSheet(book,fzData);
			
			CreationHelper factory = book.getCreationHelper();
			Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
			
			int drlpzIndex = 1;
			int rowIndex = -1;
			
			rowIndex ++;
			Row row = sheet.createRow(rowIndex);
			
			//表格头部行
			Iterator<ExcelRow> it_header = unacceptRows.iterator();
			if(it_header.hasNext()) {
				ExcelRow firstRow = it_header.next();
				List<ExcelCell> cells =  firstRow.getCells();
				int columnIndex = -1;
				for(ExcelCell c : cells) {
					drlpzIndex++;
					columnIndex ++;
					//是否必填
					boolean mustInput = false;
					DrlpzModel drlpz = c.getDrlpzModel();
					
					//render辅助列信息
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
						}else {
							//不必处理其他情况
						}
					}
					//表格单元
					createPoiHeaderCell(book,sheet,row,rowIndex,columnIndex,mustInput,drlpz,drlpzIndex,factory,drawingPatriarch,fzData);
				}
			}
			//表格数据行
			Iterator<ExcelRow> it_all_rows = unacceptRows.iterator();
			while(it_all_rows.hasNext()) {
				
				rowIndex ++;
				Row dataRow = sheet.createRow(rowIndex);
				
				ExcelRow excelRow = it_all_rows.next();
				int columnIndex = -1;
				List<ExcelCell> oneColumnCells = excelRow.getCells();
				for(ExcelCell excelCell : oneColumnCells) {
					columnIndex ++;
					createPoiDataCell(book,dataRow,columnIndex,excelCell,factory,drawingPatriarch);
				}
			}
			//写数据
			book.write(fileOut);
		}catch (Exception e) {
			throw e;
		}finally {
			if(book != null) {
				try {
					book.close();
				} catch (IOException e) {
					log.error("关闭Workbook异常",e);
				}
			}
			if(fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					log.error("关闭fileOut异常",e);
				}
			}
		}
		return file;
	}

	/**
	 * 创建辅助sheet
	 * @param book
	 * @param fzData
	 */
	private void createHelpSheet(Workbook book,Map<String, List<Map<String, String>>> fzData) {
		if (fzData != null && fzData.size() > 0) {
			Set<String> keySet = fzData.keySet();
			for (String key : keySet) {
				log.debug("创建辅助sheet:[{}]",key);
				List<Map<String, String>> fzlist = fzData.get(key);
				Sheet fzSheet = book.createSheet(key);
				Row fzRow = fzSheet.createRow(0);
				CellStyle fzcellStyle = book.createCellStyle();
				Font fzcellFont = book.createFont();
				// fzcellFont.setBold(Boolean.TRUE);
				fzcellStyle.setFont(fzcellFont);
				fzcellStyle.setAlignment(HorizontalAlignment.CENTER);
				fzcellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				fzcellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				fzcellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				Cell fzCellDM = fzRow.createCell(0, CellType.STRING);
				Cell fzCellMC = fzRow.createCell(1, CellType.STRING);
				fzCellDM.setCellStyle(fzcellStyle);
				fzCellMC.setCellStyle(fzcellStyle);
				for (int i = 1; i <= fzlist.size(); i++) {
					Row fzdataRow = fzSheet.createRow(i);
					fzdataRow.createCell(0, CellType.STRING).setCellValue(fzlist.get(i - 1).get(DrfzQuery.DM));
					fzdataRow.createCell(1, CellType.STRING).setCellValue(fzlist.get(i - 1).get(DrfzQuery.MC));
				}
			}
		}
	}

	private void createPoiDataCell(Workbook book,Row dataRow,int columnIndex,ExcelCell excelCell,CreationHelper factory, Drawing<?> drawingPatriarch) {
		final Cell cell = dataRow.createCell(columnIndex, CellType.STRING);
		
		//错误cell标识为背景红色
		String error = excelCell.getError();
		if(StringUtils.isNotEmpty(error)) {
			//style
			CellStyle cellStyle = this.cellStyleDataCellCacheForError.get(drawingPatriarch);
			if(cellStyle == null) {
				cellStyle = book.createCellStyle();
				cellStyle.setAlignment(HorizontalAlignment.LEFT);
				cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				
				this.cellStyleDataCellCacheForError.put(drawingPatriarch, cellStyle);
			}
			
			ClientAnchor clientAnchor = factory.createClientAnchor();
			clientAnchor.setCol1(cell.getColumnIndex());
			clientAnchor.setCol2(cell.getColumnIndex() + 1);
			clientAnchor.setRow1(dataRow.getRowNum());
			clientAnchor.setRow2(dataRow.getRowNum() + 3);
			Comment cellComment = drawingPatriarch.createCellComment(clientAnchor);
			cellComment.setString(factory.createRichTextString(error));//错误信息
			cell.setCellComment(cellComment);
			cell.setCellStyle(cellStyle);
		}else {
			//style
			CellStyle cellStyle = this.cellStyleDateCellCacheForNotError.get(drawingPatriarch);
			if(cellStyle == null) {
				cellStyle = book.createCellStyle();
				cellStyle.setAlignment(HorizontalAlignment.LEFT);
				
				this.cellStyleDateCellCacheForNotError.put(drawingPatriarch, cellStyle);
			}
			cell.setCellStyle(cellStyle);
		}
		
		//value
		String excelValue = excelCell.getExcelOriginValue(); 
		cell.setCellValue(excelValue);
	}

	private void createPoiHeaderCell(Workbook book,Sheet sheet,Row row,int rowIndex, int columnIndex, boolean mustInput, DrlpzModel drlpz,int drlpzIndex,CreationHelper factory,Drawing<?> drawingPatriarch,Map<String, List<Map<String, String>>> fzData) {
		//一个列，一个样式
		CellStyle cellStyle = null;
		
		//若导入列名称必填，文本颜色红色
		if(mustInput) {
			cellStyle = this.cellStyleHeaderCellCacheForMustInput.get(drawingPatriarch);
			if(cellStyle == null) {
				
				cellStyle = book.createCellStyle();
				
				cellStyle.setAlignment(HorizontalAlignment.CENTER);
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				
				Font cellFont = book.createFont();
				cellFont.setColor(IndexedColors.RED.getIndex());
				cellStyle.setFont(cellFont);
				
				cellStyleHeaderCellCacheForMustInput.put(drawingPatriarch, cellStyle);
			}
		}else {
			cellStyle = this.cellStyleHeaderCellCacheForNotMustInput.get(drawingPatriarch);
			if(cellStyle == null) {
				
				cellStyle = book.createCellStyle();
				
				cellStyle.setAlignment(HorizontalAlignment.CENTER);
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				
				Font cellFont = book.createFont();
				cellFont.setColor(IndexedColors.RED.getIndex());
				cellStyle.setFont(cellFont);
				
				cellStyleHeaderCellCacheForMustInput.put(drawingPatriarch, cellStyle);
			}
		}

		final Cell cell = row.createCell(columnIndex, CellType.STRING);
		sheet.setColumnWidth(columnIndex, columnWidth*256);
		
		//导入列名称
		String drlmc = drlpz.getDrlmc();
		cell.setCellValue(drlmc);
		
		//设置下拉选项
		List<String> dropdownValues = drlpz.getDropdownValues();
		if (dropdownValues != null && dropdownValues.size() > 0) {
			// String[] dvs = dropdownValues.toArray(new String[]{});
			String hiddenSheetName = "hidden_" + columnIndex;
			Sheet hidden = book.createSheet(hiddenSheetName);
			int hiddenSheetIndex = book.getSheetIndex(hidden);
			book.setSheetHidden(hiddenSheetIndex, true);
			for (int j = 0; j < dropdownValues.size(); j++) {
				String dv = dropdownValues.get(j);
				Row dvRow = hidden.createRow(j);
				Cell dvCell = dvRow.createCell(0);
				dvCell.setCellValue(dv);
			}
			Name namedCell = book.createName();
			namedCell.setNameName(hiddenSheetName);
			namedCell.setRefersToFormula(hiddenSheetName + "!$A$1:$A$" + dropdownValues.size());

			DataValidationHelper helper = sheet.getDataValidationHelper();
			CellRangeAddressList addressList = new CellRangeAddressList(1,SpreadsheetVersion.EXCEL2007.getMaxRows() - 1, columnIndex, columnIndex);
			DataValidationConstraint constraint = helper.createFormulaListConstraint(hiddenSheetName);
			DataValidation dataValidation = helper.createValidation(constraint, addressList);
			if (dataValidation instanceof XSSFDataValidation) {
				dataValidation.setSuppressDropDownArrow(true);
				dataValidation.setShowErrorBox(true);
			} else {
				dataValidation.setSuppressDropDownArrow(false);
			}
			sheet.addValidationData(dataValidation);
		}
		//......
		cell.setCellStyle(cellStyle);
		ClientAnchor clientAnchor = factory.createClientAnchor();
		clientAnchor.setCol1(cell.getColumnIndex());
		clientAnchor.setCol2(cell.getColumnIndex() + 1);
		clientAnchor.setRow1(row.getRowNum());
		clientAnchor.setRow2(row.getRowNum() + 3);
		Comment cellComment = drawingPatriarch.createCellComment(clientAnchor);
		
		String constraintMessage = drlpz.getConstraintMessage();
		cellComment.setString(factory.createRichTextString(constraintMessage));
		cell.setCellComment(cellComment);
		
	}
}
