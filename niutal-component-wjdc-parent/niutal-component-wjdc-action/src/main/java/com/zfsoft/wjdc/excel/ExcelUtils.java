package com.woshidaniu.wjdc.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.wjdc.dao.entites.StglModel;
import com.woshidaniu.wjdc.dao.entites.WjpzModel;
import com.woshidaniu.wjdc.dao.entites.WjtjModel;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtils {

	/**
	 * 系统默认临时路径
	 */
	public static final String SYS_TEMP_PATH = "/temp/";
	
	static final String EXCEL_SUFFIX_2003 = ".xls";
	
	static final String EXCEL_SUFFIX_2007 = ".xlsx";

	/**
	 * 创建一个空的EXCEL文档
	 * @param filename
	 * @return
	 */
	static synchronized File createTemplateExcel2003(String contextPath){
		String generateName = contextPath +UniqID.getInstance().getUniqIDHash().toLowerCase() + EXCEL_SUFFIX_2003;
		try {
			File tempDir = new File(contextPath);
			if(!tempDir.exists()){
				FileUtils.forceMkdir(new File(contextPath));
			}
			File temp = new File(generateName);
			temp.createNewFile();
			temp.deleteOnExit();
			return temp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建EXCEL表头
	 * @return
	 */
	private static boolean createExcelTableHeader(){
		return true;
	}
	
	/**
	 * 
	 * 问卷答卷情况导出生成EXCEL文件
	 * 
	 * @param requestWjtjModel -- 问卷基本统计信息
	 * @param requestStxxList  -- 问卷试题列表
	 * @param lxbtList	-- 答卷人类别列表
	 * @param dcData -- 答卷人答卷信息数据
	 */
	public static File createExcel(
						String tempPath, 
						WjtjModel requestWjtjModel,
						List<StglModel> requestStxxList, 
						List<Map<String, Object>> dcData) {
		if(dcData == null || dcData.size() == 0){
			return null;
		}
		if(requestStxxList == null || requestStxxList.size() == 0){
			return null;
		}
		//试题数
		int sts = requestStxxList.size();
		// 打开文件
		WritableWorkbook book = null;
		File excelFile = createTemplateExcel2003(tempPath);
		try {
			book = Workbook.createWorkbook(excelFile);
			Integer count = 0;
			for (Map<String, Object> data : dcData) {
				WritableSheet sheet = null;
				Label label = null;
				String lxid = (String) data.get("LXID");
				String lxmc = (String) data.get("LXMC");
				List<WjpzModel> sjyZdList = (List<WjpzModel>) data.get("ZDS");
				//List<Map<String, String>> djrxx = (List<Map<String, String>>) data.get("DJRXX");
				List<Map<String, String>> djxx = (List<Map<String, String>>) data.get("DJXX");
				//创建sheet
				sheet = book.createSheet(lxmc, count++);
				WritableCellFormat wcf = new WritableCellFormat();
				WritableFont wf = new WritableFont(WritableFont.ARIAL);
				wf.setBoldStyle(WritableFont.NO_BOLD);
				wf.setPointSize(10);
				wf.setColour(Colour.WHITE);
				wcf.setFont(wf);
				wcf.setAlignment(Alignment.LEFT);
				wcf.setBackground(Colour.GREEN);
				 //设置垂直居中;   
				wcf.setVerticalAlignment(VerticalAlignment.CENTRE);   
				wcf.setWrap(false);
				sheet.setRowView(0, 400, false); //设置行高
				//String[] zjzArray = new String[djrxx.size()];
				String[] keySort = new String[sjyZdList.size()+requestStxxList.size()];
				
				for (int i = 0,j = 0, k = 0; k < (sjyZdList.size()+requestStxxList.size()); k++) {
					if(k < sjyZdList.size()){
						String zd = sjyZdList.get(i).getZd();
						String zdmc = sjyZdList.get(i).getZdmc();
						label = new Label(k, 0, zdmc,wcf);
						WritableCellFeatures cellFeatures = new WritableCellFeatures();
						label.setCellFeatures(cellFeatures);
						sheet.addCell(label);
						keySort[k] = StringUtils.upperCase(zd);
						/*for (int l = 1; l <= djrxx.size(); l++) {
							Map<String, String> map = djrxx.get(l - 1);
							String zdz = map.get(zd.toUpperCase());
							label = new Label(k, l, zdz);
							sheet.addCell(label);
							if(k == 0){
								zjzArray[l-1] = map.get("ZJZ");
							}
						}*/
						i++;
					}else{
						String stid = requestStxxList.get(j).getStid();
						String stmc = requestStxxList.get(j).getStmc();
						label = new Label(k, 0, stmc, wcf);
						WritableCellFeatures cellFeatures = new WritableCellFeatures();
						label.setCellFeatures(cellFeatures);
						sheet.addCell(label);
						keySort[k] = StringUtils.upperCase(stid);
						/*for (int l = 1; l <= zjzArray.length; l++) {
							String zjz1 = zjzArray[l-1];
							for (Map<String, String> djxxItem : djxx) {
								String zjz2 = djxxItem.get("ZJZ");
								String djzt2 = djxxItem.get("DJZT");
								String stid2 = djxxItem.get("STID");
								String hdnr = djxxItem.get("HDNR");
								if(StringUtils.equals("0", djzt2)){
									label = new Label(k, l, "");
									sheet.addCell(label);
									break;
								}else{
									if(StringUtils.equals(zjz1, zjz2) && StringUtils.equals(stid, stid2)){
										label = new Label(k, l, hdnr);
										sheet.addCell(label);
										break;
									}
								}
							}
						}*/
						j++;
					}
				}
				
				for (int l = 1; l <= djxx.size(); l++) {
					Map<String, String> map = djxx.get(l - 1);
					for (int n = 0; n < keySort.length; n++) {
						String value = map.get(keySort[n]);
						label = new Label(n, l, value);
						sheet.addCell(label);
					}
				}
				
			}
		} catch (Exception e) {
			throw new RuntimeException("创建excel失败" + e.getMessage(), e);
		} finally {
			if (book != null) {
				try {
					book.write();
					book.close();
				} catch (Exception e) {
					throw new RuntimeException("生成excel失败" + e.getMessage(), e);
				}
			}
		}
		return excelFile;
	}

	/**
	 * 创建文件目录
	 * 
	 * @param dir
	 */
	private static void makeDir(File file) {
		if (!file.getParentFile().exists()) {
			makeDir(file.getParentFile());
		}
		file.mkdir();
	}

	
	public static void main(String[] args) {
		System.out.println(FileUtils.getTempDirectoryPath());
		System.out.println(FileUtils.getTempDirectory().getAbsolutePath());
		File createTemplateExcel2003 = 
			createTemplateExcel2003("C:\\Users\\woshidaniu\\AppData\\Local\\Temp\\wjdc\\");
		System.out.println(createTemplateExcel2003.getAbsolutePath());
	}
	
	
}
