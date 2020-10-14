package com.woshidaniu.common.filter;

import java.io.Serializable;
import java.util.Date;

public class RecentlyUsedMenu implements Serializable{
	private static final long serialVersionUID = 4208912013646294464L;
	
	private String gnmkdm;
	private Date lastTime;
	private int count = 1;
	
	public String getGnmkdm() {
		return gnmkdm;
	}
	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public String toString(){
		return this.gnmkdm;
	}
}