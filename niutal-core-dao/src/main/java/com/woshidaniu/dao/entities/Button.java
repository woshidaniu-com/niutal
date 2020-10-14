package com.woshidaniu.dao.entities;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：功能按钮
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月24日上午9:59:58
 */
@Alias(value="button")
public class Button implements Serializable {

	private static final long serialVersionUID = -7721627859928970573L;

	private String gnczid;
	private String czdm;
	private String czmc;
	
	public String getGnczid() {
		return gnczid;
	}
	public void setGnczid(String gnczid) {
		this.gnczid = gnczid;
	}
	public String getCzdm() {
		return czdm;
	}
	public void setCzdm(String czdm) {
		this.czdm = czdm;
	}
	public String getCzmc() {
		return czmc;
	}
	public void setCzmc(String czmc) {
		this.czmc = czmc;
	}
	
	
}
