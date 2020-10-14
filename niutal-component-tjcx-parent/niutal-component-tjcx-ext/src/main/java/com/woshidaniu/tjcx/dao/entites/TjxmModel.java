package com.woshidaniu.tjcx.dao.entites;

import java.io.Serializable;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计项目表
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:00:18
 * @版本： V1.0
 * @修改记录:
 */
public class TjxmModel  implements Serializable{
	private static final long serialVersionUID = 7532662280629320158L;
	private String xmdm;// 项目ID
	private String ysjdm;// 源数据代码
	private String xmmc;// 项目名称
	private String xtbh;// 系统编号
	private String gnmk;// 功能模块
	private String ms;// 描述
	private String qyfw;// 启用范围' 0:不启用,1:统计查询,2:统计报表,3(或为空):统计查询及统计报表共用 ';
	private String xssx;// 显示顺序
	private String xmxsms;// 显示模式 1:针对非超级管理员，功能首页仅显示快照链接

	public TjxmModel() {
		super();
	}

	public String getXmdm() {
		return xmdm;
	}

	public void setXmdm(String xmdm) {
		this.xmdm = xmdm;
	}

	public String getYsjdm() {
		return ysjdm;
	}

	public void setYsjdm(String ysjdm) {
		this.ysjdm = ysjdm;
	}

	public String getXmmc() {
		return xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public String getXtbh() {
		return xtbh;
	}

	public void setXtbh(String xtbh) {
		this.xtbh = xtbh;
	}

	public String getGnmk() {
		return gnmk;
	}

	public void setGnmk(String gnmk) {
		this.gnmk = gnmk;
	}

	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public String getQyfw() {
		return qyfw;
	}

	public void setQyfw(String qyfw) {
		this.qyfw = qyfw;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getXmxsms() {
		return xmxsms;
	}

	public void setXmxsms(String xmxsms) {
		this.xmxsms = xmxsms;
	}

}
