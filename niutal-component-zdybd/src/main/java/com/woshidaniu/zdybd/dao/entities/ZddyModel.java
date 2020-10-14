package com.woshidaniu.zdybd.dao.entities;

/**
 * 
 * @系统名称: 学生工作管理系统
 * @模块名称: 自定义表单
 * @类功能描述: 字段定义表
 * @作者： ligl
 * @时间： 2013-11-26 下午03:50:38
 * @版本： V1.0
 * @修改记录:
 */
public class ZddyModel implements java.io.Serializable {
	private static final long serialVersionUID = -2924542695182937526L;
	public static String[] REPEAT_ZDDY = { "sfqy", "bdmc", "zdsm", "zdlx",
			"szlx", "yzlx", "szz", "zbwz", "szls", "bkxg", "yxwk", "yxxg",
			"bdkd" };
	private String type;
	private String zddyid;// 字段定义id
	private String flszid;// 分类设置id
	private String zd;// 字段
	private String bdmc;// 表单名称
	private String zdsm;// 字段说明
	/*
	 * 字段类型 0:仅显示 1:隐藏域 2:下拉框 3:单选框 4:复选框 5:文本域 6:文件 11:字符文本框 13:日期文本框 21:照片
	 * 22:省市县选择 23:链接 24;学院专业班级 99:功能自定义
	 */
	private String zdlx;

	/*
	 * 设置类型 当“字段类型”为：2:下拉框、3:单选框、4:复选框三种类型时，来源类型字段有效 1.固定值，格式为：1:男,2:女
	 * 2:数据库取值,“表名:代码,名称”, 3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型；
	 * 字段类型为输入框时，做为验证类型配置： 11:数字、字母、下划线 12:仅字母 13:仅数字 14:小数 21:邮箱 22:电话号码
	 * 23:手机号码 24:身份证号 25:邮编 99:正则表达式
	 */
	private String szlx;
	private String yzlx;// 验证类型
	// 设置值 该字段与“字段类型”、“来源类型”配合使用

	/*
	 * 设置类型 当“字段类型”为：2:下拉框、3:单选框、4:复选框三种类型时，来源类型字段有效 1.固定值，格式为：1:男,2:女
	 * 2:数据库取值,“表名:代码,名称”, 3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型； 字段类型为：
	 * 5:文本域，格式为：“x,y”，x指宽度，y指高度 11:普通文本框，格式为：“x,y”，x指最小字符长度，y指最大字符长度
	 * 13:日期文本框，格式为：yyyyMMdd等 21:照片，格式为：1:左上角显示，2:右上角显示 22:省市县选择 23:链接，格式为：链接url
	 * 99:功能自定义，格式为：定义代码，与界面中div的id关联
	 */
	private String szz;
	private String zbwz;// 坐标位置
	private String szls;// 所占列数
	private String bkxg;// 不可修改 0:不可修改1:可以修改

	private String yxwk;// 允许为空 1:允许为空,0:不允许为空
	private String yxxg;// 允许修改1:允许修改0:不允许修改
	private String bdkd;// 表单宽度

	private String dataJson;//
	private String lb;//
	private String gndm;//

	private String bdms;// 表单模式 1:单条记录模式:2:多条记录模式;3:功能自定义代码
	private String bdszz;// 表单设置值

	private String hccl;// 缓存处理 1:缓存，0或不设置:不缓存。此设置仅对取值类型为：数据库及方法的进行控制,其余全部缓存
	private String zjpz;
	private String xgpz;
	private String ckpz;
	private String shpz;
	private String shpz1;
	private String shpz2;
	private String xszjpz;
	private String xsxgpz;
	private String xsckpz;
	private String qtpz1;
	private String qtpz2;

	private String sfqy;
	private String tslx;//提示类型 提示信息的显示方式，1:在表单后显示，2:在表单下一行显示，其他为显示提示图标，鼠标移上弹层显示

	public ZddyModel() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getZddyid() {
		return zddyid;
	}

	public void setZddyid(String zddyid) {
		this.zddyid = zddyid;
	}

	public String getFlszid() {
		return flszid;
	}

	public void setFlszid(String flszid) {
		this.flszid = flszid;
	}

	public String getZd() {
		return zd;
	}

	public void setZd(String zd) {
		this.zd = zd;
	}

	public String getBdmc() {
		return bdmc;
	}

	public void setBdmc(String bdmc) {
		this.bdmc = bdmc;
	}

	public String getZdsm() {
		return zdsm;
	}

	public void setZdsm(String zdsm) {
		this.zdsm = zdsm;
	}

	public String getZdlx() {
		return zdlx;
	}

	public String getTslx() {
		return tslx;
	}

	public void setTslx(String tslx) {
		this.tslx = tslx;
	}

	public void setZdlx(String zdlx) {
		this.zdlx = zdlx;
	}

	public String getSzlx() {
		return szlx;
	}

	public void setSzlx(String szlx) {
		this.szlx = szlx;
	}

	public String getYzlx() {
		return yzlx;
	}

	public void setYzlx(String yzlx) {
		this.yzlx = yzlx;
	}

	public String getSzz() {
		return szz;
	}

	public void setSzz(String szz) {
		this.szz = szz;
	}

	public String getZbwz() {
		return zbwz;
	}

	public void setZbwz(String zbwz) {
		this.zbwz = zbwz;
	}

	public String getSzls() {
		return szls;
	}

	public void setSzls(String szls) {
		this.szls = szls;
	}

	public String getBkxg() {
		return bkxg;
	}

	public void setBkxg(String bkxg) {
		this.bkxg = bkxg;
	}

	public String getYxwk() {
		return yxwk;
	}

	public void setYxwk(String yxwk) {
		this.yxwk = yxwk;
	}

	public String getYxxg() {
		return yxxg;
	}

	public void setYxxg(String yxxg) {
		this.yxxg = yxxg;
	}

	public String getBdkd() {
		return bdkd;
	}

	public void setBdkd(String bdkd) {
		this.bdkd = bdkd;
	}

	public String getDataJson() {
		return dataJson;
	}

	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}

	public String getLb() {
		return lb;
	}

	public void setLb(String lb) {
		this.lb = lb;
	}

	public String getGndm() {
		return gndm;
	}

	public void setGndm(String gndm) {
		this.gndm = gndm;
	}

	public String getBdms() {
		return bdms;
	}

	public void setBdms(String bdms) {
		this.bdms = bdms;
	}

	public String getBdszz() {
		return bdszz;
	}

	public void setBdszz(String bdszz) {
		this.bdszz = bdszz;
	}

	public String getHccl() {
		return hccl;
	}

	public void setHccl(String hccl) {
		this.hccl = hccl;
	}

	public String getZjpz() {
		return zjpz;
	}

	public void setZjpz(String zjpz) {
		this.zjpz = zjpz;
	}

	public String getXgpz() {
		return xgpz;
	}

	public void setXgpz(String xgpz) {
		this.xgpz = xgpz;
	}

	public String getCkpz() {
		return ckpz;
	}

	public void setCkpz(String ckpz) {
		this.ckpz = ckpz;
	}

	public String getShpz() {
		return shpz;
	}

	public void setShpz(String shpz) {
		this.shpz = shpz;
	}

	public String getQtpz1() {
		return qtpz1;
	}

	public void setQtpz1(String qtpz1) {
		this.qtpz1 = qtpz1;
	}

	public String getQtpz2() {
		return qtpz2;
	}

	public void setQtpz2(String qtpz2) {
		this.qtpz2 = qtpz2;
	}

	public String getSfqy() {
		return sfqy;
	}

	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}

	public String getXszjpz() {
		return xszjpz;
	}

	public void setXszjpz(String xszjpz) {
		this.xszjpz = xszjpz;
	}

	public String getXsxgpz() {
		return xsxgpz;
	}

	public void setXsxgpz(String xsxgpz) {
		this.xsxgpz = xsxgpz;
	}

	public String getXsckpz() {
		return xsckpz;
	}

	public void setXsckpz(String xsckpz) {
		this.xsckpz = xsckpz;
	}

	public String getShpz1() {
		return shpz1;
	}

	public void setShpz1(String shpz1) {
		this.shpz1 = shpz1;
	}

	public String getShpz2() {
		return shpz2;
	}

	public void setShpz2(String shpz2) {
		this.shpz2 = shpz2;
	}


}
