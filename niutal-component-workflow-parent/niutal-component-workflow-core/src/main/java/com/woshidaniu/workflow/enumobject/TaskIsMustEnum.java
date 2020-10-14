package com.woshidaniu.workflow.enumobject;

/**
 * 
 * 类描述：工作审核节点是否必须执行状态枚举类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:37:54
 */
public enum TaskIsMustEnum {
	YES("是", "Y"), // 必须执行
	NO("否", "N");// 不必须执行

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private TaskIsMustEnum(String text, String key) {
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
