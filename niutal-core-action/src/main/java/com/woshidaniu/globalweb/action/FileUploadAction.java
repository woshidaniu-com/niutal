package com.woshidaniu.globalweb.action;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.service.svcinterface.IFileUploadService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 通用文件上传
 * @author Penghui.Qu
 *
 */
@Controller
@Scope("prototype")
public class FileUploadAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	protected String dir;//目录
	private String fileName;
	private String fileContentType;
	private String limitSize; //单位kb
	@Autowired
	private IFileUploadService fileUploadService;
	//文件上传至目录 
	protected static final String ROOT_PATH = "/attached/";
	//图片类型
	protected String[] imageTypes = new String[]{"gif","jpg","jpeg","png","bmp"};
	//可上传和浏览的目录 
	protected String[] dirType = new String[] { "image", "flash", "media", "file" };
	
	private static HashMap<String, String> extMap = new HashMap<String, String>();

	static {
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,mp4,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,pdf," +
				"gif,jpg,jpeg,png,bmp,swf,flv,swf,flv,mp3,mp4,wav," +
				"wma,wmv,mid,avi,mpg,asf,rm,rmvb");
	}
	
	
	public void setFileUploadService(IFileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileName;
	}

	public void setFileFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	
	/**
	 * 获取文件上传后URL、FileName
	 * @return
	 * @throws Exception
	 */
	protected JSONObject getUploadInfo() throws Exception{
		
		dir = dir == null ? "file" : dir;
		//检查文件目录 
		if(!extMap.containsKey(dir)){
			throw new Exception("目录名不被允许。");
		}
		
		//检查文件类型（扩展名）
		String fileExt = (fileName).substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!Arrays.<String>asList(extMap.get(dir).split(",")).contains(fileExt)){
			throw new Exception("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dir) + "格式。");
		}
		
		//程序目录
		String appPath = ServletActionContext.getServletContext().getContextPath();
		//上传目录 
		String uploadPath = ROOT_PATH+"/"+dir+"/"+DateUtils.format(DateUtils.DATE_FORMAT_FOUR)+"/";
		//上传文件的绝对路径
		String rootPath = ServletActionContext.getServletContext().getRealPath(uploadPath);
		JSONObject obj = new JSONObject();
		if(!StringUtils.isNull(limitSize)){
			long limiSize=Long.parseLong(limitSize);
			long s=file.length();
			if(s>limiSize*1024){
				obj.put("errorMsg", "上传文件超过限制大小");
			}
		}
		File newFile = fileUploadService.uploadFile(rootPath, file, fileName, fileContentType);
		obj.put("url", appPath+uploadPath+newFile.getName());
		obj.put("fileName", fileName);
		obj.put("path", newFile.getPath());
		
		return obj;
	}
	
	
	
	/**
	 * ajax 文件上传
	 * @return
	 */
	public String fileUpload(){
		
		try{
			JSONObject obj = getUploadInfo();
			obj.put("error", 0);
			
			ValueStack vs = getValueStack();
			vs.set(DATA, obj);
//			return DATA;
			return "fileUpload";
		}catch (Exception e) {
			logException(e);
			return getUploadError("上传文件失败,"+e.getMessage());
		}
	}
	
	/**
	 * 文件上传抛出的错误
	 * @param message
	 * @return
	 */
	private String getUploadError(String message){
		ValueStack vs = getValueStack();
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		vs.set(DATA, obj);
		return DATA;
	}

	public void setLimitSize(String limitSize) {
		this.limitSize = limitSize;
	}

	public String getLimitSize() {
		return limitSize;
	}
}
