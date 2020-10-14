package com.woshidaniu.common.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.ServletUtils;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.BooleanUtils;
import com.woshidaniu.io.utils.FilemimeUtils;
import com.woshidaniu.security.algorithm.RSACodec;
import com.woshidaniu.web.servlet.AbstractHttpServlet;
import com.woshidaniu.web.utils.WebResponseUtils;

/***
 * 
 *@类名称	: FileStreamServlet.java
 *@类描述	：文件访问处理，用于所有不想暴露路径给用户的文件访问，通过流将文件写给客户端
 *@创建人	：kangzhidong
 *@创建时间	：Mar 18, 2016 1:51:46 PM
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
@SuppressWarnings("serial")
public class FileStreamServlet extends AbstractHttpServlet {

	protected String charset = "UTF-8";
	protected final Logger LOG = LoggerFactory.getLogger(FileStreamServlet.class);
	
	public FileStreamServlet() {
		LOG.info("FileStreamServlet inited.");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//设置请求响应字符编码  
        request.setCharacterEncoding(charset);  
        response.setCharacterEncoding(charset); 
		String imagePath = request.getParameter("filePath");
		String attachment = request.getParameter("attachment");
		String filename = request.getParameter("filename");
		boolean isAttachment = BlankUtils.isBlank(attachment)?true:BooleanUtils.parse(attachment) ;
		// 需要对图片路径解密操作
		String filePath = RSACodec.getInstance().decodeByPrivateKey(imagePath,RSACodec.private_key);
		//中文乱码解决
		filePath = URLDecoder.decode(filePath, charset);
		//获得文件扩展名 
		String extension = FilenameUtils.getExtension(filePath);
		//根据文件扩展名 获得相应的文件响应类型
		String contentType =  FilemimeUtils.getExtensionMimeType(extension);
		String fileName = BlankUtils.isBlank(filename) ? filePath.substring(filePath.lastIndexOf(File.separator)+1) :
			URLDecoder.decode(new String(filename.getBytes("ISO-8859-1"),charset),charset);
        if(contentType != null)   {
        	response.setContentType(contentType);
        }else{
        	response.setContentType("application/octet-stream");
        }
        response.setHeader( "Content-Disposition",WebResponseUtils.getContentDisposition(isAttachment, fileName));
		//使用cos工具类直接将文件写给客户端
		ServletUtils.returnFile(filePath, response.getOutputStream());
		
		/*
		FileInputStream hFile = new FileInputStream(filePath);
		// 得到文件大小
		int i = hFile.available();
		byte data[] = new byte[i];
		// 读数据
		hFile.read(data);
		
		//获得文件扩展名 
		String extension = FilenameUtils.getExtension(filePath);
		//根据文件扩展名 获得相应的文件响应类型
		String contentType =  FilemimeUtils.getExtensionMimeType(extension);
		// 设置返回的文件类型
		response.setContentType(contentType);
		// 得到向客户端输出二进制数据的对象
		OutputStream toClient = response.getOutputStream();
		// 输出数据
		toClient.write(data);
		toClient.flush();
		toClient.close();
		hFile.close();*/
	}
 
}
