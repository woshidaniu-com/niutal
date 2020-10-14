package com.woshidaniu.editor.ueditor.provider;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.provider.UploadProvider;
import com.baidu.ueditor.utils.StoreUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.editor.api.model.KindeditorConstant;
import com.woshidaniu.editor.ueditor.ProviderEnum;
import com.woshidaniu.io.utils.FilenameUtils;
import com.woshidaniu.io.utils.IOUtils;
import com.woshidaniu.security.algorithm.RSACodec;
import com.woshidaniu.smbclient.client.ISMBClient;
import com.woshidaniu.util.base.MessageUtil;
/**
 * 使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
 */
public class SMBBinaryUploadProvider implements UploadProvider {

	public static final int BUFFER_SIZE = 8192;
	/**
	 * 文件共享客户端
	 */
	@Resource
	protected ISMBClient smbClient;
	//用于加密文件路径的RSA公钥
	protected String public_key = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.RSA_PUBLIC_KEY), RSACodec.public_key);
		
	
	@Override
	public String getName() {
		return ProviderEnum.SMB_BINARY_UPLOAD.getKey();
	}

	@Override
	public State doExec(HttpServletRequest request, Map<String, Object> conf) {

		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, 5);
		}

		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

		if (isAjaxUpload) {
			upload.setHeaderEncoding("UTF-8");
		}
		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				
				fileStream = iterator.next();

				if (!fileStream.isFormField()){
					break;
				}
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA );
			}

			String savePath = (String) conf.get("savePath");
			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, 8);
			}

			savePath = PathFormat.parse(savePath, originFileName);

			String remoteDir = "";

			int pos = savePath.lastIndexOf("/");
			if (pos > -1) {
				remoteDir = savePath.substring(0, pos + 1);
			}
			
			State storageState;
			InputStream input = null;
			try {
				input = fileStream.openStream();
				String physicalPath = (String) conf.get("rootPath") + savePath;
				boolean keepLocalFile = "false".equals(conf.get("keeplocal")) ? false : true;
				storageState = saveFileByInputStream( input , remoteDir, physicalPath, maxSize, keepLocalFile);
				if (storageState.isSuccess()) {
					// storageState.putInfo("url", savePath); 这里返回地址为了返回的是，ftp服务器上的地址，所以这里注释掉
					storageState.putInfo("type", suffix);
					storageState.putInfo("original", originFileName + suffix);
				}
			} finally {
				IOUtils.closeQuietly(input);
			}

			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException localIOException) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	@SuppressWarnings("rawtypes")
	protected boolean validType(String type, String[] allowTypes) {
		List list = Arrays.asList(allowTypes);
		return list.contains(type);
	}

	/**
	 * 上传FTP文件
	 * @param input
	 * @param physicalPath
	 * @param maxSize
	 * @return
	 */
	public State saveFileByInputStream(InputStream input,String remoteDir, String physicalPath, long maxSize, boolean keepLocalFile) {
		try {
			State state = null;
			//创建临时文件和缓冲区
			File tmpFile = StoreUtils.getTmpFile();
			byte[] dataBuf = new byte[2048];
			//拷贝上传的文件到临时地址存
			BufferedInputStream buffInput = null;
			BufferedOutputStream output = null;
			try {
				buffInput = new BufferedInputStream(input, BUFFER_SIZE);
				output = new BufferedOutputStream(new FileOutputStream(tmpFile), BUFFER_SIZE);
				int count = 0;
				while ((count = input.read(dataBuf)) != -1) {
					output.write(dataBuf, 0, count);
				}
				output.flush();
			} finally {
				IOUtils.closeQuietly(buffInput);
				IOUtils.closeQuietly(output);
			}
			//拷贝异常，删除临时文件
			if (tmpFile.length() > maxSize) {
				tmpFile.delete();
				return new BaseState(false, AppInfo.MAX_SIZE);
			}
			//执行临时文件的保存
			state = saveTmpFile(tmpFile, remoteDir, physicalPath, keepLocalFile);
			//临时文件保存成功,删除临时文件
			if (!state.isSuccess()) {
				tmpFile.delete();
			}
			return state;
		} catch (IOException localIOException) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	//保存临时文件
	public State saveTmpFile(File tmpFile, String remoteDir, String physicalPath, boolean keepLocalFile) {
		
		File targetFile = new File(physicalPath);
		if (targetFile.canWrite())
			return new BaseState(false, AppInfo.PERMISSION_DENIED );
		try {
			FileUtils.moveFile(tmpFile, targetFile);
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR );
		}
		
		//唯一ID
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		//重命名新的文件名
		String newFileName = uuid + Math.random() + FilenameUtils.getFullExtension(physicalPath);
		//创建状态对象
		State state = new BaseState(true);
		state.putInfo("size", targetFile.length());
		state.putInfo("title", newFileName);
		try {
			File file = targetFile;
			if (file != null) {
				
				//上传文件至文件共享服务器
				getSmbClient().upload(file, remoteDir , newFileName);
				//加密文件路径
				String filePath  = RSACodec.getInstance().encodeByPublicKey(remoteDir + File.separator + newFileName , public_key );
				//使用文件共享服务器作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
				String smbPrefix = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.SMB_PREFIX), "../smb/");
				//共享文件模式的访问路径
				state.putInfo("url", smbPrefix + filePath + KindeditorConstant.FILENAME_PREFIX + URLEncoder.encode(newFileName, "UTF-8") );
				//state.putInfo("url", ftpPath);
				
				try {
					if (!keepLocalFile) {
						targetFile.delete();
					}
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {
			return new BaseState(false, AppInfo.IO_ERROR );
		}

		return state;
		
	}

	public ISMBClient getSmbClient() {
		return smbClient;
	}

	public void setSmbClient(ISMBClient smbClient) {
		this.smbClient = smbClient;
	}

}
