/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 982 张昌路
 */
public class DrlpzModel{
	//未设置是否必填标志
	public static final int SFBT_NOT_SET_FLAG = 0x00000000;
	//是否必填，当插入时
	public static final int SFBT_ON_INSERT_FLAG = 0x00000001;
	//是否必填，当更新时
	public static final int SFBT_ON_UPDATE_FLAG = 0x00000004;
	
	// drlpzid varchar2(32) 32 TRUE FALSE TRUE
	private String drlpzid;
	// 导入配置id
	private String drmkdm;
	// 导入列 varchar2(20) 20 FALSE FALSE TRUE
	private String drl;
	// 导入列名称，用于excel模板显示 varchar2(100) 100 FALSE FALSE TRUE
	private String drlmc;
	// 导入编号，用于excel模板列顺序 varchar2(5) 5 FALSE FALSE TRUE
	private String drbh;
	// "格式化本列值 例如：0替换成男 配置格式 {[0:'男'],[1:'女']} sql支持
	private String lsjgsh;
	private String gshxx;
	// select sex from sexTable where key=#name
	// varchar2(1000) 1000 FALSE FALSE FALSE
	// 是否主键(唯一) 0:否 1:是  默认0" varchar2(2) 2 FALSE FALSE
	private String sfzj;
	// 是否必填 
	//0:否 
	//1:插入时必填  
	//2:更新时必填
	//4:插入和更新必填
	private String sfbt;
	//是否必填标志
	//是否必填,int类型 根据所占位的标志来判断
	//00000000 不是必填
	//00000001 插入操作时必填
	//00000010 更新操作时必填
	//00000100 插入和更新操作时必填
	
	//综上，则
	//若想设置字段不必填，那么sfbt就是""或者null或者"0"
	//若想设置字段插入必填，那么sfbt就是"1"
	//若想设置字段更新必填，那么sfbt就是"2"
	//若想设置字段插入和更新都必填，那么sfbt就是"4"
	//其他组合情况使用与运算获得
	private int sfbtFlag;
	// 是否唯一 0:否 1:是  默认0" varchar2(2) 2 FALSE FALSE TRUE
	private String sfwy;
	// 最大长度 varchar2(5) 5 FALSE FALSE TRUE
	private String zdcd;
	// 显示顺序
	private String xsxx;
	//excel模板中约束提示选项
	private String constraintMessage;
	//excel模板中字段下拉选项
	private List<String> dropdownValues = new ArrayList<String>();
	/**
	 * 对应的验证规则关系
	 */
	private List<LyzgzdzModel> lyzgzdzList=new ArrayList<LyzgzdzModel>();

	public String getDrlpzid() {
		return drlpzid;
	}

	public void setDrlpzid(String drlpzid) {
		this.drlpzid = drlpzid;
	}

	public String getDrl() {
		return drl;
	}

	public void setDrl(String drl) {
		this.drl = drl;
	}

	public String getDrlmc() {
		return drlmc;
	}

	public void setDrlmc(String drlmc) {
		this.drlmc = drlmc;
	}

	public String getDrbh() {
		return drbh;
	}

	public void setDrbh(String drbh) {
		this.drbh = drbh;
	}

	public String getLsjgsh() {
		return lsjgsh;
	}

	public void setLsjgsh(String lsjgsh) {
		this.lsjgsh = lsjgsh;
	}

	public String getSfzj() {
		return sfzj;
	}

	public void setSfzj(String sfzj) {
		this.sfzj = sfzj;
	}

	public void setSfbt(String sfbt) {
		this.sfbt = sfbt;
		//FIXME 注意，这里有代码的
		if(this.sfbt == null) {
			this.sfbtFlag = 0;
		}else {
			this.sfbtFlag = Integer.valueOf(sfbt);
		}
	}

	public String getZdcd() {
		return zdcd;
	}

	public void setZdcd(String zdcd) {
		this.zdcd = zdcd;
	}

	public List<LyzgzdzModel> getLyzgzdzList() {
		return lyzgzdzList;
	}

	public void setLyzgzdzList(List<LyzgzdzModel> lyzgzdzList) {
		this.lyzgzdzList = lyzgzdzList;
	}

	public String getGshxx() {
		return gshxx;
	}

	public void setGshxx(String gshxx) {
		this.gshxx = gshxx;
	}

	public String getDrmkdm() {
		return drmkdm;
	}

	public void setDrmkdm(String drmkdm) {
		this.drmkdm = drmkdm;
	}

	public String getXsxx() {
		return xsxx;
	}

	public void setXsxx(String xsxx) {
		this.xsxx = xsxx;
	}

	public String getSfwy() {
		return sfwy;
	}

	public void setSfwy(String sfwy) {
		this.sfwy = sfwy;
	}

	public String getConstraintMessage() {
		return constraintMessage;
	}

	public void setConstraintMessage(String constraintMessage) {
		this.constraintMessage = constraintMessage;
	}

	public List<String> getDropdownValues() {
		return dropdownValues;
	}

	public void setDropdownValues(List<String> dropdownValues) {
		this.dropdownValues = dropdownValues;
	}

	public int getSfbtFlag() {
		return sfbtFlag;
	}

	@Override
	public String toString() {
		return "DrlpzModel [drlpzid=" + drlpzid + ", drmkdm=" + drmkdm + ", drl=" + drl + ", drlmc=" + drlmc + ", drbh="
				+ drbh + ", lsjgsh=" + lsjgsh + ", gshxx=" + gshxx + ", sfzj=" + sfzj + ", sfbt=" + sfbt + ", sfbtFlag="
				+ sfbtFlag + ", sfwy=" + sfwy + ", zdcd=" + zdcd + ", xsxx=" + xsxx + ", constraintMessage="
				+ constraintMessage + ", dropdownValues=" + dropdownValues + ", lyzgzdzList=" + lyzgzdzList + "]";
	}
}
