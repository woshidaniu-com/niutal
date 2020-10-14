package com.woshidaniu.editor.ueditor.provider;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPFile;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.provider.FileManagerProvider;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.editor.api.model.KindeditorConstant;
import com.woshidaniu.editor.ueditor.ProviderEnum;
import com.woshidaniu.ftpclient.FTPClient;
import com.woshidaniu.ftpclient.client.IFTPClient;
import com.woshidaniu.ftpclient.utils.FTPClientUtils;
import com.woshidaniu.ftpclient.utils.FTPStringUtils;
import com.woshidaniu.security.algorithm.RSACodec;
import com.woshidaniu.util.base.MessageUtil;
/**
 * 使用FTP服务器，该方式需要配置ftpclient.properties文件中的参数
 */
public class FTPFileManagerProvider implements FileManagerProvider {

	/**
	 * FTP文件服务客户端
	 */
	@Resource
	protected IFTPClient ftpClient;
	//用于加密文件路径的RSA公钥
	protected String public_key = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.RSA_PUBLIC_KEY), RSACodec.public_key);
		
	protected String dir = null;
	protected String rootPath = null;
	protected String[] allowFiles = null;
	protected int count = 0;
	
	@Override
	public String getName() {
		return ProviderEnum.FTP_FILE_MANAGER.getKey();
	}

	@Override
	public FileManagerProvider config(Map<String, Object> conf) {
		this.rootPath = (String)conf.get( "rootPath" );
		this.dir = this.rootPath + (String)conf.get( "dir" );
		this.allowFiles = this.getAllowFiles( conf.get("allowFiles") );
		this.count = (Integer)conf.get( "count" );
		return this;
	}

	@Override
	public State listFile ( int index ) {
		//释放连接  
		try {
			//获得一个活动连接的FTPClient
			FTPClient ftpClient = getFtpClient().getFTPClient();
			try {
				
				//判断访问目录是否存在
				if(!FTPClientUtils.hasDirectory( ftpClient, this.dir ) ){
					return new BaseState( false, AppInfo.NOT_EXIST );
				}
	
				FTPFile ftpFile = FTPClientUtils.getFTPFile(ftpClient, this.dir );
				if ( !ftpFile.isDirectory() ) {
					return new BaseState( false, AppInfo.NOT_DIRECTORY );
				}
				
				State state = null;
				//遍历目录取的文件信息
				Collection<FTPFile>  list = getFtpClient().listFiles( this.dir, this.allowFiles, true );
				if ( index < 0 || index > list.size() ) {
					state = new MultiState( true );
				} else {
					Object[] fileList = Arrays.copyOfRange( list.toArray(), index, index + this.count );
					state = this.getState( ftpClient, fileList );
				}
				
				state.putInfo( "start", index );
				state.putInfo( "total", list.size() );
				
				return state;
			} finally {
				getFtpClient().releaseClient(ftpClient);
			}
		} catch (Exception e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}
	}
	
	protected State getState (FTPClient ftpClient, Object[] files ) throws IOException {
		
		MultiState multiState = new MultiState( true );
		BaseState fileState = null;
		FTPFile file = null;
		for ( Object obj : files ) {
			if ( obj == null ) {
				break;
			}
			file = (FTPFile)obj;
			fileState = new BaseState( true );
			String remoteDir = FTPStringUtils.getRemoteName(ftpClient, this.dir );
			String fileName = FTPStringUtils.getRemoteName(ftpClient, file.getName() );
			//加密文件路径
			String filePath  = RSACodec.getInstance().encodeByPublicKey(remoteDir + File.separator + fileName , public_key );
			//使用FTP服务器作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
			String ftpPrefix = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.FTP_PREFIX), "../ftp/");
			//FTP文件服务器模式的访问路径
			fileState.putInfo("url", ftpPrefix + filePath + KindeditorConstant.FILENAME_PREFIX + URLEncoder.encode(fileName, "UTF-8") );
			//fileState.putInfo( "url", PathFormat.format( this.getPath( file ) ) );
			multiState.addState( fileState );
		}
		return multiState;
	}
	 
	protected String[] getAllowFiles ( Object fileExt ) {
		
		String[] exts = null;
		String ext = null;
		
		if ( fileExt == null ) {
			return new String[ 0 ];
		}
		
		exts = (String[])fileExt;
		
		for ( int i = 0, len = exts.length; i < len; i++ ) {
			
			ext = exts[ i ];
			exts[ i ] = ext.replace( ".", "" );
			
		}
		
		return exts;
		
	}

	public IFTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(IFTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

}
