package com.woshidaniu.api.event;

import org.springframework.enhanced.context.event.EnhancedEvent;

import java.util.Properties;

@SuppressWarnings("serial")
public class PropsUpdateEvent extends EnhancedEvent<Properties> {
	
	public PropsUpdateEvent(Object source, Properties props) {
		super(source, props);
	}
	
}
