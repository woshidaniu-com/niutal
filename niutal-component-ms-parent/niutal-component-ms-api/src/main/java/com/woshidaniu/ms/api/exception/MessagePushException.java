package com.woshidaniu.ms.api.exception;

@SuppressWarnings("serial")
public class MessagePushException extends RuntimeException {

	public MessagePushException() {
		super();
	}

	public MessagePushException(String message) {
		super(message);
	}

	public MessagePushException(Throwable cause) {
		super(cause);
	}

	public MessagePushException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
