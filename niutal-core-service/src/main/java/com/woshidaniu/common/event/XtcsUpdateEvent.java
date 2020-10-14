package com.woshidaniu.common.event;

import org.springframework.enhanced.context.event.EnhancedEvent;

/**
 * 
 *@类名称		： XtcsUpdateEvent.java
 *@类描述		：系统参数更新事件
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月20日 下午3:05:22
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@SuppressWarnings("serial")
public class XtcsUpdateEvent extends EnhancedEvent<Object[]> {
	
	public XtcsUpdateEvent(Object source, Object[] props) {
		super(source, props);
	}
	
	@Override
	public Object[] getBind() {
		return super.getBind();
	}
	
}
