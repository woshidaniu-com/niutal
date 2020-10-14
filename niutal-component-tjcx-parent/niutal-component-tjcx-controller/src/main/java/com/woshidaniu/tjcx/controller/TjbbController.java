package com.woshidaniu.tjcx.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.User;
import com.woshidaniu.io.utils.FilenameUtils;
import com.woshidaniu.tjcx.dao.entites.KzszModel;
import com.woshidaniu.tjcx.dao.entites.TjbbModel;
import com.woshidaniu.tjcx.dao.entites.TjxmModel;
import com.woshidaniu.tjcx.html.DefaultHtmlTableToXlsParser;
import com.woshidaniu.tjcx.html.HtmlTableToXlsParser;
import com.woshidaniu.tjcx.html.TraceInputHtmlTableToXlsParser;
import com.woshidaniu.tjcx.service.svcinterface.IKzszService;
import com.woshidaniu.tjcx.service.svcinterface.ITjbbService;
import com.woshidaniu.tjcx.service.svcinterface.ITjxmService;
import com.woshidaniu.util.base.MessageUtil;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计报表action
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-23 上午08:38:45
 * @版本： V1.0
 * @修改记录:
 */
@Controller
@RequestMapping("/niutal/tjcx/tjbb")
public class TjbbController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(TjbbController.class);
	//是否跟踪统计查询的导出xls的输入html
	private static final String IS_TRACE_HTML_TO_XLS_TO_INPUT_HTML_CONFIG_KEY  = "niutal.tjcx.isTraceHtmlToXlsInputHtml";
	//默认xls文件前缀
	private static final String DEFAULT_XLS_FILE_NAME_PREFIX = "export";
	//默认xls文件名称
	private static final String DEFAULT_XLS_FILE_NAME = DEFAULT_XLS_FILE_NAME_PREFIX+".xls";
	// 工作目录内文件暂留7天
	protected static final long TEMP_FILE_KEEP_ALIVE_DAY = 7;
	// 单位换算
	protected static final long TEMP_FILE_KEEP_ALIVE_TIME = TEMP_FILE_KEEP_ALIVE_DAY /* 天 */ * 24/* 时 */ * 60/* 分 */	* 60 /* 秒 */ * 1000/* 毫秒 */;
	// 临时文件目录
	protected final File TEMP_DIR = FileUtils.getTempDirectory();
	// 公司临时文件工作根目录
	protected final String COMPANY_ROOT_WORK_DIR = "woshidaniu";
	// 这个模块的临时文件工作目录
	protected final String MODULAR_WORK_DIR_NAME = TjbbController.class.getSimpleName();
	// 清理线程调度周期，1天
	protected static final int CLEANER_SCHEDULE_INTERVAL_DAYS = 1;
	// 执行器
	protected ScheduledExecutorService cleanerScheduledExecutor;
	// 这个模块使用的临时文件工作目录,can't be null
	protected File modularWorkDir;
	//是否跟踪导出统计的html片段
	protected boolean isTraceHtmlToXlsInputHtml = false;
	
	// 当初始化
	@PostConstruct
	public void init() {
		String configValue = MessageUtil.getText(IS_TRACE_HTML_TO_XLS_TO_INPUT_HTML_CONFIG_KEY);
		if("true".equals(configValue)) {
			this.isTraceHtmlToXlsInputHtml = true;
		}
		log.info("初始化统计查询-统计报表-是否跟踪统计查询的导出xls的输入html,配置key[{}],配置value[{}],是否跟踪[{}]",IS_TRACE_HTML_TO_XLS_TO_INPUT_HTML_CONFIG_KEY,configValue,this.isTraceHtmlToXlsInputHtml);
		try {
			this.doInit();
		} catch (Throwable t) {
			log.error("初始化工作目录[{}]异常", t);
		}
	}

	private void doInit() {

		File companyRootWorkDir = FileUtils.getFile(TEMP_DIR, COMPANY_ROOT_WORK_DIR);
		if (!companyRootWorkDir.exists()) {
			companyRootWorkDir.mkdirs();
		}
		this.modularWorkDir = FileUtils.getFile(companyRootWorkDir, MODULAR_WORK_DIR_NAME);

		log.info("初始化统计查询模块工作目录:{}", this.modularWorkDir);
		if (this.modularWorkDir.exists()) {
			this.cleanModularDir(true);
		} else {
			this.modularWorkDir.mkdirs();
		}
		// 线程执行器
		final String cleanerThreadNamePrefix = TjbbController.class.getSimpleName() + "_workDir_cleaner_" + this.modularWorkDir.toString();
		this.cleanerScheduledExecutor = Executors.newScheduledThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread newThread = new Thread(r, cleanerThreadNamePrefix);
				return newThread;
			}
		});
		// 周期调度任务
		this.cleanerScheduledExecutor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				TjbbController.this.cleanModularDir(false);
			}
		}, CLEANER_SCHEDULE_INTERVAL_DAYS, CLEANER_SCHEDULE_INTERVAL_DAYS, TimeUnit.DAYS);
	}

	// 当销毁
	@PreDestroy
	public void destory() {
		this.cleanerScheduledExecutor.shutdown();
	}

	/**
	 * tomcat的reload会触发spring的reload,但session不会清除,所以只能采取删除一定时间之前的工作文件
	 * 其实最佳删除周期,是session过期周期,但这里7天也是为了统计导入次数,导入错误次数,方便排查问题
	 */
	private void cleanModularDir(boolean showLog) {
		try {
			this.doCleanModularDir(showLog);
		} catch (Throwable t) {
			log.error("清理工作目录[{}]异常", t);
		}
	}

	private void doCleanModularDir(boolean showLog) {
		File[] files = this.modularWorkDir.listFiles();
		if (files != null) {
			if (showLog) {
				log.info("清理工作目录{}内的过期文件", this.modularWorkDir);
			}
			long now = System.currentTimeMillis();
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f.isFile()) {
					long lastModefied = f.lastModified();
					if (now - lastModefied > TEMP_FILE_KEEP_ALIVE_TIME) {
						f.delete();
						if (showLog) {
							log.info("删除{}天前产生的工作文件:{}", TEMP_FILE_KEEP_ALIVE_DAY, f);
						}
					}
				}
			}
		}
	}
	
	protected static String TJCX_GNMK_BS	= "TJCX_GNMK";
	
	@Autowired
	protected ITjbbService service;
	@Autowired
	protected ITjxmService tjxmService;
	@Autowired
	protected IKzszService kzszService;
	
	@ResponseBody
	@RequestMapping("/getSjByXmdm.zf")
	public Object getSjByXmdm(KzszModel model) {
		try {
			User user = getUser();
			model.setCzy(user.getYhm());
			TjbbModel tjbbModel = service.getSjByXmdm(model);
		    return tjbbModel;
		} catch (Exception e) {
			logException(e);
		}
		return MessageKey.DO_FAIL.getJson();
	}

	@RequestMapping("/tjbb.zf")
	public String tjbb(HttpServletRequest request, KzszModel model) {
		String gnmk = model.getGnmk();
		if(gnmk == null){
			gnmk = (String)request.getSession().getAttribute(TJCX_GNMK_BS);
		}
		model.setGnmk(gnmk);
		model.setCzy(getUser().getYhm());
		request.setAttribute("model", model);
		return "/tjcx/tjbb";
	}
	
	@RequestMapping("/tjlbxq.zf")
	public String tjlbxq() {
		return "/tjcx/tjlbxq";
	}

	/**
	 * 
	 * @描述:统计列表
	 * @作者：ligl
	 * @日期：2013-9-3 上午10:49:06
	 * @修改记录: 
	 * @return
	 * String 返回类型 
	 * @throws
	 */
	@RequestMapping("/tjlb.zf")
	public String tjlb(HttpServletRequest request,KzszModel model) {
		String gltjmc = model.getGltjmc();
		if (StringUtils.isNotBlank(model.getKzszid())) {
			request.setAttribute("kzszid", model.getKzszid());
			KzszModel kzszModel = model = kzszService.getModel(model);
			String xmdm = kzszModel.getXmdm();
			TjxmModel tjxmModel = new TjxmModel();
			tjxmModel.setXmdm(xmdm);
			TjxmModel tjxmModel2 = tjxmService.getModel(tjxmModel);
			request.setAttribute("gnmk", tjxmModel2.getGnmk());
		}else{
			request.setAttribute("gnmk", model.getGnmk());
		}
		request.setAttribute("xmdm", model.getXmdm());
		request.setAttribute("gltj", model.getGltj());
		request.setAttribute("gltjmc", gltjmc);
		request.setAttribute("bbhxl", model.getBbhxl());
		request.setAttribute("bbzxl", model.getBbzxl());
		request.setAttribute("tsx", model.getTsx());
		request.setAttribute("kzms", model.getKzms());
		return "/tjcx/tjlb";
	}
	
	/**
	 * 
	 * @描述:统计图表
	 * @作者：ligl
	 * @日期：2013-9-3 上午10:49:06
	 * @修改记录: 
	 * @return
	 * String 返回类型 
	 * @throws
	 */
	@RequestMapping("/tjtb.zf")
	public String tjtb() {
		return "/tjcx/tjtb";
	}

	@RequestMapping("/export.zf")
	public void export(HttpServletRequest request, HttpServletResponse response, String tableHtml) throws IOException{
		//解析得到表格列表数据
		HtmlTableToXlsParser parser = null;
		if(this.isTraceHtmlToXlsInputHtml) {
			parser = new TraceInputHtmlTableToXlsParser(modularWorkDir,tableHtml);
		}else {
			parser = new DefaultHtmlTableToXlsParser(modularWorkDir,tableHtml);
		}
		File xlsFile = parser.parse();
		
		request.setAttribute("tableHtml", tableHtml);
		response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;");
        
        String exportXlsFileName = DEFAULT_XLS_FILE_NAME;
        if(this.isTraceHtmlToXlsInputHtml) {
        	String fileId = FilenameUtils.getBaseName(xlsFile.getName());
        	exportXlsFileName = DEFAULT_XLS_FILE_NAME_PREFIX + "-" + fileId +".xls";
        }
        response.setHeader("Content-Disposition", "attachment;filename=\""+ new String(exportXlsFileName.getBytes(),"ISO-8859-1") +"\"");
		OutputStream outputStream = response.getOutputStream();
		FileUtils.copyFile(xlsFile, outputStream);
		outputStream.flush();
		outputStream.close();
	}

	@RequestMapping("/tjlbBzsm.zf")
	public String tjlbBzsm(){
		return "/tjcx/tjlbBzsm";
	}
	
	@ResponseBody
	@RequestMapping("/tjsj.zf")
	public Object tjsj(KzszModel model) {
		try {
			User user = getUser();
			model.setCzy(user.getYhm());
			
			Integer limit = MessageUtil.getInt("tjcx_data_limit");
			model.setLimit(limit == null?-1:limit);
			TjbbModel tjbbModel = service.getTjbb(model);
			return tjbbModel;
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}

	public ITjbbService getService() {
		return service;
	}

	public void setService(ITjbbService service) {
		this.service = service;
	}

	public ITjxmService getTjxmService() {
		return tjxmService;
	}

	public void setTjxmService(ITjxmService tjxmService) {
		this.tjxmService = tjxmService;
	}
	
	public IKzszService getKzszService() {
		return kzszService;
	}

	public void setKzszService(IKzszService kzszService) {
		this.kzszService = kzszService;
	}

}
