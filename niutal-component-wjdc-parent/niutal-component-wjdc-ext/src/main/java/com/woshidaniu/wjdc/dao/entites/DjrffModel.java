/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.entites;

import java.io.Serializable;

/**
 * @author 		：康康（1571）
 * @description	： 答卷人分发表Model
 */
public class DjrffModel implements Serializable{
	
	private static final long serialVersionUID = 7439215992598395612L;
	
	private String wjid;//问卷id
	private String lxid;//类型id
	private String zjz;//主键值
	private String djzt;//答卷状态
	
	public String getWjid() {
		return wjid;
	}
	public void setWjid(String wjid) {
		this.wjid = wjid;
	}
	public String getLxid() {
		return lxid;
	}
	public void setLxid(String lxid) {
		this.lxid = lxid;
	}
	public String getZjz() {
		return zjz;
	}
	public void setZjz(String zjz) {
		this.zjz = zjz;
	}
	public String getDjzt() {
		return djzt;
	}
	public void setDjzt(String djzt) {
		this.djzt = djzt;
	}
	
	@Override
	public String toString() {
		return "DjrffModel [wjid=" + wjid + ", lxid=" + lxid + ", zjz=" + zjz + ", djzt=" + djzt + "]";
	}
}
