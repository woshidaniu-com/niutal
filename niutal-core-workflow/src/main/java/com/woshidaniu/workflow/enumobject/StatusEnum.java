package com.woshidaniu.workflow.enumobject;

/**
 * 
 * 类描述：状态枚举类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:37:40
 */
public enum StatusEnum {
	VALID_STATUS("有效状态", "1"), // 有效状态
	INVALID_STATUS("无效状态", "0");// 无效状态

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private StatusEnum(String text, String key) {
		this.text = text;
		this.key = key;
	}

	/**
	 * 展示文本
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * 代码编号
	 * 
	 * @return
	 */
	public String getKey() {
		return key;
	}

}
