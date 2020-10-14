package com.woshidaniu.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：zip 工具包
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月16日上午9:12:33
 */
@Deprecated
public class ZipUtil {

	private static final String ZIP = ".zip";
	private static final int BUFFER = 1024;
	private static Log logger = LogFactory.getLog(ZipUtil.class);

	private ZipUtil(){
		super();
	}
	
	
	/**
	 * 
	 * <p>方法说明：压缩文件目录<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日上午9:17:24<p>
	 * @param srcPath 目录
	 * @return zip文件
	 */
	public static File zip(String srcPath) {

		File srcDir = new File(srcPath);
		if (!srcDir.exists()) {
			logger.error("srcPath not exists !");
			return null;
		}

		Project project = new Project();

		Zip zip = new Zip();
		zip.setProject(project);
		File destFile = new File(System.currentTimeMillis() + ZIP);

		zip.setDestFile(destFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(project);
		fileSet.setDir(srcDir);

		zip.addFileset(fileSet);
		zip.execute();
		return destFile;
	}

	
	/**
	 * 
	 * <p>方法说明：压缩文件<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日上午9:18:28<p>
	 * @param files 压缩文件
	 * @return zip文件
	 */
	public static File zip(File... files) {

		return zip(String.valueOf(System.currentTimeMillis()), files);
	}

	
	
	/**
	 * 
	 * <p>方法说明：压缩文件，指定zip文件名<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日上午9:19:22<p>
	 * @param filename 文件名
	 * @param files 压缩文件
	 * @return zip文件
	 */
	public static File zip(String filename, File... files) {

		if (files.length == 0) {
			logger.error("files can't null ");
			return null;
		}

		Project project = new Project();
		Zip zip = new Zip();
		zip.setProject(project);
		File destFile = new File(filename + ZIP);
		zip.setDestFile(destFile);

		for (File file : files) {
			FileSet fileSet = new FileSet();
			fileSet.setProject(project);
			fileSet.setFile(file);
			zip.addFileset(fileSet);
		}
		zip.execute();
		return destFile;
	}

	
	
	/**
	 * 
	 * <p>方法说明：解压缩<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日上午9:21:53<p>
	 * @param zipFilePath zip文件目录
	 * @param targetPath 解压目录
	 * @throws IOException 
	 */
	public static void unzip(String zipFilePath, String targetPath) throws IOException{
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(zipFilePath); 
	        Enumeration<?> emu = zipFile.getEntries();
	        while (emu.hasMoreElements()) {
	            ZipEntry entry = (ZipEntry) emu.nextElement();
	            if (entry.isDirectory()) {
	                new File(targetPath+ File.separator + entry.getName()).mkdirs();
	                continue;
	            }
	            BufferedInputStream bis = new BufferedInputStream(
	                    zipFile.getInputStream(entry));
	            File file = new File(targetPath+ File.separator + entry.getName());
	            File parent = file.getParentFile();
	            if (parent != null && (!parent.exists())) {
	                parent.mkdirs();
	            }
	            FileOutputStream fos = new FileOutputStream(file);
	            BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
	            byte[] buf = new byte[BUFFER];
	            int len = 0;
	            while ((len = bis.read(buf, 0, BUFFER)) != -1) {
	                fos.write(buf, 0, len);
	            }
	            bos.flush();
	            bos.close();
	            bis.close();
	        }
		} catch(Exception ex){
			ex.printStackTrace();
		} finally{
			if (zipFile != null){
				zipFile.close(); 
			}
		}
	}

}
