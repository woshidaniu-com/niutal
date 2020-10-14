/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.service;

/**
 * 导出处理编号管理
 * 命名规则：系统名称_模块编号_功能点
 * @author Penghui.Qu
 */
public interface ExportID {
	
	/**
	 * 就业系统
	 */
	//就业单位信息导出
	public static final String JOB_N04_COMPANY = "job_n04_company";
	//毕业去向审核导出
	public static final String JOB_N030301_BYQXSH = "job_n030301_byqxsh";
	//毕业去向审核导出
	public static final String JOB_N030306_BYQXCX = "job_n030306_byqxcx";
	//去向变更审核导出
	public static final String JOB_N030302_QXBGSH = "job_n030302_qxbgsh";
	//协议书遗失登记导出
	public static final String JOB_N030303_XYSYSDJ = "job_n030303_xysysdj";
	//协议书发放登记导出
	public static final String JOB_N030304_XYSFFDJ = "job_n030304_xysffdj";
	//离校信息审核导出
	public static final String JOB_N030401_LXXXSH = "job_n030401_lxxxsh";
	//就业率统计学历层次
	public static final String JOB_N030601_JYLTJXLCC = "job_n030601_jyltjxlcc";
	//就业率统计学院
	public static final String JOB_N030602_JYLTJXY = "job_n030602_jyltjxy";
	//就业率统计专业
	public static final String JOB_N030603_JYLTJZY = "job_n030603_jyltjzy";
	//就业率统计学院专业
	public static final String JOB_N030604_JYLTJXYZY = "job_n030604_jyltjxyzy";
	//毕业去向统计就业薪资
	public static final String JOB_N030501_BYQXJYXZ = "job_n030501_byqxjyxz";
	//毕业去向统计出国奖学金
	public static final String JOB_N030502_BYQXCGJXJ = "job_n030502_byqxcgjxj";
	//招聘信息供需比
	public static final String JOB_N040601_ZPXXGXB = "job_n040601_zpxxgxb";
	//招聘信息招聘对象
	public static final String JOB_N040602_ZPXXZPDX = "job_n040602_zpxxzpdx";
	//招聘信息职位类别
	public static final String JOB_N040603_ZPXXZWLB = "job_n040603_zpxxzwlb";
	//招聘信息工作地点
	public static final String JOB_N040604_ZPXXGZDD = "job_n040604_zpxxgzdd";
	//招聘信息招聘学院专业
	public static final String JOB_N040605_ZPXXZPXYZY = "job_n040605_zpxxzpxyzy";
	//招聘会信息单位申请数
	public static final String JOB_N040606_ZPHXXDWSQS = "job_n040606_zphxxdwsqs";
	//就业系统学生登录次数
	public static final String JOB_N030503_JYXTXSDLCS = "job_n030503_jyxtxsdlcs";
	//签约单位录用人数
	public static final String JOB_N030504_QYDWLYRS = "job_n030504_qydwlyrs";
	//生源信息导出
	public static final String JOB_N030101_SYXXGL = "job_n030101_syxxgl";
	//档案寄送信息
	public static final String JOB_N030401_DAJSGL = "job_n030401_dajsgl";
	//档案清单信息
	public static final String JOB_N030402_DAQDGL = "job_n030402_daqdgl";
	//学生档案清单信息
	public static final String JOB_N030403_XSDAQDGL = "job_n030403_xsdaqdgl";
	//校友信息导出
	public static final String JOB_N030101_XYXXGL = "job_n030101_xyxxgl";
	//查询招聘会审核
	public static final String JOB_N040303_CXZPHSH = "job_n040303_cxzphsh";
	
	/**
	 * 就业-四川机电
	 */
	//档案寄送统计
	public static final String JOB_N030401_DAJSTJ = "job_n030401_dajstj";
	
	/**
	 * 就业-云南师范
	 */
	public static final String JOB_N038001_YNSFZZCY="job_n038001_ynsfzzcy";
	
	/**
	 * 北京电影学院招生系统
	 */
	//考生信息
	public static final String ZS_N020102_KSXXGL = "zs_n020102_ksxxgl";
	
	//监考人员信息
	public static final String ZS_N020102_JKRYGL = "zs_n020201_jkrygl";
	
	//监考信息
	public static final String ZS_N020202_JKXXGL = "zs_n020202_jkxxgl";
	
	//监考费用统计
	public static final String ZS_N020203_JKFYTJ = "zs_n020203_jkfytj";
	
	//高考成绩
	public static final String ZS_N020102_GKCJ = "zs_n020102_ksgkcj";
	
	//考生录取
	public static final String ZS_N020102_KSLQ = "zs_n020102_kslq";
	
	/**
	 * 学工-团委
	 */
	//经费出入账汇总表
    public static final String TW_N060603_JFCRZHZ = "tw_n060603_jfcrzhz";
    
    /**
     * 支付平台
     */
	public static final String ZFPT_N020103_DDXXGL = "zfpt_n020103_ddxxgl";
}