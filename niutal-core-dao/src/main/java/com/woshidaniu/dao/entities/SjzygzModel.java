package com.woshidaniu.dao.entities;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：数据资源规则
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年3月22日上午10:24:35
 */
@Alias(value="SjzygzModel")
public class SjzygzModel implements Serializable {

	private static final long serialVersionUID = 8224911732292526074L;
	private String gzid;
	private String gztgz;
	private String gzmc;
	private String gzsm;
	private String zyid;
	
	public String getGzid() {
		return gzid;
	}
	public void setGzid(String gzid) {
		this.gzid = gzid;
	}
	public String getGztgz() {
		return gztgz;
	}
	public void setGztgz(String gztgz) {
		this.gztgz = gztgz;
	}
	public String getGzmc() {
		return gzmc;
	}
	public void setGzmc(String gzmc) {
		this.gzmc = gzmc;
	}
	public String getGzsm() {
		return gzsm;
	}
	public void setGzsm(String gzsm) {
		this.gzsm = gzsm;
	}
	public String getZyid() {
		return zyid;
	}
	public void setZyid(String zyid) {
		this.zyid = zyid;
	}
	
	
}
