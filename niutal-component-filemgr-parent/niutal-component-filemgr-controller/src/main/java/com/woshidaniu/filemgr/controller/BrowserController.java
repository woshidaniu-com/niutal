package com.woshidaniu.filemgr.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.filemgr.api.BrowserProvider;
import com.woshidaniu.filemgr.api.FileServ;
import com.woshidaniu.io.utils.FileUtils;

/**
 * 
 *@类名称	: BrowserController.java
 *@类描述	： 浏览器下载服务
 *@创建人	：kangzhidong
 *@创建时间	：2017年7月27日 下午4:13:01
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Controller
@RequestMapping("/filemgr/browser/")
public class BrowserController extends BaseController {

	/**
	 * 浏览器下载提供者
	 */
	@Autowired@Qualifier("browserFileLocalProvider")
	protected BrowserProvider localProvider;
	@Autowired(required = false)
	@Qualifier("browserFileSmabaProvider")
	protected BrowserProvider smabaProvider;
	@Autowired(required = false)
	@Qualifier("browserFileFTPProvider")
	protected BrowserProvider ftpProvider;
	
	//服务类型
	@Value("#{filemgrProps['filemgr.serv']}")
	protected String serv = FileServ.LOCAL.getName();
	
	@RequestMapping(value="index")
	public String index(HttpServletRequest request, HttpServletResponse response,Model model){
		
		model.addAttribute("nextURL", "valdiateYhmCard");
		model.addAttribute("step", "1");
		
		return "/filemgr/browser/info";
	}
	
	/**
	 * 
	 *@描述		：浏览器文件下载
	 *@创建人	: kangzhidong
	 *@创建时间	: 2017年7月27日下午4:21:03
	 *@param info
	 *@param name
	 *@return
	 *@修改人	: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	@RequestMapping("download")
	public ResponseEntity<byte[]> download(@RequestParam("browserType")String browserType) {
		try{
			/**
			 * #文件存储方式：
			 *	#1：表示使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
			 *	#2：表示使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
			 *	#其他：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常
			 */
			File file = null;
			if(FileServ.FTP.compareTo(getServ())){
				//从FTP服务器下载文件
				file = getFtpProvider().get(browserType);
			}else if(FileServ.SAMBA.compareTo(getServ())){
				//从文件共享服务器下载文件
				file = getSmabaProvider().get(browserType);
			}else{
				//本地环境下载文件
				file = getLocalProvider().get(browserType);
			}
			// Http响应头
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setContentDispositionFormData("attachment", file.getName());
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);
		} catch(Exception e){
			logException(e);
		}
		return null;
	}

	public BrowserProvider getLocalProvider() {
		return localProvider;
	}

	public void setLocalProvider(BrowserProvider localProvider) {
		this.localProvider = localProvider;
	}

	public BrowserProvider getSmabaProvider() {
		return smabaProvider;
	}

	public void setSmabaProvider(BrowserProvider smabaProvider) {
		this.smabaProvider = smabaProvider;
	}

	public BrowserProvider getFtpProvider() {
		return ftpProvider;
	}

	public void setFtpProvider(BrowserProvider ftpProvider) {
		this.ftpProvider = ftpProvider;
	}

	public String getServ() {
		return serv;
	}

	public void setServ(String serv) {
		this.serv = serv;
	}
	
	
}
