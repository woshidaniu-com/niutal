/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.file.TempFileService;
import com.woshidaniu.common.jmx.JmxBeanRegisterHelper;
import com.woshidaniu.drdcsj.drsj.comm.Constants;
import com.woshidaniu.drdcsj.drsj.dao.entities.CrpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
import com.woshidaniu.drdcsj.drsj.excel.DrExcelUtils;
import com.woshidaniu.drdcsj.drsj.handler.excel.ExcelRow;
import com.woshidaniu.drdcsj.drsj.handler.excel.HandlerExcelUtils;
import com.woshidaniu.drdcsj.drsj.svcinterface.IExcelImportService;
import com.woshidaniu.drdcsj.drsj.svcinterface.IImportService;
import com.woshidaniu.drdcsj.drsj.svcinterface.impl.ExcelImportServiceV2;
import com.woshidaniu.util.base.MessageUtil;

/**
 * common import controller
 * @author kzd
 * @author zhidong
 */
@Controller
@RequestMapping("/niutal/dr/import")
public class ImportController extends BaseController implements ImportControllerMBean{
	
	private static final Logger log = LoggerFactory.getLogger(ImportController.class);

	private static final String TEMP_FILE_NAME_KEY = ImportController.class.getSimpleName() + "_temp_file_name";

	private static final String ORIGIN_FILE_NAME_KEY = ImportController.class.getSimpleName() + "_origin_file_name";

	private static final Set<String> VERSIONS = new HashSet<String>(Arrays.asList("v1","v2"));
	//选择列分隔符号
	private static final String SELECT_IMPORT_COLUMN_SING = ",";

	private String version = "v1";
	
	private boolean isDownloadChineseTemplateFileName = false;
	
	private boolean isDownloadChineseErrorFileName = false;
	
	@Autowired
	private IExcelImportService excelImportService;
	
	@Autowired
	private ExcelImportServiceV2 excelImportServiceV2;
	
	@Autowired
	private TempFileService tempFileService;
	
	private JmxBeanRegisterHelper jmxBeanRegisterHelper = new JmxBeanRegisterHelper();
	
	@PostConstruct
	public void init() {
		{
			String val = MessageUtil.getText("niutal.dr.version");
			if (StringUtils.isNotEmpty(val) && VERSIONS.contains(val)) {
				this.version = val;
				log.info("导入模块,模块版本key:[niutal.dr.version],模块版本value:[{}],最终使用版本[{}]",val,this.version);
			}
		}
		{
			String val = MessageUtil.getText("niutal.downloadTemplateFileName.useChinese");
			this.isDownloadChineseTemplateFileName = StringUtils.isNotEmpty(val) ? Boolean.parseBoolean(val) : this.isDownloadChineseTemplateFileName;  
		}
		{
			String val = MessageUtil.getText("niutal.downloadErrorFileName.useChinese");
			this.isDownloadChineseErrorFileName = StringUtils.isNotEmpty(val) ? Boolean.parseBoolean(val) : this.isDownloadChineseErrorFileName;
		}
		{
			jmxBeanRegisterHelper.registerMBean(this);
		}
	}
	
	@PreDestroy
	public void destory() {
		jmxBeanRegisterHelper.unregisterMBean(this);
	}
	
	/**
	 * @description	： 导入页面
	 * @param request
	 * @param drmkdm
	 * @return
	 */
	@RequestMapping("/showImport.zf")
	public String showImport(HttpServletRequest request, String drmkdm) {
		try {
			//参数携带的版本信息
			String versionParameter = request.getParameter("version");
			if(StringUtils.isNotEmpty(versionParameter)) {
				if(VERSIONS.contains(versionParameter)) {
					log.debug("version参数:[{}]",versionParameter);
				}else {
					versionParameter = this.version;
					log.warn("非法version参数:[{}],使用配置的版本参数:[{}]",versionParameter,this.version);
				}
			}
			List<DrpzModel> importList = excelImportService.getDrPzxx(drmkdm);
			request.setAttribute("importList", importList);
			request.setAttribute("version", versionParameter);
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
		return "/dr/import";
	}
	
	/**
	 * @description	： 下载模板
	 * @param request
	 * @param drmkdm
	 * @return
	 */
	@RequestMapping("/dowloadTemplate.zf")
	public ResponseEntity<byte[]> dowloadTemplate(HttpServletRequest request, String drmkdm) {
		try {
			File file = excelImportService.getTemplateFile(drmkdm);
			String fileName = String.format("%s_%s.%s", drmkdm,System.currentTimeMillis(),"xls");
			if(this.isDownloadChineseTemplateFileName) {
				List<DrpzModel> list = this.excelImportService.getDrPzxx(drmkdm);
				if(!list.isEmpty()) {
					DrpzModel drpzModel = list.get(0);
					String drmkmc = drpzModel.getDrmkmc();
					fileName = String.format("%s_导入模板.%s", drmkmc,"xls");					
				}
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}
	
	/**
	 * @description	： 下载错误汇总excel文件
	 * @param request
	 * @param drmkdm
	 * @param resultFileId
	 * @return
	 */
	@RequestMapping("/dowloadError.zf")
	public ResponseEntity<byte[]> dowloadError(HttpServletRequest request, String drmkdm,String resultFileId) {
		try {
			String tempFileName = resultFileId+".xls";
			File file = this.tempFileService.getTempFile(Constants.MODULAR_WORK_DIR_NAME, tempFileName);
			String createFileName = String.format("%s_%s_error_result.%s", drmkdm,System.currentTimeMillis(),"xls");
			if(this.isDownloadChineseErrorFileName) {
				List<DrpzModel> list = this.excelImportService.getDrPzxx(drmkdm);
				if(!list.isEmpty()) {
					DrpzModel drpzModel = list.get(0);
					String drmkmc = drpzModel.getDrmkmc();
					createFileName = String.format("%s_导入错误结果汇总.%s", drmkmc,"xls");					
				}
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", URLEncoder.encode(createFileName, "UTF-8"));
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
		} catch (Exception ex) {
			logException(ex);
		}
		return null;
	}

	/**
	 * @description	： 导入数据
	 * @param request
	 * @param drlpzids
	 * @param drmkdm
	 * @param crfs
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/importData.zf")
	public Map<String,String> importData(HttpServletRequest request, String drlpzids,String drmkdm,String crfs) {

		String versionParameter = request.getParameter("version");
		String currentVersion = this.version;
		if(StringUtils.isNotEmpty(versionParameter) && VERSIONS.contains(versionParameter)) {
			log.debug("使用URL内version参数:"+versionParameter);
			currentVersion = versionParameter;
		}
		HttpSession session = request.getSession();

		String currentUser = getUser().getYhm();
		String sessionId = request.getSession().getId();
		log.info("开始执行导入,版本[{}],用户名:{},会话id:{}",currentVersion,currentUser,sessionId);

		String tempFileName = (String) session.getAttribute(TEMP_FILE_NAME_KEY);
	    String originFileName = (String) session.getAttribute(ORIGIN_FILE_NAME_KEY);

	    List<String> selectColumns = Collections.emptyList();
		if (StringUtils.isNotEmpty(drlpzids)) {
			selectColumns = Arrays.asList(drlpzids.split(SELECT_IMPORT_COLUMN_SING));
		}

	    File tempImportDataFile = this.tempFileService.getTempFile(Constants.MODULAR_WORK_DIR_NAME, tempFileName);
	    if(!tempImportDataFile.exists()) {
	    	log.warn("没有找到{}文件",tempImportDataFile);
			Map<String,String> errorResultMap = this.getInternalError(currentVersion);
			log.info("导入结果返回:[{}]",errorResultMap);
			return errorResultMap;
	    }
		try {
			Map<String,String> resultMap = this.doImportData(tempImportDataFile,originFileName,drmkdm,crfs,selectColumns,currentUser,currentVersion);
			return resultMap;
		}catch(Exception e) {
			log.error("导入数据异常",tempImportDataFile,e);
			Map<String,String> errorResultMap = this.getInternalError(currentVersion);
			log.info("导入结果返回:[{}]",errorResultMap);
			return errorResultMap;
		}finally {
			session.removeAttribute(TEMP_FILE_NAME_KEY);
		    session.removeAttribute(ORIGIN_FILE_NAME_KEY);
		}
	}
	
	private Map<String, String> doImportData(File tempImportDataFile,String originFileName, String drmkdm, String crfs, List<String> selectColumns, String currentUser, String currentVersion) {
		if("v1".equals(currentVersion)) {
			Map<String, List<String>> data = DrExcelUtils.readerExcel(tempImportDataFile);
			try {
				Map<String,String> resultMap = excelImportService.importData(drmkdm,crfs,tempImportDataFile,data,selectColumns,currentUser);				
				log.info("导入结果返回:[{}]",resultMap);
				return resultMap;
				
			}catch (Exception e) {
				log.error("",e);
				Map<String,String> errorResultMap = this.getInternalError(currentVersion);
				log.info("导入结果返回:[{}]",errorResultMap);
				return errorResultMap;
			}
		}else if("v2".equals(currentVersion)) {
			
			List<ExcelRow> rows = HandlerExcelUtils.readExcelRowByRow(tempImportDataFile);
			try {
				Map<String,String> resultMap = this.excelImportServiceV2.importData(currentUser, drmkdm, crfs, selectColumns, rows);
				log.info("导入结果返回:[{}]",resultMap);
				return resultMap;
				
			}catch (Exception e) {
				log.error("",e);
				Map<String,String> errorResultMap = this.getInternalError(currentVersion);
				log.info("导入结果返回:[{}]",errorResultMap);
				return errorResultMap;
			}
		}else {
			log.error("导入版本不是v1或v2");
			Map<String,String> errorResultMap = this.getInternalError(currentVersion);
			log.info("导入结果返回:[{}]",errorResultMap);
			return errorResultMap;
		}
	}

	/**
	 * @description	： 获得导入列
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getImportColumn.zf")
	public Object getImportColumn(DrpzModel model,HttpServletRequest request) {
		try {
			List<DrlpzModel> list = excelImportService.getImportColumn(model);

		    HttpSession session = request.getSession();
		    String tempFileName = (String) session.getAttribute(TEMP_FILE_NAME_KEY);
		    String originFileName = (String) session.getAttribute(ORIGIN_FILE_NAME_KEY);

		    //避免出现状态不正确的情况
		    if(tempFileName == null || originFileName == null) {
		    	log.error("sessionId:{},获得导入列异常tempFileName == null is {} , originFileName == null is {}",session.getId(),tempFileName == null,originFileName == null);
		    }
		    File tempFile = this.tempFileService.getTempFile(Constants.MODULAR_WORK_DIR_NAME, tempFileName);

			Map<String/*表头文字*/, List<String>/*这一列的数据*/> map = DrExcelUtils.readerExcel(tempFile);
			
			CrpzModel crpzModel = this.excelImportService.getCrpz(model.getDrmkdm());
			
			this.hiddenDrlpzModelDabataseInfo(list);
			
			Object[] data = new Object[] { map.keySet(), list,crpzModel};
			return data;
		} catch (Exception ex) {
			logException(ex);
		}
		return null;
	}

	//隐藏某些关键信息
	private void hiddenDrlpzModelDabataseInfo(List<DrlpzModel> list) {
		for(DrlpzModel drlpzModel : list) {
			drlpzModel.setLsjgsh("");
			drlpzModel.setGshxx("");
		}
	}

	/**
	 * @description	： 选择导入列页面
	 * @return
	 */
	@RequestMapping("/selectColumn.zf")
	public String selectColumn() {
		return "/dr/selectColumn";
	}
	
	/**
	 * @description	： 上传excel
	 * @param uploadfile
	 * @param drmkdm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadExcel.zf", method = RequestMethod.POST)
	public Object uploadExcel(@RequestParam("importFile") MultipartFile uploadfile, String drmkdm,HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			session.removeAttribute(TEMP_FILE_NAME_KEY);
			session.removeAttribute(ORIGIN_FILE_NAME_KEY);

			String extension = FilenameUtils.getExtension(uploadfile.getOriginalFilename());
			File newTempFile =  this.tempFileService.createTempFile(Constants.MODULAR_WORK_DIR_NAME, extension);

			FileUtils.copyInputStreamToFile(uploadfile.getInputStream(), newTempFile);

			session.setAttribute(TEMP_FILE_NAME_KEY, newTempFile.getName());
			session.setAttribute(ORIGIN_FILE_NAME_KEY, uploadfile.getOriginalFilename());

			return "true";
		} catch (Exception ex) {
			logException(ex);
		}
		return MessageKey.SYSTEM_ERROR.getJson();
	}
	
	/**
	 * @description	： 获取规则验证信息
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRulers.zf")
	public Object getRulers(DrpzModel model) {
		return excelImportService.getRulers(null, model);
	}
	
	private Map<String,String> getInternalError(String currentVersion){
		
		Map<String,String> errorResultMap = new HashMap<String,String>();
		errorResultMap.put(IImportService.ResultKey, "-1");
		errorResultMap.put(IImportService.SystemErrorKey, "内部错误，请重试！");
		
		if("v1".equals(currentVersion)) {
			errorResultMap.put(IImportService.VersionKey, IImportService.Version_v1);
			return errorResultMap;
		}else if("v2".equals(currentVersion)){
			errorResultMap.put(IImportService.VersionKey, IImportService.Version_v2);
			return errorResultMap;
		}else {
			return errorResultMap;
		}
	}
	
	//-------------------for jmx-------------------------start
	@Override
	public boolean isDownloadChineseTemplateFileName() {
		return this.isDownloadChineseTemplateFileName;
	}

	@Override
	public void setDownloadChineseTemplateFileName(boolean isDownloadChineseTemplateFileName) {
		this.isDownloadChineseTemplateFileName = isDownloadChineseTemplateFileName;
	}

	@Override
	public boolean isDownloadChineseErrorFileName() {
		return this.isDownloadChineseErrorFileName ;
	}

	@Override
	public void setDownloadChineseErrorFileName(boolean isDownloadChineseErrorFileName) {
		this.isDownloadChineseErrorFileName =  isDownloadChineseErrorFileName;
	}

	@Override
	public String getVersion() {
		return this.version;
	}

	@Override
	public void setVersion(String version) {
		if(VERSIONS.contains(version)) {
			this.version = version;			
		}
	}
	
	//-------------------for jmx-------------------------end
}
