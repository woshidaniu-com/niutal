package com.woshidaniu.wjdc.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author 		：康康（1571）
 * 
 * 问卷试题更新事件
 */
public class WjstUpdateEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	
	private String wjid;
	
	public WjstUpdateEvent(Object source,String wjid) {
		super(source);
		this.wjid = wjid;
	}

	public String getWjid() {
		return wjid;
	}
}
