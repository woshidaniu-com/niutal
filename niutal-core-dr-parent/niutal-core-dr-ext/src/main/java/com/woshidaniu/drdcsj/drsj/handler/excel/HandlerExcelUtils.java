/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.StringUtils;

/**
 * @author zhidong
 * 
 * 用于读取excel文件，一行一行读取
 */
public final class HandlerExcelUtils {
	
	private static final Logger log = LoggerFactory.getLogger(HandlerExcelUtils.class);
	
	//允许的连续的空白行个数,最多只探测20行
	private static final int MAX_BLANK_ROW_COUNT = 32;
	
	private static final String DATA_SHEET_NAME = "import";

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * @param cell Excel单元格
	 * @return String 单元格数据内容
	 */
	private static String getStringCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		String strCell = "";
		switch (cell.getCellTypeEnum()) {
		case STRING: // 字符串
			String originStr = cell.getStringCellValue();
			if(originStr != null) {
				strCell = StringUtils.trimAllWhitespace(originStr);
			}
			break;
		case NUMERIC: // 数字
			cell.setCellType(CellType.STRING);
			/*
			 * // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串 String value
			 * = cell.getStringCellValue(); if (value.indexOf(".") > -1) { val =
			 * String.valueOf(new Double(temp)).trim(); } else { val =
			 * temp.trim(); }
			 */
			strCell = new BigDecimal(cell.getStringCellValue()).toPlainString(); // 数字格式,避免出現科学符号
			break;
		case BOOLEAN: // Boolean
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case BLANK: // 空值
			strCell = "";
			break;
		case ERROR: // 故障
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		return strCell;
	}

	/**
	 * 获取数据行数
	 * @param sheet
	 * @return
	 */
	public static int getCorrectRows(Sheet sheet) {
		if (sheet == null) {
			return 0;
		}
		int rows = sheet.getPhysicalNumberOfRows();
		int columns = sheet.getRow(0).getPhysicalNumberOfCells();
		int nullValueCellNum;
		int correctRows = rows;
		for (int i = 0; i < rows; i++) {
			nullValueCellNum = 0;
			for (int j = 0; j < columns; j++) {
				// String contents =
				// sheet.getRow(i).getCell(j).getStringCellValue();
				String contents = getStringCellValue(sheet.getRow(i).getCell(j));
				if (StringUtils.isBlank(contents))
					nullValueCellNum++;
			}
			if (nullValueCellNum == columns) {
				correctRows--;
			}
		}
		return correctRows;
	}

	private static Workbook getWorkbook(File file) throws IOException {
		Workbook book = null;
		String fileName = file.getName();
		InputStream excelStream = new FileInputStream(file);
		// 获取文件工作表
		if (fileName.toLowerCase().endsWith("xls")) {
			book = new HSSFWorkbook(excelStream);
		} else if (fileName.toLowerCase().endsWith("xlsx")) {
			book = new XSSFWorkbook(excelStream);
		}else {
			//can't not happen
		}
		return book;
	}
	
	/**
	 * 根据ExcelFile 获取 数据列表
	 * @param file excel文件对象
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static List<ExcelRow> readExcelRowByRow(File file) {

		Workbook book = null;
		// 工作簿
		Sheet sheet = null;
		//
		List<ExcelRow> data = new LinkedList<ExcelRow>();
		
		try {
			book = getWorkbook(file);
			// 获得第一个工作表对象
			sheet = book.getSheet(DATA_SHEET_NAME);
			// 得到列数
			int columnum = sheet.getRow(0).getPhysicalNumberOfCells();
			// 得到行数
			int rownum = sheet.getPhysicalNumberOfRows();
			
			//记录头部的列号和名称
			Map<Integer,String> headerColumn2Name = new LinkedHashMap<Integer,String>();
			
			//第一个行
			Row firstRow = sheet.getRow(0);
			for(int i=0;i<columnum;i++) {
				Cell cell = firstRow.getCell(i);
				String h = getStringCellValue(cell);
				if(!isBlank(h)) {
					headerColumn2Name.put(i, h);
				}else {
					log.trace("第{}列是空白头",i);
				}
			}
			//空白行个数
			int blankRowCount = 0;
			//其余各行
			for(int i=1;i<rownum;i++) {
				
				log.trace("reade rownum:{}",i);
				
				List<ExcelCell> cells = new ArrayList<ExcelCell>();
				
				//这一行的空白单元格个数
				int blankColumnValueCount = 0;
				
				Set<Entry<Integer, String>> headerSet = headerColumn2Name.entrySet();
				for(Entry<Integer, String> e : headerSet) {
					String h =e.getValue();
					Integer j = e.getKey();
					
					Cell cell = sheet.getRow(i).getCell(j);
					String v = getStringCellValue(cell);
					
					if(isBlank(v)) {
						blankColumnValueCount ++;
					}
					ExcelCell vc = new ExcelCell();
					vc.setIndex(j);
					vc.setExcelHeaderName(h);
					vc.setExcelCellValue(v);
					cells.add(vc);
				}
				
				if(blankColumnValueCount == headerSet.size()) {
					log.trace("空白行 rownum[{}]",i);
					blankRowCount++;
					if(blankRowCount >= MAX_BLANK_ROW_COUNT) {
						log.trace("发现连续{}行是空白行,判定文件读到末尾,提前结束",blankRowCount);
						break;
					}
				}else {
					//若发现不是空白行了，初始化为0
					blankRowCount = 0;
					ExcelRow validateRow = new ExcelRow(i,cells);
					data.add(validateRow);
				}
				
			}
		} catch (Exception e) {
			log.info("读取excel失败", e);
			throw new RuntimeException("读取excel失败!", e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (IOException e) {
					log.error("关闭Workbook异常",e);
				}
			}
		}
		return data;
	}
	
	private static boolean isBlank(String v) {
		if(v == null || "".equals(v)) {
			return true;			
		}else {
			String trimValue = v.trim();
			if("".equals(trimValue)) {
				return true;
			}
		}
		return false;
	}
}
