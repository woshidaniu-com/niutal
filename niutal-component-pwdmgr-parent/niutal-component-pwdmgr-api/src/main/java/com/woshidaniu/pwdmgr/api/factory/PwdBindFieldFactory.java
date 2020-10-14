package com.woshidaniu.pwdmgr.api.factory;

import org.springframework.beans.factory.FactoryBean;

import com.woshidaniu.pwdmgr.api.model.BindField;

public class PwdBindFieldFactory implements FactoryBean<BindField[]> {

	@Override
	public BindField[] getObject() throws Exception {
		return null;
	}

	@Override
	public Class<?> getObjectType() {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
