package com.woshidaniu.editor.action;
import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPFile;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.basicutils.TimeUtils;
import com.woshidaniu.common.constant.BaseConstant;
import com.woshidaniu.editor.action.comparator.NameComparator;
import com.woshidaniu.editor.action.comparator.SizeComparator;
import com.woshidaniu.editor.action.comparator.TypeComparator;
import com.woshidaniu.editor.api.model.KindeditorConstant;
import com.woshidaniu.editor.api.model.KindeditorModel;
import com.woshidaniu.ftpclient.utils.FTPClientUtils;
import com.woshidaniu.io.utils.CapacityUtils;
import com.woshidaniu.io.utils.FilenameUtils;
import com.woshidaniu.security.algorithm.RSACodec;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.util.file.DirectoryUtils;
import com.woshidaniu.util.request.WebRequestUtils;

import jcifs.smb.SmbFile;

/**
 * 
 *@类名称		：KindeditorFileAction.java
 *@类描述		：KindEditor 编辑器文件管理 增加了
 *			1.支持用户指定目录访问；
 *			2.支持加密路径文件访问
 *@创建人		：kangzhidong
 *@创建时间	：Jun 2, 2016 11:12:43 AM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@SuppressWarnings("serial")
public class KindeditorFileAction extends KindeditorBaseAction<KindeditorModel> {

	protected KindeditorModel model = new KindeditorModel();

	protected SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 
	 *@描述		：服务器上文件浏览与选择
	 *@创建人		: kangzhidong
	 *@创建时间	: Jun 2, 20162:09:01 PM
	 *@return
	 *@throws Exception
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	@SuppressWarnings("unchecked")
	public String fileManage() throws Exception{
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		//得到上传的目录
		String dir = model.getDir() == null ? "file" : model.getDir();
		
		//检查文件目录 
		if (dir == null || !Arrays.<String> asList(dirTypes).contains(dir)) {
			result.put("error", 1);
			
			result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_ILLEGAL_VISIT));
			getValueStack().set(DATA, result );
			return DATA;
		}
		
		String path = model.getPath() == null ? "" : model.getPath();
		// 不允许使用..移动到上一级目录 ,最后一个字符不是/
		if (path.indexOf("src/main") >= 0 || (!StringUtils.isEmpty(path) && !path.endsWith("/"))) {
			result.put("error", 1);
			result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_ILLEGAL_VISIT));
			getValueStack().set(DATA, result );
			return DATA;
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
			
			//当前访问目录 
			String currentPath = DirectoryUtils.getResolvePath( rootDir +  File.separator + path) ;
			// 遍历目录取的文件信息
			List<Hashtable<String, Object>> fileList = new ArrayList<Hashtable<String, Object>>();
			//用于加密文件路径的RSA公钥
			String public_key = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.RSA_PUBLIC_KEY), RSACodec.public_key);
			/**
			 * #文件存储方式：
			 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
			 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
			 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
			 */
			if("1".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
				
				try {
					
					//判断访问目录是否存在
					if(!FTPClientUtils.hasDirectory(getFtpClient().getFTPClient(), currentPath ) ){
						result.put("error", 1);
						result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_UNDEFINED_VISIT));
						getValueStack().set(DATA, result );
						return DATA;
					}
					
					//遍历目录取的文件信息
					List<FTPFile>  listFiles = getFtpClient().listFiles(currentPath);
					
					if ( listFiles != null) {
						for (FTPFile file : listFiles ) {
							Hashtable<String, Object> hash = new Hashtable<String, Object>();
							String fileName = file.getName();
							if (file.isDirectory()) {
								hash.put("is_dir", true);
								//尝试列举当前目录下的文件信息
								String[] listNames = getFtpClient().listNames(currentPath + File.separator +  fileName);
								hash.put("has_file", (listNames != null && listNames.length > 0));
								hash.put("filesize", 0L);
								hash.put("is_photo", false);
								hash.put("filetype", "");
							} else if (file.isFile()) {
								String fileExt = FilenameUtils.getExtension(fileName).toLowerCase();
								hash.put("is_dir", false);
								hash.put("has_file", false);
								hash.put("filesize", file.getSize());
								hash.put("is_photo", Arrays.<String> asList(imageTypes).contains(fileExt));
								hash.put("filetype", fileExt);
								//增加当前文件的地址；此处对应改造filemanager.js
								//加密文件路径
								String filePath  = RSACodec.getInstance().encodeByPublicKey(currentPath + File.separator +  fileName, public_key);
								//使用FTP服务器作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
								String ftpPrefix = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.FTP_PREFIX), "../ftp/");
								//FTP文件服务器模式的访问路径
								hash.put("encrypt_url", ftpPrefix + filePath);
							}
							hash.put("filename", fileName);
							hash.put("datetime", FORMAT.format(file.getTimestamp().getTimeInMillis()));
							fileList.add(hash);
						}
					}
					
				} catch (Exception e) {
					logStackException(e);
					// 目录访问异常
					result.put("error", 1);
					result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_VISIT_EXCEPTION));
				}
				
			}else if("2".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
				
				try {
					
					//当前访问目录
					SmbFile targetDir = getSmbClient().getFile(currentPath);
					//判断访问目录是否存在
					if(!targetDir.exists() || !targetDir.isDirectory()){
						result.put("error", 1);
						result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_UNDEFINED_VISIT));
						getValueStack().set(DATA, result );
						return DATA;
					}
					
					//遍历目录取的文件信息
					SmbFile[]  listFiles = getSmbClient().listFiles(currentPath);
					
					if ( listFiles != null) {
						for (SmbFile file : listFiles ) {
							Hashtable<String, Object> hash = new Hashtable<String, Object>();
							String fileName = file.getName();
							if (file.isDirectory()) {
								hash.put("is_dir", true);
								hash.put("has_file", (file.listFiles() != null));
								hash.put("filesize", 0L);
								hash.put("is_photo", false);
								hash.put("filetype", "");
								hash.put("filename", fileName.endsWith("/") ? fileName.subSequence(0, fileName.length() - 1) : (
										fileName.endsWith("\\") ? fileName.substring(0, fileName.length() - 1) : fileName
										));
							} else if (file.isFile()) {
								String fileExt = FilenameUtils.getExtension(fileName).toLowerCase();
								hash.put("is_dir", false);
								hash.put("has_file", false);
								hash.put("filesize", file.length());
								hash.put("is_photo", Arrays.<String> asList(imageTypes).contains(fileExt));
								hash.put("filetype", fileExt);
								//增加当前文件的地址；此处对应改造filemanager.js
								//加密文件路径
								String filePath  = RSACodec.getInstance().encodeByPublicKey(currentPath + File.separator + fileName ,public_key);
								//使用文件共享服务器作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
								String smbPrefix = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.SMB_PREFIX), "../smb/");
								//共享文件模式的访问路径
								hash.put("encrypt_url", smbPrefix + filePath);
								hash.put("filename", fileName);
							}
							hash.put("datetime", FORMAT.format(file.lastModified()));
							fileList.add(hash);
						}
					}
					
				} catch (Exception e) {
					// 目录访问异常
					result.put("error", 1);
					result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_VISIT_EXCEPTION));
				}
				
			}else{
 
				//当前访问目录
				File targetDir =  DirectoryUtils.getTargetDir( currentPath );
				//判断访问目录是否存在
				if (!targetDir.exists() || !targetDir.isDirectory()) {
					result.put("error", 1);
					result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_UNDEFINED_VISIT));
					getValueStack().set(DATA, result );
					return DATA;
				}
				
				//遍历目录取的文件信息
				File[] listFiles = targetDir.listFiles();
				if ( listFiles != null) {
					for (File file : listFiles ) {
						Hashtable<String, Object> hash = new Hashtable<String, Object>();
						String fileName = file.getName();
						if (file.isDirectory()) {
							hash.put("is_dir", true);
							hash.put("has_file", (file.listFiles() != null));
							hash.put("filesize", 0L);
							hash.put("is_photo", false);
							hash.put("filetype", "");
						} else if (file.isFile()) {
							String fileExt = FilenameUtils.getExtension(fileName).toLowerCase();
							hash.put("is_dir", false);
							hash.put("has_file", false);
							hash.put("filesize", file.length());
							hash.put("is_photo", Arrays.<String> asList(imageTypes).contains(fileExt));
							hash.put("filetype", fileExt);
							
							//增加当前文件的地址；此处对应改造filemanager.js
							//加密文件路径
							String filePath  = RSACodec.getInstance().encodeByPublicKey( targetDir.getAbsolutePath() +File.separator + fileName,public_key);
							
							//使用应用服务器本身作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
							String innerPrefix = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.SERVLET_PREFIX), "../servlet/streamServlet?filePath=");
							//应用内部文件目录模式
							hash.put("encrypt_url", innerPrefix + filePath);
							
						}
						hash.put("filename", fileName);
						hash.put("datetime", FORMAT.format(file.lastModified()));
						fileList.add(hash);
					}
				}
				
			}
			
			// 排序形式，name or size or type
			String order = model.getOrder() == null ? "order" : model.getOrder().toLowerCase();
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

			getValueStack().set(DATA, result);
		} catch (Exception e) {
			logStackException(e);
			// 目录访问异常
			result.put("error", 1);
			result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_VISIT_EXCEPTION));
		}
		return DATA;
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
	public String fileUpload(){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			//检查文件上传请求
			if(! WebRequestUtils.isMultipartRequest(request)){
				
				result.put("error", 1);
				result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_INVALID_REQUEST));
				getValueStack().set(DATA, result );
				
				return DATA;
			}
			
			//得到上传的目录
			String dir = model.getDir() == null ? "file" : model.getDir();
			
			//检查文件目录 
			if (dir == null || !Arrays.<String> asList(dirTypes).contains(dir)) {
				result.put("error", 1);
				result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_ILLEGAL_VISIT));
				getValueStack().set(DATA, result );
				return DATA;
			}
			
			if(!BlankUtils.isBlank(fileName)){
				result.put("fileName", fileName);
				fileName = fileName.replaceAll(" ", "");
			}
			//检查文件类型（扩展名）
			String fileExt = FilenameUtils.getExtension(fileName);
			String extTypes = StringUtils.getSafeStr(extMap.get(dir)).trim();
			if(!"*".equals(extTypes) && !Arrays.<String>asList(extTypes.split(",")).contains(fileExt)){
				result.put("error", 1);
				result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_INVALID_EXT,new String[]{ extMap.get(dir) }));
				getValueStack().set(DATA, result );
				return DATA;
			}
			
			//检查文件大小
			String fileMaxSize = sizeMap.get(fileExt) == null ? maxSize : sizeMap.get(fileExt);
			if(file.length() > CapacityUtils.getLongCapacity(fileMaxSize) ){
				result.put("error", 1);
				result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_INVALID_SIZE,new String[]{ fileMaxSize }));
				getValueStack().set(DATA, result );
				return DATA;
			}
			
			
			result.put("error", 0);
			
			
			//唯一ID
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			//重命名新的文件名
			String newFileName = uuid + FilenameUtils.getFullExtension(fileName);
			
			//用于加密文件路径的RSA公钥
			String public_key = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.RSA_PUBLIC_KEY), RSACodec.public_key);
					
			/**
			 * #文件存储方式：
			 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
			 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
			 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
			 */
			if("1".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
				
				try {
					
					//上传文件的根路径 [指定根目录/用户名/相对根目录/日期]
					String rootDir = ROOT_PATH + File.separator + getUser().getYhm() + File.separator + dir + File.separator + TimeUtils.getDay() ;
					//上传文件至FTP服务器
					getFtpClient().upload(file, rootDir , newFileName);
					//加密文件路径
					String filePath  = RSACodec.getInstance().encodeByPublicKey(rootDir + File.separator + newFileName , public_key );
					//使用FTP服务器作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
					String ftpPrefix = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.FTP_PREFIX), "../ftp/");
					//FTP文件服务器模式的访问路径
					result.put("url", ftpPrefix + filePath + KindeditorConstant.FILENAME_PREFIX + URLEncoder.encode("file"+System.currentTimeMillis()+ "" +fileExt, "UTF-8") );
					
				} catch (Exception e) {
					logStackException(e);
					result.put("error", 1);
					result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_UPLOAD_EXCEPTION));
				}
				
			}else if("2".equalsIgnoreCase(BaseConstant.UPLOAD_TO)){
				
				try {
					
					//上传文件的根路径 [指定根目录/用户名/相对根目录/日期]
					String rootDir = ROOT_PATH + File.separator + getUser().getYhm() + File.separator + dir + File.separator + TimeUtils.getDay() ;
					//上传文件至文件共享服务器
					//getSmbClient().upload(localFile ,"attached\\admin\\image\\2016-06-01","tst.mp4");
					getSmbClient().upload(file, rootDir, newFileName );
					//加密文件路径
					String filePath  = RSACodec.getInstance().encodeByPublicKey(rootDir + File.separator + newFileName , public_key );
					//使用文件共享服务器作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
					String smbPrefix = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.SMB_PREFIX), "../smb/");
					//共享文件模式的访问路径
					result.put("url", smbPrefix + filePath + KindeditorConstant.FILENAME_PREFIX + URLEncoder.encode("file"+System.currentTimeMillis()+ "" +fileExt, "UTF-8") );
					
				} catch (Exception e) {
					logStackException(e);
					result.put("error", 1);
					result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_UPLOAD_EXCEPTION));
				}
				
			}else{
				
				//上传文件的绝对路径 [指定根目录/相对根目录/用户名/类型/日期]
				String rootDir = DirectoryUtils.getTargetDir( ROOT_PATH, getUser().getYhm(), (dir + File.separator + TimeUtils.getDay()+File.separator)).getAbsolutePath();
				
				//检查目录
				File uploadDir = new File(rootDir);
				if(!uploadDir.exists()){
					uploadDir.setReadable(true);
					uploadDir.setWritable(true);
					uploadDir.mkdirs();
				}
				if(!uploadDir.isDirectory()){
					result.put("error", 1);
					result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_UNDEFINED_DIR));
					getValueStack().set(DATA, result );
					return DATA;
				}
				
				//检查目录写权限
				if(!uploadDir.canWrite()){
					result.put("error", 1);
					result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_NO_WRITE));
					getValueStack().set(DATA, result );
					return DATA;
				}
				
				try {
					
					//上传文件
					File newFile = new File(uploadDir, newFileName);
					FileUtils.copyFile(file, newFile);
					//加密文件路径
					String filePath  = RSACodec.getInstance().encodeByPublicKey(rootDir + File.separator + newFileName , public_key);
					//使用应用服务器本身作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
					String innerPrefix = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.SERVLET_PREFIX), "../servlet/streamServlet?filePath=");
					//应用内部文件目录模式
					result.put("url", innerPrefix + filePath + KindeditorConstant.FILENAME_PREFIX + URLEncoder.encode("file"+System.currentTimeMillis()+ "" +fileExt, "UTF-8") );
					
				} catch (Exception e) {
					logStackException(e);
					result.put("error", 1);
					result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_UPLOAD_EXCEPTION));
				}
				
			}
			
		}catch (Exception e) {
			
			logStackException(e);
			result.put("error", 1);
			
			result.put("message", getText(KindeditorConstant.FILEMGR_KINDEDITOR_UPLOAD_EXCEPTION));
			
		}
		getValueStack().set(DATA, result );
		return DATA;
	}
	
	/**
	 * 编辑器插件HTML文件跳转
	 * @return
	 */
	public String plugins(){
		return SUCCESS;
	}
	
	@Override
	public KindeditorModel getModel() {
		return model;
	}
}

