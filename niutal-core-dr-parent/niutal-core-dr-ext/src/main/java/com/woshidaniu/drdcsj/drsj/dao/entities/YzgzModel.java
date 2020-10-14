/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.entities;

/**
 * @author 982
 * 验证规则
 */
public class YzgzModel{
	// 先从spring容器中获取，获取不到则通过类路径获取
	private String llj;
	// 规则名称
	private String gzmc;
	// 具体规则的作用，使用环境 
	private String gzms;
	// 配置规则验证不通过提示信息，不配置使用默认提示 
	private String gzts;

	public String getLlj() {
		return llj;
	}

	public void setLlj(String llj) {
		this.llj = llj;
	}

	public String getGzmc() {
		return gzmc;
	}

	public void setGzmc(String gzmc) {
		this.gzmc = gzmc;
	}

	public String getGzms() {
		return gzms;
	}

	public void setGzms(String gzms) {
		this.gzms = gzms;
	}

	public String getGzts() {
		return gzts;
	}

	public void setGzts(String gzts) {
		this.gzts = gzts;
	}

	@Override
	public String toString() {
		return "YzgzModel [llj=" + llj + ", gzmc=" + gzmc + ", gzms=" + gzms + ", gzts=" + gzts + "]";
	}
}
