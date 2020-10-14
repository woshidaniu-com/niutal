package com.woshidaniu.filemgr.controller;


import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.filemgr.api.FileObject;
import com.woshidaniu.filemgr.api.FileServ;
import com.woshidaniu.filemgr.api.FileServConstant;
import com.woshidaniu.filemgr.api.FileServProvider;
import com.woshidaniu.filemgr.api.exception.FilemgrException;
import com.woshidaniu.filemgr.service.svcinterface.FileInfoService;
import com.woshidaniu.io.utils.FilemimeUtils;
import com.woshidaniu.io.utils.IOUtils;
import com.woshidaniu.util.ResultUtils;
import com.woshidaniu.web.utils.UserAgentUtils;

import eu.bitwalker.useragentutils.Browser;

@Controller
@RequestMapping(value = "/filemgr/file/")
public class FileMgrController extends BaseController {
	
	@Resource(name = "messageSource")
	protected MessageSource messageSource;
	
	@Resource(name = "viewResolver")
	protected UrlBasedViewResolver viewResolver;
	
	/**
	 * 文件服务提供者实现
	 */
	@Autowired
	@Qualifier("localFileServProvider")
	protected FileServProvider localProvider;
	@Autowired(required = false)
	@Qualifier("sambaFileServProvider")
	protected FileServProvider smabaProvider;
	@Autowired(required = false)
	@Qualifier("ftpFileServProvider")
	protected FileServProvider ftpProvider;
	//服务类型
	@Value("#{filemgrProps['filemgr.serv']}")
	protected String serv = FileServ.LOCAL.getName();
	/**
	 * 文件信息服务接口
	 */
	@Resource
	protected FileInfoService fileInfoService;
		
	@RequiresPermissions("filemgr-file:cx")
	@RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
        
        return "/filemgr/index";
    }
	
	@ResponseBody
	@RequiresPermissions("filemgr-file:cx")
	@RequestMapping(value = "list", method = RequestMethod.POST )
	public Object listFiles(@RequestParam String parent) throws Exception {
		try {
			if(FileServ.FTP.compareTo(getServ())){
				return getFtpProvider().listFiles(getUser().getYhm(), parent);
			} else if(FileServ.SAMBA.compareTo(getServ())){
				return getSmabaProvider().listFiles(getUser().getYhm(), parent);
			} else{
				return getLocalProvider().listFiles(getUser().getYhm(), parent);
			}
		} catch (Exception e) {
			logException(this, e);
		}
		return null;
	}
	
	@ResponseBody
	@RequiresPermissions("filemgr-file:cx")
	@RequestMapping(value = "info", method = RequestMethod.POST )
	public Object info(@RequestParam String uid) throws Exception {
		try {
			if(FileServ.FTP.compareTo(getServ())){
				return getFtpProvider().info(uid);
			} else if(FileServ.SAMBA.compareTo(getServ())){
				return getSmabaProvider().info(uid);
			} else{
				return getLocalProvider().info(uid);
			}
		} catch (Exception e) {
			logException(this, e);
		}
		return null;
	}
	
	@ResponseBody
	@RequiresPermissions("filemgr-file:upload")
	@RequestMapping(value = "upload", method = RequestMethod.POST )
	public Map<String, Object> upload(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request) throws Exception {
		
		/**
		 * #文件存储方式：
		 *	#FileServ.FTP：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
		 *	#FileServ.SAMBA：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
		 *	#FileServ.LOCAL：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
		 */
		FileObject info = null;
		if(FileServ.FTP.compareTo(getServ())){
			//上传文件至FTP服务器
			info = getFtpProvider().output(file.getBytes(), file.getOriginalFilename());
		}else if(FileServ.SAMBA.compareTo(getServ())){
			//上传文件至文件共享服务器
			info = getSmabaProvider().output(file.getBytes(), file.getOriginalFilename());
		}else{
			//上传文件至本地环境
			info = getLocalProvider().output(file.getBytes(), file.getOriginalFilename());
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		//文件的访问路径
		result.put("url", String.format("/filemgr/file/download.zf?uid=%s", info.getUid() ));
		result.put("fileName", file.getOriginalFilename());
		
		return result;
	}
	
	//@RequiresPermissions("filemgr-file:preview")
	@RequestMapping(value = "preview", method = RequestMethod.GET )
	public ResponseEntity<byte[]> previewFile(@RequestParam("uid") String uid, HttpServletRequest request, HttpServletResponse response ) throws Exception {
		
		if( StringUtils.isEmpty(uid) ){
			throw new FilemgrException(this.getMessage(FileServConstant.FILE_UID_REQUIRED));
		}
		
		try {
			
			/**
			 * #文件存储方式：
			 *	#FileServ.FTP：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
			 *	#FileServ.SAMBA：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
			 *	#FileServ.LOCAL：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
			 */
			File file = null;
			// 新接口
			if(StringUtils.isNotEmpty(uid)){
				if(FileServ.FTP.compareTo(getServ())){
					//从FTP服务器下载文件
					file = getFtpProvider().file(uid);
				}else if(FileServ.SAMBA.compareTo(getServ())){
					//从文件共享服务器下载文件
					file = getSmabaProvider().file(uid);
				}else{
					//本地环境下载文件
					file = getLocalProvider().file(uid);
				}
			}
			
			//浙江大学系统配置环境问题导致火狐浏览器直接输出实体对象无法显示，因此做判断是否为火狐浏览器，进入不同输出方式
			if(UserAgentUtils.getBrowser(request).compareTo(Browser.FIREFOX) == 0){
				OutputStream output = null;
				try {
					output = response.getOutputStream();
					FileUtils.copyFile(file, output);
				} finally {
					IOUtils.closeOutput(output);
				}
			}else{
				// Http响应头
				HttpHeaders headers = new HttpHeaders();
				String mediaType = FilemimeUtils.getFileMimeType(file);
				headers.setContentType(MediaType.parseMediaType(mediaType));
				return new ResponseEntity<byte[]>( FileUtils.readFileToByteArray(file) , headers, HttpStatus.OK);
			}
		}catch(Exception e){
			logException(this, e);
			throw new FilemgrException(e.getMessage());
		}
		return null;
	}
	
	@ResponseBody
	//@RequiresPermissions("filemgr-file:download")
	@RequestMapping(value = "download", method = RequestMethod.GET )
	public ResponseEntity<byte[]> download(@RequestParam("uid") String uid,HttpServletRequest request) throws Exception {
		 
		if( StringUtils.isEmpty(uid) ){
			throw new FilemgrException(this.getMessage(FileServConstant.FILE_UID_REQUIRED));
		}
		
		try{
			/**
			 * #文件存储方式：
			 *	#FileServ.FTP：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
			 *	#FileServ.SAMBA：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
			 *	#FileServ.LOCAL：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
			 */
			File file = null;
			// 新接口
			if(StringUtils.isNotEmpty(uid)){
				if(FileServ.FTP.compareTo(getServ())){
					//从FTP服务器下载文件
					file = getFtpProvider().file(uid);
				}else if(FileServ.SAMBA.compareTo(getServ())){
					//从文件共享服务器下载文件
					file = getSmabaProvider().file(uid);
				}else{
					//本地环境下载文件
					file = getLocalProvider().file(uid);
				}
				// Http响应头
				HttpHeaders headers = new HttpHeaders();
				String mediaType = FilemimeUtils.getFileMimeType(file);
				headers.setContentType(MediaType.parseMediaType(mediaType));
				headers.setContentDispositionFormData("attachment", file.getName());
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
			}
		}catch(Exception e){
			logException(this, e);
		}
		return null;
		
	}
	
	
	@ResponseBody
	@RequiresPermissions("filemgr-file:del")
	@RequestMapping(value = "del", method = RequestMethod.POST )
	public Map<String, Object> del(@RequestParam("uid") String uid,HttpServletRequest request) throws Exception {
		try{
			if(StringUtils.isEmpty(uid)){
				return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, getMessage(FileServConstant.FILE_UID_REQUIRED));
			}
			boolean done = false;
			if(FileServ.FTP.compareTo(getServ())){
				//从FTP服务器下载文件
				done = getFtpProvider().delete(uid);
			}else if(FileServ.SAMBA.compareTo(getServ())){
				//从文件共享服务器下载文件
				done = getSmabaProvider().delete(uid);
			}else{
				//本地环境下载文件
				done = getLocalProvider().delete(uid);
			}
			
			if(done){
				return ResultUtils.statusMap(MESSAGE_STATUS_SUCCESS, getMessage(FileServConstant.FILE_DEL_SUCCESS));
			}
			return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, getMessage(FileServConstant.FILE_DEL_FAILED));
		} catch(Exception e){
			logException(this, e);
			return ResultUtils.statusMap(MESSAGE_STATUS_ERROR, getMessage(FileServConstant.FILE_DEL_ERROR));
		}
	}
	
	@ResponseBody
	@RequiresPermissions("filemgr-file:update")
	@RequestMapping(value = "update", method = RequestMethod.POST )
	public Map<String, Object> update(@RequestParam("uid") String uid,@RequestParam("name") String filename,HttpServletRequest request) throws Exception {
		
		try{
			if(StringUtils.isEmpty(uid)){
				return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, getMessage(FileServConstant.FILE_UID_REQUIRED));
			}
			if(StringUtils.isEmpty(filename)){
				return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, getMessage(FileServConstant.FILE_NAME_REQUIRED));
			}
			//修改文件名称
			boolean done = getFileInfoService().updateFileInfo(uid, filename);
			if(done){
				return ResultUtils.statusMap(MESSAGE_STATUS_SUCCESS, getMessage(FileServConstant.FILE_UPDATE_SUCCESS));
			}
			return ResultUtils.statusMap(MESSAGE_STATUS_FAIL, getMessage(FileServConstant.FILE_UPDATE_FAILED));
		}catch(Exception e){
			logException(this, e);
			return ResultUtils.statusMap(MESSAGE_STATUS_ERROR, getMessage(FileServConstant.FILE_UPDATE_ERROR));
		}
	}

	public FileServProvider getLocalProvider() {
		return localProvider;
	}

	public void setLocalProvider(FileServProvider localProvider) {
		this.localProvider = localProvider;
	}

	public FileServProvider getSmabaProvider() {
		return smabaProvider;
	}

	public void setSmabaProvider(FileServProvider smabaProvider) {
		this.smabaProvider = smabaProvider;
	}

	public FileServProvider getFtpProvider() {
		return ftpProvider;
	}

	public void setFtpProvider(FileServProvider ftpProvider) {
		this.ftpProvider = ftpProvider;
	}

	public String getServ() {
		return serv;
	}

	public void setServ(String serv) {
		this.serv = serv;
	}

	public FileInfoService getFileInfoService() {
		return fileInfoService;
	}

	public void setFileInfoService(FileInfoService fileInfoService) {
		this.fileInfoService = fileInfoService;
	}
	
	
}