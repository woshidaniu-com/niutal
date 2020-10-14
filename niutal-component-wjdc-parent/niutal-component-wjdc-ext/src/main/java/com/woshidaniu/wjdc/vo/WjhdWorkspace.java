package com.woshidaniu.wjdc.vo;

import java.util.LinkedList;

import com.woshidaniu.wjdc.dao.entites.WjhdModel;

/**
 * 用户答卷工作区间
 * @author 1571
 */
public class WjhdWorkspace {

	private final String wjid;
	
	private final String zjz;
	
	private boolean finish;
	
	private long lastAccessTime;
	
	public LinkedList<WjhdModel> wjhdModels = new LinkedList<WjhdModel>();
	
	public WjhdWorkspace(String wjid,String zjz) {
		super();
		this.zjz = zjz;
		this.wjid = wjid;
	}
	
	public String getZjz() {
		return zjz;
	}

	public String getWjid() {
		return wjid;
	}

	public LinkedList<WjhdModel> getWjhdModels() {
		return wjhdModels;
	}

	public void setWjhdModels(LinkedList<WjhdModel> wjhdModels) {
		this.wjhdModels = wjhdModels;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	@Override
	public String toString() {
		return "WjhdWorkspace [zjz=" + zjz + ", wjid=" + wjid + ", finish=" + finish + ", lastAccessTime="
				+ lastAccessTime + ", wjhdModels=" + wjhdModels + "]";
	}
}
