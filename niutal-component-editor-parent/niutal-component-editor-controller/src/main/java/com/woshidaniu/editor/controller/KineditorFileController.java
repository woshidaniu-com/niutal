package com.woshidaniu.editor.controller;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.utils.WebUtils;
import com.woshidaniu.editor.api.model.KindeditorConstant;
import com.woshidaniu.editor.controller.comparator.NameComparator;
import com.woshidaniu.editor.controller.comparator.SizeComparator;
import com.woshidaniu.editor.controller.comparator.TypeComparator;
import com.woshidaniu.filemgr.api.FileObject;
import com.woshidaniu.filemgr.api.FileServ;
import com.woshidaniu.filemgr.api.FileServProvider;
import com.woshidaniu.io.utils.CapacityUtils;
import com.woshidaniu.io.utils.FilenameUtils;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.util.file.DirectoryUtils;
import com.woshidaniu.web.WebContext;
import com.woshidaniu.web.utils.WebRequestUtils;

@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "/filemgr/kineditor/")
public class KineditorFileController extends BaseController implements InitializingBean {
	
	// 文件上传至目录
	protected static final String ROOT_PATH = "attached";
	// 默认图片类型
	protected String[] imageTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };
	// 默认可上传和浏览的目录
	protected String[] dirTypes = new String[] { "image", "flash", "media", "file" };
	// 默认目录可上传文件类型
	protected HashMap<String, String> extMap = new HashMap<String, String>();
	// 可上传文件大小限制；默认 10M
	protected String maxSize = "10MB";
	// 具体指定每种文件类型可上传文件大小
	protected HashMap<String, String> sizeMap = new HashMap<String, String>();
	
	protected SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
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
	@Value("#{filemgrProps['filemgr.serv']} ")
	protected String serv = FileServ.LOCAL.getName();
	@Value("#{filemgrProps['filemgr.file.preview.url']} ")
	protected String previewURL;
	@Value("#{filemgrProps['filemgr.file.download.url']} ")
	protected String downloadURL;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		//默认目录可上传文件类型
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media","swf,flv,mp3,mp4,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file","doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,pdf,"
						+ "gif,jpg,jpeg,png,bmp,swf,flv,swf,flv,mp3,mp4,wav,"
						+ "wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		
		//获取判断一个类型是图片的依据
		String imageTypesStr = MessageUtil.getText(KindeditorConstant.IMAGE_TYPES);
		if(!BlankUtils.isBlank(imageTypesStr)){
			imageTypes = 	StringUtils.tokenizeToStringArray(imageTypesStr.trim());
		}
		//获取默认全局上传文件大小限制
		String maxsizeStr = MessageUtil.getText(KindeditorConstant.MAX_SIZE);
		if(!BlankUtils.isBlank(maxsizeStr)){
			maxSize = maxsizeStr.trim();
		}
		//获取配置文件中的目录类型
		String dirTypesStr = MessageUtil.getText(KindeditorConstant.DIR_TYPES);
		if(!BlankUtils.isBlank(dirTypesStr)){
			dirTypes = 	StringUtils.tokenizeToStringArray(dirTypesStr.trim());
			for (String dirType : dirTypes) {
				//获取该目录指定可上传文件类型
				String fileTypesStr = MessageUtil.getText("kindeditor." + dirType.trim() + ".types");
				if(!BlankUtils.isBlank(fileTypesStr)){
					extMap.put( dirType.trim(), fileTypesStr.trim());
					//获取当前文件类型指定的最大上传限制
					String[] fileTypes = 	StringUtils.tokenizeToStringArray(fileTypesStr.trim());
					for (String fileType : fileTypes) {
						String fileMaxSizeStr = MessageUtil.getText("kindeditor." + fileType.trim() + ".maxsize");
						if(!BlankUtils.isBlank(fileMaxSizeStr)){
							sizeMap.put( fileType.trim(), fileMaxSizeStr.trim());
						}
					}
				}
			}
		}
		
	}
	
	protected List<Hashtable<String, Object>> toMapList(List<FileObject>  listFiles) {
		List<Hashtable<String, Object>> fileList = new ArrayList<Hashtable<String, Object>>();
		if ( !CollectionUtils.isEmpty(listFiles)) {
			
			for (FileObject info : listFiles ) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = info.getName();
				if (info.isDir()) {
					hash.put("is_dir", true);
					hash.put("has_file", true);
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
					hash.put("filename", fileName);
				} else  {
					String fileExt = FilenameUtils.getExtension(fileName).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", info.getSize());
					hash.put("is_photo", Arrays.<String> asList(imageTypes).contains(fileExt));
					hash.put("filetype", fileExt);
					//增加当前文件的地址；此处对应改造filemanager.js
					hash.put("encrypt_url", String.format("../../filemgr/file/preview.zf?uid=%s", info.getUid() ));
					hash.put("filename", fileName);
				}
				hash.put("datetime", FORMAT.format(info.getTime()));
				fileList.add(hash);
			}
			
		}
		return fileList;
	}
	
	@ResponseBody
	//@RequiresPermissions("filemgr-kineditor:fileManage")
	@RequestMapping(value = "fileManage", method = RequestMethod.POST )
	public Object fileManage(
			//上传目录
			@RequestParam("dir") String dir,
			//移动目录
			@RequestParam("path") String path,
			//排序形式，name or size or type
			@RequestParam("order") String order,
			//HTTP请求对象
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		//得到上传的目录
		dir = StringUtils.isEmpty(dir) ? "file" : dir;
		
		//检查文件目录 
		if (dir == null || !Arrays.<String> asList(dirTypes).contains(dir)) {
			result.put("error", 1);
			result.put("message", this.getMessage(KindeditorConstant.FILEMGR_KINDEDITOR_ILLEGAL_VISIT));
			return result;
		}
		
		path = StringUtils.isEmpty(path) ? "" : path;
		// 不允许使用..移动到上一级目录 ,最后一个字符不是/
		if (path.indexOf("..") >= 0 || (!StringUtils.isEmpty(path) && !path.endsWith("/"))) {
			result.put("error", 1);
			result.put("message", this.getMessage(KindeditorConstant.FILEMGR_KINDEDITOR_ILLEGAL_VISIT));
			return result;
		}
		
		//访问路径 [相对根目录/用户名/类型]
		String rootDir = ROOT_PATH + File.separator + getUser().getYhm() + File.separator + dir ;
		
		//当前访问
		String currentURL = rootDir + path;
		String currentDir = path;
		String moveupDir = "";
		
		if (!StringUtils.isEmpty(path)) {
			String str = currentDir.substring(0, currentDir.length() - 1);
			moveupDir = str.lastIndexOf( File.separator) >= 0 ? str.substring(0, str.lastIndexOf( File.separator) + 1) : "";
		}
		
		try {
			
			User user = WebContext.getUser();
			
			//当前访问目录 
			String currentPath = DirectoryUtils.getResolvePath( rootDir +  File.separator + path) ;
			// 遍历目录取的文件信息
			List<Hashtable<String, Object>> fileList = null;
			
			/**
			 * #文件存储方式：
			 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
			 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
			 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
		     */
			if(FileServ.FTP.compareTo(getServ())){
				
				//判断访问目录是否存在
				if(! getFtpProvider().existsDir( user.getYhm(), currentPath ) ){
					result.put("error", 1);
					result.put("message", this.getMessage(KindeditorConstant.FILEMGR_KINDEDITOR_UNDEFINED_VISIT));
					return result;
				}
				
				//遍历目录取的文件信息
				List<FileObject>  listFiles = getFtpProvider().listFiles(WebContext.getUser().getYhm(), currentPath);
				
				fileList = toMapList(listFiles);
				
						
			}else if(FileServ.SAMBA.compareTo(getServ())){
				
				//判断访问目录是否存在
				if(! getSmabaProvider().existsDir( user.getYhm(), currentPath ) ){
					result.put("error", 1);
					result.put("message", this.getMessage(KindeditorConstant.FILEMGR_KINDEDITOR_UNDEFINED_VISIT));
					return result;
				}
				
				//遍历目录取的文件信息
				List<FileObject>  listFiles = getSmabaProvider().listFiles( user.getYhm(), currentPath);
				
				fileList = toMapList(listFiles);
					
				
			} else {
				
				//判断访问目录是否存在
				if(! getLocalProvider().existsDir( user.getYhm(), currentPath ) ){
					result.put("error", 1);
					result.put("message", this.getMessage(KindeditorConstant.FILEMGR_KINDEDITOR_UNDEFINED_VISIT));
					return result;
				}
				
				//遍历目录取的文件信息
				List<FileObject>  listFiles = getLocalProvider().listFiles( user.getYhm(), currentPath);
				
				fileList = toMapList(listFiles);
				
			}
			
			// 排序形式，name or size or type
			order = StringUtils.isEmpty(order) ? "order" : order;
			// 根据不同的方式排序
			if ("size".equals(order)) {
				Collections.sort(fileList, new SizeComparator());
			} else if ("type".equals(order)) {
				Collections.sort(fileList, new TypeComparator());
			} else {
				Collections.sort(fileList, new NameComparator());
			}
			
			result.put("moveup_dir_path", moveupDir);
			result.put("current_dir_path", currentDir);
			result.put("current_url", currentURL);
			result.put("total_count", fileList.size());
			result.put("file_list", fileList);

		} catch (Exception e) {
			logException(e);
			// 目录访问异常
			result.put("error", 1);
			result.put("message", this.getMessage(KindeditorConstant.FILEMGR_KINDEDITOR_VISIT_EXCEPTION));
		}
		return result;
	}
	 
	/**
	 * 
	 *@描述		：Kindeditor文件上传
	 *@创建人		: kangzhidong
	 *@创建时间	: Jun 2, 20163:28:18 PM
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	@ResponseBody
	//@RequiresPermissions("filemgr-kineditor:upload")
	@RequestMapping(value = "upload", method = RequestMethod.POST )
	public Object fileUpload(
			//当前上传的文件对象
			@RequestParam("file") CommonsMultipartFile file ,
			//上传目录
			@RequestParam("dir") String dir,
			//HTTP请求对象
			HttpServletRequest request){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try{
			
			//检查文件上传请求
			if(! WebRequestUtils.isMultipartRequest(request)){
				result.put("error", 1);
				result.put("message", this.getMessage(KindeditorConstant.FILEMGR_KINDEDITOR_INVALID_REQUEST));
				return result;
			}
			
			//得到上传的目录
			dir = StringUtils.isEmpty(dir) ? "file" : dir;
			
			//检查文件目录 
			if (dir == null || !Arrays.<String> asList(dirTypes).contains(dir)) {
				result.put("error", 1);
				result.put("message", this.getMessage(KindeditorConstant.FILEMGR_KINDEDITOR_ILLEGAL_VISIT));
				return result;
			}
			
			//检查文件类型（扩展名）
			String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
			String extTypes = StringUtils.getSafeStr(extMap.get(dir)).trim();
			if(!"*".equals(extTypes) && !Arrays.<String>asList(extTypes.split(",")).contains(fileExt)){
				result.put("error", 1);
				result.put("message", this.getMessage(KindeditorConstant.FILEMGR_KINDEDITOR_INVALID_EXT, new String[]{ extMap.get(dir) }));
				return result;
			}
			
			//检查文件大小
			String fileMaxSize = sizeMap.get(fileExt) == null ? maxSize : sizeMap.get(fileExt);
			if(file.getSize() > CapacityUtils.getLongCapacity(fileMaxSize) ){
				result.put("error", 1);
				result.put("message", this.getMessage(KindeditorConstant.FILEMGR_KINDEDITOR_INVALID_SIZE, new String[]{ fileMaxSize }));
				return result;
			}
			
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
			
			//文件的访问路径
			String contextPath = WebUtils.getContextPath(request);
			String url = String.format(contextPath+"/filemgr/file/download.zf?uid=%s", info.getUid());
			result.put("url", url);
			result.put("fileName", file.getOriginalFilename());
			result.put("error", 0);
			
		} catch (Exception e) {
			logException(e);
			result.put("error", 1);
			result.put("message", this.getMessage(KindeditorConstant.FILEMGR_KINDEDITOR_UPLOAD_EXCEPTION));
		}
		return result;
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
		return serv.trim();
	}

	public void setServ(String serv) {
		this.serv = serv;
	}
	
	
}