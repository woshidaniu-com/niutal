package com.woshidaniu.designer.dao.entities;

import java.util.List;

import com.woshidaniu.common.query.ModelBase;
import com.woshidaniu.entities.PairModel;

/**
 * 
 *@类名称:DesignZdybdZddyModel.java
 *@类描述：自定义表单：字段定义信息对象Model
 *@创建人：kangzhidong
 *@创建时间：Oct 27, 2015 10:20:02 AM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignZdybdZddyModel extends ModelBase {

	/* ===============自定义表单:字段定义信息======================== */

	protected String type;
	protected String zddyid;// 字段定义id
	protected String fjzddyid;// 父级字段定义id :解决多层嵌套问题
	protected String flszid;// 分类设置id
	protected String zd;// 字段
	protected String bdmc;// 表单名称
	protected String zdsm;// 字段说明
	/*
	 * 字段类型 
	 * 0:仅显示 
	 * 1:隐藏域
	 * 2:下拉框 
	 * 3:单选框 
	 * 4:复选框 
	 * 5:文本域 
	 * 6:文件 
	 * 11:字符文本框 
	 * 13:日期文本框
	 * 21:照片
	 * 22:省市县选择 
	 * 23:链接 
	 * 24;学院专业班级 
	 * 98:元素分组
	 * 99:功能自定义
	 */
	protected String zdlx;

	/*
	 * 设置类型 当“字段类型”为：2:下拉框、3:单选框、4:复选框三种类型时，来源类型字段有效 
	 * 1.固定值，格式为：1:男,2:女
	 * 2:数据库取值,“表名:代码,名称”, 
	 * 3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型； 字段类型为输入框时，做为验证类型配置：
	 * 11:数字、字母、下划线 
	 * 12:仅字母 
	 * 13:仅数字 
	 * 14:小数 
	 * 21:邮箱 
	 * 22:电话号码
	 * 23:手机号码
	 * 24:身份证号 
	 * 25:邮编 
	 * 99:正则表达式
	 */
	protected String szlx;
	protected String yzlx;// 验证类型
	protected String yzlx_temp;
	
	// 设置值 该字段与“字段类型”、“来源类型”配合使用

	/*
	 * 设置类型 当“字段类型”为：2:下拉框、3:单选框、4:复选框三种类型时，来源类型字段有效 
	 * 1.固定值，格式为：1:男,2:女
	 * 2:数据库取值,“表名:代码,名称”, 
	 * 3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型； 字段类型为：
	 * 5:文本域，格式为：“x,y”，x指宽度，y指高度 11:普通文本框，格式为：“x,y”，x指最小字符长度，y指最大字符长度
	 * 13:日期文本框，格式为：yyyyMMdd等 
	 * 21:照片，格式为：1:左上角显示，2:右上角显示 
	 * 22:省市县选择 
	 * 23:链接，格式为：链接url
	 * 99:功能自定义，格式为：定义代码，与界面中div的id关联
	 */
	protected String szz;
	protected String zbwz;// 坐标位置
	protected String szls;// 所占列数
	protected String kdbl;// 宽度比例
	protected String kdbl1;// 宽度比例
	protected String kdbl2;// 宽度比例
	protected String kdbl3;// 宽度比例
	protected String kdbl4;// 宽度比例
	
	protected String sfbt;// 是否必填： 1:必须填写,0:可不填写
	protected String sfkg;// 是否可修改：1:可修改;0:不可修改
	protected String sfsh;// 是否要审核：1:要审核;0:不审核

	protected String dataJson;//
	protected String lb;//
	protected String gndm;//

	protected String bdms;// 表单模式 1:单条记录模式:2:多条记录模式;3:功能自定义代码

	protected String bdszz;// 表单设置值

	protected String sfkbj;// 是否可编辑：根据数据库各个字段标记状态进行逻辑判断得到当前元素是否可编辑
	//当前分类下定义的字段
	protected List<DesignZdybdZddyModel> zddy_list;
	
	/**
	 * 字段数据值对象
	 */
	protected List<PairModel> data_list;
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the fjzddyid
	 */
	public String getFjzddyid() {
		return fjzddyid;
	}

	/**
	 * @param fjzddyid the fjzddyid to set
	 */
	public void setFjzddyid(String fjzddyid) {
		this.fjzddyid = fjzddyid;
	}

	/**
	 * @return the zddyid
	 */
	public String getZddyid() {
		return zddyid;
	}

	/**
	 * @param zddyid
	 *            the zddyid to set
	 */
	public void setZddyid(String zddyid) {
		this.zddyid = zddyid;
	}

	/**
	 * @return the flszid
	 */
	public String getFlszid() {
		return flszid;
	}

	/**
	 * @param flszid
	 *            the flszid to set
	 */
	public void setFlszid(String flszid) {
		this.flszid = flszid;
	}

	/**
	 * @return the zd
	 */
	public String getZd() {
		return zd;
	}

	/**
	 * @param zd
	 *            the zd to set
	 */
	public void setZd(String zd) {
		this.zd = zd;
	}

	/**
	 * @return the bdmc
	 */
	public String getBdmc() {
		return bdmc;
	}

	/**
	 * @param bdmc
	 *            the bdmc to set
	 */
	public void setBdmc(String bdmc) {
		this.bdmc = bdmc;
	}

	/**
	 * @return the zdsm
	 */
	public String getZdsm() {
		return zdsm;
	}

	/**
	 * @param zdsm
	 *            the zdsm to set
	 */
	public void setZdsm(String zdsm) {
		this.zdsm = zdsm;
	}

	/**
	 * @return the zdlx
	 */
	public String getZdlx() {
		return zdlx;
	}

	/**
	 * @param zdlx
	 *            the zdlx to set
	 */
	public void setZdlx(String zdlx) {
		this.zdlx = zdlx;
	}

	/**
	 * @return the szlx
	 */
	public String getSzlx() {
		return szlx;
	}

	/**
	 * @param szlx
	 *            the szlx to set
	 */
	public void setSzlx(String szlx) {
		this.szlx = szlx;
	}

	/**
	 * @return the yzlx
	 */
	public String getYzlx() {
		return yzlx;
	}

	/**
	 * @param yzlx
	 *            the yzlx to set
	 */
	public void setYzlx(String yzlx) {
		this.yzlx = yzlx;
	}

	/**
	 * @return the szz
	 */
	public String getSzz() {
		return szz;
	}

	/**
	 * @param szz
	 *            the szz to set
	 */
	public void setSzz(String szz) {
		this.szz = szz;
	}

	/**
	 * @return the zbwz
	 */
	public String getZbwz() {
		return zbwz;
	}

	/**
	 * @param zbwz
	 *            the zbwz to set
	 */
	public void setZbwz(String zbwz) {
		this.zbwz = zbwz;
	}

	/**
	 * @return the szls
	 */
	public String getSzls() {
		return szls;
	}

	/**
	 * @param szls
	 *            the szls to set
	 */
	public void setSzls(String szls) {
		this.szls = szls;
	}

	/**
	 * @return the kdbl
	 */
	public String getKdbl() {
		return kdbl;
	}

	/**
	 * @param kdbl
	 *            the kdbl to set
	 */
	public void setKdbl(String kdbl) {
		this.kdbl = kdbl;
	}

	/**
	 * @return the sfbt
	 */
	public String getSfbt() {
		return sfbt;
	}

	/**
	 * @param sfbt
	 *            the sfbt to set
	 */
	public void setSfbt(String sfbt) {
		this.sfbt = sfbt;
	}

	/**
	 * @return the sfkg
	 */
	public String getSfkg() {
		return sfkg;
	}

	/**
	 * @param sfkg
	 *            the sfkg to set
	 */
	public void setSfkg(String sfkg) {
		this.sfkg = sfkg;
	}

	/**
	 * @return the sfsh
	 */
	public String getSfsh() {
		return sfsh;
	}

	/**
	 * @param sfsh
	 *            the sfsh to set
	 */
	public void setSfsh(String sfsh) {
		this.sfsh = sfsh;
	}

	/**
	 * @return the dataJson
	 */
	public String getDataJson() {
		return dataJson;
	}

	/**
	 * @param dataJson
	 *            the dataJson to set
	 */
	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}

	/**
	 * @return the lb
	 */
	public String getLb() {
		return lb;
	}

	/**
	 * @param lb
	 *            the lb to set
	 */
	public void setLb(String lb) {
		this.lb = lb;
	}

	/**
	 * @return the gndm
	 */
	public String getGndm() {
		return gndm;
	}

	/**
	 * @param gndm
	 *            the gndm to set
	 */
	public void setGndm(String gndm) {
		this.gndm = gndm;
	}

	/**
	 * @return the bdms
	 */
	public String getBdms() {
		return bdms;
	}

	/**
	 * @param bdms
	 *            the bdms to set
	 */
	public void setBdms(String bdms) {
		this.bdms = bdms;
	}

	/**
	 * @return the bdszz
	 */
	public String getBdszz() {
		return bdszz;
	}

	/**
	 * @param bdszz
	 *            the bdszz to set
	 */
	public void setBdszz(String bdszz) {
		this.bdszz = bdszz;
	}

	/**
	 * @return the zddy_list
	 */
	public List<DesignZdybdZddyModel> getZddy_list() {
		return zddy_list;
	}

	/**
	 * @param zddyList the zddy_list to set
	 */
	public void setZddy_list(List<DesignZdybdZddyModel> zddyList) {
		zddy_list = zddyList;
	}

	/**
	 * @return the data_list
	 */
	public List<PairModel> getData_list() {
		return data_list;
	}

	/**
	 * @param dataList the data_list to set
	 */
	public void setData_list(List<PairModel> dataList) {
		data_list = dataList;
	}

	/**
	 * @return the kdbl1
	 */
	public String getKdbl1() {
		return kdbl1;
	}

	/**
	 * @param kdbl1 the kdbl1 to set
	 */
	public void setKdbl1(String kdbl1) {
		this.kdbl1 = kdbl1;
	}

	/**
	 * @return the kdbl2
	 */
	public String getKdbl2() {
		return kdbl2;
	}

	/**
	 * @param kdbl2 the kdbl2 to set
	 */
	public void setKdbl2(String kdbl2) {
		this.kdbl2 = kdbl2;
	}

	/**
	 * @return the kdbl3
	 */
	public String getKdbl3() {
		return kdbl3;
	}

	/**
	 * @param kdbl3 the kdbl3 to set
	 */
	public void setKdbl3(String kdbl3) {
		this.kdbl3 = kdbl3;
	}

	/**
	 * @return the kdbl4
	 */
	public String getKdbl4() {
		return kdbl4;
	}

	/**
	 * @param kdbl4 the kdbl4 to set
	 */
	public void setKdbl4(String kdbl4) {
		this.kdbl4 = kdbl4;
	}

	/**
	 * @return the sfkbj
	 */
	public String getSfkbj() {
		return sfkbj;
	}

	/**
	 * @param sfkbj the sfkbj to set
	 */
	public void setSfkbj(String sfkbj) {
		this.sfkbj = sfkbj;
	}

	public String getYzlx_temp() {
		return yzlx_temp;
	}

	public void setYzlx_temp(String yzlx_temp) {
		this.yzlx_temp = yzlx_temp;
	}
	
	
	
}
