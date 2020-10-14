package com.woshidaniu.drdcsj.drsj.comm;

public class ImportDataException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ImportDataException() {
		super();
	}

	public ImportDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ImportDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImportDataException(String message) {
		super(message);
	}

	public ImportDataException(Throwable cause) {
		super(cause);
	}
}
