/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.dao.entites;

import java.io.Serializable;
import java.util.Set;

/**
 * @author 		：Penghui.Qu[445]
 * @description	： 问卷分发对象
 */
public class FfdxModel implements Serializable{

	private static final long serialVersionUID = 2300092692199934723L;

	private String dxid;
	private String dxmc;
	private String dxly;
	private Set<FfdxtjModel> fftjs;//分发过滤条件
	private Set<WjtjtjModel> tjtjs;//统计过滤条件
	
	public String getDxid() {
		return dxid;
	}
	public void setDxid(String dxid) {
		this.dxid = dxid;
	}
	public String getDxmc() {
		return dxmc;
	}
	public void setDxmc(String dxmc) {
		this.dxmc = dxmc;
	}
	public Set<FfdxtjModel> getFftjs() {
		return fftjs;
	}
	public void setFftjs(Set<FfdxtjModel> fftjs) {
		this.fftjs = fftjs;
	}
	public String getDxly() {
		return dxly;
	}
	public void setDxly(String dxly) {
		this.dxly = dxly;
	}
	public Set<WjtjtjModel> getTjtjs() {
		return tjtjs;
	}
	public void setTjtjs(Set<WjtjtjModel> tjtjs) {
		this.tjtjs = tjtjs;
	}
	@Override
	public String toString() {
		return "FfdxModel [dxid=" + dxid + ", dxmc=" + dxmc + ", dxly=" + dxly + ", fftjs=" + fftjs + ", tjtjs=" + tjtjs
				+ "]";
	}
}
