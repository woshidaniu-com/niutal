package com.woshidaniu.workflow.enumobject;

/**
 * 
 * 类描述：节点类型枚举类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:37:15
 */
public enum NodeTypeEnum {
	START_NODE("起始节点", "START_NODE"), // 起始节点
	NORMAL_NODE("正常节点", "NORMAL_NODE"), // 正常节点
	COMMIT_NODE("申报节点", "COMMIT_NODE"), // 申报节点
	BRANCH_NODE("分支节点", "BRANCH_NODE"), // 分支节点
	MERME_NODE("合并节点", "MERME_NODE"), // 合并节点
	END_NODE("终止节点", "END_NODE");// 终止节点

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private NodeTypeEnum(String text, String key) {
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
