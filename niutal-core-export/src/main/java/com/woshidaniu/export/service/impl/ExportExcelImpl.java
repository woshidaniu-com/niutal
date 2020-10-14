/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.service.impl;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.utils.ExportComparator;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Penghui.Qu
 * 导出实现 
 * 
 * @author zhidong
 * 代码整理优化
 */
@Service("exportExcel")
public class ExportExcelImpl extends AbstractExportService {
	
	private final String sheetNameConfigKey = "niutal.ExportExcelImpl.sheetName";
	
	@Override
	protected String getSheetNameConfigKey() {
		return sheetNameConfigKey;
	}
	
	@Override
	protected File doGetExportFile(ExportModel model) throws Exception {
		
		File file = null;
		WritableWorkbook wwb = null;
		try {
			String dcclbh = model.getDcclbh();
			String exportPhid = model.getExportPHID();
			file = this.createFile(dcclbh,exportPhid,"xls");
			FileOutputStream stream = new FileOutputStream(file);
			
			wwb = Workbook.createWorkbook(stream);
			WritableSheet ws = wwb.createSheet(sheetName, 0);
			
			//获取导出配置
			List<ExportConfigModel> configList = getConfigList(model);
			
			//写入Excel
			writeExcel(model.getDataList(), configList, ws);
			wwb.write();
		} catch (Exception ex) {
			log.error("Export failed info:ID("+model.getDcclbh()+"),User("+model.getZgh()+")",ex);
			throw ex;
		}finally {
			try {
				wwb.close();	
			}finally {
				//ignore
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
	private void writeExcel(List dataList, List<ExportConfigModel> configList, WritableSheet ws) throws RowsExceededException, WriteException,SecurityException, IllegalArgumentException, NoSuchFieldException,IllegalAccessException {

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
								value = (String)fieldValue;
							}
						}
						ws.addCell(new Label(j, i+1, value));
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
		if(StringUtils.isEmpty(dcclbh)){
			throw new IllegalArgumentException("dcclbh dcclbh can't be empty");
		}
		if(StringUtils.isEmpty(zgh)){
			throw new IllegalArgumentException("zgh zgh can't be empty");
		}
		if(dataList == null) {
			throw new IllegalArgumentException("dataList can't be null");
		}
		
		ExportModel model=new ExportModel();
		model.setDcclbh(dcclbh);
		model.setZgh(zgh);
		model.setDataList(dataList);
		model.setSelectCol(selectCol);

		File file = null;
		WritableWorkbook wwb = null;
		
		try {
			file = this.createFile(dcclbh,"","xls");
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
		} catch (Exception ex) {
			log.error("Export failed info:ID("+model.getDcclbh()+"),User("+model.getZgh()+")",ex);
			throw ex;
		}finally {
			try {
				wwb.close();				
			}catch (Exception e) {
				//ignore
			}
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
	private void writeExcel(WritableSheet ws, List<ExportConfigModel> configList, List<HashMap<String, String>> dataList)throws RowsExceededException, WriteException,SecurityException, IllegalArgumentException, NoSuchFieldException,IllegalAccessException {

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
}