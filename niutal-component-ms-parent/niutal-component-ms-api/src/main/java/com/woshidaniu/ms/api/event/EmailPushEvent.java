package com.woshidaniu.ms.api.event;

import org.springframework.enhanced.context.event.EnhancedEvent;

import com.woshidaniu.ms.api.MessageBody;

@SuppressWarnings("serial")
public class EmailPushEvent extends EnhancedEvent<MessageBody> {

	public EmailPushEvent(Object source, MessageBody bind) {
		super(source, bind);
	}
	
}
