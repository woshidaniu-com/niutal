package com.woshidaniu.editor.ueditor;

public enum ProviderEnum {

	FTP_BINARY_UPLOAD("ftp:binary-upload"),
	FTP_FILE_MANAGER("ftp:file-manager"),
	FTP_IMAGE_HUNTER("ftp:image-hunter"),
	
	SMB_BINARY_UPLOAD("smb:binary-upload"),
	SMB_FILE_MANAGER("smb:file-manager"),
	SMB_IMAGE_HUNTER("smb:image-hunter");
	
	protected String key;
	
	private ProviderEnum(String key){
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
}

