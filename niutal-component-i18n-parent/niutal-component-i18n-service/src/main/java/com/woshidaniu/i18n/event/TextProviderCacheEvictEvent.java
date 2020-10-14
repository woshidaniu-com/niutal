package com.woshidaniu.i18n.event;

import org.springframework.enhanced.context.event.EnhancedEvent;

public class TextProviderCacheEvictEvent extends EnhancedEvent<Object> {

	public TextProviderCacheEvictEvent(Object source, Object bind) {
		super(source, bind);
	}

}
