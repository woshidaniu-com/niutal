package com.woshidaniu.workflow.enumobject;

/**
 * 
 * 类描述：任务类型枚举类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:37:47
 */
public enum TaskCategoryEnum {
	PERSON_OPERATE("人工操作", "PERSON_OPERATE"), // 人工操作
	AUTO_EXECUTE("自动执行", "AUTO_EXECUTE"), // 自动执行
	DROP_DOWN_LIST("下拉框", "DROP_DOWN_LIST"), // 下拉框
	INPUT_LIST("输入框", "INPUT_LIST");// 输入框

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private TaskCategoryEnum(String text, String key) {
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
