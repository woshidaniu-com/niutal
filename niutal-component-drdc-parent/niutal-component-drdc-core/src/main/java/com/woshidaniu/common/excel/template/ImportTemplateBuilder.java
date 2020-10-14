package com.woshidaniu.common.excel.template;

import java.util.List;

import jxl.Cell;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;

/**
 * Excel模板建造者
 * 
 * @author JiangDong.Yi
 * 
 */
public class ImportTemplateBuilder extends ATemplateBuilder {
	public List<String[]> postilList;
	
	/**
	 * 初始化批注列表
	 * @param postilList
	 */
	public ImportTemplateBuilder(List<String[]> postilList) {
		this.postilList = postilList;
	}

	/**
	 * 建造格式
	 */
	public void buildFormat() throws Exception {
		// 得到列数
		int columnum = sheet.getColumns();
		// 得到行数
		int rownum = sheet.getRows();
		// 单元格
		Cell cell = null;
		
		//设置字体
		WritableCellFormat wcf = new WritableCellFormat();
		WritableFont wf = new WritableFont(WritableFont.ARIAL);
		wf.setBoldStyle(WritableFont.BOLD);
		wf.setPointSize(10);
		wcf.setFont(wf);
		wcf.setAlignment(Alignment.CENTRE);
		wcf.setBackground(Colour.GREEN);
		
		// 循环进行读写  && i != 1 导入模板中只设置表头样式
		for (int i = 0; i < rownum && i != 1; i++) {
			for (int j = 0; j < columnum; j++) {
				cell = sheet.getCell(j, i);
				sheet.addCell(new Label(j, i, cell.getContents() ,wcf));
			}
		}
		
	}

	/**
	 * 建造批注
	 */
	public void buildPostil() throws Exception {
		if(postilList == null || postilList.size() == 0){
			return;
		}
		WritableCellFeatures wcfeat =null;
		WritableCell wc = null;
		for (int i = 0; i < postilList.size(); i++) {
			
			for (int j = 0; j < postilList.get(i).length; j++) {
				if(postilList.get(i)[j] == null || "".equals(postilList.get(i)[j])){
					continue;
				}
				
				wcfeat = new WritableCellFeatures();
				wc = sheet.getWritableCell(j, i);
				wcfeat.setComment(postilList.get(i)[j]);
				// 写入批注
				wc.setCellFeatures(wcfeat);
			}
		}
	}
}
