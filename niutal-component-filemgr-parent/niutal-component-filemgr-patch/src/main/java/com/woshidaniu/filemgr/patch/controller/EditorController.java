package com.woshidaniu.filemgr.patch.controller;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.filemgr.patch.service.FileService;

import net.sf.json.JSONObject;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：编辑器-文件上传
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年10月27日下午6:36:17
 */
@Controller
@RequestMapping("/editor")
@Deprecated
public class EditorController extends BaseController {

	
	@Autowired
	@Qualifier("localFileService")
	private FileService fileService;
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
	

	
	//文件上传
	private JSONObject getUploadInfo(CommonsMultipartFile file , HttpServletRequest request) throws Exception{
		FileItem item = file.getFileItem();
		String fileName = item.getName();
		String dir = StringUtils.isNull(request.getParameter("dir")) ? "file" : request.getParameter("dir");
		//检查文件目录 
		if(!extMap.containsKey(dir)){
			throw new Exception("目录名不被允许。");
		}
		//检查文件类型（扩展名）
		String fileExt = (fileName).substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!Arrays.<String>asList(extMap.get(dir).split(",")).contains(fileExt)){
			throw new Exception("上传文件类型是不被允许的。\n只允许" + extMap.get(dir) + "格式。");
		}
		
		JSONObject obj = new JSONObject();
		
		File newFile=new File(fileName);
		newFile.createNewFile();
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newFile);
        
        String info = fileService.upload(newFile, "attached"+File.separator+dir);
		obj.put("url", String.format("%s/editor/download.zf?info=%s&name=%s", request.getContextPath(),info,fileName));
		obj.put("fileName", fileName);
		return obj;
	}
	
	
	
	/**
	 * 
	 * <p>方法说明：编辑器文件上传<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年10月28日上午9:01:48<p>
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fileUpload")
	public Object fileUpload(@RequestParam("file") CommonsMultipartFile file , HttpServletRequest request){
		try{
			JSONObject obj =  getUploadInfo(file, request);
			obj.put("error", 0);
			return obj;
		}catch (Exception e) {
			JSONObject obj =  new JSONObject();
			logException(e);
			obj.put("error", 1);
			obj.put("message", "上传文件失败,"+e.getMessage());
			return obj;
		}
	}
	
	/**
	 * 
	 * <p>方法说明：编辑器文件下载<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年10月28日下午1:42:13<p>
	 * @param info
	 * @return
	 */
	@RequestMapping("/download")
	public ResponseEntity<byte[]> dowloadTemplate(@RequestParam("info")String info,@RequestParam("name")String name) {
		try{
			File file = fileService.download(info);
			// Http响应头
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setContentDispositionFormData("attachment", name);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);
		}catch(Exception e){
			logException(e);
		}
		return null;
	}
}
