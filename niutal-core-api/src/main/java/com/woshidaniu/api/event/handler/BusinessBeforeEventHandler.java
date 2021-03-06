package com.woshidaniu.api.event.handler;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.enhanced.context.event.EventInvocation;
import org.springframework.enhanced.context.event.aspect.JoinPointBeforeEvent;
import org.springframework.enhanced.context.event.handler.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class BusinessBeforeEventHandler implements EventHandler<JoinPointBeforeEvent> {

	protected Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Override
	public void handle(JoinPointBeforeEvent event) {
		
		if (LOG.isDebugEnabled()) {
			// 获取事件环境对象
			EventInvocation invocation = event.getBind();
			JoinPoint point = invocation.getPoint();
			Signature signature = point.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;

			LOG.debug("Business Before [ Method ： {} , ArgNames : {} ] ", methodSignature.getName(),
					invocation.getArgNames());

		}
		
	}
	
}
