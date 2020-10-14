package com.woshidaniu.metrics.api.factory;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarFactory;
import org.springframework.beans.factory.FactoryBean;

public class SigarBeanFactory implements FactoryBean<Sigar> {

	@Override
	public Sigar getObject() throws Exception {
		return (Sigar) SigarFactory.newSigar();
	}

	@Override
	public Class<?> getObjectType() {
		return Sigar.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
 
}
