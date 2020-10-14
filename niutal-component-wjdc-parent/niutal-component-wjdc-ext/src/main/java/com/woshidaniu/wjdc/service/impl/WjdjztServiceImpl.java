/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.fastxls.core.Suffix;
import com.woshidaniu.fastxls.poi.utils.POIWorkbookUtils;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.wjdc.dao.daointerface.IWjdjztDao;
import com.woshidaniu.wjdc.dao.entites.WjdjztModel;
import com.woshidaniu.wjdc.service.svcinterface.IWjdjztService;

/**
 * @description	： 问卷答卷状态服务实现类
 * @author 		：康康（1571）
 */
@Service("wjdjztService")
public class WjdjztServiceImpl implements IWjdjztService{
	
	private static final String ZJZ_KEY = "XH";
	
	//数据库查询的结果集的列名
	private String exportColumnNames = "WJID;WJMC;XH;XM;ZYMC;BMMC;NJMC;BJMC;XBMC;DJZT;WJFZ";
	
	//excel中的表头label
	private String exportColumnLabels = "问卷ID;问卷名称;学号;姓名;专业名称;部门名称;年级名称;班级名称;性别;答卷状态;答卷总分值";
	
	private Map<String,String> exportColumnToExcelHeaderMapping = new LinkedHashMap<String,String>();
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Resource
	private IWjdjztDao wjdjztDao;
	
	@PostConstruct
	public void init() {
		
		String[] columnArray = null;
		{
			String val = MessageUtil.getText(this.getClass().getName()+".exportColumnNames");
			val = StringUtils.isNotEmpty(val) ? val : exportColumnNames;
			columnArray = StringUtils.splitByWholeSeparator(val, ";");
			
			this.checkArrayEmptyStr(val,columnArray);
			
			this.checkXh(val,columnArray);
		}
		
		String[] labelArray = null;
		{
			String val = MessageUtil.getText(this.getClass().getName()+".exportColumnLabels");
			val = StringUtils.isNotEmpty(val) ? val : exportColumnLabels;
			labelArray = StringUtils.splitByWholeSeparator(val, ";");
			
			this.checkArrayEmptyStr(val,labelArray);
		}
		
		this.checkArray(columnArray,labelArray);
		
		for(int i = 0;i<columnArray.length;i++) {
			String column = columnArray[i];
			String label = labelArray[i];
			exportColumnToExcelHeaderMapping.put(column, label);
		}
	}
	
	private void checkArray(String[] columnArray, String[] labelArray) {
		if(columnArray == null || columnArray.length == 0 || labelArray == null || labelArray.length == 0) {
			throw new IllegalStateException("columnArray == null || columnArray.length == 0 || labelArray == null || labelArray.length == 0");
		}
		
		if(columnArray.length != labelArray.length) {
			throw new IllegalStateException("数据库列的个数["+ columnArray.length +"] != Excel中label的个数["+ labelArray.length +"]");
		}
	}

	private void checkXh(String val, String[] columnArray) {
		for(int i = 0;i<columnArray.length;i++) {
			String column = columnArray[i];
			if(column.equals(ZJZ_KEY)) {
				return;
			}
		}
		throw new IllegalStateException("数据库列配置中必须包含"+ZJZ_KEY);
	}

	private void checkArrayEmptyStr(String val,String[] array) {
		if(array == null) {
			throw new IllegalStateException("字符串["+ val +"]按;切割后是空数组");
		}
		for(String str : array) {
			if(StringUtils.isEmpty(str)) {
				throw new IllegalStateException("字符串["+ val +"]按;切割后存在空白字符串");
			}
		}
	}

	@Override
	public List<Map<String,String>> getPagedDjztList(WjdjztModel model) {
		return this.wjdjztDao.getPagedDjztList(model);
	}

	@Override
	public synchronized File exportDjztList(WjdjztModel model) throws Exception{
		
		/**
		 * 这里需要分页
		 * 我们先查询总条数，然后分配这些数据到多个sheet中，每个sheet再执行多次查询
		 * 每次查询查询一个page的数据量，也就是perPageRowSize
		 * 
		 * 这样每个sheet由多个page组成，每个page就是一次查询所获得的数据量,XLS最大行数是65535
		 * 我们取整，每个sheet由600个page组成，每个page是100行数据，这样，一个sheet就有6000条数据，基本满足需求
		 * 
		 * 为了避免mybatis一级缓存存储太多数据，我们手动调用sqlSession的clearCache方法清空缓存，防止OOM清空发生，
		 * 毕竟一个sqlSession存储60000条缓存数据，还是很占内存的
		 */
		
		//每次分页查询的行数
		int perPageRowSize = 100;
		{
			String val = MessageUtil.getText(this.getClass().getName()+".perQueryRowSize");
			perPageRowSize = StringUtils.isNotEmpty(val) ? Integer.parseInt(val) : perPageRowSize;
		}
		
		//每个sheet的page个数
		int perSheetPageCount = 600;
		{
			String val = MessageUtil.getText(this.getClass().getName()+".perSheetQueryPageCount");
			perSheetPageCount = StringUtils.isNotEmpty(val) ? Integer.parseInt(val) : perSheetPageCount;
		}
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		FileOutputStream fos = null;
		Workbook book = null;
		File file = new File(String.format("%s%s答卷状态详情.xls", System.getProperty("java.io.tmpdir"), File.separator));

		try {
			fos = new FileOutputStream(file);
			book = POIWorkbookUtils.getWorkbook(Suffix.XLS);
			
			IWjdjztDao dao = sqlSession.getMapper(IWjdjztDao.class);
			
			//总记录数
			int totalCount = dao.getDjztListCount(model);
			
			//每个sheet的行数
			int perSheetRowSize = perPageRowSize * perSheetPageCount;
			
			//总的sheet个数
			int totalSheetSize = totalCount / perSheetRowSize;
			
			totalSheetSize = totalCount % perSheetRowSize > 0 ? totalSheetSize + 1 : totalSheetSize;
			
			end:
			for(int sheetIndex = 1; sheetIndex <= totalSheetSize;sheetIndex++) {
				
				//这个sheet的将要被使用的row的索引号
				int thisSheetRowIndex = 0;
				
				for(int i = 0;i<perSheetPageCount;i++) {
					
					//数据库开始行号
					int startRowInDatabase = (perSheetRowSize * (sheetIndex - 1))/*上一个sheet的总页数构成的总行数*/ + perPageRowSize * i/*这一个sheet的第几个page*/;
					
					//数据库结束行号
					int endRowInDatabase = startRowInDatabase + perPageRowSize;
					
					model.setStartRow(startRowInDatabase);
					model.setEndRow(endRowInDatabase);
					
					//查询数据
					List<Map<String,String>> list = this.wjdjztDao.getDjztList(model);
					
					if(!list.isEmpty()) {
						
						thisSheetRowIndex = renderToSheet(list,book,sheetIndex,thisSheetRowIndex,model.getWjid());
						
						//清空的mybatis一级缓存，防止OOM
						sqlSession.clearCache();
					}
					
					if(list.size() < perPageRowSize){
						//如果说查询结果集个数到小于perPageRowSize，那么就是最后sheet的最后一页，必定是没有数据的，此时，我们就完成了所有的查询，提前结束
						break end;
					}else if(list.size() == perPageRowSize) {
						//继续执行
					}else {//list.size() > perPageRowSize
						//can't happen
					}
				}
			}
			book.write(fos);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				sqlSession.clearCache();
				sqlSession.close();		
			}catch (Throwable e) {
				//ignore
			}
			try {
				book.close();				
			}catch (Throwable e) {
				//ignore
			}
			IOUtils.closeQuietly(fos);
		}
		return file;
	}

	private int renderToSheet(List<Map<String,String>> list,Workbook book,int sheetIndex,int thisSheetRowIndex,String wjid) {

		String sheetName = "sheet-" + sheetIndex;
		
		Sheet sheet = null;
		
		boolean firstRendThisSheet =  thisSheetRowIndex == 0;
		
		if(firstRendThisSheet) {
			sheet = book.createSheet(sheetName);
		}else {
			sheet = book.getSheet(sheetName);
		}
		if(firstRendThisSheet) {
			Row row = sheet.createRow(thisSheetRowIndex);
			//表头
			int index = 0;
			Iterator<Entry<String, String>> it = exportColumnToExcelHeaderMapping.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, String> e = it.next();
				String value = e.getValue();
				
				row.createCell(index).setCellValue(value);
				index++;
			}
		}
		//数据,逐行填充
		int maxColumnIndex = 0;
		for(Map<String,String> oneRowDataMap : list) {
			
			thisSheetRowIndex ++;
			Row dataRow = sheet.createRow(thisSheetRowIndex);
			int columnIndex = 0;
			Iterator<Entry<String, String>> it_for_data = exportColumnToExcelHeaderMapping.entrySet().iterator();
			while(it_for_data.hasNext()) {
				Entry<String, String> e = it_for_data.next();
				String key = e.getKey();
				
				Object data = oneRowDataMap.get(key);
				if(data == null) {
					data = "";
				}
				dataRow.createCell(columnIndex).setCellValue(data.toString());
				columnIndex++;
				if(columnIndex > maxColumnIndex) {
					maxColumnIndex = columnIndex;
				}
			}
		}
		
		if(StringUtils.isNotEmpty(wjid)) {
			List<Map<String, String>> stflMaps = this.wjdjztDao.getStflMapList(wjid);
			if(!stflMaps.isEmpty()) {
				//查询中类别的试题并计算
				List<Map<String,String>> stflfzMaps = this.wjdjztDao.getStflfz(wjid);
				
				int startColumn = maxColumnIndex;
				//填充Excel头部
				Row firstRow = sheet.getRow(0);
				for(Map<String,String> stflMap : stflMaps) {
					String stflMc = stflMap.get("FLMC");
					firstRow.createCell(startColumn).setCellValue(stflMc);
					startColumn++;
				}
				int stflRowIndex = 0;
				for(Map<String,String> oneRowDataMap : list) {
					String zjz = oneRowDataMap.get(ZJZ_KEY);
					stflRowIndex ++;
					List<Map<String,String>> maps = filterByZjz(zjz,stflfzMaps);
					if(maps != null && !maps.isEmpty()) {
						startColumn = maxColumnIndex;
						Row stflRow = sheet.getRow(stflRowIndex);
						Iterator<Map<String, String>> it_stfl = stflMaps.iterator();
						while(it_stfl.hasNext()) {
							Map<String, String> map = it_stfl.next();
							String flid = map.get("FLID");
							for(Map<String,String> m : maps) {
								String _flid = m.get("FLID");
								if(StringUtils.isNotEmpty(_flid) && _flid.equals(flid)) {
									String xxfz = m.get("XXFZ");
									stflRow.createCell(startColumn).setCellValue(xxfz);
									startColumn++;																		
								}
							}
						}
					}
				}
			}
		}
		return thisSheetRowIndex;
	}

	private List<Map<String, String>> filterByZjz(String zjz, List<Map<String, String>> stflfzMaps) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Iterator<Map<String, String>> it = stflfzMaps.iterator();
		while(it.hasNext()) {
			Map<String, String> map = it.next();
			String djrid = map.get("DJRID").toString();
			if(zjz.equals(djrid)) {
				result.add(map);
			}
		}
		return result;
	}

	@Override
	public int getWjCount(String wjid) {
		return this.wjdjztDao.getWjCount(wjid);
	}

	@Override
	public List<Map<String, String>> getYffWjList() {
		return this.wjdjztDao.getYffWjList();
	}
}
