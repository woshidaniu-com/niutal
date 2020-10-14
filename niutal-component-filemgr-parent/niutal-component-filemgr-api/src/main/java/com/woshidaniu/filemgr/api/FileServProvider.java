package com.woshidaniu.filemgr.api;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface FileServProvider {

	/**
	 * 将指定的文件写入文件服务；方法返回文件的存储信息
	 * @param file 
	 * @return
	 * @throws Exception
	 */
	public FileObject output(File file) throws Exception;
	
	/**
	 * 将指定的文件写入文件服务；方法返回文件的存储信息
	 * @param file 
	 * @return
	 * @throws Exception
	 */
	public FileObject output(File file, String fileName) throws Exception;
	
	/**
	 * 将指定的字节流写入文件服务，自动创建文件；方法返回文件的存储信息
	 * @param file 
	 * @return
	 * @throws Exception
	 */
	public FileObject output(byte[] bytes, String fileName) throws Exception;
	
	/**
	 * 根据UID获取文件对象描述信息
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public FileObject info(String uid) throws Exception;
	
	/**
	 * 根据UID获取文件对象：特别注意，调用完后记得删除文件，否则可能会导致临时目录临时文件过多影响读写和性能
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public File file(String uid) throws Exception;
	
	/**
	 * 根据UID获取输入流对象，该对象为 ByteArrayInputStream ：特别注意，大文件不推荐此方法，内存占用比较多
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public InputStream input(String uid) throws Exception;
	
	/**
	 * 将根据UID获取到的文件对象直接写入输出流 ：特别注意，调用完后请务必关闭输出流
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public void copyLarge(String uid, OutputStream output) throws Exception;
	
	/**
	 * 根据UID删除文件对象
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public boolean delete(String uid) throws Exception;
	
	public boolean existsDir(String ukey, String parent) throws Exception;
	
	/**
	 * 根据用户ID获取该用户目录、文件树形结构数据
	 * @param ukey
	 * @return List<Map<String, String>>
	 * @throws Exception
	 */
	public List<FileObject> listFiles(String ukey, String parent) throws Exception;
	
}
