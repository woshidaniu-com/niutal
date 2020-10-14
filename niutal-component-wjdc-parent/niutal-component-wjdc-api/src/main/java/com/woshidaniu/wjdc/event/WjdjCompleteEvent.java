/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author 		：康康（1571）
 * 
 * 问卷答卷完成事件
 */
public class WjdjCompleteEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	/**
	 * 问卷id
	 */
	private String wjid;
	/**
	 * 答卷人
	 */
	private String djr;
	/**
	 * 用户类型
	 */
	private String yhlx;
	
	public WjdjCompleteEvent(Object source) {
		super(source);
	}

	public WjdjCompleteEvent(Object source, String wjid, String djr, String yhlx) {
		super(source);
		this.wjid = wjid;
		this.djr = djr;
		this.yhlx = yhlx;
	}

	public String getWjid() {
		return wjid;
	}

	public String getDjr() {
		return djr;
	}

	public String getYhlx() {
		return yhlx;
	}

	@Override
	public String toString() {
		return "WjdjCompleteEvent [wjid=" + wjid + ", djr=" + djr + ", yhlx=" + yhlx + ", source=" + source + "]";
	}
}
