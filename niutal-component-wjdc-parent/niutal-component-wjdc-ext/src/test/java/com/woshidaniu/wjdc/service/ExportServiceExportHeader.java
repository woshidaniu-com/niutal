/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.woshidaniu.io.utils.FileUtils;
import com.woshidaniu.wjdc.BaseTest;
import com.woshidaniu.wjdc.dao.daointerface.IStglDao;
import com.woshidaniu.wjdc.dao.daointerface.IWjdcExportDao;
import com.woshidaniu.wjdc.dao.daointerface.IWjglDao;
import com.woshidaniu.wjdc.dao.daointerface.IWjhdDao;
import com.woshidaniu.wjdc.dao.daointerface.IWjstxxDao;
import com.woshidaniu.wjdc.dao.entites.StglModel;
import com.woshidaniu.wjdc.dao.entites.WjhdModel;
import com.woshidaniu.wjdc.dao.entites.WjstxxModel;
import com.woshidaniu.wjdc.enums.QuestionType;
import com.woshidaniu.wjdc.service.svcinterface.IStglService;
import com.woshidaniu.wjdc.service.svcinterface.IWjglService;
import com.woshidaniu.wjdc.utils.StNumberSequence;
import com.woshidaniu.wjdc.utils.StxxNumberSequence;

import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportServiceExportHeader extends BaseTest{
	
	private static final Logger log = LoggerFactory.getLogger(ExportServiceExportHeader.class);
	
	@Resource
	private IStglDao stglDao;

	@Resource
	private IWjglDao wjglDao;
	
	@Resource
	private IWjstxxDao wjstxxDao;
	
	@Resource
	private IWjdcExportDao wjdcExportDao;
	
	@Resource
	private IWjglService wjglService;
	
	@Resource
	private IWjhdDao wjhdDao;

	@Autowired
	private IStglService stglService;

	@Test
	public void test() {
		try {
			File f = exportDtxqById_10698("b65208abd5229cca4aa9ef57e248c665");
			this.renderFile(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public File exportDtxqById_10698(String wjid) throws Exception {
		
		List<StglModel> stglModels = this.stglDao.getStxxList(wjid);
		List<WjstxxModel> wjstxxModels = this.wjstxxDao.queryWjstxx(wjid);
		//问卷试题和其选项的map,key是stid，value是选项列表
		Map<String,List<WjstxxModel>> wjstxxMap = new HashMap<String,List<WjstxxModel>>();
		for(WjstxxModel wjstxx : wjstxxModels) {
			String stid = wjstxx.getStid();
			List<WjstxxModel> list = wjstxxMap.get(stid);
			if(list == null) {
				list = new ArrayList<WjstxxModel>();
				wjstxxMap.put(stid, list);
			}
			list.add(wjstxx);
		}
		//姓名	学号	书院	学院	专业	班级
		List<String> baseHeaders = Arrays.asList("姓名", "学号", "书院", "学院","专业","班级"); 
		List<String> headers = this.getExportDtxqCustomerHeaders(wjid,baseHeaders,stglModels,wjstxxModels,wjstxxMap);

		//查询出所有参与这个问卷的人
		List<String> xhs = this.wjdcExportDao.queryWjhdDjr(wjid);
		
		
		//有多少学生，就有多少行数据,预先分配，被动避免扩张
		int xlsDataRowCount = xhs.size();
		//有多少选表头列，就有多少数据列
		int xlsDataColumnCount = headers.size();
		List<List<String>> xlsData = new ArrayList<List<String>>(xlsDataRowCount);
		
		log.info("学生总人数:"+xhs.size());
		
		//查询出这个文件所有的用户信息
		for(int i=0;i<xhs.size();i++) {
			
			//一行的数据
			List<String> xlsRowData = new ArrayList<String>(xlsDataColumnCount);
			xlsData.add(xlsRowData);
			//基础信息
			String xh = xhs.get(i);
			log.info("第"+i+"个学生:"+xh);
			//姓名
			String xm = xh;
			xlsRowData.add(xm);
			
			//学号
			xlsRowData.add(xm);
			
			//书院
			xlsRowData.add(xm);
			
			//学院
			xlsRowData.add(xm);
			
			//专业
			xlsRowData.add(xm);
		
			//班级
			xlsRowData.add(xm);
			
			//当前学生的所有回答
			List<WjhdModel> wjhdModels = this.wjhdDao.queryWjhd(wjid, xh);
			
			//试题id和试题回答的map,key是试题id,value是回答的列表
			Map<String,LinkedList<WjhdModel>> wjhdMap = new HashMap<String,LinkedList<WjhdModel>>(wjhdModels.size());
			
			for(WjhdModel wjhdModel:wjhdModels) {
				String stid = wjhdModel.getStid();
				LinkedList<WjhdModel> list = wjhdMap.get(stid);
				if(list == null) {
					list = new LinkedList<WjhdModel>();
					wjhdMap.put(stid, list);
				}
				list.addLast(wjhdModel);
			}
			
			//试题信息
			for (StglModel stxx : stglModels) {
				
				String stid = stxx.getStid();
				String stlx = stxx.getStlx();
				QuestionType type = QuestionType.getTypeByKey(Integer.valueOf(stlx));
				
				List<WjhdModel> wjhdModelList = wjhdMap.get(stid);
				if(wjhdModelList == null || wjhdModelList.isEmpty()) {
					xlsRowData.add("");			
					continue;
				}
				
				if (type == QuestionType.RADIO) {// 单选
					
					WjhdModel wjhdModel = wjhdModelList.get(0);
					String xxid = wjhdModel.getXxid();
					int startIndex = xxid.indexOf("xxid_");
					String xxIndex = xxid.substring(startIndex+"xxid_".length(), xxid.length());
					xxIndex = xxIndex.trim();
					try {
						int xxIndex_integer = Integer.valueOf(xxIndex);
						xxIndex_integer++;
						String xxIndexNo = ""+xxIndex_integer;
						xlsRowData.add(xxIndexNo);						
					}catch(NumberFormatException e) {
						log.error("数字转换异常,xh["+ xh +"],wjid["+ wjid +"],stid["+ stid +"],xxid["+ xxid +"]",e);
					}
					
				} else if (type == QuestionType.RADIO_GROUP) {// 单选组合
					
					WjhdModel wjhdModel = wjhdModelList.get(0);
					if(StringUtils.isNotEmpty(wjhdModel.getXxid())) {
						String xxid = wjhdModel.getXxid();
						int startIndex = xxid.indexOf("xxid_");
						String xxIndex = xxid.substring(startIndex+"xxid_".length(), xxid.length());
						xxIndex = xxIndex.trim();
						xlsRowData.add(xxIndex);						
					}else {
						//自定义回答
						boolean find = false;
						for(WjhdModel wjhd:wjhdModelList) {
							if(StringUtils.isNotEmpty(wjhd.getTxnr())) {
								xlsRowData.add(wjhd.getTxnr());	
								find = true;
								break;
							}
						}
						if(!find) {
							xlsRowData.add("");	
						}
					}
				} else if (type == QuestionType.MULTI) {// 多选
					
					List<WjstxxModel> wjstxxs = wjstxxMap.get(stid);
					for(WjstxxModel wjxx : wjstxxs) {
						String xxid = wjxx.getXxid();
						boolean find = false;
						for(WjhdModel wjhd:wjhdModelList) {
							if(xxid.equals(wjhd.getXxid())) {
								find  = true;
							}
						}
						if(find) {
							xlsRowData.add("1");	
						}else {
							xlsRowData.add("0");	
						}
					}
				} else if (type == QuestionType.MULTI_GROUP) {// 多选组合
					
					List<WjstxxModel> wjstxxs = wjstxxMap.get(stid);
					for(int j=0;j<wjstxxs.size();j++) {
						
						WjstxxModel wjxx = wjstxxs.get(j);
						String xxid = wjxx.getXxid();
						if(j < wjstxxs.size() - 1) {
							boolean find = false;
							for(WjhdModel wjhd:wjhdModelList) {
								if(xxid.equals(wjhd.getXxid())) {
									find  = true;
									break;
								}
							}
							if(find) {
								xlsRowData.add("1");
							}else {
								xlsRowData.add("0");	
							}
						}else {
							//自定义回答
							boolean find = false;
							for(WjhdModel wjhd:wjhdModelList) {
								if(StringUtils.isNotEmpty(wjhd.getTxnr())) {
									xlsRowData.add(wjhd.getTxnr());	
									find = true;
									break;
								}
							}
							if(!find) {
								xlsRowData.add("");	
							}
						}
					}
				} else if (type == QuestionType.TEXT) {// 文本
					
					WjhdModel wjhdModel = wjhdModelList.get(0);
					xlsRowData.add(wjhdModel.getTxnr());	
					
				}else if (type == QuestionType.DESCRIBE) {// 描述
					continue;
				} else {// 未定义类型
					log.warn("未定义的试题类型:type["+type+"]");
					continue;
				}
			}
			
			log.info("数据:"+xlsRowData);
		}
		
		
		//写入xls
		//构建xls,放到最后
		File newXlsFile = this.buildNewXlsFile();
		WritableWorkbook workbook = this.buildNewXls(headers,newXlsFile,false,null);
		WritableSheet sheet = workbook.getSheet(0);
		
		for(int i=0;i<xlsData.size();i++) {
			
			List<String> xlsRowData = xlsData.get(i);
			for(int j=0;j<xlsRowData.size();j++) {
				String nr = xlsRowData.get(j);
				Label label = new Label(j,i+1,nr);
				sheet.addCell(label);			
			}
		}
		workbook.write();
		workbook.close();
		return newXlsFile;
	}
	
	private List<String> getExportDtxqCustomerHeaders(//
			String wjid,//
			List<String> baseHeaders,//
			List<StglModel> stglModels,//
			List<WjstxxModel> wjstxxModels,//
			Map<String,List<WjstxxModel>> wjstxxMap//
		) {

		List<String> headers = new ArrayList<String>(baseHeaders);
		
		//试题列表头部
		StNumberSequence stNumberSequence = new StNumberSequence(true);
		
		for(StglModel stxx:stglModels) {
			
			String stid = stxx.getStid();
			String stlx = stxx.getStlx();
			
			int stlx_integer = 1;
			try {
				stlx_integer = Integer.valueOf(stlx);				
			}catch(NumberFormatException e) {
				log.error("数字转换异常,wjid["+ wjid +"],stid["+ stid +"]",e);
			}
			
			QuestionType type = QuestionType.getTypeByKey(stlx_integer);
			
			if (type == QuestionType.RADIO) {// 单选
				
				headers.add(stNumberSequence.next(stxx.getStmc()));
				
			} else if (type == QuestionType.RADIO_GROUP) {// 单选组合
				
				headers.add(stNumberSequence.next(stxx.getStmc()));
				
			} else if (type == QuestionType.MULTI) {// 多选
				
				stNumberSequence.next();
				StxxNumberSequence stxxNumberSequence = stNumberSequence.createXxNumberSequence();
				
				List<WjstxxModel> list = wjstxxMap.get(stid);
				if(list != null && !list.isEmpty()) {
					for(WjstxxModel xx : list) {
						headers.add(stxxNumberSequence.next(xx.getXxmc()));
					}
				}
				
			} else if (type == QuestionType.MULTI_GROUP) {// 多选组合
				
				stNumberSequence.next();
				StxxNumberSequence stxxNumberSequence = stNumberSequence.createXxNumberSequence();
				
				List<WjstxxModel> list = wjstxxMap.get(stid);
				if(list != null && !list.isEmpty()) {
					for(WjstxxModel xx : list) {
						headers.add(stxxNumberSequence.next(xx.getXxmc()));
					}
				}
				
			} else if (type == QuestionType.TEXT) {// 文本
				
				headers.add(stNumberSequence.next(stxx.getStmc()));
				
			}else if (type == QuestionType.DESCRIBE) {// 描述
				continue;
			} else {// 未定义类型
				log.warn("未定义的试题类型:type["+type+"]");
				continue;
			}
		}
		
		return headers;
	}

	private File buildNewXlsFile() throws Exception{
		File tempDir = FileUtils.getTempDirectory();
		String newXlsFileName = UUID.randomUUID().toString() + ".xls";
		File newXlsFile = new File(tempDir, newXlsFileName);
		newXlsFile.createNewFile();
		return newXlsFile;
	}
	
	private WritableWorkbook buildNewXls(List<String> headers,File newXlsFile,boolean useWcfTitle,Integer columnViewWidth) throws Exception{
		WritableWorkbook workbook = jxl.Workbook.createWorkbook(newXlsFile);
		WritableSheet sheet = workbook.createSheet("default", 0);

		// 表头
		for (int i = 0; i < headers.size(); i++) {
			String content = headers.get(i);

			WritableCellFormat wcf_title = null;
			try {
				wcf_title = this.createWcfTitile();
			} catch (Exception e) {
				log.error("创建单元格样式异常", e);
			}
			Label label = null;
			if (wcf_title != null && useWcfTitle) {
				label = new Label(i, 0, content, wcf_title);
			} else {
				label = new Label(i, 0, content);
			}
			sheet.addCell(label);
			if(columnViewWidth != null) {
				sheet.setColumnView(i, columnViewWidth);				
			}
		}
		return workbook;
	}
	
	/**
	 * @description ： 创建单元格样式
	 * @author ： 康康（1571）
	 * @date ：2018年7月31日 下午3:40:16
	 * @return
	 * @throws Exception
	 */
	private WritableCellFormat createWcfTitile() throws Exception {

		WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
		WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义
		wcf_title.setAlignment(jxl.format.Alignment.CENTRE);// 水平对齐方式
		wcf_title.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直对齐方式
		wcf_title.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // 设置边框
		return wcf_title;
	}
}
