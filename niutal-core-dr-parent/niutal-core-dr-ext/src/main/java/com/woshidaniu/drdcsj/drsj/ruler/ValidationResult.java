/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler;

import com.woshidaniu.drdcsj.drsj.comm.ImportErrorMessage;

/**
 * @author zhidong.kang
 * 验证结果
 */
public final class ValidationResult {

	protected ImportErrorMessage errorMessages;

	protected int totalCount = 0;
	
	protected int errorCount =  0 ;
	
	protected int insertCount = 0;
	
	protected int updateCount = 0;
	
	private ValidationResult(ImportErrorMessage errorMessages) {
		this(errorMessages, 0);
	}

	public ValidationResult(ImportErrorMessage errorMessages, int totalCount) {
		this.errorMessages = errorMessages;
		this.totalCount = totalCount;
		if(this.errorMessages == null){
			errorCount = this.errorMessages.size();
		}
	}
	
	public ValidationResult(ImportErrorMessage errorMessages, int totalCount, int insertCount, int updateCount) {
		this(errorMessages, totalCount);
		this.insertCount = insertCount;
		this.updateCount = updateCount;
	}

	public static final ValidationResult create(ImportErrorMessage errorMessages) {
		return new ValidationResult(errorMessages);
	}
	
	public static final ValidationResult create(ImportErrorMessage errorMessages, int totalCount) {
		return new ValidationResult(errorMessages, totalCount);
	}
	
	public static final ValidationResult create(ImportErrorMessage errorMessages, int totalCount, int insertCount, int updateCount) {
		return new ValidationResult(errorMessages, totalCount, insertCount, updateCount);
	}

	public final void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}
	/**
	 * 是否有错误
	 */
	public final boolean hasError() {
		return errorMessages == null ? false : errorMessages.size() > 0;
	}
	/**
	 * 数据总条数
	 * @return
	 */
	public final int totalCount(){
		return totalCount;
	}
	/**
	 * 错误条数
	 * @return
	 */
	public final int errorCount(){
		return errorCount;
	}
	/**
	 * 插入条数
	 * @return
	 */
	public final int insertCount(){
		return insertCount;
	}
	/**
	 * 更新条数
	 * @return
	 */
	public final int updateCount(){
		return updateCount;
	}
	
	/**
	 * 获取错误信息
	 * @return
	 */
	public final ImportErrorMessage errorMessage(){
		return errorMessages;
	}
	
	public void setErrorMessages(ImportErrorMessage errorMessages) {
		this.errorMessages = errorMessages;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public void setInsertCount(int insertCount) {
		this.insertCount = insertCount;
	}

	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}

	@Override
	public String toString() {
		return "ValidationResult [errorMessages=" + errorMessages + ", totalCount=" + totalCount + ", errorCount="
				+ errorCount + ", insertCount=" + insertCount + ", updateCount=" + updateCount + "]";
	}
}
