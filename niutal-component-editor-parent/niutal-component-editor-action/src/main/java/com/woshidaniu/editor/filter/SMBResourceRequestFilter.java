package com.woshidaniu.editor.filter;

import java.io.File;
import java.net.URLDecoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.BooleanUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.editor.EditorParameter;
import com.woshidaniu.editor.EditorParameters;
import com.woshidaniu.io.utils.FileReadUtils;
import com.woshidaniu.io.utils.FilemimeUtils;
import com.woshidaniu.security.algorithm.RSACodec;
import com.woshidaniu.smbclient.client.ISMBClient;
import com.woshidaniu.smbclient.utils.SMBPathUtils;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.util.file.DirectoryUtils;
import com.woshidaniu.web.utils.WebResponseUtils;


public class SMBResourceRequestFilter extends com.woshidaniu.smbclient.web.SMBResourceRequestFilter {
	
	/**
	 * 用于解密文件路径的RSA私钥
	 */
	protected static final String RSA_PRIVATE_KEY = "kindeditor.rsa.private_key";
	protected String fileName_cn = null;
	protected String splitStr = "&filename=";
	protected String charset = "UTF-8";

	
	@Override
	protected ISMBClient initSMBClient(FilterConfig filterConfig) {
		
		//获取文件存储路径
		localDir = EditorParameters.getGlobalString(EditorParameter.SMBCLIEN_LOCALDIR, localDir);
		//获取文件存储路径
		requestPrefix = EditorParameters.getGlobalString(EditorParameter.SMBCLIEN_REQUESTPREFIX);
		//获取是否缓存共享文件到本地存储路径
		cacheLocal = EditorParameters.getGlobalBoolean(EditorParameter.SMBCLIEN_CACHELOCAL);
		//获取共享文件在本地缓存的时间;默认10分钟
		cacheExpiry = EditorParameters.getGlobalLong(EditorParameter.SMBCLIEN_CACHEEXPIRY);
		//获取文件本地存储目录
		directory = SMBPathUtils.getLocalDir(filterConfig.getServletContext(), localDir);
		//异常信息重定向路径
		redirectURL = EditorParameters.getGlobalString(EditorParameter.SMBCLIEN_REDIRECTURL, redirectURL);
		
		return (ISMBClient) ServiceFactory.getService("smbClient");
	}

	@Override
	protected String getDecryptPath(String encryptPath) throws Exception {
		int index = encryptPath.indexOf(splitStr);
		if( index > -1){
			//&name=xxxx
			fileName_cn = encryptPath.substring(index + splitStr.length());
			if(!BlankUtils.isBlank(fileName_cn)){
				fileName_cn = URLDecoder.decode(new String(fileName_cn.getBytes("ISO-8859-1"),charset),charset);
			}
			encryptPath = encryptPath.substring(0,index);
		}
		//用于解密文件路径的RSA私钥
		String private_key = StringUtils.getSafeStr(MessageUtil.getText(RSA_PRIVATE_KEY), RSACodec.private_key);
		//需要对图片路径解密操作
		String filePath = RSACodec.getInstance().decodeByPrivateKey(encryptPath,private_key);
		//中文乱码解决
		return URLDecoder.decode(filePath, "UTF-8");
	}

	@Override
	protected void setHeader(HttpServletRequest httpRequest,HttpServletResponse response, String filePath, String extension, String filename) throws Exception {
		//根据文件扩展名 获得相应的文件响应类型
		String contentType =  FilemimeUtils.getExtensionMimeType(extension);
		String fileName = BlankUtils.isBlank(fileName_cn) ? filePath.substring(filePath.lastIndexOf(File.separator)+1) : fileName_cn;
        if(contentType != null)   {
        	response.setContentType(contentType);
        }else{
        	response.setContentType("application/octet-stream");
        }
    	String attachment = httpRequest.getParameter("attachment");
		boolean isAttachment = BlankUtils.isBlank(attachment)?true:BooleanUtils.parse(attachment) ;
		response.setHeader( "Content-Disposition",WebResponseUtils.getContentDisposition(isAttachment, fileName));
	}
	
	public static void main(String[] args) throws Exception {
		
		Pattern pattern_find = Pattern.compile("((?:filePath=)([^\">]*?)(?:\">))+");
		List<String> lines = FileReadUtils.readerLines(new File("D:\\tmp.txt"));
		for(String line:lines){
			Matcher matcher = pattern_find.matcher(line);
			if(matcher.find()){
				System.out.println(DirectoryUtils.getDecodePath(matcher.group(2), "&filename=", "UTF-8", MessageUtil.getText(RSA_PRIVATE_KEY)));
			}
		}
	}

}
