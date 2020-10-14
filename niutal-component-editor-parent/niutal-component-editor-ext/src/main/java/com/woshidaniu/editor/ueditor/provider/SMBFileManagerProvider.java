package com.woshidaniu.editor.ueditor.provider;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.provider.FileManagerProvider;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.editor.api.model.KindeditorConstant;
import com.woshidaniu.editor.ueditor.ProviderEnum;
import com.woshidaniu.security.algorithm.RSACodec;
import com.woshidaniu.smbclient.client.ISMBClient;
import com.woshidaniu.util.base.MessageUtil;

import jcifs.smb.SmbFile;
/**
 * 使用文件共享服务，该方式需要配置smbclient.properties文件中的参数
 */
public class SMBFileManagerProvider implements FileManagerProvider {

	/**
	 * 文件共享客户端
	 */
	@Resource
	protected ISMBClient smbClient;
	//用于加密文件路径的RSA公钥
	protected String public_key = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.RSA_PUBLIC_KEY), RSACodec.public_key);
	
	protected String dir = null;
	protected String rootPath = null;
	protected String[] allowFiles = null;
	protected int count = 0;
	
	@Override
	public String getName() {
		return ProviderEnum.SMB_FILE_MANAGER.getKey();
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
		
		try {
			
			//当前访问目录
			SmbFile targetDir = getSmbClient().getFile( this.dir );
			
			if ( !targetDir.exists() ) {
				return new BaseState( false, AppInfo.NOT_EXIST );
			}
			if ( !targetDir.isDirectory() ) {
				return new BaseState( false, AppInfo.NOT_DIRECTORY );
			}
			
			State state = null;
			//遍历目录取的文件信息
			SmbFile[]  list = getSmbClient().listFiles( this.dir, this.allowFiles, true );
			if ( index < 0 || index > list.length ) {
				state = new MultiState( true );
			} else {
				Object[] fileList = Arrays.copyOfRange( list , index, index + this.count );
				state = this.getState( fileList );
			}

			state.putInfo( "start", index );
			state.putInfo( "total", list.length );
			
			return state;
			
		} catch (Exception e) {
			// 目录访问异常
			return new BaseState(false, AppInfo.IO_ERROR);
		}
		
	}
	
	protected State getState ( Object[] files ) throws IOException {
		
		MultiState multiState = new MultiState( true );
		BaseState fileState = null;
		
		SmbFile file = null;
		
		for ( Object obj : files ) {
			if ( obj == null ) {
				break;
			}
			file = (SmbFile)obj;
			fileState = new BaseState( true );
			
			//加密文件路径
			String filePath  = RSACodec.getInstance().encodeByPublicKey( this.dir + File.separator + file.getName() , public_key );
			//使用文件共享服务器作为文件存储时生成访问路径的前缀;注意此处的..为相对路径
			String smbPrefix = StringUtils.getSafeStr(MessageUtil.getText(KindeditorConstant.SMB_PREFIX), "../smb/");
			//共享文件模式的访问路径
			fileState.putInfo("url", smbPrefix + filePath + KindeditorConstant.FILENAME_PREFIX + URLEncoder.encode(file.getName(), "UTF-8") );
			//fileState.putInfo( "url", PathFormat.format( this.getPath( file ) ) );
			
			multiState.addState( fileState );
		}
		
		return multiState;
		
	}
	
	protected String getPath ( File file ) {
		
		String path = file.getAbsolutePath();
		
		return path.replace( this.rootPath, "/" );
		
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

	public ISMBClient getSmbClient() {
		return smbClient;
	}

	public void setSmbClient(ISMBClient smbClient) {
		this.smbClient = smbClient;
	}

}
