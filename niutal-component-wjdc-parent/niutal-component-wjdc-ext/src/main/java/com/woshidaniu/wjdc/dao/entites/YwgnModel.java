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
@Alias(value="YwgnModel")
public class YwgnModel implements Serializable {

	private static final long serialVersionUID = -9067454204001992587L;

	private String gnid;//功能id
	private String gnmc;//功能名称
	private String gnlj;//功能路径
	private String clsx;//处理顺序
	private String wjid;//问卷id
	
	public String getGnid() {
		return gnid;
	}
	public void setGnid(String gnid) {
		this.gnid = gnid;
	}
	public String getGnmc() {
		return gnmc;
	}
	public void setGnmc(String gnmc) {
		this.gnmc = gnmc;
	}
	public String getGnlj() {
		return gnlj;
	}
	public void setGnlj(String gnlj) {
		this.gnlj = gnlj;
	}
	public String getWjid() {
		return wjid;
	}
	public void setWjid(String wjid) {
		this.wjid = wjid;
	}
	public String getClsx() {
		return clsx;
	}
	public void setClsx(String clsx) {
		this.clsx = clsx;
	}
	@Override
	public String toString() {
		return "YwgnModel [gnid=" + gnid + ", gnmc=" + gnmc + ", gnlj=" + gnlj + ", clsx=" + clsx + ", wjid=" + wjid
				+ "]";
	}
	
}
