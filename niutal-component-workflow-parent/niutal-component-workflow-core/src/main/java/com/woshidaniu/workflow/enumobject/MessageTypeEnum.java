package com.woshidaniu.workflow.enumobject;

/**
 * 
 * 类描述：消息发送类型
 *
 * @version: 1.0
 * @author: jiangyy
 * @since: 2018-2-25 下午14:37:15
 */
public enum MessageTypeEnum {
	AUDITTING_ROLE("发送到审批流审核过程(按角色)", "0"), 	//发送到审批流审核过程(按角色)
	AUDITTING_PERSON("发送到审批流审核过程(按个人)", "1"), 	//发送到审批流审核过程(按个人)
	AUDITTED_PERSON("发送到申请人(比如流程结束)", "2");		//发送到申请人(比如流程结束)
	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private MessageTypeEnum(String text, String key) {
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
