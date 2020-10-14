/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.global;

/**
 * 
 *@类名称 : GlobalXtszx.java
 *@类描述 ：系统设置常量
 *@创建人 ：majun
 *@创建时间 ：2014-6-24 下午08:03:20
 *@修改人 ：kangzhidong
 *@修改时间 ：Mar 21, 2016 12:00:40 PM
 *@修改描述 ：
 *@版本号 :v1.0
 */
public abstract class GlobalXtszx {
	
	/**
	 * 【防恶意刷新】过滤器是否开启
	 */
	public static String REFRESH_ON = "REFRESH_ON";
	/**
	 * 认定恶意刷新【周期时间】
	 */
	public static String REFRESH_INTERVAL = "REFRESH_INTERVAL";
	/**
	 * 认定恶意刷新【同一URL周期内刷新的次数】
	 */
	public static String REFRESH_COUNT = "REFRESH_COUNT";
	/**
	 * 当前学年码
	 */
	public static String DQXNM = "DQXNM";
	/**
	 * 当前学期
	 */
	public static String DQXQM = "DQXQM";
	/**
	 * 照片大小
	 */
	public static String ZPDX = "ZPDX";
	/***
	 * 照片分辨率
	 */
	public static String ZPFBL = "ZPFBL";
	/***
	 * 照片像素要求
	 */
	public static String ZPXSYQ = "ZPXSYQ";
	/***
	 * 照片格式
	 */
	public static String ZPGE = "ZPGE";
	/**
	 * 当前年度
	 */
	public static String DQND = "DQND";
	
	/**
	 * 判断评价学年
	 */
	@Deprecated
	public static String PDPJXNM = "PDPJXNM";
	/**
	 * 判断评价学期
	 */
	@Deprecated
	public static String PDPJXQM = "PDPJXQM";
	/**
	 * 选课学年
	 */
	@Deprecated
	public static String XKXNM = "XKXNM";
	/**
	 *选课学期
	 */
	@Deprecated
	public static String XKXQM = "XKXQM";
	/**
	 *选课来源
	 */
	@Deprecated
	public static String XKLY = "XKLY";
	/**
	 *重修选课报名项目
	 */
	@Deprecated
	public static String CXXKBMXM = "CXXKBMXM";
	/**
	 *重修报名(重修选课)数据来源
	 */
	@Deprecated
	public static String CXXKBMLY = "CXXKBMLY";
	/**
	 *重修报名资格开关
	 */
	@Deprecated
	public static String CXBMZGKG = "CXBMZGKG";
	/**
	 *成绩录入学年
	 */
	@Deprecated
	public static String CJLRXN = "CJLRXN";
	/**
	 *成绩录入学期
	 */
	@Deprecated
	public static String CJLRXQ = "CJLRXQ";
	/**
	 *成绩分项提交后能否修改
	 */
	@Deprecated
	public static String CJFXNFXG = "CJFXNFXG";
	/**
	 *学生成绩查询控制', '成绩查询结果控制
	 *（0：不限制成绩查询，1：仅限制【正考成绩录入学年学期】成绩查询，2：仅限制【补考成绩录入学年学期】成绩查询，3：同时限制【补考成绩录入学年学期】和【正考成绩录入学年】成绩查询）
	 */
	@Deprecated
	public static String XSCJCXKZ = "XSCJCXKZ";
	/**
	 *成绩合成后总评成绩保留位数
	 */
	@Deprecated
	public static String ZPCJBLWS = "ZPCJBLWS";
	/**
	 *缓考总评成绩计算方式
	 */
	@Deprecated
	public static String HKZPCJJSFS = "HKZPCJJSFS";
	/**
	 *成绩录入密码初始化方式
	 */
	@Deprecated
	public static String CJLRMMCSHFS = "CJLRMMCSHFS";
	/**
	 *补考成绩录入学年
	 */
	@Deprecated
	public static String BKCJLRXN = "BKCJLRXN";
	/**
	 *补考成绩录入学期
	 */
	@Deprecated
	public static String BKCJLRXQ = "BKCJLRXQ";
	/**
	 *阶段性成绩保留位数
	 */
	@Deprecated
	public static String JDXCJBLWS = "JDXCJBLWS";
	/**
	 *课程成绩录入时，教师能否改变录入级制
	 */
	@Deprecated
	public static String FGCGLKCJZXG = "FGCGLKCJZXG";
	/**
	 *场地借用时间
	 */
	@Deprecated
	public static String CDJYSJ = "CDJYSJ";
	/**
	 *默认周数
	 */
	@Deprecated
	public static String MRZS = "MRZS";
	/**
	 * 周学时是否可修改
	 */
	@Deprecated
	public static String ZHXSSFXG = "ZHXSSFXG";
	/**
	 * 是否跨校区合班
	 */
	@Deprecated
	public static String SFKXQHB = "SFKXQHB";
	/**
	 * 任务合班控制项
	 */
	@Deprecated
	public static String RWHBKZX = "RWHBKZX";
	/**
	 * 判断培养方案和执行计划课程是否需要审核
	 */
	@Deprecated
	public static String SFKCSH = "SFKCSH";
	/**
	 * 是否开启人员统一注册接口
	 */
	@Deprecated
	public static String SFKQRYTYZCJK = "SFKQRYTYZCJK";
	/**
	 * 课程负责人是否自动进入上课资格库
	 */
	@Deprecated
	public static String ZDKCRKZG = "ZDKCRKZG";
	/**
	 *教学日历录入方式
	 */
	@Deprecated
	public static String JXRLRLFS = "JXRLRLFS";
	/**
	 *调课允许调课学时标志
	 */
	@Deprecated
	public static String TKYXTKXS = "TKYXTKXS";
	/**
	 * 是否超学时
	 */
	@Deprecated
	public static String SFCXS = "SFCXS";
	/**
	 * 是否允许修改排课教师
	 */
	@Deprecated
	public static String SFXGPKJS = "SFXGPKJS";
	/**
	 * 排考试学年码
	 */
	@Deprecated
	public static String PKSXNM = "PKSXNM";
	/**
	 * 排考试学期码
	 */
	@Deprecated
	public static String PKSXQM = "PKSXQM";
	/**
	 *学分节点显示
	 */
	@Deprecated
	public static String XFJDXS = "XFJDXS";
	/**
	 *培养方案课程类别显示
	 */
	@Deprecated
	public static String PYFAKCLBXS = "PYFAKCLBXS";
	/**
	 * 默认考试周
	 */
	@Deprecated
	public static String MRKSZ = "MRKSZ";
	/**
	 * 教师是否可选分配方案学时
	 */
	@Deprecated
	public static String SFFPZHXS = "SFFPZHXS";
	/**
	 * 多教师合并上课录入次数设置
	 */
	@Deprecated
	public static String JXRLRLJSSZ = "JXRLRLJSSZ";
	/**
	 * 执行计划学时
	 */
	@Deprecated
	public static String ZXJHXS = "ZXJHXS";
	/**
	 * 教师自选指标最大个数
	 */
	@Deprecated
	public static String JSZXZBZDGS = "JSZXZBZDGS";
	/**
	 *课程成绩录入时，教师能否改变分项录入级制
	 */
	@Deprecated
	public static String CJFXJZXG = "CJFXJZXG";
	/**
	 *场地借用单打印控制，1提交后就可以打印；2审核通过后才可以打印
	 */
	@Deprecated
	public static String CDJYDDYKZ = "CDJYDDYKZ";
	/**
	 *设置监考安排的学院;0:开课学院<默认>;1:【派监考学院】功能分派的学院
	 */
	@Deprecated
	public static String JKAPXYSZ = "JKAPXYSZ";
	/**
	 *排考方式页签控制;控制【考试地点安排】排考方式页签，可多选
	 */
	@Deprecated
	public static String PKFSYQKZ = "PKFSYQKZ";
	/**
	 *考场人数安排算法设置
	 */
	@Deprecated
	public static String KCRSAPSFSZ = "KCRSAPSFSZ";
	/**
	 *补缓考统一录入标示
	 */
	@Deprecated
	public static String BHKTYLR = "BHKTYLR";
	/**
	 预选学年
	 */
	@Deprecated
	public static String YXXNM = "YXXNM";
	/**
	 * 预选学期
	 */
	@Deprecated
	public static String YXXQM = "YXXQM";
	
}
