package com.woshidaniu.entities.webservice;

/**
 *@类名称:TzggModel.java
 *@类描述：通知公告
 *@创建人："huangrz"
 *@创建时间：2014-8-28 下午02:10:17
 *@版本号:v1.0
 */
public class TzggModel {
	private String xwbh;         //新闻id
	private String xwbt;         //标题
	private String fbsj;         //发布时间
	private String fbr;          //发布人
	private String fbdx;         //发布对象
	private String sffb;         //是否发布
	private String sfzd;         //是否置顶
	private String url;          //详细链接地址
	
	public String getXwbh() {
		return xwbh;
	}
	public void setXwbh(String xwbh) {
		this.xwbh = xwbh;
	}
	public String getXwbt() {
		return xwbt;
	}
	public void setXwbt(String xwbt) {
		this.xwbt = xwbt;
	}
	public String getFbsj() {
		return fbsj;
	}
	public void setFbsj(String fbsj) {
		this.fbsj = fbsj;
	}
	public String getFbr() {
		return fbr;
	}
	public void setFbr(String fbr) {
		this.fbr = fbr;
	}
	public String getFbdx() {
		return fbdx;
	}
	public void setFbdx(String fbdx) {
		this.fbdx = fbdx;
	}
	public String getSffb() {
		return sffb;
	}
	public void setSffb(String sffb) {
		this.sffb = sffb;
	}
	public String getSfzd() {
		return sfzd;
	}
	public void setSfzd(String sfzd) {
		this.sfzd = sfzd;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}	
}
