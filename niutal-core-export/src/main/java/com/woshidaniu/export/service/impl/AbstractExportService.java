/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.service.impl;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.dao.daointerface.IExportAuthDao;
import com.woshidaniu.dao.daointerface.IExportDao;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportConfigPhModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.dao.daointerface.IExportAuthDao;
import com.woshidaniu.dao.entities.ExportConfigPhModel;
import com.woshidaniu.export.api.ExportFileNameGenerator;
import com.woshidaniu.export.api.ExportFileNameGeneratorContext;
import com.woshidaniu.export.service.svcinterface.IExportService;
import com.woshidaniu.export.service.utils.ExportComparator;
import com.woshidaniu.export.service.utils.dbf.DBFWriter;
import com.woshidaniu.export.service.utils.dbf.JDBField;
import com.woshidaniu.export.api.ExportFileNameGenerator;
import com.woshidaniu.export.service.svcinterface.IExportService;
import com.woshidaniu.export.service.utils.ExportComparator;
import com.woshidaniu.io.utils.FileUtils;
import com.woshidaniu.util.base.MessageUtil;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Penghui.Qu
 * 公用导出实现
 * 
 * @author zhidong
 * 优化
 */
public abstract class AbstractExportService implements IExportService {
	//日志，不能是static,因为子类实现了这个类
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	//是否使用角色控制导出列显示
	private static final String EXPORT_FILE_GENERATOR_CLASS_NAME = "niutal.ExportExcel.FileGeneratorClassName";
	//是否使用角色控制导出列显示
	private static final String USE_ROLE_CONTROL_COLUMN_DISPALY_KEY = "niutal.ExportExcel.isUseRoleControlColumnDispaly";
	@Autowired
	protected IExportDao exportDao;
	@Autowired
	protected IExportAuthDao exportAuthDao;
	//工作目录内文件暂留7天
	private static final long TEMP_FILE_KEEP_ALIVE_DAY = 7;
	//单位换算
	private static final long TEMP_FILE_KEEP_ALIVE_TIME = TEMP_FILE_KEEP_ALIVE_DAY /*天*/* 24/*时*/ * 60/*分*/ * 60 /*秒*/* 1000/*毫秒*/;
	//临时文件目录
	private static final File TEMP_DIR = FileUtils.getUserDirectory();
	//公司临时文件工作根目录 
	private static final String COMPANY_ROOT_WORK_DIR = "woshidaniu";
	//这个模块的临时文件工作目录
	private static final String MODULAR_WORK_DIR_NAME = "ExportService";
	//清理线程调度周期，1天
	private static final int CleanerScheduleIntervalDays = 1;
	//执行器
	private ScheduledExecutorService cleanerScheduledExecutor;
	//这个模块使用的临时文件工作目录,can't be null
	protected File modularWorkDir;
	//导出模块导出的sheet的名称
	protected String sheetName = IExportService.SHEET_NAME;
	
	private boolean isUseRoleControlColumnDispaly = false;
	
	private ExportFileNameGenerator exportFileGenerator;
	
	private String exportFileGeneratorClassName = "com.woshidaniu.export.service.impl.SimpleTimeExportFileNameGenerator";
	
	@PostConstruct
	public void init() {
		{
			String val = MessageUtil.getText(EXPORT_FILE_GENERATOR_CLASS_NAME);
			this.exportFileGeneratorClassName = StringUtils.isNotEmpty(val) ? val : this.exportFileGeneratorClassName;
			
			try {
				Class<?> clazz = Class.forName(this.exportFileGeneratorClassName);
				Object object = clazz.newInstance();
				this.exportFileGenerator = ExportFileNameGenerator.class.cast(object);
			} catch (Exception e) {
				log.error("初始化配置的key[{}],value[{}]类异常",EXPORT_FILE_GENERATOR_CLASS_NAME,val,e);
				this.exportFileGenerator = new SimpleTimeExportFileNameGenerator();
			}
		}
		{
			String key = this.getSheetNameConfigKey();
			String val = MessageUtil.getText(key);
			if(StringUtils.isNotEmpty(val)) {
				sheetName = val;
			}
			log.info("导出模块,导出sheet的名称,配置key[{}],获得value[{}],最终使用值[{}]",key,val,this.sheetName);
		}

		{
			String val = MessageUtil.getText(USE_ROLE_CONTROL_COLUMN_DISPALY_KEY);
			if(StringUtils.isNotEmpty(val)) {
				this.isUseRoleControlColumnDispaly = Boolean.valueOf(val);
			}
			log.info("导出模块,是否使用角色控制导出列显示,配置key[{}],获得value[{}],最终使用值[{}]",USE_ROLE_CONTROL_COLUMN_DISPALY_KEY,val,this.isUseRoleControlColumnDispaly);
		}

		{
			File companyRootWorkDir = FileUtils.getFile(TEMP_DIR, COMPANY_ROOT_WORK_DIR);
			if(!companyRootWorkDir.exists()) {
				companyRootWorkDir.mkdirs();
			}
			this.modularWorkDir = FileUtils.getFile(companyRootWorkDir, MODULAR_WORK_DIR_NAME);

			log.info("初始化导出模块工作目录:{}",this.modularWorkDir);
			if(!this.modularWorkDir.exists()) {
				this.modularWorkDir.mkdirs();
			}
			this.cleanModularDir(true);
			//线程执行器
			this.cleanerScheduledExecutor = Executors.newScheduledThreadPool(1,new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					final String cleanerThreadNamePrefix =  "ExportService_workDir_cleaner_0";
					Thread newThread = new Thread(r,cleanerThreadNamePrefix);
					return newThread;
				}
			});
			//周期调度任务
			this.cleanerScheduledExecutor.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					AbstractExportService.this.cleanModularDir(false);
				}
			}, CleanerScheduleIntervalDays, CleanerScheduleIntervalDays, TimeUnit.DAYS);
		}
	}

	abstract protected String getSheetNameConfigKey();

	@PreDestroy
	public void destory() {
		this.cleanerScheduledExecutor.shutdown();
	}

	private synchronized void cleanModularDir(boolean showLog) {
		File[] files = this.modularWorkDir.listFiles();
		if(files != null) {
			if(showLog) {
				log.info("清理工作目录{}内的过期文件",this.modularWorkDir);
			}
			long now = System.currentTimeMillis();
			for(int i=0;i<files.length;i++) {
				File f = files[i];
				if(f.isFile()) {
					long lastModefied = f.lastModified();
					if(now - lastModefied > TEMP_FILE_KEEP_ALIVE_TIME) {
						f.delete();
						if(showLog) {
							log.info("删除{}天前产生的工作文件:{}",TEMP_FILE_KEEP_ALIVE_DAY,f);
						}
					}
				}
			}
		}
	}
	/**
	 * @description	： 创建存放在modularWorkDir目录内的问卷
	 * @param type
	 * @return
	 */
	protected synchronized File createFile(String dcclbh,String phid,String type) throws Exception{

		String dcclmc = "";
		{
			ExportModel model = new ExportModel();
			model.setDcclbh(dcclbh);
			List<ExportConfigModel> list = this.exportDao.getExportConfig(model);
			ExportConfigModel configModel = list.get(0);
			dcclmc = configModel.getDcclmc();
		}

		String phmc = "";
		{
			if(StringUtils.isNotEmpty(phid)) {
				ExportModel model = new ExportModel();
				model.setExportPHID(phid);
				List<ExportConfigPhModel> list = this.exportDao.getExportConfigPh(model);
				ExportConfigPhModel phModel = list.get(0);
				phmc = phModel.getMc();
			}
		}
		ExportFileNameGeneratorContext context = new DefaultExportFileNameGeneratorConotext(dcclbh, dcclmc,phid,phmc, type);
		
		String newFileName = exportFileGenerator.generateFileName(context);
		File file = new File(this.modularWorkDir,newFileName);
		
		int loopCount = 2000;
		while(file.exists() && loopCount > 0) {
			loopCount --;
			file = new File(this.modularWorkDir,newFileName);
			TimeUnit.MILLISECONDS.sleep(1);
		}
		if(loopCount <= 0) {
			log.warn("loopCount <= 0 !!!");
		}
		try {
			file.createNewFile();
			file.setWritable(true);
		} catch (IOException e) {
			log.error("创建新文件{}异常",file,toString(),e);
			throw e;
		}
		return file;
	}

	@Override
	public File getExportFile(ExportModel model) throws Exception {
		
		this.checkExportModelArgument(model);
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		File resultFile = null;
		//插入dbf处理
		String exportWjgs = model.getExportWjgs();
		if (exportWjgs != null && exportWjgs.equals("dbf")) {
			resultFile =  this.createDbfFile(model);
		}else {
			resultFile = this.doGetExportFile(model);
		}

		stopWatch.stop();
		log.debug("生成导出文件[{}]耗时[{}]ms",resultFile,stopWatch.getTime());
		
		return resultFile;
	}
	
	/**
	 * @description	： 得到导出文件
	 * @param model
	 * @return
	 */
	protected abstract File doGetExportFile(ExportModel model) throws Exception;
	
	/*
	 * 生成dbf文件
	 */
	protected File createDbfFile(ExportModel model) throws Exception {
		String dcclbh = model.getDcclbh();
		String phid = model.getExportPHID();
		File file = this.createFile(dcclbh,phid,"dbf");
		
		//获取导出配置
		List<ExportConfigModel> configList = getConfigList(model);

		ExportConfigModel exportConfigModel = null;
		for (int i = configList.size() - 1; i >= 0; i--) {
			exportConfigModel = configList.get(i);
			if (UNSELECT_ZT.equals(exportConfigModel.getZd())) {
				configList.remove(i);
			}
		}

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
								value = (String)fieldValue;
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
				dbfWriter = new DBFWriter(file, dbfField);
				for (int i = 0; i < aobjList.size(); i++) {
					dbfWriter.addRecord(aobjList.get(i));
				}
			}
		} catch (Exception e) {
			log.error("创建dbf文件异常",e);
		} finally {
			if (dbfWriter != null) {
				try {
					dbfWriter.close();
				} catch (Exception e) {
					log.error("",e);
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

	@Override
	public List<ExportConfigModel> getExportPublicConfig(ExportModel model){
		if(StringUtils.isBlank(model.getDcclbh())) {
			return Collections.emptyList();
		}else {
			List<ExportConfigModel> configList = exportDao.getExportPublicConfig(model.getDcclbh());
			return this.doFilteConfigListByAuth(configList,model);
		}
	}
	
	@Override
	public List<ExportConfigModel> getConfigList(ExportModel model) {
		
		List<ExportConfigModel> configList = this.doGetConfigList(model);
		
		return this.doFilteConfigListByAuth(configList,model);
	}
	
	private List<ExportConfigModel> doGetConfigList(ExportModel model) {
		
		this.checkExportModelArgument(model);

		String[] selectZd = model.getSelectCol();
		if(selectZd == null || selectZd.length <= 0) {
			log.warn("ExportModel的属性selectCol(选择字段列表)为null");
		}
		
		List<ExportConfigModel> configList = exportDao.getExportConfig(model);;
		model.setZgh(PUBLIC_CONFIG);
		List<ExportConfigModel> publicConfigList = exportDao.getExportConfig(model);
		
		if (selectZd != null && selectZd.length > 0){
			
			configList = new ArrayList<ExportConfigModel>();
			
			for (String zdStr : selectZd){
				
				//ExportConfigModel configModel = new ExportConfigModel();
				//configModel.setZd();
				//configModel.setZdmc(selectZd[i].split("@")[1]);
				//configModel.setZt(SELECT_ZT);
				
				String[] strArray = zdStr.split("!_##_!");
				String col = strArray[0];
				String zd = strArray[1];
				log.debug("选择的列[{}],字段名称[{}]",col,zd);
				ExportConfigModel resolvedConfigModel = resolveConfigModel(col, publicConfigList);
				configList.add(resolvedConfigModel);
			}
		} else {
			//导出字段列表以public为准
			String zgh = model.getZgh();
			for (ExportConfigModel publicExportConfigModel : publicConfigList) {
				String zd = publicExportConfigModel.getZd();
				
				for (ExportConfigModel exportConfigModel : configList) {
					String zt = exportConfigModel.getZt();
					if(zd.equals(exportConfigModel.getZd()) && zt != null && zt.equals("0")){
						publicExportConfigModel.setZt("0");
						break;
					}
				}
				publicExportConfigModel.setZgh(zgh);
				publicExportConfigModel.setSfmrzd(null);
			}
			configList = publicConfigList;
			//按显示顺序排序
			Collections.sort(configList,new ExportComparator());
		}
		return configList;
	}

	private List<ExportConfigModel> doFilteConfigListByAuth(List<ExportConfigModel> configList, ExportModel model) {
		
		//未启用基于角色的控制列展示
		if(!isUseRoleControlColumnDispaly || StringUtils.isEmpty(model.getJs())) {
			return configList;
		}
		
		Set<String> set = this.exportAuthDao.getExportAuthZdList(model.getDcclbh(), model.getJs());
		
		//没有配置
		if(set.isEmpty()) {
			return Collections.emptyList();
		}
		Iterator<ExportConfigModel> it = configList.iterator();
		while(it.hasNext()) {
			ExportConfigModel exportConfigModel = it.next();
			String zd = exportConfigModel.getZd();
			if(!set.contains(zd)) {
				it.remove();
				log.debug("当前查询参数角色:[{}],导出编号:[{}],移除未配置字段[{}]",model.getJs(),model.getDcclbh(),zd);
			}
		}
		return configList;
	}

	protected ExportConfigModel resolveConfigModel(String string, List<ExportConfigModel> publicConfigList) {
		if((publicConfigList != null) && (string != null)){
			for (ExportConfigModel exportConfigModel : publicConfigList) {
				if(string.equals(exportConfigModel.getZd())){
					return exportConfigModel;
				}
			}
		}
		return null;
	}


	@Override
	public List<ExportConfigPhModel> getConfigPhList(ExportModel model) {
		this.checkExportModelArgument(model);
		return exportDao.getExportConfigPh(model);
	}

	@Override
	public List<ExportConfigModel> getConfigPhZdList(String id, String zgh){
		if(StringUtils.isBlank(zgh) || StringUtils.isBlank(id)){
			return Collections.emptyList();
		}
		Map<String,String> args = new HashMap<String,String>();
		args.put("id", id);
		args.put("zgh", zgh);
		return exportDao.getExportConfigPhList(args);
	}
	
	@Override
	public boolean deleteExportConfig(ExportModel model){
		if(StringUtils.isBlank(model.getExportPHID())){
			return false;
		}
		return exportDao.deleteConfigPh(model.getExportPHID()) > 0;
	}
	
	@Override
	public boolean saveExportConfig(ExportModel model) {
		
		this.checkExportModelArgument(model);
		
		String selectZd = model.getSelectZd();
		//处理选中的字段
		List<ExportConfigPhModel> configPhModelList = new ArrayList<ExportConfigPhModel>();
		if(StringUtils.isNotEmpty(selectZd)){
			String[] selectCol = selectZd.split(",");
			String id = UniqID.getInstance().getUniqIDHash().toUpperCase();
			for (int i = 0 ; i < selectCol.length ; i++){
				ExportConfigPhModel phModel = new ExportConfigPhModel();
				phModel.setDcclbh(model.getDcclbh());
				phModel.setId(id);
				phModel.setMc(model.getExportPHMC());
				phModel.setZd(selectCol[i].split("!_##_!")[0]);
				phModel.setZdmc(selectCol[i].split("!_##_!")[1]);
				phModel.setZgh(model.getZgh());
				configPhModelList.add(phModel);
			}
		}
		//判断是否已经存在配置
		//如果存在配置则更新
		//如果不存在则进行插入
		//1.先根据偏好设置名称查询记录
		int result = 0;
		
		if(StringUtils.isBlank(model.getExportPHID())){
			//插入
			result = exportDao.insertConfigPh(configPhModelList);
		}else {
			List<ExportConfigPhModel> exportConfigPh = exportDao.getExportConfigPh(model);
			if(exportConfigPh != null && exportConfigPh.size() > 0){
				//更新
				exportDao.deleteConfigPh(model.getExportPHID());
				for (ExportConfigPhModel exportConfigPhModel : configPhModelList) {
					exportConfigPhModel.setId(model.getExportPHID());
				}
				result = exportDao.insertConfigPh(configPhModelList);
			}else{
				//插入
				result = exportDao.insertConfigPh(configPhModelList);
			}
		}
		return result > 0;
		
	}

	@Override
	public ExportModel saveExportConfig2(ExportModel model) {
		
		this.checkExportModelArgument(model);
		
		String dcclbh = model.getDcclbh(),id = UniqID.getInstance().getUniqIDHash().toUpperCase(),mc = model.getExportPHMC(),zgh = model.getZgh();
		String selectZd = model.getSelectZd();
		//处理选中的字段
		List<ExportConfigPhModel> configPhModelList = new ArrayList<ExportConfigPhModel>();
		if(StringUtils.isNotEmpty(selectZd)){
			String[] selectCol = selectZd.split(",");
			for (int i = 0 ; i < selectCol.length ; i++){
				ExportConfigPhModel phModel = new ExportConfigPhModel();
				phModel.setDcclbh(dcclbh);
				phModel.setId(id);
				phModel.setMc(mc);
				phModel.setZd(selectCol[i].split("!_##_!")[0]);
				phModel.setZdmc(selectCol[i].split("!_##_!")[1]);
				phModel.setZgh(zgh);
				phModel.setXssx(String.valueOf(i + 1));
				configPhModelList.add(phModel);
			}
		}
		//判断是否已经存在配置
		//如果存在配置则更新
		//如果不存在则进行插入
		//1.先根据偏好设置名称查询记录
		if(StringUtils.isBlank(model.getExportPHID())){
			//插入
			model.setExportPHID(id);
			exportDao.insertConfigPh(configPhModelList);
		}else {
			List<ExportConfigPhModel> exportConfigPh = exportDao.getExportConfigPh(model);
			if(exportConfigPh != null && exportConfigPh.size() > 0){
				//更新
				exportDao.deleteConfigPh(model.getExportPHID());
				for (ExportConfigPhModel exportConfigPhModel : configPhModelList) {
					exportConfigPhModel.setId(model.getExportPHID());
				}
				exportDao.insertConfigPh(configPhModelList);
			}else{
				//插入
				model.setExportPHID(id);
				exportDao.insertConfigPh(configPhModelList);
			}
		}
		return model;
	}
	
	protected void checkExportModelArgument(ExportModel exportModel) {
		if(exportModel == null) {
			throw new IllegalArgumentException("exportModel argument can't be null");
		}
		if(StringUtils.isEmpty(exportModel.getDcclbh())){
			throw new IllegalArgumentException("exportModel's dcclbh can't be empty");
		}
		if(StringUtils.isEmpty(exportModel.getDcclbh())){
			throw new IllegalArgumentException("exportModel's zgh can't be empty");
		}
	}
	
}