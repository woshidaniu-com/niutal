package com.woshidaniu.editor.action;

import java.io.File;
import java.util.HashMap;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.editor.api.model.KindeditorConstant;
import com.woshidaniu.editor.api.model.KindeditorModel;
import com.woshidaniu.ftpclient.client.IFTPClient;
import com.woshidaniu.smbclient.client.ISMBClient;
import com.woshidaniu.util.base.MessageUtil;

/**
 * 
 *@类名称		：KindeditorBaseAction.java
 *@类描述		：Kindeditor富文本编辑组件基础Action
 *@创建人		：kangzhidong
 *@创建时间	：Jun 2, 2016 11:35:25 AM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@SuppressWarnings("serial")
public abstract class KindeditorBaseAction<T extends KindeditorModel> extends BaseAction implements ModelDriven<T>{

	/**
	 * 文件共享客户端
	 */
	@Resource
	private ISMBClient smbClient;
	/**
	 * FTP文件服务客户端
	 */
	@Resource
	private IFTPClient ftpClient;
	
	protected File imgFile;
	protected String imgFileFileName;
	protected String imgFileContentType;

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
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
		
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

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public String getImgFileContentType() {
		return imgFileContentType;
	}

	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}

	/**
	 * 通过编辑器浏览访问抛出的错误
	 * @param message
	 * @return
	 */
	protected String getManageEorror(String message) {
		ValueStack vs = getValueStack();
		vs.set(DATA, message);
		return DATA;
	}

	/**
	 * 文件上传抛出的错误
	 * 
	 * @param message
	 * @return
	 */
	protected String getUploadError(String message) {
		ValueStack vs = getValueStack();
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		vs.set(DATA, obj);
		return DATA;
	}

	public ISMBClient getSmbClient() {
		return smbClient;
	}

	public void setSmbClient(ISMBClient smbClient) {
		this.smbClient = smbClient;
	}

	public IFTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(IFTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

}
