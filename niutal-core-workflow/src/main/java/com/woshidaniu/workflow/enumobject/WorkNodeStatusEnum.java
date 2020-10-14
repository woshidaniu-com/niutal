package com.woshidaniu.workflow.enumobject;

/**
 * 
 * 类描述：工作审核节点审核状态枚举类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:38:17
 */
public enum WorkNodeStatusEnum {
	INITAIL("未上报", "INITAIL"), // 初始化
	WAIT_AUDITING("待审核", "WAIT_AUDITING"), // 待审核
	IN_AUDITING("审核中", "IN_AUDITING"), // 待审核
	PASS_AUDITING("审核通过", "PASS_AUDITING"), // 审核通过
	NO_PASS_AUDITING("审核不通过", "NO_PASS_AUDITING"), // 审核不通过
	CANCEL_AUDITING("撤销", "CANCEL_AUDITING"), // 撤销
	RETURN_AUDITING("退回", "RETURN_AUDITING");// 退回

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private WorkNodeStatusEnum(String text, String key) {
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
