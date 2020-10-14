package com.woshidaniu.export.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.common.constant.BaseConstant;
import com.woshidaniu.common.exception.ServiceException;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.utils.ExportComparator;
import com.woshidaniu.export.service.utils.dbf.DBFWriter;
import com.woshidaniu.export.service.utils.dbf.JDBField;
import com.woshidaniu.util.file.DirectoryUtils;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 导出实现 
 * @author Penghui.Qu
 *
 */
public class ExportExcelImpl extends AbstractDocExportService{

	//日期格式对象
 	private static SimpleDateFormat date_format = new SimpleDateFormat();
	/*
	 * (non-Javadoc)
	 * @see com.woshidaniu.service.svcinterface.IExportService#getExcel(com.woshidaniu.dao.entities.ExportModel, java.io.OutputStream)
	 */
	public File getExportFile(ExportModel model) throws Exception {
		
		//检测参数合法性
		if (null == model || StringUtils.isEmpty(model.getDcclbh()) || StringUtils.isEmpty(model.getZgh())){
			throw new ServiceException("Nonlicet params !");
		}

		// //////////////插入dbf处理////////////////////////////////////
		String wjgs = model.getExportWjgs();
		if (wjgs != null && wjgs.equals("dbf")) {
			return createDbfFile(model);
		}
		// /////////////////////////////////////////////////////////////

		WritableWorkbook wwb = null;
		File file = createFile();

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
			writeExcel(model.getDataList(), configList, ws);
			wwb.write();
			wwb.close();
		} catch (Exception ex) {
			log.error("Export failed info:ID("+model.getDcclbh()+"),User("+model.getZgh()+")",ex);
			throw new Exception(ex);
		}
		
		return file;
	}
	
	// 创建临时文件
	private File createFile() {
		//导出文件存放 的临时目录
		File tempDir = DirectoryUtils.getRealPath(BaseConstant.TEMP_PATH);
		if (!tempDir.exists()){
			tempDir.mkdir();
		}
		//创建导出文件
		File file = new File( tempDir.getPath() + "/" + String.valueOf(System.currentTimeMillis())+".xls");
		file.setWritable(true);
		return file;
	}
	
	private String createFileName(String type) {
		String fileName = null;
		//导出文件存放 的临时目录
		File tempDir = DirectoryUtils.getRealPath(BaseConstant.TEMP_PATH);
		if (!tempDir.exists()){
			tempDir.mkdir();
		}
		fileName = tempDir.getPath() + "/" + String.valueOf(System.currentTimeMillis()) + "." + type;
		return fileName;
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
	private void writeExcel(List dataList, List<ExportConfigModel> configList,
			WritableSheet ws) throws RowsExceededException, WriteException,
			SecurityException, IllegalArgumentException, NoSuchFieldException,
			IllegalAccessException {

		//设置表头字体
		WritableCellFormat wcf = new WritableCellFormat();
		WritableFont wf = new WritableFont(WritableFont.ARIAL);
		wf.setBoldStyle(WritableFont.BOLD);
		wf.setPointSize(10);
		wf.setColour(Colour.WHITE);
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
				Object o = dataList.get(i);
				int j = 0;
				
				for (ExportConfigModel config : configList) {
					if (SELECT_ZT.equals(config.getZt())) {
						String zd = config.getZd();
						//ws.addCell(new Label(j, i+1, (String.valueOf(ReflectionUtils.getFieldValue(o, zd)))));
						Object value = "";
						if(o instanceof Map){//数据集为map方式时
							HashMap dataMap = (HashMap) o;
							Object  object = dataMap.get(zd);
									object = (object == null) ? dataMap.get(zd.toUpperCase()) : object;
									
							if(object != null){
								value = object + "";
							}
						}else{
							Object fieldValue = ReflectionUtils.getValueByFieldName(o, zd);
							if(fieldValue != null){
								value = fieldValue;
							}
						}
						if(value instanceof String){
							ws.addCell(new Label(j, i+1,String.valueOf(value)));
						}else if (value instanceof Number) {
							if((value instanceof Integer)||(value instanceof Long)){
								ws.addCell(new jxl.write.Number(j, i+1,Double.valueOf(String.valueOf(value))));
							}else if((value instanceof Double)||(value instanceof Float)){
								ws.addCell(new jxl.write.Number(j, i+1,Double.valueOf(String.valueOf(value))));
							}else{
								String number = new BigDecimal(((Number)value).doubleValue()).toPlainString();
								ws.addCell(new Label(j, i+1,number));
							}
						} else if (value instanceof Date) {
							ws.addCell(new jxl.write.DateTime(j, i+1,(Date)value));
						} else if(value instanceof Boolean){
							ws.addCell(new jxl.write.Boolean(j, i+1,(Boolean)value));
						}else{
							ws.addCell(new Label(j, i+1,String.valueOf(value)));
						}
						//ws.addCell(new Label(j, i+1, value));
						j++;							
					}

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
	public File getExportFile(String dcclbh,String zgh,String[] selectCol,List<HashMap<String, String>> dataList) throws Exception {
		//检测参数合法性
		if (StringUtils.isEmpty(dcclbh) || StringUtils.isEmpty(zgh) || dataList == null){
			throw new ServiceException("Nonlicet params !");
		}
		
		ExportModel model=new ExportModel();
		model.setDcclbh(dcclbh);
		model.setZgh(zgh);
		model.setDataList(dataList);
		model.setSelectCol(selectCol);
		//检测参数合法性
		if (null == model || StringUtils.isEmpty(model.getDcclbh()) || StringUtils.isEmpty(model.getZgh())){
			throw new ServiceException("Nonlicet params !");
		}
		WritableWorkbook wwb = null;
		
		//导出文件存放 的临时目录
		File tempDir = DirectoryUtils.getRealPath(BaseConstant.TEMP_PATH);
		
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
	/*
	 * 生成dbf文件
	 */
	private File createDbfFile(ExportModel model) throws Exception {

		String fileName = createFileName("dbf");
		File file = new File(fileName);
		
		// 获取导出配置
		 List<ExportConfigModel> configList = getConfigList(model);

		 ExportConfigModel exportConfigModel = null;
		for (int i = configList.size() - 1; i >= 0; i--) {
			exportConfigModel = configList.get(i);
			if (UNSELECT_ZT.equals(exportConfigModel.getZd())) {
				configList.remove(i);
			}
		}
		// 按显示顺序排序，DAO层去做排序容易造成to_number时SQL错误
		Collections.sort(configList, new ExportComparator());

		JDBField[] dbfField = new JDBField[configList.size()];

		String title = null;
		for (int i = 0; i < configList.size(); i++) {
			exportConfigModel = configList.get(i);
			title = exportConfigModel.getZdmc();
			dbfField[i] = new JDBField(title, 'C', 20, 0);
		}
		DBFWriter dbfWriter = null;
		try {
			List<Object> dataList = model.getDataList();
			Object[] aobj = null;
			// 数据集写入
			if (dataList != null && dataList.size() > 0) {
				List<Object[]> aobjList = new ArrayList<Object[]>();
				for (int i = 0; i < dataList.size(); i++) {
					Object o = dataList.get(i);
					aobj = new Object[configList.size()];
					for (int j = 0; j < configList.size(); j++) {
						String zd = configList.get(j).getZd();
						String value = null;
						if(o instanceof Map){//数据集为map方式时
							HashMap dataMap = (HashMap) o;
							Object object = dataMap.get(zd.toUpperCase());
							if(object != null){
								value = object + "";
							}
						}else{
							Object fieldValue = ReflectionUtils.getValueByFieldName(o, zd);
							if(fieldValue != null){
								if(fieldValue instanceof String){
									value = (String)fieldValue;
								}else if (fieldValue instanceof Number) {
									if((fieldValue instanceof Integer)||(fieldValue instanceof Long)){
										value =String.valueOf(value);
									}else if((fieldValue instanceof Double)||(fieldValue instanceof Float)){
										value =String.valueOf(value);
									}else{
										value = new BigDecimal(((Number)fieldValue).doubleValue()).toPlainString();
									}
								} else if (fieldValue instanceof Date) {
									value = date_format.format((Date)fieldValue);
								} else if(fieldValue instanceof Boolean){
									value = ((Boolean)fieldValue).toString();
								}else{
									value = String.valueOf(fieldValue);
								}
							}
						}
						
						int valueLength = getStrLength(value);
						if (dbfField[j] != null && value != null
								&& dbfField[j].getLength() < valueLength) {					
							dbfField[j] = new JDBField(dbfField[j].getName(), dbfField[j].getType(),valueLength, dbfField[j].getDecimalCount());
							if(valueLength > dbfField[j].getLength()){
								value = value.substring(0, dbfField[j].getLength()/2);
							}
						}
						aobj[j] = value;
					}
					aobjList.add(aobj);
				}
				dbfWriter = new DBFWriter(fileName, dbfField);
				for (int i = 0; i < aobjList.size(); i++) {
					dbfWriter.addRecord(aobjList.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbfWriter != null) {
				try {
					dbfWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}
	
	/*
	 * 取字符串长度，中文算两个字符
	 */
	private int getStrLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		if(value != null){
			for (int i = 0; i < value.length(); i++) {
				/* 获取一个字符 */
				String temp = value.substring(i, i + 1);
				/* 判断是否为中文字符 */
				if (temp.matches(chinese)) {
					/* 中文字符长度为2 */
					valueLength += 2;
				} else {
					/* 其他字符长度为1 */
					valueLength += 1;
				}
			}			
		}
		return valueLength;
	}


}


