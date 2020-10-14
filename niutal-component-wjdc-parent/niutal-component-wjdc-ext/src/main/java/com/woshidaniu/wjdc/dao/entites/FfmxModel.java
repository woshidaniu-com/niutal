/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.entites;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
/**
 * @author Penghui.Qu(445)
 * 试题管理DAO
 * 
  * @author ：康康（1571）
 * 整理，优化
 * */
@Alias(value="FfmxModel")
public class FfmxModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String wjid;
	private String ffdx;
	private String dxtj;
	private String tjz;
	public String getWjid() {
		return wjid;
	}
	public void setWjid(String wjid) {
		this.wjid = wjid;
	}
	public String getFfdx() {
		return ffdx;
	}
	public void setFfdx(String ffdx) {
		this.ffdx = ffdx;
	}
	public String getTjz() {
		return tjz;
	}
	public void setTjz(String tjz) {
		this.tjz = tjz;
	}
	public String getDxtj() {
		return dxtj;
	}
	public void setDxtj(String dxtj) {
		this.dxtj = dxtj;
	}
	
	
}
