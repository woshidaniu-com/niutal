package com.woshidaniu.drdcsj.drsj.action;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.svcinterface.IExcelImportService;

/**
 * 通用导入Action
 * @author kzd
 */
@Controller
public class ImportAction extends BaseAction implements ModelDriven<DrpzModel> {
	
	private static final long serialVersionUID = -1378324821416609346L;
	
	private static final Logger log = LoggerFactory.getLogger(ImportAction.class);
	//临时文件名称放入sessin的key
	private static final String TEMP_FILE_NAME_KEY = ImportAction.class.getName() + "_temp_file_name"; 
	//临时文件的原文件名称放入sessin的key
	private static final String ORIGIN_FILE_NAME_KEY = ImportAction.class.getName() + "_origin_file_name";
	//工作目录内文件暂留15天
	private static final long TEMP_FILE_KEEP_ALIVE_DAY = 15;
	private static final long TEMP_FILE_KEEP_ALIVE_TIME = TEMP_FILE_KEEP_ALIVE_DAY /*天*/* 24/*时*/ * 60/*分*/ * 60 /*秒*/* 1000/*毫秒*/;
	//公司临时文件工作根目录 
	private static final String COMPANY_ROOT_WORK_DIR = "woshidaniu";
	//这个模块的临时文件工作目录
	private static final String MODULAR_WORK_DIR_NAME = ImportAction.class.getName();
	//这个模块使用的临时文件工作目录,can't be null
	private File modularWorkDir;
	@Autowired
	private IExcelImportService excelImportService;
	private DrpzModel model = new DrpzModel();
//	private File importFile;
//	private String importFileFileName; //文件名称
//    private String importFileContentType; //文件类型
	private String json;
	public static String yhm = null;
	/*
	 * 选择列分隔符号
	 */
	private final String _SELECT_IMPORT_COLUMN_SING = ",";
	/**
	 * 用户设置的导入列配置(名称)
	 */
	private String drlpzs;
	/**
	 * 用户设置的导入列配置(id)
	 */
	private String drlpzids;
	
	public ImportAction() {
		
		File tempDir = FileUtils.getTempDirectory();
		File companyRootWorkDir = FileUtils.getFile(tempDir, COMPANY_ROOT_WORK_DIR);
		
		if(!companyRootWorkDir.exists()) {
			log.info("初始化创建公司临时文件工作根目录:{}",companyRootWorkDir);
			companyRootWorkDir.mkdirs();
		}else {
			log.info("使用公司临时文件工作根目录:{}",companyRootWorkDir);
		}
		this.modularWorkDir = FileUtils.getFile(companyRootWorkDir, MODULAR_WORK_DIR_NAME);
		
		log.info("初始化导入模块工作目录:{}",this.modularWorkDir);
		if(this.modularWorkDir.exists()) {
			//tomcat的reload会触发spring的reload,但session不会清除,所以只能采取删除一定时间之前的工作文件
			//其实最佳删除周期,是session过期周期,但这里15天也是为了统计导入次数,导入错误次数,方便排查问题
			File[] files = this.modularWorkDir.listFiles();
			if(files != null) {
				log.info("清理工作目录{}内的过期文件",this.modularWorkDir);
				long now = System.currentTimeMillis();
				for(int i=0;i<files.length;i++) {
					File f = files[i];
					if(f.isFile()) {
						long lastModefied = f.lastModified();
						if(now - lastModefied > TEMP_FILE_KEEP_ALIVE_TIME) {
							f.delete();
							log.info("删除{}天前产生的工作文件:{}",TEMP_FILE_KEEP_ALIVE_DAY,f);	
						}
					}
				}
			}
		}else {
			this.modularWorkDir.mkdirs();
		}
	}

	/**
	 * 导航到导入页面
	 * @return
	 */
	public String showImport() {
		try {
			List<DrpzModel> importList = excelImportService.getDrPzxx(model.getDrmkdm());
			getValueStack().set("importList", importList);
		} catch (Exception e) {
			logException(e);
		}
		return "import";
	}

	public String showRuler() {

		return null;
	}

	/**
	 * 下载模板
	 * 
	 * @return
	 */
	public String dowloadTemplate() {
		try {
			String drmkdm = model.getDrmkdm();
			file = excelImportService.getTemplateFile(drmkdm);
		} catch (Exception e) {
			logException(e);
		}
		return "exportExcel";
	}

	/**
	 * 下载错误信息
	 * 
	 * @return
	 */
	public String dowloadError() {
		return "exportExcel";
	}

	/**
	 * 获取用户选择的导入列
	 * 
	 * @return
	 */
	public List<String> getSelectImportColumn() {
		if (StringUtils.isEmpty(drlpzs)) {
			return null;
		}
		return Arrays.asList(drlpzs.split(_SELECT_IMPORT_COLUMN_SING));
	}

	public List<String> getSelectImportColumnId() {
		if (StringUtils.isEmpty(drlpzids)) {
			return null;
		}
		return Arrays.asList(drlpzids.split(_SELECT_IMPORT_COLUMN_SING));
	}

	/**
	 * 导入
	 * @return
	 */
	public String importData() {
		String yhm = getUser().getYhm();
		//Map<String,String>  resultMap = excelImportService.importData(null,null,null,null,null,null);
		//getValueStack().set(DATA, resultMap);
		return DATA;
	}

	/**
	 * 检测导入
	 * 
	 * @return
	 */
	public String checkImport() {
		//try {
		//	excelImportService.checkExcel(model.getImportFile(), getSelectImportColumn());
		//} catch (Exception e) {
		//	logException(e);
		//}
		return null;
	}

	/**
	 * 获取导入列信息
	 * 
	 * @return
	 */
	public String getImportColumn() {
		try {
			List<DrlpzModel> list = excelImportService.getImportColumn(model);
			if (null == list || list.isEmpty()) {
				list = null;
			}
			//TODO 改进
			//Map<String, List<String>> map = Cache.getData(model.getDrmkdm() + "_"+ yhm);
			//Object[] data = new Object[] {map.keySet(), list,excelImportService.getCrpz(model.getDrmkdm())};
			//getValueStack().set(DATA, data);
			
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	public String selectColumn() {
		return "selectColumn";
	}

	/**
	 * 上传excel
	 * 
	 * @return
	 */
	public String uploadExcel() {
		/**
		User user = getUser();
		yhm = user.getYhm();
		Map<String, List<String>> map = null;
		String filetype = model.getFiletype();
		if(StringUtils.equalsIgnoreCase("dbf", filetype)){
			map = DBFUtils.readerDBF(model.getImportFile());
		}else{
			map = DrExcelUtils.readerExcel(model.getImportFile(),model.getImportFileFileName());
			//map = ExcelUtils.readerExcel(importFile);
		}
		**/
		//TODO 改进
		//Cache.setData(model.getDrmkdm() + "_" + yhm, map);
		//getValueStack().set("json", "true");
		return "json";
	}

	/**
	 * 获取规则验证信息
	 * @return
	 */
	public String getRulers() {
		try {
			List<DrlpzModel> list = excelImportService.getRulers(null, model);
			getValueStack().set(DATA, list);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	public String showRulers() {
		return "showrulers";
	}

	@Override
	public DrpzModel getModel() {
		return model;
	}

	public void setModel(DrpzModel model) {
		this.model = model;
	}


	public IExcelImportService getExcelImportService() {
		return excelImportService;
	}

	public void setExcelImportService(IExcelImportService excelImportService) {
		this.excelImportService = excelImportService;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getDrlpzs() {
		return drlpzs;
	}

	public void setDrlpzs(String drlpzs) {
		this.drlpzs = drlpzs;
	}

	public String getDrlpzids() {
		return drlpzids;
	}

	public void setDrlpzids(String drlpzids) {
		this.drlpzids = drlpzids;
	}

//	public File getImportFile() {
//		return importFile;
//	}
//
//	public void setImportFile(File importFile) {
//		this.importFile = importFile;
//	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

//	public String getImportFileFileName() {
//		return importFileFileName;
//	}
//
//	public void setImportFileFileName(String importFileFileName) {
//		this.importFileFileName = importFileFileName;
//	}
//
//	public String getImportFileContentType() {
//		return importFileContentType;
//	}
//
//	public void setImportFileContentType(String importFileContentType) {
//		this.importFileContentType = importFileContentType;
//	}

}
