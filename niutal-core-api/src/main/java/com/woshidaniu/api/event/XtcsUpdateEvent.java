package com.woshidaniu.api.event;

import org.springframework.enhanced.context.event.EnhancedEvent;

import java.util.Map;

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
public class XtcsUpdateEvent extends EnhancedEvent<Map<String, String>> {
	
	public XtcsUpdateEvent(Object source, Map<String, String> props) {
		super(source, props);
	}
	
}
