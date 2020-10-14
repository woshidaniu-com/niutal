package com.woshidaniu.workflow.enumobject;

/**
 * 
 * 类描述：工作审核任务名称枚举类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:38:01
 */
public enum TaskNameEnum {
	//**========职务聘任模块需要的相关任务========**/
	
	CON_THOUGHT_MORAL("思想道德情况", "CON_THOUGHT_MORAL"),// 思想道德情况
	CON_TEACHING("教学情况", "CON_TEACHING"),// 教学情况
	
	//**========人才招聘模块需要的相关任务========**/
	
	LIST_WRIT_EXAM("生成笔试名单", "LIST_WRIT_EXAM"),// 生成笔试名单	
	LIST_ORAL_EXAM("生成面试名单", "LIST_ORAL_EXAM"), // 生成面试名单		
	LIST_POST_PRACTICE("生成岗位实践名单", "LIST_POST_PRACTICE"),// 生成岗位实践名单	
	LIST_WANT_EMPLOY("生成拟录用名单", "LIST_WANT_EMPLOY"),// 生成拟录用名单
	LIST_EMPLOY("生成录用名单", "LIST_EMPLOY"),// 生成录用名单
	
	NOTIFY_WRIT_EXAM("发送笔试通知", "NOTIFY_WRIT_EXAM"),// 发送笔试通知
	NOTIFY_ORAL_EXAM("发送面试通知", "NOTIFY_ORAL_EXAM"), // 发送面试通知
	NOTIFY_WANT_EMPLOY("发送拟录用通知", "NOTIFY_WANT_EMPLOY"),// 发送拟录用通知
	NOTIFY_POST_PRACTICE("发送岗位实践通知", "NOTIFY_POST_PRACTICE"),// 发送岗位实践通知
	NOTIFY_EMPLOY("发送录用通知", "NOTIFY_EMPLOY"),// 发送录用通知
	
	EDICT_WANT_EMPLOY("生成拟录用公示", "EDICT_WANT_EMPLOY"),// 生成拟录用公示
	EDICT_EMPLOY("生成录用公示", "EDICT_EMPLOY"),// 生成录用公示
	
	PERSON_ENTER_FILE("人才入库", "PERSON_ENTER_FILE"),// 人才入库
	
	SCORE_WRIT_EXAM("填写笔试成绩", "SCORE_WRIT_EXAM"),// 填写笔试成绩
	CON_ORAL_EXAM("面试情况", "CON_ORAL_EXAM"),// 面试情况
	CON_TRY_SPEAK("试讲情况", "CON_TRY_SPEAK"),// 试讲情况
	CON_PRACTICE("实践情况", "CON_PRACTICE"),// 实践情况
	CON_DELIBERATE("评议情况", "CON_DELIBERATE");// 评议情况

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private TaskNameEnum(String text, String key) {
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
