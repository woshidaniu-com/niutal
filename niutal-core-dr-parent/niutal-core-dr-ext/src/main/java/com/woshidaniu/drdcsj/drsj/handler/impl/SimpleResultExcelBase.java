/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

class SimpleResultExcelBase {
	//默认列宽度
	protected static Integer defaultColumnSize = Integer.valueOf(25);
	//错误提示列宽度
	protected static Integer defaultErrorTipColumnSize = Integer.valueOf(35);
	
	protected static final String DefaultSheetName = "import";
	
	protected static final String ErrorColumnTips = "错误汇报(若使用此文件继续导入，请删除此列)";
}
