/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.woshidaniu.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.woshidaniu.io.utils.FileCopyUtils;

/**
 * Simple utility methods for file and stream copying.
 * All copy methods use a block size of 4096 bytes,
 * and close all affected streams when done.
 * <p>Mainly for use within the framework,
 * but also useful for application code.
 * 文件和数据流拷贝实用方法,所有的拷贝方法用4096 bytes的数据块进行拷贝<br/>
 * 主要运用于框架,但也适用于应用代码中
 * @author zhaoheng(740)
 * @since 06.10.2003
 */
@Deprecated
public class FileUtils extends com.woshidaniu.io.utils.FileUtils{

		
	@Deprecated
	public static byte[] copyToByteArray(File in) throws IOException {
		return toByteArray(in);
	}
	
	@Deprecated
	public static void copy(File in, File out) throws IOException {
		FileCopyUtils.copy(in, out);
	}
	
	@Deprecated
	public static void copy(byte[] in, File out) throws IOException {
		FileCopyUtils.copy(in, out); 
	}
	
	/**
     * 导出文件
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param file 文件
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, File file) {
        downloadFile(request, response, file, "");
    }
    
    /**
     * 导出文件
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param file 文件
     * @param fileName 导出后的文件名
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, File file, String fileName) {
        // 清空输出流
        response.reset();
        fileName = (StringUtils.isEmpty(fileName)) ? file.getName() : fileName;
        if (fileName.toLowerCase().endsWith(".xls")) {
            response.setContentType("application/msexcel");
        } else if (fileName.toLowerCase().endsWith(".doc")) {
            response.setContentType("application/msword");
        } else if (fileName.toLowerCase().endsWith(".txt")) {
            response.setContentType("application/text");
        } else {
        	response.setContentType("application/stream");
        }
        response.setHeader("Cache-Control", "no-cache");

        fileName = getFileName(request, fileName);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 设置下载内容大小
        response.setContentLength((int) file.length());

        // 缓冲区
        byte[] buffer = new byte[4096];
        FileOutputStream fos = null;
        BufferedOutputStream output = null;
        BufferedInputStream input = null;
        try {
            output = new BufferedOutputStream(response.getOutputStream());
            input = new BufferedInputStream(new FileInputStream(file));
            int n = -1;
            while ((n = input.read(buffer, 0, 4096)) > -1) {
                output.write(buffer, 0, n);
            }
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != input) {
                    input.close();
                }
                if (null != fos) {
                    fos.flush();
                    fos.close();
                }
                if (null != output) {
                    output.flush();
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 根据不同的浏览器，转换为浏览器识别的中文字符串
     * 
     * @param request HttpServletRequest
     * @param fileName 原文件名（未转换前的）
     * @return 转换后的文件名
     */
	private static String getFileName(HttpServletRequest request, String fileName) {
        String rsFileName = "";
        String agent = request.getHeader("USER-AGENT");
        try {
            if (null != agent && -1 != agent.indexOf("MSIE")) {
                rsFileName = URLEncoder.encode(fileName, "UTF-8");
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
                //rsFileName = MimeUtility.encodeText(fileName, "UTF-8", "B");
            	rsFileName = fileName;
            } else {
                rsFileName = fileName;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return rsFileName;
    }
	
	public static final int BUFFER_SIZE = 4096;




	//---------------------------------------------------------------------
	// Copy methods for java.io.InputStream / java.io.OutputStream
	//---------------------------------------------------------------------

	/**
	 * Copy the contents of the given InputStream to the given OutputStream.
	 * Closes both streams when done.
	 * @param in the stream to copy from
	 * @param out the stream to copy to
	 * @return the number of bytes copied
	 * @throws java.io.IOException in case of I/O errors
	 */
	public static int copy(InputStream in, OutputStream out) throws IOException {
		Assert.notNull(in, "No InputStream specified");
		Assert.notNull(out, "No OutputStream specified");
		try {
			int byteCount = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}
		finally {
			try {
				in.close();
			}
			catch (IOException ex) {
			}
			try {
				out.close();
			}
			catch (IOException ex) {
			}
		}
	}

	/**
	 * Copy the contents of the given byte array to the given OutputStream.
	 * Closes the stream when done.
	 * @param in the byte array to copy from
	 * @param out the OutputStream to copy to
	 * @throws java.io.IOException in case of I/O errors
	 */
	public static void copy(byte[] in, OutputStream out) throws IOException {
		Assert.notNull(in, "No input byte array specified");
		Assert.notNull(out, "No OutputStream specified");
		try {
			out.write(in);
		}
		finally {
			try {
				out.close();
			}
			catch (IOException ex) {
			}
		}
	}

	/**
	 * Copy the contents of the given InputStream into a new byte array.
	 * Closes the stream when done.
	 * @param in the stream to copy from
	 * @return the new byte array that has been copied to
	 * @throws java.io.IOException in case of I/O errors
	 */
	public static byte[] copyToByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		copy(in, out);
		return out.toByteArray();
	}


	//---------------------------------------------------------------------
	// Copy methods for java.io.Reader / java.io.Writer
	//---------------------------------------------------------------------

	/**
	 * Copy the contents of the given Reader to the given Writer.
	 * Closes both when done.
	 * @param in the Reader to copy from
	 * @param out the Writer to copy to
	 * @return the number of characters copied
	 * @throws java.io.IOException in case of I/O errors
	 */
	public static int copy(Reader in, Writer out) throws IOException {
		Assert.notNull(in, "No Reader specified");
		Assert.notNull(out, "No Writer specified");
		try {
			int byteCount = 0;
			char[] buffer = new char[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}
		finally {
			try {
				in.close();
			}
			catch (IOException ex) {
			}
			try {
				out.close();
			}
			catch (IOException ex) {
			}
		}
	}

	/**
	 * Copy the contents of the given String to the given output Writer.
	 * Closes the write when done.
	 * @param in the String to copy from
	 * @param out the Writer to copy to
	 * @throws java.io.IOException in case of I/O errors
	 */
	public static void copy(String in, Writer out) throws IOException {
		Assert.notNull(in, "No input String specified");
		Assert.notNull(out, "No Writer specified");
		try {
			out.write(in);
		}
		finally {
			try {
				out.close();
			}
			catch (IOException ex) {
			}
		}
	}

	/**
	 * Copy the contents of the given Reader into a String.
	 * Closes the reader when done.
	 * @param in the reader to copy from
	 * @return the String that has been copied to
	 * @throws java.io.IOException in case of I/O errors
	 */
	public static String copyToString(Reader in) throws IOException {
		StringWriter out = new StringWriter();
		copy(in, out);
		return out.toString();
	}

	/**
	 * Recursively copy the contents of the <code>src</code> file/directory
	 * to the <code>dest</code> file/directory. <br/>
     * 递归的拷贝给定的src文件或文件夹到目标文件夹或文件内
	 * @param src the source directory <br/>
     * 源文件夹或文件
	 * @param dest the destination directory <br/>
     * 目标 文件夹或文件
	 * @throws java.io.IOException in the case of I/O errors
	 * @see com.woshidaniu.io.utils.FileCopyUtils#copyDir(File, File)
	 */
	@Deprecated
	public static void copyRecursively(File src, File dest) throws IOException {
		FileCopyUtils.copyDir(src, dest);
	}
	
}
