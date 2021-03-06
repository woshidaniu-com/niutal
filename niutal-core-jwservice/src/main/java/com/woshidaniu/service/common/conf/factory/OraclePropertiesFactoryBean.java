package com.woshidaniu.service.common.conf.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.FactoryBean;

import com.woshidaniu.service.svcinterface.IXtszService;

public class OraclePropertiesFactoryBean implements FactoryBean<Properties> {

	/**
	 * 基于配置文件的默认配置
	 */
	protected Properties props = new Properties();
	/**
	 * 要从数据库提取的属性值key
	 */
	protected List<String> propKeys = new ArrayList<String>();
	
	@Resource
	protected IXtszService xtszService;
	
	@Override
	public Properties getObject() throws Exception {
		
		//系统配置参数
		props.putAll(System.getProperties());
		
		if(getPropKeys() != null){
			for (String key : getPropKeys()) {
				props.put(key, getXtszService().getValue(key));
			}
		}
		return props;
	}
	
	@Override
	public Class<?> getObjectType() {
		return Properties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	

	public List<String> getPropKeys() {
		return propKeys;
	}

	public void setPropKeys(List<String> propKeys) {
		this.propKeys = propKeys;
	}

	public IXtszService getXtszService() {
		return xtszService;
	}

	public void setXtszService(IXtszService xtszService) {
		this.xtszService = xtszService;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

}
