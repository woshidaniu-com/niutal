package com.woshidaniu.util.file;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Calendar;

import org.apache.commons.lang.SystemUtils;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.io.utils.FileCopyUtils;
import com.woshidaniu.security.algorithm.RSACodec;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.context.WebContext;
/**
 * 
 * @package com.woshidaniu.util.file
 * @className: DirectoryHelper
 * @description: 文件目录工具
 * @author : kangzhidong
 * @date : 2014-4-15
 * @time : 下午04:40:04
 */
public class DirectoryUtils extends com.woshidaniu.io.utils.DirectoryUtils {

	/**
	 * 
	 * @description: 获得用户目录
	 * @author : kangzhidong
	 * @date : 2014-4-15
	 * @time : 下午04:35:34 
	 * @param session 当前http会话
	 * @param fileType 文件类型
	 * @param userDir  用户目录名
	 * @param dir	分类目录
	 * @return
	 * @throws IOException
	 */
	public static File getUserDir(String userDir,String dir,String fileType) throws IOException {
		//获得根目录
		File directory = getUserDir(userDir);
		//得到用户目录下指定的目录
		if (!StringUtils.isBlank(dir)) {
			directory = getExistDir(directory.getAbsolutePath()+ File.separator + dir);
		}
		//得到用户目录下指定的目录下文件类型对应的目录
		if (!StringUtils.isBlank(fileType)) {
			directory = getExistDir(directory.getAbsolutePath()+ File.separator + fileType);
		}
		// 返回最终路径
		if (!directory.isDirectory()) {
			throw new IOException("Source '" + directory+ "' is not a directory");
		}
		return directory;
	}
	
	public static File getUserDir(String userDir) throws IOException {
		//获得根目录
		//获得根目录
		File directory = getRootDir();
		//得到用户目录
		if (!StringUtils.isBlank(userDir)) {
			directory = getExistDir(directory.getAbsolutePath()+ File.separator + userDir);
		}
		// 返回最终路径
		if (!directory.isDirectory()) {
			throw new IOException("Source '" + directory+ "' is not a directory");
		}
		return directory;
	}

	public static File getTargetDir(String targetDir) throws IOException {
		File directory = getRootDir();
		//添加模块路径
		if (!BlankUtils.isBlank(targetDir)) {
			directory = new File(directory , targetDir);
		}
		return directory;
	}
	
	public static File getTargetDir(String rootDir, String userDir,String dir) throws IOException {
		//获得系统设置文件存储根目录
		File directory = getRootDir();
		//获得指定目录的根目录
		if (!BlankUtils.isBlank(rootDir)) {
			directory = getExistDir(directory.getAbsolutePath()+ File.separator + rootDir);
		}
		//获取用户目录
		if (!BlankUtils.isBlank(userDir)) {
			directory = getExistDir(directory.getAbsolutePath()+ File.separator + userDir);
		}
		// 返回最终路径
		String targetPath = "";
		if(!BlankUtils.isBlank(dir)){
			// 处理目标路径
			targetPath = DirectoryUtils.getResolvePath(dir);
			if (targetPath.startsWith(File.separator)) {
				targetPath = targetPath.substring(File.separator.length());
			}
		}
		return new File(directory , targetPath);
	}
	
	public static File getExistTargetDir(String targetDir) throws IOException {
		File directory = getRootDir();
		//添加模块路径
		if (!BlankUtils.isBlank(targetDir)) {
			directory = getExistDir(directory.getAbsolutePath()+ File.separator + targetDir);
		}
		return directory;
	}

	public static File getExistTargetDir(String rootDir, String userDir,String dir) throws IOException {
		//获得系统设置文件存储根目录
		File directory = getTargetDir(rootDir, userDir, dir);
		directory = getExistDir(directory.getAbsolutePath());
		if (!directory.isDirectory()) {
			throw new IOException("Source '" + directory+ "' is not a directory");
		}
		return directory;
	}
	
	/**
	 * 
	 * @description: 获取根目录
	 * @author : kangzhidong
	 * @date : 2014-4-15
	 * @time : 下午04:37:18 
	 * @param session
	 * @return
	 * @throws IOException
	 */
	public static File getRootDir() throws IOException {
		File directory = null;
		//从配置文件找到指定的外部存储路径配置
		String directoryPath = getResolvePath(MessageUtil.getText("system.uploadDir"));
		if(StringUtils.isEmpty(directoryPath)){
			//未找到指定的外部目录，使用本地程序根目录
			directoryPath = WebContext.getServletContext().getRealPath(File.separator);
			//.getContextPath()
			directory = getExistDir(directoryPath);
		}else{
			//判断指定的路径是否存在
			directory = SystemUtils.getUserDir();
		}
		return directory;
	}

	public static File getRootExistDir(String dirPath ) throws IOException {
		//获得根目录
		return getExistDir(getRootDir().getAbsolutePath()+ File.separator + dirPath);
	}
	
	public static File getExistDir(String dirPath ) {
		File directory = new File(DirectoryUtils.getResolvePath(dirPath));
		// 如果目录不存在，则创建根目录
		if (!directory.exists()) {
			directory.setReadable(true);
			directory.setWritable(true);
			directory.mkdirs();
		}
		return directory;
	}
	
	public static File getTmpDir() throws IOException {
		//获得tmpdir目录
		return getExistDir(getRootDir().getAbsolutePath()+ File.separator + MessageUtil.getText("niutal.tempPath"));
	}
	
	public static File getRealPath(String dirPath ){
		//获得根目录
		return getExistDir(WebContext.getServletContext().getRealPath(DirectoryUtils.getResolvePath(dirPath)));
	}
	

	public static boolean isRootExist(String path ) throws IOException {
		//获得根目录
		File directory = getRootDir();
		return isExist(directory.getAbsolutePath()+ File.separator + path);
	}
	
	public static boolean isExist(String path ) {
		File target = new File(DirectoryUtils.getResolvePath(path));
		// 如果目录不存在，则创建根目录
		if (!target.exists()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 *@描述：根据业务模块得到附件目录
	 *@创建人:kangzhidong
	 *@创建时间:2014-7-5上午10:49:13
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param module
	 *@return
	 *@throws IOException
	 */
	public static File getAttachmentDir(String module) throws IOException {
		//获得根目录
		File directory = getTargetDir(module);
		//添加文件目录路径
		String fileDir = File.separator + Calendar.getInstance().get(Calendar.YEAR) + File.separator + (Calendar.getInstance().get(Calendar.MONTH) + 1);
		if (!BlankUtils.isBlank(fileDir)) {
			directory = getExistDir(directory.getAbsolutePath()+ File.separator + fileDir.toString());
		}
		if (!directory.isDirectory()) {
			throw new IOException("Source '" + directory+ "' is not a directory");
		}
		return directory;
	}
	
	public static File getAttachmentFile(String module,String filePath) throws IOException{
		File directory = getAttachmentDir(module);
		//添加模块路径
		if (!StringUtils.isBlank(filePath)) {
			return new File(DirectoryUtils.getResolvePath(directory.getAbsolutePath()+ File.separator + filePath));
		}
		return null;
	}
	
	/*保存新附件*/
	public static String uploadAttachment(String module,File sourceFile,String fileName) throws Exception {
		//获得附件目录
		File directory = getAttachmentDir(module);
		//保存到文件服务器
		FileCopyUtils.copy(sourceFile, new File(directory.getAbsolutePath() + File.separator + fileName ));
		//返回加密文件路径
		//返回加密文件路径
		String strEncodedPath=java.net.URLEncoder.encode(directory.getAbsolutePath() + File.separator + fileName,"UTF-8");
		return RSACodec.getInstance().encodeByPublicKey(strEncodedPath,RSACodec.public_key);
		//return RSACodec.getInstance().encodeByPublicKey(directory.getAbsolutePath() + File.separator + fileName,RSACodec.public_key);
	}
	
	
	/*获取解密后的路径*/
	public static String getDecodePath(String encryptPath,String splitStr,String charset,String rsaKey) throws Exception {
		String fileName_cn = null;
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
		String private_key = StringUtils.getSafeStr(rsaKey, RSACodec.private_key);
		//需要对图片路径解密操作
		String filePath = RSACodec.getInstance().decodeByPrivateKey(encryptPath,private_key);
		//中文乱码解决
		return URLDecoder.decode(filePath, "UTF-8");
	}
	
}
