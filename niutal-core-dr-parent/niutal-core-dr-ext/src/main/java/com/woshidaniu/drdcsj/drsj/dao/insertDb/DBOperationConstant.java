/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.insertDb;

/**
 * @author xiaokang
 * 常量
 */
public class DBOperationConstant {
	//默认批量操作数量
	public static final int DEFAULT_BATCH_NUMBER = 100;
	
	/**
	 * @author xiaokang
	 * 数据库字段类型
	 */
	public static class DBColumnType{
		
		public static final String VARCHAR2 = "VARCHAR2";
		
		public static final String NUMBER = "NUMBER";
		
		public static final String DATE = "DATE";
		
		public static final String TIMESTAMP = "TIMESTAMP";
		
		public static final String CHAR = "CHAR";
	}
}
