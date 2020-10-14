package com.woshidaniu.common.conf.def;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import com.woshidaniu.common.conf.PropertiesProvider;
import com.woshidaniu.service.svcinterface.ICsszService;

/**
 * 
 *@类名称		： OraclePropertiesProvider.java
 *@类描述		：整合系统参数设置表数据为常用的系统参数配置
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月17日 下午12:15:55
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class OraclePropertiesProvider implements PropertiesProvider {

	/**
	 * 基于配置文件的默认配置
	 */
	protected Properties defaultProps = new Properties();
	/**
	 * 默认配置
	 */
	protected Properties props = new Properties();
	/**
	 * 要从数据库提取的属性值key
	 */
	protected List<String> propKeys = new ArrayList<String>();
	
	@Resource
	protected ICsszService csszService;
	
	
	@Override
	public Properties props() {
		
		//清除参数
		props.clear();
		//默认参数配置
		props.putAll(getDefaultProps());
		
		//根据给出的key查找数据库存储的参数
		if(getPropKeys() != null){
			for (String key : getPropKeys()) {
				props.setProperty(key, getCsszService().getValue(key));
			}
		}
		
		return props;
	}
	
	public List<String> getPropKeys() {
		return propKeys;
	}

	public void setPropKeys(List<String> propKeys) {
		this.propKeys = propKeys;
	}

	public ICsszService getCsszService() {
		return csszService;
	}

	public void setCsszService(ICsszService csszService) {
		this.csszService = csszService;
	}

	public Properties getProps() {
		return props;
	}

	@Override
	public void setProps(Properties props) {
		this.defaultProps = props;
	}

	public Properties getDefaultProps() {
		//系统配置参数
		defaultProps.putAll(System.getProperties());
		return defaultProps;
	}

	public void setDefaultProps(Properties defaultProps) {
		this.defaultProps = defaultProps;
	}
	
}
