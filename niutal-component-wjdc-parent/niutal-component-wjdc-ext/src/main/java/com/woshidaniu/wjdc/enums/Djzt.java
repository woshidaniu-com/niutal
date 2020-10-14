/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.enums;

/**
 * @author 		：康康（1571）
 * @description	： 答卷状态
 */
public enum Djzt {
	
	unstart("0","未开始"),
	doing("1","答卷中"),
	fininsh("2","完成");

	private String flag;
	private String text;
	
	Djzt(String flag,String text){
		this.flag = flag;
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public String getFlag() {
		return flag;
	}
}
