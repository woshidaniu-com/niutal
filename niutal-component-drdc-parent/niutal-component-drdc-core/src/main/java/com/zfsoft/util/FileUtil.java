/**
 * @部门:学工产品事业部
 * @日期：2013-5-16 上午09:00:54 
 */  
package com.woshidaniu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;





import sun.misc.BASE64Encoder;

/** 
 * @系统名称: 学生工作管理系统
 * @模块名称: XXXX管理模块
 * @类功能描述: 文件操作相关工具类
 * @作者： Penghui.Qu 
 * @时间： 2013-5-16 上午09:00:54 
 * @版本： V1.0
 * @修改记录: 类修改者-修改日期-修改说明  
 */

public class FileUtil {

	public static final String WORD = "application/vnd.ms-word";
	public static final String EXCEL = "application/vnd.ms-excel";
	public static final String PDF = "application/pdf";
	public static final String ZIP = "application/zip";
	
	
	/**
	 *
	 * @描述: 获取文件流的BASE64编码
	 * @作者：Penghui.Qu
	 * @日期：2013-5-16 上午09:03:01
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param file
	 * @return
	 * @throws IOException
	 * String 返回类型 
	 *  @throws IOException 
	 */
	public static String getBASE64(File file) throws IOException {
		
		if (file == null || !file.exists()){
			return null;
		}
		InputStream in = null;
		byte[] bytes = null;
		try{
			in = new FileInputStream(file);
			bytes = new byte[in.available()];
			in.read(bytes);
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			if (in != null){
				in.close();
			}
		}
		
		BASE64Encoder encoder = new BASE64Encoder();
		
		return bytes != null ? encoder.encode(bytes) : null;
	}
	
	/**
	 * 
	 * @描述:输出Word格式文件
	 * @作者：Penghui.Qu
	 * @日期：2013-5-16 下午02:13:57
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param response
	 * @param file
	 * @throws IOException
	 * void 返回类型 
	 * @throws
	 */
	public static void outputWord(HttpServletResponse response, File file) throws IOException{
		outputFile(response, file, WORD);
	}
	
	/**
	 * 
	 * @描述:输出Excel格式文件
	 * @作者：Penghui.Qu
	 * @日期：2013-5-16 下午02:20:28
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param response
	 * @param file
	 * @throws IOException
	 * void 返回类型 
	 * @throws
	 */
	public static void outputExcel(HttpServletResponse response, File file) throws IOException{
		outputFile(response, file, EXCEL);
	}
	
	
	/**
	 * 
	 * @描述:输出pdf格式文件
	 * @作者：Penghui.Qu [工号：445]
	 * @日期：2013-5-21 下午02:03:59
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param response
	 * @param file
	 * @throws IOException
	 * void 返回类型 
	 * @throws
	 */
	public static void outputPdf(HttpServletResponse response, File file) throws IOException{
		outputFile(response, file, PDF);
	}
	
	
	/**
	 * 
	 * @描述: 输出ZIP文件
	 * @作者：Penghui.Qu [工号：445]
	 * @日期：2013-5-21 下午05:18:49
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param response
	 * @param file
	 * @throws IOException
	 * void 返回类型 
	 * @throws
	 */
	public static void outputZip(HttpServletResponse response, File file) throws IOException{
		outputFile(response, file, ZIP);
	}
	
	
	/**
	 * 
	 * @描述: 输出文件流
	 * @作者：Penghui.Qu [工号：445]
	 * @日期：2013-5-24 上午10:36:41
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param response
	 * @param file
	 * @throws IOException
	 * void 返回类型 
	 * @throws
	 */
	public static void outputFile(HttpServletResponse response, File file) throws IOException{
		outputFileStream(new FileInputStream(file),response.getOutputStream());
	}
	
	
	/**
	 * 
	 * @描述: 输出文件流
	 * @作者：Penghui.Qu
	 * @日期：2013-5-20 下午02:22:14
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param in
	 * @param out
	 * @throws IOException
	 * void 返回类型 
	 * @throws
	 */
	public static void outputFileStream(InputStream in,OutputStream out) throws IOException{
		try {
			
			int len;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			if (out != null){
				out.close();
			} 
			if (in != null){
				in.close();
			}
		}
	}
	
	
	/**
	 * 
	 * @描述: 输出文件流
	 * @作者：Penghui.Qu [工号：445]
	 * @日期：2013-5-21 下午02:16:21
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param response
	 * @param file
	 * @param type
	 * @throws IOException
	 * void 返回类型 
	 * @throws
	 */
	public static void outputFile(HttpServletResponse response, File file,String type) throws IOException{
		
		String fileName = new String(file.getName().getBytes("GBK"), "ISO-8859-1");
		
		response.setHeader("Content-Disposition","attachment; filename="+fileName);
		response.setContentType(type);
		
		OutputStream out = null;
		InputStream in = null;
		
		out = response.getOutputStream();
		in = new FileInputStream(file);
		outputFileStream(in, out);
		
	}
}
