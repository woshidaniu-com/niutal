package com.woshidaniu.workflow.enumobject;

/**
 * 
 * 类描述：日志类型枚举类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:37:08
 */
public enum LogTypeEnum {
	NODE_LOG("节点日志", "NODE_LOG"), // 节点日志
	TASK_LOG("任务日志", "TASK_LOG");// 任务日志

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private LogTypeEnum(String text, String key) {
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
