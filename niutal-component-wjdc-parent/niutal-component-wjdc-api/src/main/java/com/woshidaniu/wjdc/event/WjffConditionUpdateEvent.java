/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author 		：康康（1571）
 * 
 * 问卷分发条件更新事件
 */
public class WjffConditionUpdateEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	
	public WjffConditionUpdateEvent(Object source) {
		super(source);
	}
}
