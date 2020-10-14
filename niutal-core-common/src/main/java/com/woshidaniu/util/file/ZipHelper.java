package com.woshidaniu.util.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipException;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.io.utils.FileUtils;
import com.woshidaniu.io.utils.IOUtils;

public abstract class ZipHelper {

	protected static Logger LOG = LoggerFactory.getLogger(ZipHelper.class);
	
	protected static int bufferSize = 1024;
	
	public static void zipDir( File directory, String[] extensions, boolean recursive, ArchiveOutputStream ouputStream) throws IOException {
		Collection<File> inputFiles = FileUtils.listFiles(directory, extensions, recursive);
		ZipHelper.zipFiles(inputFiles, ouputStream, directory.getAbsolutePath()+ File.separator);
	}


	/**
	 * 这个方法接受的就是一个所要打包文件的集合， 还有一个ArchiveOutputStream
	 * @throws IOException 
	 */
	public static void zipFiles(Collection<File> inputFiles, ArchiveOutputStream ouputStream,String rootDir) throws IOException {
		for (File file : inputFiles) {
			zipFile(file, ouputStream,rootDir);
		}
		ouputStream.flush();
		ouputStream.finish();
	}

	/**
	 * 根据输入的文件与输出流对文件进行打包
	 * 
	 * @param File
	 * @param 
	 */
	public static void zipFile(File inputFile, ArchiveOutputStream ouputStream,String rootDir) {
		try {
			if (inputFile.exists()) {
				/**
				 * 如果是目录的话这里是不采取操作的， 至于目录的打包正在研究中
				 */
				if (inputFile.isFile()) {
					if (ouputStream instanceof ZipArchiveOutputStream) {
						
						ZipArchiveOutputStream zipOut = (ZipArchiveOutputStream) ouputStream;
						ZipArchiveEntry zipEntry = new ZipArchiveEntry(inputFile, inputFile.getPath().replace(rootDir, ""));
						zipOut.putArchiveEntry(zipEntry);
						// 向压缩文件中输出数据
						int nNumber;
						FileInputStream IN = null;
						BufferedInputStream bins = null;
						try {
							byte[] buffer = new byte[bufferSize];
							IN = new FileInputStream(inputFile);
							bins = new BufferedInputStream(IN, bufferSize);
							while ((nNumber = bins.read(buffer)) != -1) {
								zipOut.write(buffer, 0, nNumber);
							}
							zipOut.closeArchiveEntry();
						} finally{
							// 关闭创建的流对象
							IOUtils.closeQuietly(bins);
							IOUtils.closeQuietly(IN);
						}
					}
				} else {
					try {
						File[] files = inputFile.listFiles();
						for (int i = 0; i < files.length; i++) {
							zipFile(files[i], ouputStream,rootDir);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (ZipException zipe) {
			System.out.println(inputFile.getName() + "不是一个ZIP文件！文件格式错误");
		} catch (Exception i) {
			System.out.println("over");
		}

	}
	
	
	/**
	 * 
	 *@描述：把一个zip文件解压到一个指定的目录中
	 *@创建人:kangzhidong
	 *@创建时间:Aug 10, 20154:02:29 PM
	 *@param zipSourceFile zip文件抽象地址
	 *@param outputdir 文件解压目录
	 *@param encoding
	 *@throws IOException
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
    @SuppressWarnings("unchecked")
	public static void unZip(File zipSourceFile, File outputdir,String encoding) throws IOException {
        if (zipSourceFile.exists()) {
        	//输出目录添加/
            outputdir = new File(outputdir.getAbsolutePath() + File.separator);
            //确保目录存在
            FileUtils.forceMkdir(outputdir);
            ZipFile zipFile = encoding ==null ? new ZipFile(zipSourceFile, "GBK") : new ZipFile(zipSourceFile,encoding);
            //解析出所以的文件
            Enumeration zipArchiveEntrys = zipFile.getEntries();
            //循环压缩文件内的文件
            while (zipArchiveEntrys.hasMoreElements()) {
            	
                ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) zipArchiveEntrys.nextElement();
                if (zipArchiveEntry.isDirectory()) {
                    FileUtils.forceMkdir(new File(outputdir, zipArchiveEntry.getName() + File.separator));
                } else {
                	InputStream in = null;
                	OutputStream out = null;
                	try {
                		
                		in = new ByteArrayInputStream(zipArchiveEntry.getExtra());
                		//in = zipFile.getInputStream(zipArchiveEntry);
						out = FileUtils.openOutputStream(new File(outputdir, zipArchiveEntry.getName()));
						IOUtils.copy(in, out);
					} finally {
						IOUtils.closeQuietly(in);
						IOUtils.closeQuietly(out);
					}
                }
            }
        } else {
            throw new IOException("指定的解压文件不存在：\t" + zipSourceFile.getAbsolutePath());
        }
    }
	
    
    public static void unZip(File zipSourceFile, File outputdir) throws IOException {
    	unZip(zipSourceFile, outputdir,null);
    }
    
    public static void main(String[] args) throws IOException {
		
    	File zipSourceFile = new File("D:/xiaomi3qianggrj-v4.24.zip");
    	
    	ZipHelper.unZip(zipSourceFile, new File("D:/ss"));
		
		
	}

}
