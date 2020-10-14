package com.woshidaniu.workflow.enumobject;

/**
 * 
 * 类描述：业务类型枚举类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-7-12 下午12:37:00
 */
public enum BusinessTypeEnum {
	// 职务聘任（职称评审）业务模块使用
	GJZW_TYPE("高级职务计划类别", "GJZW_TYPE"), // 高级职务计划类别
	ZWPR_TYPE("职务聘任类别", "ZWPR_TYPE"), // 职务聘任类别
	
	// 社会福利模块使用
	SHFL_TYPE("社会福利类别", "SHFL_TYPE"),// 社会福利类别
	
	// 科级定级模块使用
	KJDJ_TYPE("科级定级类别", "KJDJ_TYPE"),// 科级定级类别

	//考勤管理使用
	KQGL_TYPE("考勤管理类别", "KQGL_TYPE"), // 考勤管理类别
	
	// 培训进修模块使用
	PXJX_TYPE("培训进修类别", "PX_TYPE"), // 培训进修类别
	
	// 人才招聘模块业务使用
	BZJH_TYPE("编制计划类别", "BZJH_TYPE"), // 编制计划类别
	RYYP_TYPE("人员应聘类别", "RYYP_TYPE"),// 人员应聘类别
	
	//
	TW_ST_TYPE("团委-社团", "TW_ST_TYPE"),
	TW_HD_TYPE("团委-活动", "TW_HD_TYPE"),
	TW_PY_TYPE("团委-评优", "TW_PY_TYPE");

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private BusinessTypeEnum(String text, String key) {
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
