/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service.impl;

import java.lang.management.ManagementFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.woshidaniu.wjdc.event.WjffConditionUpdateEvent;

/**
 * @author 		：康康（1571）
 * 
 * 仅仅是调试事件机制，请勿直接引用使用
 */
@Component
public class WjglManager implements ApplicationContextAware,WjglManagerMBean{
	
	private static final Logger log = LoggerFactory.getLogger(WjglManager.class);
	
	private ApplicationContext applicationContext;
	
	@PostConstruct
	public void start() {
		this.registerMBean();			
	}

	@PreDestroy
	public void stop() {
		this.unregisterMBean();
	}
	
	private ObjectName getObjectName() {
		String packageName = this.getClass().getPackage().getName();
		String name = String.format("%s:type=%s_%s",packageName,this.getClass().getSimpleName(),System.identityHashCode(this));
		try {
			ObjectName objectName = new ObjectName(name);
			return objectName;
		} catch (Exception e) {
			log.error("",e);
		}
		return null;
	}
	
	private void unregisterMBean() {
		try {
			ObjectName objectName = this.getObjectName();
			if(objectName != null) {
				MBeanServer server = ManagementFactory.getPlatformMBeanServer();
				try {
					if(server.isRegistered(objectName)) {
						server.unregisterMBean(objectName);					
					}
				}catch (Exception e) {
					log.error("",e);
				}
			}
		}catch (Exception e) {
			log.error("",e);
		}
	}

	private void registerMBean() {
		try {
			ObjectName objectName = this.getObjectName();
			if(objectName != null) {
				
				MBeanServer server = ManagementFactory.getPlatformMBeanServer();
				try {
					if(server.isRegistered(objectName)) {
						server.unregisterMBean(objectName);					
					}
					server.registerMBean(this,objectName);
				}catch (Exception e) {
					log.error("",e);
				}
			}
		}catch (Exception e) {
			log.error("",e);
		}
	}

	@Override
	public void publishWjffConditionUpdateEvent() {
		this.applicationContext.publishEvent(new WjffConditionUpdateEvent(this));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
