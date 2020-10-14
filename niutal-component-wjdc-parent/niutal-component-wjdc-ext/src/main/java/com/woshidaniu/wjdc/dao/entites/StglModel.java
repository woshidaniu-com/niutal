/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.entites;

import java.io.Serializable;

/**
 * @author 		：Penghui.Qu[445]
 * @description	：分发对象条件
 */
public class StglModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String wjid;//问卷id
	private String stid;//试题id
	private String stmc;//试题名称
	private String stlx;//试题类型
	private String stlxmc;//试题类型名称
	private String dhxxgs;//单行选项个数
	private String sfbd;//是否必答
	private String stzf;//试题总分
	private String xssx;//显示顺序
	private String stdlid;//试题大类代码
	private int xxkzdxzs;//选项可最多选择数
	private String stbh;//试题编号
	private String zdzs;//最大字数
	private String wbgd;//文本高度
	private String mhxxgs;//每行选项个数
	private String dqfs;//对齐方式
	private String wblx;//文本类型
	private String sfwc;//是否完成试题答题 0未完成 1完成
	private String ts;//提示信息，如最多选择几个选项等等
	private String display = "block";//是否显示
	private String sfyxpx;//是否允许排序
	private String dags;
	
	public String getZdzs() {
		return zdzs;
	}

	public void setZdzs(String zdzs) {
		this.zdzs = zdzs;
	}

	public String getWbgd() {
		return wbgd;
	}

	public void setWbgd(String wbgd) {
		this.wbgd = wbgd;
	}
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
	public String getStmc() {
		return stmc;
	}
	public void setStmc(String stmc) {
		this.stmc = stmc;
	}
	public String getStlx() {
		return stlx;
	}
	public void setStlx(String stlx) {
		this.stlx = stlx;
	}
	public String getDhxxgs() {
		return dhxxgs;
	}
	public void setDhxxgs(String dhxxgs) {
		this.dhxxgs = dhxxgs;
	}
	public String getSfbd() {
		return sfbd;
	}
	public void setSfbd(String sfbd) {
		this.sfbd = sfbd;
	}
	public String getStzf() {
		return stzf;
	}
	public void setStzf(String stzf) {
		this.stzf = stzf;
	}
	public String getXssx() {
		return xssx;
	}
	public void setXssx(String xssx) {
		this.xssx = xssx;
	}
	public String getStdlid() {
		return stdlid;
	}
	public void setStdlid(String stdlid) {
		this.stdlid = stdlid;
	}
	public final String getStlxmc() {
		return stlxmc;
	}
	public final void setStlxmc(String stlxmc) {
		this.stlxmc = stlxmc;
	}
	public final int getXxkzdxzs() {
		return xxkzdxzs;
	}
	public final void setXxkzdxzs(int xxkzdxzs) {
		this.xxkzdxzs = xxkzdxzs;
	}
	public final String getStbh() {
		return stbh;
	}
	public final void setStbh(String stbh) {
		this.stbh = stbh;
	}

	public String getMhxxgs() {
		return mhxxgs;
	}

	public void setMhxxgs(String mhxxgs) {
		this.mhxxgs = mhxxgs;
	}

	public String getDqfs() {
		return dqfs;
	}

	public void setDqfs(String dqfs) {
		this.dqfs = dqfs;
	}
	
	public String getWblx() {
		return wblx;
	}

	public void setWblx(String wblx) {
		this.wblx = wblx;
	}

	public String getSfwc() {
		return sfwc;
	}

	public void setSfwc(String sfwc) {
		this.sfwc = sfwc;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	public String getSfyxpx() {
		return sfyxpx;
	}
	public void setSfyxpx(String sfyxpx) {
		this.sfyxpx = sfyxpx;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getDags() {
		return dags;
	}

	public void setDags(String dags) {
		this.dags = dags;
	}

	@Override
	public String toString() {
		return "StglModel [wjid=" + wjid + ", stid=" + stid + ", stmc=" + stmc + ", stlx=" + stlx + ", stlxmc=" + stlxmc
				+ ", dhxxgs=" + dhxxgs + ", sfbd=" + sfbd + ", stzf=" + stzf + ", xssx=" + xssx + ", stdlid=" + stdlid
				+ ", xxkzdxzs=" + xxkzdxzs + ", stbh=" + stbh + ", zdzs=" + zdzs + ", wbgd=" + wbgd + ", mhxxgs="
				+ mhxxgs + ", dqfs=" + dqfs + ", wblx=" + wblx + ", sfwc=" + sfwc + ", ts=" + ts + ", display="
				+ display + ", sfyxpx=" + sfyxpx + ", dags=" + dags + "]";
	}
}
