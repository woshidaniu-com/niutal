package com.woshidaniu.file.exception;

public class UploadException extends Exception {

	private static final long serialVersionUID = -1099652087275146181L;

	public UploadException(){
		super();
	}
	
	public UploadException(String msg){
		super(msg);
	}

	public UploadException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UploadException(Throwable arg0) {
		super(arg0);
	}
	
}
