package com.woshidaniu.common.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmxBeanRegisterHelper {
	
	private static final Logger log = LoggerFactory.getLogger(JmxBeanRegisterHelper.class);

	private ObjectName getObjectName(Object target) {
		String packageName = target.getClass().getPackage().getName();
		String name = String.format("%s:type=%s_%s",packageName,target.getClass().getSimpleName(),System.identityHashCode(target));
		try {
			ObjectName objectName = new ObjectName(name);
			return objectName;
		} catch (Exception e) {
			log.error("getObjectName error,cause : " + e.getMessage(),e);
		}
		return null;
	}
	
	public synchronized void unregisterMBean(Object target) {
		try {
			ObjectName objectName = this.getObjectName(target);
			if(objectName != null) {
				MBeanServer server = ManagementFactory.getPlatformMBeanServer();
				server.unregisterMBean(objectName);
			}
		}catch (Exception e) {
			log.error("unregisterMBean:"+target+"error,cause : " + e.getMessage(),e);
		}
	}

	public synchronized void registerMBean(Object target) {
		try {
			ObjectName objectName = this.getObjectName(target);
			if(objectName != null) {
				
				MBeanServer server = ManagementFactory.getPlatformMBeanServer();
				if(server.isRegistered(objectName)) {
					server.unregisterMBean(objectName);						
				}
				server.registerMBean(target,objectName);
			}
		}catch (Exception e) {
			log.error("registerMBean:"+target+"error,cause : " + e.getMessage(),e);
		}
	}
}