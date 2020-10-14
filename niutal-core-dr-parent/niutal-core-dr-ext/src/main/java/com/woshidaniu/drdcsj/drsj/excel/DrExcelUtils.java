/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrFzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.svcinterface.impl.SfbtColumnSwitchProcessor;

/**
 * @author kzd
 *         通用导入excel操作工具
 *         实现方式 1.jxl(不支持2007版本,废弃使用) 2.poi(支持2007)
 * @author zhidong
 * 		   1.去除无用的代码，比如无聊的空指针判断
 * 		   2.文件流异常，凡是关闭的地方遇到异常，添加日志
 */
public final class DrExcelUtils {

	private static final Logger log = LoggerFactory.getLogger(DrExcelUtils.class);
	//允许的连续的空白行个数,最多只探测20行
	private static final int MAX_BLANK_ROW_COUNT = 32;

	private static final int MAX_POPULATE_ROW_SIZE = 1024;
	
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
				//strCell = StringUtils.trimAllWhitespace(originStr);
				strCell = originStr.trim();
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
				String contents = DrExcelUtils.getStringCellValue(sheet.getRow(i).getCell(j));
				if (StringUtils.isBlank(contents)) {
					nullValueCellNum++;
				}
			}
			if (nullValueCellNum == columns) {
				correctRows--;
			}
		}
		return correctRows;
	}

	/**
	 * @param file excel file object
	 * @param notNullL 必须存在列集合（对应错误提示信息）
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> checkExcel(File file, String fileName, Map<String, String> notNullL)throws Exception {
		Map<String, String> message = new HashMap<String, String>();
		Workbook book = DrExcelUtils.getWorkbook(file);
		//Sheet sheet = book.getSheet(DATA_SHEET_NAME);
		Sheet sheet = book.getSheetAt(0);
		int columns = sheet.getRow(0).getPhysicalNumberOfCells();
		// 获取导入所有列名称
		Map<String, String> lmcIndex = DrExcelUtils.getLmc(columns, sheet);
		// 验证必须存在的列配置
		Iterator<String> it = notNullL.keySet().iterator();
		String lmc = null;
		while (it.hasNext()) {
			lmc = it.next();
			// 不存在对应列
			if (StringUtils.isEmpty(lmcIndex.get(lmc))) {
				// 设置列对应错误提示信息
				message.put(lmc, notNullL.get(lmc));
			}
		}
		return message;
	}

	/**
	 * @param excel
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private static Workbook getWorkbook(File excel) throws IOException {
		Workbook book = null;
		String fileName = excel.getName();
		// 获取文件工作表
		if (fileName.toLowerCase().endsWith("xls")) {
			book = new HSSFWorkbook(new FileInputStream(excel));
		} else if (fileName.toLowerCase().endsWith("xlsx")) {
			book = new XSSFWorkbook(new FileInputStream(excel));
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
	public static List<Map<String/*头*/,String/*数据*/>> readerExcelByRow(File file) {

		Workbook book = null;
		// 工作簿
		Sheet sheet = null;
		//数据
		List<Map<String,String>> data = new LinkedList<Map<String,String>>();
		try {
			book = DrExcelUtils.getWorkbook(file);
			// 获得第一个工作表对象
			sheet = book.getSheetAt(0);
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
				
				Map<String,String> oneRowData = new LinkedHashMap<String,String>(columnum);
				
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
					oneRowData.put(h, v);
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
					data.add(oneRowData);		
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
	
	/**
	 * 根据ExcelFile 获取 数据列表 简单版
	 * @param file excel文件对象
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static Map<String, List<String>> readerExcelOldStyle(File file) {
		//按行读之后再转换成列
		Workbook book = null;
		// 工作簿
		Sheet sheet = null;
		// 创建返回数据集
		Map<String, List<String>> data = new LinkedHashMap<String, List<String>>();
		try {
			book = DrExcelUtils.getWorkbook(file);
			// 获得第一个工作表对象
			sheet = book.getSheetAt(0);
			// 得到列数
			int columnum = sheet.getRow(0).getPhysicalNumberOfCells();
			// 得到行数
			int rownum = sheet.getPhysicalNumberOfRows();
			// 单元格
			Cell cell = null;
			for (int i = 0; i < columnum; i++) {
				List<String> list = new LinkedList<String>();
				for (int j = 0; j < rownum; j++) {
					cell = sheet.getRow(j).getCell(i);
					list.add(getStringCellValue(cell));
				}

				String cloumnNameCN = getStringCellValue(sheet.getRow(0).getCell(i));
				if (StringUtils.isNotBlank(cloumnNameCN)) {
					data.put(cloumnNameCN, list);
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

	/**
	 * 根据ExcelFile 获取 数据列表 简单版
	 * @param file excel文件对象
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static Map<String/**头**/, List<String>/**这一列的数据**/> readerExcel(File file) {
		
		//结果
		Map<String,List<String>> resultDataMap = new LinkedHashMap<String,List<String>>();
		
		//按行读
		List<Map<String/*头*/,String/*数据*/>> dataList = readerExcelByRow(file);

		if(dataList.isEmpty()) {
			//如果发现按行读为空，则需要FIX适配旧风格的输出,当然，这样的情况比较少，效率低也可接受
			List<String> headers = readerExcelHeader(file);
			//填充每一列的list
			for(String header : headers) {
				
				//这一列的数据列的第一个是头
				List<String> list = new LinkedList<String>();
				list.add(header);
				
				resultDataMap.put(header,list);
			}
			return resultDataMap;
		}else {
			//取第一行的头作为头
			Map<String,String> firstRowData = dataList.get(0);
			
			Set<String> headers = firstRowData.keySet();
			//填充每一列的list
			for(String header : headers) {
				
				//这一列的数据列的第一个是头
				List<String> list = new LinkedList<String>();
				list.add(header);
				
				resultDataMap.put(header,list);
			}
			
			//一行一行来
			for(Map<String,String> oneRow : dataList) {
				for(String header : headers) {
					String value = oneRow.get(header);
					List<String> list =  resultDataMap.get(header);
					list.add(value);
				}
			}
			
			//返回结果
			return resultDataMap;
		}
	}

	private static List<String> readerExcelHeader(File file) {
		Workbook book = null;
		// 工作簿
		Sheet sheet = null;
		//头
		List<String> resultHeaders = new LinkedList<String>();
		try {
			book = DrExcelUtils.getWorkbook(file);
			// 获得第一个工作表对象
			sheet = book.getSheetAt(0);
			// 得到列数
			int columnum = sheet.getRow(0).getPhysicalNumberOfCells();
			// 得到行数
			int rownum = sheet.getPhysicalNumberOfRows();
			if(rownum > 0) {
				//第一个行
				Row firstRow = sheet.getRow(0);
				for(int i=0;i<columnum;i++) {
					Cell cell = firstRow.getCell(i);
					String h = getStringCellValue(cell);
					if(!isBlank(h)) {
						resultHeaders.add(h);
					}else {
						log.trace("第{}列是空白头",i);
					}
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
		return resultHeaders;
	}

	/**
	 * 获取所有列名称
	 * @param columnum 总列数
	 * @param sheet 当前sheet
	 * @return Map<String, String> 使用列key即可
	 */
	private static Map<String, String> getLmc(int columnum, Sheet sheet) {
		Map<String, String> map = new HashMap<String, String>();
		Cell cell = null;
		// 读取excel第一行列名称
		for (int i = 0; i < columnum; i++) {
			cell = sheet.getRow(0).getCell(i);
			map.put(DrExcelUtils.getStringCellValue(cell), DrExcelUtils.getStringCellValue(cell));
			// map.put(cell.getStringCellValue(), cell.getStringCellValue());
		}
		return map;
	}

	/**
	 * 填充模板数据到Excel文件,构成模板文件
	 * @param drpzModel
	 * @param dataList
	 * @param comments
	 * @param fzData
	 * @param file
	 * @return
	 */
	public static void renderTemplate(File xlsFile,DrpzModel drpzModel, List<DrlpzModel> drlpzModelList,Map<String, List<Map<String, String>>> fzData) {
		Workbook book = null;
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(xlsFile);
			book = new HSSFWorkbook();
			Sheet sheet = book.createSheet(DATA_SHEET_NAME);
			
			//填充主sheet的空白数据单元格,这样用户输入的就全部是字符串形式
			populateBlankDataExcel(book,sheet,0,drlpzModelList.size(),MAX_POPULATE_ROW_SIZE);
			
			CreationHelper factory = book.getCreationHelper();
			Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
			Row row = sheet.createRow(0);

			for (int i = 0; i < drlpzModelList.size(); i++) {

				//一个列，一个样式
				final CellStyle cellStyle = book.createCellStyle();
				cellStyle.setAlignment(HorizontalAlignment.CENTER);
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

				DrlpzModel drlpzModel = drlpzModelList.get(i);
				final Cell cell = row.createCell(i, CellType.STRING);

				//导入列名称
				cell.setCellValue(drlpzModel.getDrlmc());

				//先设置成白色
				final Font cellFont = book.createFont();
				cellFont.setColor(IndexedColors.WHITE.getIndex());
				cellStyle.setFont(cellFont);

				//若导入列名称必填，再将文本颜色变成红色
				final int sfbtColumnFlag = drlpzModel.getSfbtFlag();
				SfbtColumnSwitchProcessor sfbtColumnSwitchProcessor = new SfbtColumnSwitchProcessor() {
					private boolean appended = false;
					@Override
					protected void ifBtOnInsert() {
						if(!appended) {
							cellFont.setColor(IndexedColors.RED.getIndex());
							appended = true;
						}
					}
					@Override
					protected void ifBtOnUpdate() {
						if(!appended) {
							cellFont.setColor(IndexedColors.RED.getIndex());
							appended = true;
						}
					}

					@Override
					protected void ifBtOnInsertAndUpdate() {
						if(!appended) {
							cellFont.setColor(IndexedColors.RED.getIndex());
							appended = true;
						}
					}
				};
				sfbtColumnSwitchProcessor.process(sfbtColumnFlag);
				//......
				//设置下拉选项
				List<String> dropdownValues = drlpzModel.getDropdownValues();
				if (dropdownValues != null && dropdownValues.size() > 0) {
					// String[] dvs = dropdownValues.toArray(new String[]{});
					String hiddenSheetName = "hidden_" + i;
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
					CellRangeAddressList addressList = new CellRangeAddressList(1,SpreadsheetVersion.EXCEL2007.getMaxRows() - 1, i, i);
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

				String constraintMessage = drlpzModel.getConstraintMessage();
				cellComment.setString(factory.createRichTextString(constraintMessage));
				cell.setCellComment(cellComment);
			}

			// 创建辅助sheet
			if (fzData != null && fzData.size() > 0) {
				Set<String> keySet = fzData.keySet();
				for (String key : keySet) {
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
			}//end of 创建辅助sheet
			book.write(fileOut);
		} catch (Exception e) {
			log.error("创建导出模板异常",e);
			throw new RuntimeException("创建导出模板失败！" + e.getMessage(), e);
		} finally {
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
	}

	/**
	 * 渲染空白数据到Excel
	 * @param book
	 * @param sheet
	 * @param startRowNo 开始行号
	 * @param columnSize 列的个数
	 * @param rowSize 行的个数
	 */
	private static void populateBlankDataExcel(Workbook book,Sheet sheet,int startRowNo,int columnSize, int rowSize) {
		
		final CellStyle textStyle = book.createCellStyle();
		textStyle.setAlignment(HorizontalAlignment.RIGHT);
		DataFormat format = book.createDataFormat();
		textStyle.setDataFormat(format.getFormat("@"));
		
		for(int rowNo = startRowNo;rowNo<rowSize;rowNo++) {
			Row row = sheet.getRow(rowNo);
			if(row == null) {
				row = sheet.createRow(rowNo);
			}
			for(int columnNo = 0;columnNo < columnSize;columnNo++) {
				Cell cell = row.getCell(columnNo);
				if(cell == null) {
					cell = row.createCell(columnNo);
				}
				cell.setCellType(CellType.STRING);
				cell.setCellStyle(textStyle);
			}
		}
	}

	public static void renderTemplate(File file, DrpzModel drpzModel, List<DrlpzModel> drlpzModelList,List<DrFzModel> drfzModelList) {
		// 获取列名称
		Map<String, List<Map<String, String>>> fzData = new HashMap<String, List<Map<String, String>>>();
		Map<String, List<String>> fzDropDownList = new HashMap<String, List<String>>();
		// 辅助信息
		if (drfzModelList != null && drfzModelList.size() > 0) {
			for (DrFzModel drfzModel : drfzModelList) {
				String drl = drfzModel.getDrl();
				// 名称
				String fzmc = drfzModel.getFzmc();
				// 数据抓取配置
				String pz = drfzModel.getPz();
				// 方式
				String type = StringUtils.split(pz, ":", 2)[0];
				// 目标
				String source = StringUtils.split(pz, ":", 2)[1];
				DrfzQuery fzQuery = new DrfzQuery(type, source);

				if (StringUtils.isBlank(drl)) {
					fzData.put(fzmc, fzQuery.queryFzData());
				} else {
					fzDropDownList.put(drl, fzQuery.queryFzDrlData());
				}
			}
		}

		for (DrlpzModel model : drlpzModelList) {
			model.setConstraintMessage(DrExcelUtils.getComments(drpzModel, model));
			model.setDropdownValues(fzDropDownList.get(model.getDrl()));
		}

		DrExcelUtils.renderTemplate(file, drpzModel, drlpzModelList,fzData);
	}

	/**
	 * 给字段标题添加提示信息
	 * @param dm
	 * @return
	 */
	private static String getComments(DrpzModel drpzModel,DrlpzModel dm) {
		String sfzj = dm.getSfzj();
		String zdcd = dm.getZdcd();
		//FIXME 不得已用这个AtomicInteger，方便递增
		final AtomicInteger index = new AtomicInteger(1);
		final StringBuffer comments = new StringBuffer();
		if (ImportConfig._SFZJ_YES.equals(sfzj)) {
			comments.append(index.getAndIncrement()).append(". ").append("不能重复; \n");
		}
		final String crfs = drpzModel.getCrfs();
		int sfbtColumnFlag = dm.getSfbtFlag();
		final String notNullComment = ". 不可为空; \n";
		// 验证当前字段是否必填
		SfbtColumnSwitchProcessor switchProcessor = new SfbtColumnSwitchProcessor() {

			private boolean appended = false;
			@Override
			protected void ifBtOnInsert() {
				if(!appended) {
					comments.append(index.getAndIncrement()).append(notNullComment);
					appended = true;
				}
			}
			@Override
			protected void ifBtOnUpdate() {
				if(!appended) {
					comments.append(index.getAndIncrement()).append(notNullComment);
					appended = true;
				}
			}
			@Override
			protected void ifBtOnInsertAndUpdate() {
				if(!appended) {
					comments.append(index.getAndIncrement()).append(notNullComment);
					appended = true;
				}
			}
		};
		switchProcessor.process(crfs, sfbtColumnFlag);

		if (StringUtils.isNotBlank(zdcd)) {
			comments.append(index.getAndIncrement()).append(". ").append("最大长度为" + zdcd + ";");
		}
		return comments.toString();
	}
}
