/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.entites;

import java.io.Serializable;

/**
 * @author Penghui.Qu(445)
 * 试题管理DAO
 * 
  * @author ：康康（1571）
 * 整理，优化
 * */
public class XxglModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String wjid;//问卷id
	private String stid;//试题id
	private String xxid ;//选项id--对于矩阵题，行列选项要使用特殊的标记进行处理row、col作为后缀
    private String xxbh ;//选项编号
    private String xxmc ;//选项名称
    private String xxfz ;//选项分值
    private String sfklr ;//是否可录入
    private String xssx ;//显示顺序
    private String tzstid;//跳转试题id
    //问卷回答部分使用的字段
    private String djrid;//答卷人id
    private String txnr;//填写内容--用于问答题、组合题、矩阵题
    private String plsx;//排列顺序--用于排序题
    
    private String checked;
    private String isLastxx = "false";// 是否是最后一个选项 
    private String lastxxOption;//最后一个选项的额外文本
    
	public String getWjid() {
		return wjid;
	}
	public void setWjid(String wjid) {
		this.wjid = wjid;
	}
	public String getStid() {
		return stid;
	}
	public void setStid(String stid) {
		this.stid = stid;
	}
	public String getXxid() {
		return xxid;
	}
	public void setXxid(String xxid) {
		this.xxid = xxid;
	}
	public String getXxbh() {
		return xxbh;
	}
	public void setXxbh(String xxbh) {
		this.xxbh = xxbh;
	}
	public String getXxmc() {
		return xxmc;
	}
	public void setXxmc(String xxmc) {
		this.xxmc = xxmc;
	}
	public String getXxfz() {
		return xxfz;
	}
	public void setXxfz(String xxfz) {
		this.xxfz = xxfz;
	}
	public String getSfklr() {
		return sfklr;
	}
	public void setSfklr(String sfklr) {
		this.sfklr = sfklr;
	}
	public String getXssx() {
		return xssx;
	}
	public void setXssx(String xssx) {
		this.xssx = xssx;
	}
	public String getDjrid() {
		return djrid;
	}
	public void setDjrid(String djrid) {
		this.djrid = djrid;
	}
	public String getTxnr() {
		return txnr;
	}
	public void setTxnr(String txnr) {
		this.txnr = txnr;
	}
	public String getPlsx() {
		return plsx;
	}
	public void setPlsx(String plsx) {
		this.plsx = plsx;
	}
	public String getTzstid() {
		return tzstid;
	}
	public void setTzstid(String tzstid) {
		this.tzstid = tzstid;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getIsLastxx() {
		return isLastxx;
	}
	public void setIsLastxx(String isLastxx) {
		this.isLastxx = isLastxx;
	}
	public String getLastxxOption() {
		return lastxxOption;
	}
	public void setLastxxOption(String lastxxOption) {
		this.lastxxOption = lastxxOption;
	}
	@Override
	public String toString() {
		return "XxglModel [wjid=" + wjid + ", stid=" + stid + ", xxid=" + xxid + ", xxbh=" + xxbh + ", xxmc=" + xxmc
				+ ", xxfz=" + xxfz + ", sfklr=" + sfklr + ", xssx=" + xssx + ", tzstid=" + tzstid + ", djrid=" + djrid
				+ ", txnr=" + txnr + ", plsx=" + plsx + ", checked=" + checked + ", isLastxx=" + isLastxx
				+ ", lastxxOption=" + lastxxOption + "]";
	}
}
