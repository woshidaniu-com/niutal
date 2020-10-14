/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.enums;


/**
 * @author 		：Penghui.Qu(445)
 * @description	： 问卷状态
 */
public enum QuestionnaireStatus {

	DRAFT("草稿"),
	RUN("运行"), 
	STOP("停止"),
	;
	
	private String text;
	
	QuestionnaireStatus(String text){
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
