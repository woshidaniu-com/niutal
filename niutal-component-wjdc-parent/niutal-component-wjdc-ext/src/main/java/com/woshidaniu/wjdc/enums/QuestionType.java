/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.enums;

/**
 * @author Penghui.Qu(445)
 * 功能实现
 * 
 * @author ：康康（1571）
 * 整理，优化，扩展
 * 
 * */
public enum QuestionType {

	DESCRIBE(0,"描述"),
	RADIO(1,"单选"),
	RADIO_GROUP(2,"单选组合"),
	MULTI(3,"多选"),
	MULTI_GROUP(4,"多选组合"),
	TEXT(5,"文本")
	;
	
	private int key;
	private String label;
	
	
	QuestionType(int key,String label){
		this.key = key;
		this.label = label;
	}

	public int getKey() {
		return key;
	}
	
	public String getLabel() {
		return label;
	}

	public static QuestionType getTypeByKey(int key){
		QuestionType[] types = QuestionType.values();
		
		for (QuestionType type : types){
			if (type.getKey() == key) {
				return type;
			}
		}
		return null;
	}
	
}
