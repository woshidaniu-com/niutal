/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.editor.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woshidaniu.editor.editorPlugin.wordUpload.HtmlProcessor;
import com.woshidaniu.editor.editorPlugin.wordUpload.WordToHtmlTransform;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.filemgr.api.FileServ;
import com.woshidaniu.filemgr.api.FileServProvider;

/**
 * @description	： kineditor插件后端，wordupload插件，此插件上传word文档给后端，返回html片段给前端，前端直接填充到可编辑区域内部
 * @author 		：康康（1571）
 * @date		： 2018年10月18日 下午1:57:25
 * @version 	V1.0
 */
@Controller
@RequestMapping(value = "/filemgr/kineditorWordUpload")
public class KineditorPluginWordUploadController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(KineditorPluginWordUploadController.class);
	
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

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public void fileUpload(
			// 当前上传的文件对象
			@RequestParam("file") CommonsMultipartFile file,//
			HttpServletRequest request,HttpServletResponse response//
			) {
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		//文件类型判断
		String originalFilename = file.getOriginalFilename();
		String extension = FilenameUtils.getExtension(originalFilename);
		if(!extension.equals("doc")) {
			this.renderPage(response, "上传文件格式不正确，请上传doc!!!");
			return;
		}
		//暂存word文件
		FileUtils.getTempDirectory();
		String tempFileName = UUID.randomUUID().toString() + "." + extension;
		File tempFile = FileUtils.getFile(FileUtils.getTempDirectory(), tempFileName);
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
			log.debug("临时存储上传的word文件:{}",tempFile);
		}catch (Exception e) {
			String msg = "暂存存储上传的word文件("+ originalFilename +")异常";
			log.error(msg,e);
			this.renderPage(response, msg);
			return;
		}
		
		FileServProvider fileServProvider = this.getFinallyServProvider();
		// 文件的访问路径
		String imageDownloadUrl = "../../filemgr/file/download.zf?uid=";
		//转换成html字符串
		String html = null;
		try {
			WordToHtmlTransform transform = new WordToHtmlTransform(fileServProvider,imageDownloadUrl);
			html = transform.transform(tempFile);
		}catch (Exception e) {
			String msg = "转换word文件("+ originalFilename +")到html异常";
			log.error(msg,tempFile,e);
			this.renderPage(response, msg);
			return;
		}
		
		HtmlProcessor htmlProcessor = new HtmlProcessor(html);
		String result = htmlProcessor.process();
		this.renderPage(response,result);
	}

	private FileServProvider getFinallyServProvider() {
		if(FileServ.FTP.compareTo(serv)){
			return ftpProvider;
		}else if(FileServ.SAMBA.compareTo(serv)){
			return smabaProvider;
		}else{
			return localProvider;
		}
	}

	private void renderPage(HttpServletResponse response, String content) {
		try {
			response.getWriter().write(content);
		} catch (IOException e) {
			log.error("render内容到页面异常",e);
		}
	}
}
