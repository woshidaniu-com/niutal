/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.excel;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.drdcsj.drsj.comm.ImportErrorMessage;

/**
 * Excel操作工具
 * @author 982
 */
public class ExcelUtils {
	
	private static final Log log = LogFactory.getLog(ExcelUtils.class);
	/**
	 * 获取数据行数,jxl读取数据有bug
	 * @param sheet
	 * @return
	 */
	public static int getCorrectRows(Sheet sheet){
		if(sheet == null){
			return 0;
		}
		int rows = sheet.getRows();
		int columns = sheet.getColumns();
		int nullValueCellNum;
		int correctRows = rows;
		for (int i = 0; i < rows; i++) {
			nullValueCellNum = 0;
			for (int j = 0; j < columns; j++) {
				String contents = sheet.getCell(j, i).getContents();
				if(StringUtils.isBlank(contents))
					nullValueCellNum++;
			}
			if(nullValueCellNum == columns){
				correctRows--;
			}
		}
		return correctRows;
	}
	
	/**
	 * 根据ExcelFile 获取 数据列表 简单版
	 * @param file excel文件对象
	 * @return
	 */
	public static Map<String, List<String>> readerExcel(File file) {
		if (file == null) {
			throw new IllegalArgumentException("找不到文件!");
		}
		Workbook book = null;
		// 工作簿
		Sheet sheet = null;
		// 创建返回数据集
		Map<String, List<String>> data = new LinkedHashMap<String, List<String>>();
		try {
			// 获取文件工作表
			// if (book == null) {// 检测excel时一般会已经读取，避免重复读取
			book = Workbook.getWorkbook(file);
			// }
			// 获得第一个工作表对象
			sheet = book.getSheet(0);
			// 得到列数
			int columnum = sheet.getColumns();
			// 得到行数
			int rownum = getCorrectRows(sheet);
			// 单元格
			Cell cell = null;
			for (int i = 0; i < columnum; i++) {
				List<String> list = new LinkedList<String>();
				for (int j = 0; j < rownum; j++) {
					cell = sheet.getCell(i, j);
					list.add(cell.getContents());
				}
				if (!"".equals(sheet.getCell(i, 0).getContents())) {
					data.put(sheet.getCell(i, 0).getContents(), list);
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage(),e);
			throw new RuntimeException("读取excel失败!" + e.getMessage(), e);
		} finally {
			if (book != null) {
				book.close();
			}
		}
		return data;
	}

	public static File createErrorExcel(Map<String, List<String>> map, File file,ImportErrorMessage importMap) {
		if (map == null || map.isEmpty()) {
			return file;
		}
		// 打开文件
		WritableWorkbook book = null;
		// 生成名为工作表
		WritableSheet sheet = null;
		// 前冲表格内容单元格内容
		Label label = null;
		// 获取工作表名称
		String sheetName = "error";
		try {
			
			WritableCellFormat wcf = new WritableCellFormat();
			WritableFont wf = new WritableFont(WritableFont.ARIAL);
			wf.setBoldStyle(WritableFont.BOLD);
			wf.setPointSize(10);
			wf.setColour(Colour.WHITE);
			wcf.setFont(wf);
			wcf.setAlignment(Alignment.LEFT);
			wcf.setBackground(Colour.GREEN);
			 //设置垂直居中;   
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);   
			wcf.setWrap(false);
			
			book = Workbook.createWorkbook(file);
			sheet = book.createSheet(sheetName, 0);
			int i = 0;
			for (Iterator<Entry<String, List<String>>> it = map.entrySet().iterator(); it.hasNext(); i++) {
				Entry<String, List<String>> entry = it.next();
				int j = 0;
				for (String v : entry.getValue()) {
					if(j == 0){
						label = new Label(i, j, v, wcf);
					}else{
						label = new Label(i, j, v);
					}
					// 将定义好的单元格添加到工作表中
					sheet.addCell(label);
					j++;
					if (i + 1 >= map.size()) {// 如果是最后一列
						if (j == 1) {
							// 错误信息列头
							WritableCellFormat wcf2 = new WritableCellFormat();
							WritableFont wf2 = new WritableFont(WritableFont.ARIAL);
							wf2.setBoldStyle(WritableFont.BOLD);
							wf2.setPointSize(10);
							wf2.setColour(Colour.WHITE);
							wcf2.setFont(wf2);
							wcf2.setAlignment(Alignment.LEFT);
							wcf2.setBackground(Colour.RED);
							 //设置垂直居中;   
							wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);   
							wcf2.setWrap(false);
							label = new Label(map.size(), j - 1, "错误信息", wcf2);
							sheet.addCell(label);
						}
						// 每行末尾列填写错误信息
						WritableCellFormat wcf3 = new WritableCellFormat();
						wcf3.setBackground(Colour.YELLOW);
						String string = importMap.get(j);
						label = new Label(map.size(), j, string);
						if(StringUtils.isNotBlank(string)){	
							label.setCellFormat(wcf3);
						}
						
						sheet.addCell(label);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("创建excel失败" + e.getMessage(), e);
		} finally {
			// 写入数据并关闭文件
			if (book != null) {
				try {
					book.write();
					book.close();
				} catch (Exception e) {
					throw new RuntimeException("生成excel失败" + e.getMessage(), e);
				}
			}
		}
		return file;
	}
}
