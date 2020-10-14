package com.woshidaniu.workflow.enumobject;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 类描述：代办事宜业务类型枚举类
 * 
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-5-13 上午09:36:05
 */
public enum PendingAffairBusinessTypeEnum {

	// 【职务聘任】模块使用
	JH_GJZW("<span class='blue'>【职务聘任】</span>您有 <B>高级职务计划审核</B> 事宜需要处理！", "JH_GJZW"), // 高级职务计划审核
	PS_ZWRD("<span class='blue'>【职务聘任】</span>您有 <B>职务认定审核</B> 事宜需要处理！", "PS_ZWRD"), // 职务认定审核
	PS_JSZJZWYS("<span class='blue'>【职务聘任】</span>您有 <B>晋升中级职务预审核</B> 事宜需要处理！", "PS_JSZJZWYS"), // 晋升中级职务预审核
	PS_JSZJZW("<span class='blue'>【职务聘任】</span>您有 <B>晋升中级职务审核</B> 事宜需要处理！", "PS_JSZJZW"), // 晋升中级职务审核
	PS_JSGJZWYS("<span class='blue'>【职务聘任】</span>您有 <B>晋升高级职务预审核</B> 事宜需要处理！", "PS_JSGJZWYS"), // 晋升高级职务预审核
	PS_JSGJZW("<span class='blue'>【职务聘任】</span>您有 <B>晋升高级职务审核</B> 事宜需要处理！", "PS_JSGJZW"), // 晋升高级职务审核
	
	PS_WKFGYS("<span class='blue'>【职务聘任】</span>您有 <B>晋升文科副高职务预审核</B> 事宜需要处理！", "PS_WKFGYS"), // 晋升文科副高职务预审核
	PS_LKFGYS("<span class='blue'>【职务聘任】</span>您有 <B>晋升理科副高职务预审核</B> 事宜需要处理！", "PS_LKFGYS"), // 晋升理科副高职务预审核
	PS_WKZGYS("<span class='blue'>【职务聘任】</span>您有 <B>晋升文科正高职务预审核</B> 事宜需要处理！", "PS_WKZGYS"), // 晋升文科正高职务预审核
	PS_LKZGYS("<span class='blue'>【职务聘任】</span>您有 <B>晋升理科正高职务预审核</B> 事宜需要处理！", "PS_LKZGYS"), // 晋升理科正高职务预审核

	// 【社会福利】模块使用
	FL_TQJ("<span class='blue'>【社会福利】</span>您有 <B>探亲假审核</B> 事宜需要处理！", "FL_TQJ"), // 探亲假审核
	FL_SZFXJ("<span class='blue'>【社会福利】</span>您有 <B>丧葬抚恤金审核</B> 事宜需要处理！", "FL_SZFXJ"), // 丧葬抚恤金审核
	FL_ZGDBBZ("<span class='blue'>【社会福利】</span>您有 <B>职工大病补助审核</B> 事宜需要处理！", "FL_ZGDBBZ"), // 职工大病补助审核
	FL_DSZNFY("<span class='blue'>【社会福利】</span>您有 <B>独生子女费用审核</B> 事宜需要处理！", "FL_DSZNFY"), // 独生子女费用审核
	FL_TFBX("<span class='blue'>【社会福利】</span>您有 <B>托费报销审核</B> 事宜需要处理！", "FL_TFBX"), // 托费报销审核

	// 【科级定级】模块使用
	KJ_KJDJ("<span class='blue'>【科级定级】</span>您有 <B>科级定级审核</B> 事宜需要处理！", "KJ_KJDJ"), // 科级定级审核

	// 【个人信息】模块使用
	SH_GRXX("<span class='blue'>【个人信息】</span>您有 <B>个人信息审核</B> 事宜需要处理！", "SH_GRXX"), // 个人信息审核
	
	// 【培训进修】模块使用
	PX_PXJX("<span class='blue'>【培训进修】</span>您有 <B>培训进修审核</B> 事宜需要处理！", "PX_PXJX"), // 培训进修审核
	
	// 【考勤管理】模块使用
	KQ_YDKQ("<span class='blue'>【考勤管理】</span>您有 <B>月度考勤审核</B> 事宜需要处理！", "KQ_YDKQ"), // 月度考勤审核
	
	// 【人才招聘】模块使用
	JH_BZJH("<span class='blue'>【人才招聘】</span>您有 <B>编制计划审核</B> 事宜需要处理！", "JH_BZJH"), // 编制计划审核
	YP_JXKY("<span class='blue'>【人才招聘】</span>您有 <B>教学科研类人员应聘审核</B> 事宜需要处理！", "YP_JXKY"), // 教学科研类人员应聘审核
	YP_FDY("<span class='blue'>【人才招聘】</span>您有 <B>辅导员类人员应聘审核</B> 事宜需要处理！", "YP_FDY"), // 辅导员类人员应聘审核
	YP_QT("<span class='blue'>【人才招聘】</span>您有 <B>其他类人员审核</B> 事宜需要处理！", "YP_QT"); // 其他类人员审核

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;
	private static final Map<String, String> map = new HashMap<String, String>();

	static {
		for (PendingAffairBusinessTypeEnum s : EnumSet.allOf(PendingAffairBusinessTypeEnum.class)) {
			map.put(s.getKey(), s.getText());
		}
	}

	private PendingAffairBusinessTypeEnum(String text, String key) {
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

	/**
	 * @return map : return the property map.
	 */
	
	public static Map<String, String> getMap() {
		return map;
	}
}
