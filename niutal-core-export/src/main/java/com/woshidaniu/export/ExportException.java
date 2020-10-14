/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export;

/**
 * @类名 ExportException.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年2月3日 下午1:48:32
 * @功能描述 导出异常
 */
public class ExportException extends RuntimeException {

	private static final long serialVersionUID = 544837986207223578L;

	public ExportException() {
		super();
	}

	public ExportException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExportException(String message) {
		super(message);
	}

	public ExportException(Throwable cause) {
		super(cause);
	}
}