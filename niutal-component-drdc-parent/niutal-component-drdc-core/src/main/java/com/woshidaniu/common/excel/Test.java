package com.woshidaniu.common.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.woshidaniu.common.excel.template.ATemplateBuilder;
import com.woshidaniu.common.excel.template.ImportTemplateBuilder;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath="E:\\实验区\\";
		String fileName="tmp001.xls";
		
		File file =new File(filePath+fileName);
		try {
			List<String[]> list=ExcelUtils.getDataList(file);
			System.out.println(list.size());
			
			fileName="tmp003.xls";
			File file2 =new File(filePath+fileName);
			List<String[]> list2=ExcelUtils.getDataList(file2);
			
			filePath="E:\\实验区\\001\\002\\";
			
			List<String[]> templateList=new ArrayList<String[]>();
			String[] temp=new String[]{"111","222"};
			templateList.add(temp);
			ATemplateBuilder templateBuilder =new ImportTemplateBuilder(templateList);
			File f= ExcelUtils.createExcel(list2, filePath+fileName,templateBuilder);
			System.out.println(f);
		} catch (Exception e1) {
		    e1.printStackTrace();
		}
	}

}
