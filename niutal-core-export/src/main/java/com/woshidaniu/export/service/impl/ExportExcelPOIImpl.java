/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.service.impl;

import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportModel;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaokang
 * poi实现 流实现
 * 
 * @author zhidong
 * 代码整理优化
 */
@Service("exportExcelPOI")
public class ExportExcelPOIImpl extends AbstractExportService {
	
	private final String sheetNameConfigKey = "niutal.ExportExcelPOIImpl.sheetName";
	
	@Override
	protected String getSheetNameConfigKey() {
		return sheetNameConfigKey;
	}

	@Override
	protected File doGetExportFile(ExportModel model) throws Exception {
		String exportPhid = model.getExportPHID();
		String dcclbh = model.getDcclbh();
		Workbook wb = new SXSSFWorkbook(100);
		//设置头单元格显示格式
		CellStyle headCellStyle = wb.createCellStyle();
		Font headCellFont = wb.createFont();
		headCellFont.setBold(true);
		headCellFont.setFontHeightInPoints((short) 10);
		headCellFont.setColor(IndexedColors.WHITE.index);
		headCellStyle.setFont(headCellFont);
		headCellStyle.setAlignment(HorizontalAlignment.CENTER);
		headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		headCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headCellStyle.setWrapText(false);
		
		Sheet sheet = wb.createSheet(sheetName);
		//获取导出配置
		List<ExportConfigModel> configList = getConfigList(model);
		
		File file = null;
		FileOutputStream stream = null;
		try {
			this.writeExcel(model.getDataList(), configList, sheet);
			
			file = this.createFile(dcclbh,exportPhid,"xlsx");
			stream = new FileOutputStream(file);
			wb.write(stream);
		} catch (Exception ex) {
			log.error("导出异常,导出编号[{}],导出用户[{}]",model.getDcclbh(),model.getZgh(),ex);
			throw ex;
		}finally {
			if(wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					log.error("",e);
				}
			}
			if(stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					log.error("",e);
				}
			}
		}
		return file;
	}

	/**
	 * 写入EXCEL
	 * @param dataList
	 * @param configList
	 * @param ws
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	@SuppressWarnings("rawtypes")
	private void writeExcel(List dataList, List<ExportConfigModel> configList, Sheet ws) throws Exception {
		
		SXSSFSheet ssht = (SXSSFSheet)ws;
		
		Row headRow = ws.createRow(0);
		
		//表头写入
		int cellNum = 0;// 输出的第cellNum列
		for (ExportConfigModel config : configList) {
			if (SELECT_ZT.equals(config.getZt())) {
				SXSSFCell c = (SXSSFCell) headRow.createCell(cellNum, CellType.STRING);
				c.setCellValue(config.getZdmc());
				//导出的时候会有错误提示信息，取消表头样式后不会再出现该错误
				//c.setCellStyle(headCellStyle);
				cellNum++;
			}
		}
		
		ssht.flushRows();
		
		//固定表头
		ssht.createFreezePane(0, 1, 0, 1);
		
		//数据集写入 
		if (dataList != null && dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				
				Row contentRow = ws.createRow(i + 1);
				
				Object o = dataList.get(i);
				int j = 0;
				
				for (ExportConfigModel config : configList) {
					if (SELECT_ZT.equals(config.getZt())) {
						String zd = config.getZd();
						String value = "";
						if(o instanceof Map){//数据集为map方式时
							HashMap dataMap = (HashMap) o;
							Object object = dataMap.get(zd.toUpperCase());
							if(object != null){
								value = object + "";
							}
						}else{
							Object fieldValue = ReflectionUtils.getValueByFieldName(o, zd);
							if(fieldValue != null){
								value = fieldValue.toString();
							}
						}
						//Cell c = contentRow.createCell(j, Cell.CELL_TYPE_STRING);
						Cell c = contentRow.createCell(j, CellType.STRING);
						c.setCellValue(value);
						j++;							
					}
				}
				if(i%100 == 0){
					ssht.flushRows();
				}
			}
		}
	}
	
	/**
	 * 导出文件datalist 为List<HashMap<String, String>>		临时使用
	 * @param dcclbh	导出类型
	 * @param zgh		操作用户名
	 * @param selectCol	导出列
	 * @param dataList	导出结果集
	 * @return
	 * @throws Exception
	 */
	/**
	public File getExportFile(String dcclbh,String zgh,String[] selectCol,List<HashMap<String, String>> dataList) throws Exception {
		//检测参数合法性
		if (StringUtil.isEmpty(dcclbh) || StringUtil.isEmpty(zgh) || dataList == null){
			throw new ServiceException("Nonlicet params !");
		}
		
		ExportModel model=new ExportModel();
		model.setDcclbh(dcclbh);
		model.setZgh(zgh);
		model.setDataList(dataList);
		model.setSelectCol(selectCol);
		//检测参数合法性
		if (null == model || StringUtil.isEmpty(model.getDcclbh()) || StringUtil.isEmpty(model.getZgh())){
			throw new ServiceException("Nonlicet params !");
		}
		WritableWorkbook wwb = null;
		
		//导出文件存放 的临时目录
		File tempDir = new File(ServletActionContext.getServletContext().getRealPath(MessageUtil.getText(TEMP_PATH_KEY)));
		
		if (!tempDir.exists()){
			tempDir.mkdir();
		}
		//创建导出文件
		File file = new File( tempDir.getPath() + "/" + String.valueOf(System.currentTimeMillis())+".xls");
		file.setWritable(true);
		try {
			FileOutputStream stream = new FileOutputStream(file);
			//创建excel工作表
			wwb = Workbook.createWorkbook(stream);
			WritableSheet ws = wwb.createSheet(SHEET_NAME, 0);
			//获取导出配置
			List<ExportConfigModel> configList = getConfigList(model);
			//按显示顺序排序，DAO层去做排序容易造成to_number时SQL错误
			Collections.sort(configList,new ExportComparator());
			//写入Excel
			writeExcel(ws, configList,dataList);
			wwb.write();
			wwb.close();
		} catch (Exception ex) {
			log.error("Export failed info:ID("+model.getDcclbh()+"),User("+model.getZgh()+")",ex);
			throw new Exception(ex);
		}
		return file;
	}
	**/
	
	/**
	 * 写入EXCEL			临时使用
	 * @param ws  excel对象
	 * @param configList   导出列
	 * @param dataList		导出结果
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	/**
	private void writeExcel(WritableSheet ws, List<ExportConfigModel> configList,
				List<HashMap<String, String>> dataList
			) throws RowsExceededException, WriteException,
			SecurityException, IllegalArgumentException, NoSuchFieldException,
			IllegalAccessException {

		//设置表头字体
		WritableCellFormat wcf = new WritableCellFormat();
		WritableFont wf = new WritableFont(WritableFont.ARIAL);
		wf.setBoldStyle(WritableFont.BOLD);
		wf.setColour(Colour.WHITE);
		wf.setPointSize(10);
		wcf.setFont(wf);
		wcf.setAlignment(Alignment.CENTRE);
		wcf.setBackground(Colour.GREEN);
		wcf.setWrap(false);
		//表头写入
		int cellNum = 0;// 输出的第cellNum列
		for (ExportConfigModel config : configList) {
			if (SELECT_ZT.equals(config.getZt())) {
				String title = config.getZdmc();
				ws.addCell(new Label(cellNum, 0, title ,wcf));
				ws.setColumnView(cellNum, title.getBytes().length*2);
				cellNum++;
			}
		}
		
		//数据集写入 
		if (dataList != null && dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				HashMap<String, String> o = dataList.get(i);
				int j = 0;
				
				for (ExportConfigModel config : configList) {
					if (SELECT_ZT.equals(config.getZt())) {
						String zd = config.getZd();
						String value = o.get(zd);
						if(value == null){
							value = "";
						}
						ws.addCell(new Label(j, i+1, value));
						j++;
					}
				}
			}
		}
	}
	**/
}