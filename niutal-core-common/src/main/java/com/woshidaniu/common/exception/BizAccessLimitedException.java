package com.woshidaniu.common.exception;

/**
 * @author xiaokang
 * 
 * @描述 业务方法访问受限异常
 *
 */
public class BizAccessLimitedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4354396291611330528L;
	
	/**
	 * 访问受限原因,默认是权限不足
	 */
	private int accessLimitedType;

	public int getAccessLimitedType() {
		return accessLimitedType;
	}

	public void setAccessLimitedType(int accessLimitedType) {
		this.accessLimitedType = accessLimitedType;
	}

	public BizAccessLimitedException(int accessLimitedType) {
		super();
		this.accessLimitedType = accessLimitedType;
	}

	public BizAccessLimitedException() {
		super();
	}	
}
