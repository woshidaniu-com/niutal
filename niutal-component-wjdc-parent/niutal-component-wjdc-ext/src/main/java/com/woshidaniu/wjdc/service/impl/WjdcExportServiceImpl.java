/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.dao.entities.XsxxModel;
import com.woshidaniu.fastxls.core.Suffix;
import com.woshidaniu.fastxls.poi.utils.POIWorkbookUtils;
import com.woshidaniu.io.utils.FileUtils;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.wjdc.common.WjdcExportUserInfoHandler;
import com.woshidaniu.wjdc.dao.daointerface.IWjdcExportDao;
import com.woshidaniu.wjdc.dao.daointerface.IWjglDao;
import com.woshidaniu.wjdc.dao.daointerface.IWjhdDao;
import com.woshidaniu.wjdc.dao.daointerface.IWjstxxDao;
import com.woshidaniu.wjdc.dao.entites.StglModel;
import com.woshidaniu.wjdc.dao.entites.WjglModel;
import com.woshidaniu.wjdc.dao.entites.WjhdModel;
import com.woshidaniu.wjdc.dao.entites.WjstxxModel;
import com.woshidaniu.wjdc.enums.QuestionType;
import com.woshidaniu.wjdc.service.svcinterface.IStglService;
import com.woshidaniu.wjdc.service.svcinterface.IWjdcExportService;
import com.woshidaniu.wjdc.service.svcinterface.IWjglService;
import com.woshidaniu.wjdc.utils.StNumberSequence;
import com.woshidaniu.wjdc.utils.StxxNumberSequence;
import com.woshidaniu.wjdc.utils.WjstUtil;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @author 		：康康（1571）
 * @description	： 问卷调查导出服务实现类
 */
@Service("wjdcExportService")
public class WjdcExportServiceImpl implements IWjdcExportService {
	
	private static final Logger log = LoggerFactory.getLogger(WjdcExportServiceImpl.class);
	
	private static final String WJDC_EXPORT_USER_INFO_HANDLER_CLASS_NAME_KEY = "niutal.wjdc.wjdcExportUserInfoHandlerClassName";

	@Resource
	private IWjglDao wjglDao;
	
	@Resource
	private IWjhdDao wjhdDao;
	
	@Resource
	private IWjstxxDao wjstxxDao;
	
	@Resource
	private IWjdcExportDao wjdcExportDao;
	
	@Resource
	private IWjglService wjglService;

	@Autowired
	private IStglService stglService;
	
	private String wjdcExportUserInfoHandlerClassName;
	
	private WjdcExportUserInfoHandler wjdcExportUserInfoHandler;
	
	private static Map<String,String> typeMapper = new HashMap<String,String>();
	
	static {
		QuestionType[] arr = QuestionType.values();
		for(int i=0;i<arr.length;i++) {
			QuestionType t = arr[i];
			String key = Integer.toString(t.getKey());
			String label = t.getLabel();
			typeMapper.put(key,label);
		}
	}
	
	private File tempFileDir;
	
	private ScheduledThreadPoolExecutor cleanerScheduledExecutor  = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
		
		private AtomicInteger i = new AtomicInteger(0);
		
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, String.format("%s_cleaner_%s",WjdcExportServiceImpl.class.getSimpleName(),i.getAndIncrement()));
		}
	});
	
	private ScheduledFuture<?> scheduledFuture;
	
	@PostConstruct
	public synchronized void init() {
		
		this.wjdcExportUserInfoHandlerClassName = MessageUtil.getText(WJDC_EXPORT_USER_INFO_HANDLER_CLASS_NAME_KEY);
		
		if(StringUtils.isNotEmpty(this.wjdcExportUserInfoHandlerClassName)) {
			Class<?> clazz = null;
			try {
				clazz = Class.forName(this.wjdcExportUserInfoHandlerClassName);
			} catch (ClassNotFoundException e) {
				log.error("没有问卷调查导出用户信息Handler类:{}",this.wjdcExportUserInfoHandlerClassName,e);
			}
			if(clazz != null) {
				try {
					Object handler = clazz.newInstance();
					this.wjdcExportUserInfoHandler = (WjdcExportUserInfoHandler)handler;
					log.info("初始化handler:[{}]",this.wjdcExportUserInfoHandler);
				} catch (InstantiationException e) {
					log.error("",e);
				} catch (IllegalAccessException e) {
					log.error("",e);
				}
			}
		}
		
		initTempFileService();
		
	}

	private void initTempFileService() {
		File baseDir = FileUtils.getUserDirectory();
		File companyDir = new File(baseDir,"woshidaniu");
		if(!companyDir.exists()) {
			companyDir.mkdirs();
		}
		this.tempFileDir = new File(companyDir,"WjdcExportServiceImpl");
		if(!this.tempFileDir.exists()) {
			this.tempFileDir.mkdirs();
		}
		
		this.scheduledFuture = this.cleanerScheduledExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					clean();			
				}catch (Throwable t) {
					log.error("schedule clean tempFileDir error,cause: " + t.getMessage(),t);
				}
			}
		}, 5, 30, TimeUnit.MINUTES);
	}
	
	@PreDestroy
	public void destory() {
		if(this.scheduledFuture != null) {
			this.scheduledFuture.cancel(true);
		}
		this.cleanerScheduledExecutor.shutdown();
		
		for(int i = 0;i<100;i++) {
			try {
				if(this.cleanerScheduledExecutor.awaitTermination(10, TimeUnit.MILLISECONDS)) {
					return;
				}
			} catch (InterruptedException e) {
				//导出问卷调查的详情此类操作非常稀少，临时文件非常少，无法在1秒之内停止线程池的情况极其少见，所以这里什么不做了
			}
		}
	}
	
	protected void clean() {
		try {
			doClean();
		}catch (Throwable t) {
			log.error("schedule clean tempFileDir["+ this.tempFileDir +"] error,cause: " + t.getMessage(),t);
		}
	}
	
	protected void doClean() {
		File[] files = this.tempFileDir.listFiles();
		if(files == null || files.length <= 0) {
			return;			
		}
		
		long now = System.currentTimeMillis();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isFile() && f.exists() && !f.isHidden()) {
				long lastModefied = f.lastModified();
				boolean needDelete = now - lastModefied > TimeUnit.DAYS.toMillis(1);
				if (needDelete) {
					f.delete();
				}
			}
		}
	}

	@Override
	public File getDtxqById(String wjid) throws IOException {
		List<StglModel> stxxList = stglService.getStxxList(wjid);
		
		Map<String/**stid**/,QuestionType/**试题类型**/> stidToStlxMap = new HashMap<String,QuestionType>();
		for(StglModel stglModel : stxxList) {
			String stid = stglModel.getStid();
			String stlx = stglModel.getStlx();
			
			int stlxNumber = -1;
			try {
				stlxNumber = Integer.parseInt(stlx);
			} catch (Exception e) {
				log.error("问卷[{}],试题[{}]的试题类型数据[{}]非法", wjid, stid, stlx, e);
				continue;
			}
			QuestionType qt = QuestionType.getTypeByKey(stlxNumber);
			stidToStlxMap.put(stid, qt);
		}
		
		FileOutputStream fos = null;
		File file = this.buildNewXlsFile();

		Workbook book = null;
		
		try {
			fos = new FileOutputStream(file);
			book = POIWorkbookUtils.getWorkbook(Suffix.XLS);
			Sheet sheet = book.createSheet("答题详情");
			Row row = sheet.createRow(0);
			row.createCell(0).setCellValue("用户名");
			row.createCell(1).setCellValue("姓名");
			row.createCell(2).setCellValue("部门/学院");

			Map<String, Integer> stIndx = new HashMap<String, Integer>();

			for (int i = 0; i < stxxList.size(); i++) {
				int stIndex=  i + 1;
				StglModel stlgModel = stxxList.get(i);
				String xxsx = stlgModel.getXssx();
				String stmc = stlgModel.getStmc();
				Cell cell = row.createCell(i + 3);
				String formatedCellValue = String.format("%s、%s", stIndex,stmc);
				cell.setCellValue(formatedCellValue);
				stIndx.put(stxxList.get(i).getStid(), i + 3);
			}

			//答题详情
			List<Map<String/*key*/, String/*value*/>> dtqxList = wjglDao.getDtxqList(wjid);
			String zgh = null;
			Row cRow = null;
			int cIndex = 1;
			for (int i = 0 ; i < dtqxList.size(); i++) {
				
				Map<String,String> map = dtqxList.get(i);
				if (zgh == null || !zgh.equals(map.get("DJRID"))) {
					cRow = sheet.createRow(cIndex++);
					zgh = map.get("DJRID");
					cRow.createCell(0).setCellValue(zgh);
					
					String xm = map.get("XM");
					String bmmc = map.get("BMMC");
					
					if(StringUtils.isEmpty(xm) && this.wjdcExportUserInfoHandler != null) {
						//获得姓名
						xm = this.wjdcExportUserInfoHandler.getXm(zgh);
					}
					
					if(StringUtils.isEmpty(bmmc) && this.wjdcExportUserInfoHandler != null) {
						//获得部门名称
						bmmc = this.wjdcExportUserInfoHandler.getBmmc(zgh);
					}
					
					cRow.createCell(1).setCellValue(xm);
					cRow.createCell(2).setCellValue(bmmc);
				}

				String stid = map.get("STID");
				String xxmc = map.get("XXMC");
				String txnr = StringUtils.isNotEmpty(map.get("TXNR")) ? map.get("TXNR") : "";
				
				Cell cell = cRow.getCell(stIndx.get(stid)) != null ? cRow.getCell(stIndx.get(stid)) : cRow.createCell(stIndx.get(stid));
				
				String originCellValue = StringUtils.isNotEmpty(cell.getStringCellValue()) ? cell.getStringCellValue() : "";
				
				// 根据不同试题类型，渲染这个表格
				QuestionType qt = stidToStlxMap.get(stid);
				if (qt == QuestionType.TEXT) {// 文本题
					cell.setCellValue(txnr);
				} else if (qt == QuestionType.RADIO || qt == QuestionType.RADIO_GROUP || qt == QuestionType.MULTI || qt == QuestionType.MULTI_GROUP) {
					
					// 填写内容为空，说明是组合题选项
					if (StringUtils.isEmpty(txnr)) {
						if (StringUtils.isNotEmpty(originCellValue)) {
							String newCellValue = originCellValue + ";" + xxmc;
							String newCellProcessedValue = this.getDuplicateRemovalCellValue(newCellValue);
							cell.setCellValue(newCellProcessedValue);
						}else {
							cell.setCellValue(xxmc);
						}
					} else {
						// 填写内容不为空，不管什么情况，都要填进去
						if (StringUtils.isNotEmpty(originCellValue)) {
							String newCellValue = originCellValue + ";" + txnr;
							String newCellProcessedValue = this.getDuplicateRemovalCellValue(newCellValue);
							cell.setCellValue(newCellProcessedValue);
						} else {
							cell.setCellValue(txnr);
						}
					}
				} else if (qt == QuestionType.DESCRIBE) {
					// can not happen
					continue;
				}
			}
			book.write(fos);
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(fos);
			IOUtils.closeQuietly(book);
		}
		return file;
	}

	private String getDuplicateRemovalCellValue(String newCellValue) {
		List<String> result = new ArrayList<String>();
		
		String[] array = newCellValue.split(";");
		Set<String> set = new HashSet<String>();
		for(String str : array) {
			if(StringUtils.isNoneEmpty(str)) {
				if(!set.contains(str)) {
					result.add(str);
				}
			}
		}
		return StringUtils.join(result, ';');
	}

	@Override
	public File exportWjtj(String wjid) throws Exception{
		
		List<Map<String, Object>> wjtjList = wjglService.getWjtjList(wjid);

		WjglModel wjglQueryModel = new WjglModel();
		wjglQueryModel.setWjid(wjid);
		List<StglModel> stglList = stglService.getStxxAndStdlXxList(wjglQueryModel);

		Map<String, List<Map<String, Object>>> stxxMap = this.buildStMap(wjtjList, stglList);

		File newXlsFile = this.buildExportXxtjFile(stxxMap, stglList);

		return newXlsFile;
	}
	/**
	 * @description ： 构建试题的stid，到试题选项的map
	 * @param wjtjList
	 * @param stglList
	 * @return
	 */
	private Map<String, List<Map<String, Object>>> buildStMap(List<Map<String, Object>> wjtjList,List<StglModel> stglList) {
		Map<String, List<Map<String, Object>>> result = new TreeMap<String, List<Map<String, Object>>>();
		for (StglModel stglModel : stglList) {
			String stid = stglModel.getStid();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			result.put(stid, list);
		}
		for (Map<String, Object> map : wjtjList) {
			String stid = (String) map.get("STID");
			List<Map<String, Object>> list = result.get(stid);
			list.add(map);
		}
		return result;
	}

	private File buildNewXlsFile(){
		String newXlsFileName = UUID.randomUUID().toString() + ".xls";
		File newXlsFile = new File(this.tempFileDir, newXlsFileName);
		try {
			newXlsFile.createNewFile();
		} catch (IOException e) {
			log.error("创建临时文件[{}]异常",newXlsFile,e);
		}
		return newXlsFile;
	}
	
	private WritableWorkbook buildNewXls(List<String> headers,File newXlsFile,boolean useWcfTitle,Integer columnViewWidth) throws Exception{
		WritableWorkbook workbook = jxl.Workbook.createWorkbook(newXlsFile);
		WritableSheet sheet = workbook.createSheet("default", 0);

		// 表头
		for (int i = 0; i < headers.size(); i++) {
			String content = headers.get(i);
			
			Label label = new Label(i, 0, content);
			sheet.addCell(label);
			if(columnViewWidth != null) {
				sheet.setColumnView(i, columnViewWidth);				
			}
		}
		return workbook;
	}
	/**
	 * @description ： 构建xls文件
	 * @param stxxMap
	 * @param stMap
	 * @return
	 * @throws Exception
	 */
	private File buildExportXxtjFile(Map<String, List<Map<String, Object>>> stxxMap, List<StglModel> stglList)throws Exception {

		List<String> headers = Arrays.asList("题目编号","题型","题目", "选项编号","选项", "人数", "比例");
		File newXlsFile = this.buildNewXlsFile();
		WritableWorkbook workbook = this.buildNewXls(headers,newXlsFile,true,60);
		WritableSheet sheet = workbook.getSheet(0);
		// 内容
		Iterator<StglModel> it = stglList.iterator();
		StNumberSequence stNumberSequence = new StNumberSequence(false);
		// 当前单元格行数
		int currentRow = 1;
		while (it.hasNext()) {
			
			StxxNumberSequence stxxNumberSequence = stNumberSequence.createXxNumberSequence();
			
			StglModel stglModel = it.next();
			// 试题id
			String stid = stglModel.getStid();
			// 试题名称
			String stmc = stglModel.getStmc();
			// 试题选项列表
			List<Map<String, Object>> stxxs = stxxMap.get(stid);
			if(stglModel.getStlx().equals("0")) {
				continue;
			}
			// 试题选项
			int stxxSize = 1;
			
			//是文本题
			if(stglModel.getStlx().equals("5")) {
				
				int startRow = currentRow;
				
				// 构建试题编号单元格 ，有几个试题选项就合并几个单元格
				Label headerStbh = new Label(0, startRow, stNumberSequence.next());
				sheet.addCell(headerStbh);
				
				// 构建试题类型单元格 ，有几个试题选项就合并几个单元格
				String stlx = stglModel.getStlx();
				Label headerStlx = new Label(1, startRow, typeMapper.get(stlx));
				sheet.addCell(headerStlx);
				
				// 构建试题名称单元格 ，有几个试题选项就合并几个单元格
				Label headerLabel = new Label(2, startRow, stmc);
				sheet.addCell(headerLabel);
				
			}else {
				
				// 试题选项
				stxxSize = stxxs.size();
				int startRow = currentRow;
				int endRow = currentRow + stxxSize - 1;
				
				// 构建试题编号单元格 ，有几个试题选项就合并几个单元格
				Label headerStbh = new Label(0, startRow, stNumberSequence.next());
				sheet.addCell(headerStbh);
				sheet.mergeCells(0, startRow, 0, endRow);
				
				// 构建试题类型单元格 ，有几个试题选项就合并几个单元格
				String stlx = stglModel.getStlx();
				Label headerStlx = new Label(1, startRow, typeMapper.get(stlx));
				sheet.addCell(headerStlx);
				sheet.mergeCells(1, startRow, 1, endRow);
				
				// 构建试题名称单元格 ，有几个试题选项就合并几个单元格
				Label headerLabel = new Label(2, startRow, stmc);
				sheet.addCell(headerLabel);
				sheet.mergeCells(2, startRow, 2, endRow);
				
				// 选项列表构建
				// 当前选项的行数
				int currentXxRow = currentRow;
				for (int i = 0; i < stxxs.size(); i++) {
					Map<String, Object> stxx = stxxs.get(i);
					String xxmc = (String) stxx.get("XXMC");
					
					java.math.BigDecimal hds = (BigDecimal) stxx.get("HDS");
					java.math.BigDecimal hdl = (BigDecimal) stxx.get("HDL");
					DecimalFormat decimalFormat = new DecimalFormat("0");
					String hdlString = decimalFormat.format(hdl);
					
					//选项编号
					Label label_bh = new Label(3, currentXxRow, stxxNumberSequence.next(""));
					sheet.addCell(label_bh);
					
					// 选项名称
					Label label_xxmc = new Label(4, currentXxRow, xxmc);
					sheet.addCell(label_xxmc);
					
					// 人数
					Label stxx_hds = new Label(5, currentXxRow, hds.toString());
					sheet.addCell(stxx_hds);
					
					// 比例
					Label stxx_hdl = new Label(6, currentXxRow, hdlString + "%");
					sheet.addCell(stxx_hdl);
					
					currentXxRow++;
				}
			}
			
			// 下一题的开始行号
			currentRow += stxxSize;
		}
		workbook.write();
		workbook.close();
		return newXlsFile;
	}

	@Override
	public File exportDtxqById_10698(String wjid) throws Exception {
		List<StglModel> stglModels = this.stglService.getStxxList(wjid);
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
		List<XsxxModel> xsssModes = this.wjdcExportDao.queryWjhdxsBaseInfo(wjid);
		
		//有多少学生，就有多少行数据,预先分配，被动避免扩张
		int xlsDataRowCount = xsssModes.size();
		//有多少选表头列，就有多少数据列
		int xlsDataColumnCount = headers.size();
		List<List<String>> xlsData = new ArrayList<List<String>>(xlsDataRowCount);
		
		log.info("开始导出答题详情,学生总人数:"+xsssModes.size());
		
		for(int i=0;i<xsssModes.size();i++) {
			//一行的数据
			List<String> xlsRowData = new ArrayList<String>(xlsDataColumnCount);
			xlsData.add(xlsRowData);
			//基础信息
			XsxxModel xsxx = xsssModes.get(i);
			//姓名
			String xm = xsxx.getXm();
			xlsRowData.add(xm);
			
			//学号
			String xh = xsxx.getXh();
			xlsRowData.add(xh);
			
			//书院
			xlsRowData.add("");
			
			//学院
			String xy = xsxx.getBmmc();
			xlsRowData.add(xy);
			
			//专业
			String zy = xsxx.getZymc();
			xlsRowData.add(zy);
			
			//班级
			String bj = xsxx.getBjmc();
			xlsRowData.add(bj);
			
			log.info("导出第"+i+"个学生,学号:"+xh+",姓名:"+ xm);
			
			//当前学生的所有回答
			List<WjhdModel> wjhdModels = this.wjhdDao.queryWjhd(wjid, xh);
			
			//试题id和试题回答的map,key是试题id,value是回答的列表
			Map<String,LinkedList<WjhdModel>> wjhdMap = new HashMap<String,LinkedList<WjhdModel>>();
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
					try {
						int xxIndex_integer = WjstUtil.parseStxxSequence(xxid);
						xlsRowData.add(""+xxIndex_integer);
					}catch(NumberFormatException e) {
						log.error("单选-数字转换异常,xh["+ xh +"]wjid["+ wjid +"],stid["+ stid +"],xxid["+ xxid +"]",e);
					}
					
				} else if (type == QuestionType.RADIO_GROUP) {// 单选组合
					
					WjhdModel wjhdModel = wjhdModelList.get(0);
					if(StringUtils.isNotEmpty(wjhdModel.getXxid())) {
						String xxid = wjhdModel.getXxid();
						try {
							int xxIndex_integer = WjstUtil.parseStxxSequence(xxid);
							xlsRowData.add(""+xxIndex_integer);
						}catch(NumberFormatException e) {
							log.error("单选组合-数字转换异常,xh["+ xh +"]wjid["+ wjid +"],stid["+ stid +"],xxid["+ xxid +"]",e);
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
					log.warn("系统未定义的试题类型:type["+type+"]");
					continue;
				}
			}
		}
		//构建xls
		File newXlsFile = this.buildNewXlsFile();
		WritableWorkbook workbook = this.buildNewXls(headers,newXlsFile,false,null);
		WritableSheet sheet = workbook.getSheet(0);
		
		//写入数据
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
	
	/**
	 * @description	： 获得答题详情定制化的表头
	 * @param wjid
	 * @param baseHeaders
	 * @param stglModels
	 * @param wjstxxModels
	 * @param wjstxxMap
	 * @return
	 */
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
				log.warn("系统未定义的试题类型:type["+type+"]");
				continue;
			}
		}
		
		return headers;
	}
}
