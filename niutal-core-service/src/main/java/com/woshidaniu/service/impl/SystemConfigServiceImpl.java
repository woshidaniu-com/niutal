package com.woshidaniu.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.jmx.BaseLifecycleMBean;
import com.woshidaniu.common.system.SystemConfigConstants;
import com.woshidaniu.service.svcinterface.ISystemConfigService;
import com.woshidaniu.util.base.MessageUtil;

@Service("systemConfigService")
public class SystemConfigServiceImpl extends BaseLifecycleMBean implements ISystemConfigService,SystemConfigServiceImplMBean{
	
	private static final Logger log = LoggerFactory.getLogger(SystemConfigServiceImpl.class);
	
	private final Map<String,String> overrideMap = new HashMap<String,String>();
	
	@PostConstruct
	public void init(){
		this.start();
	}
	
	@PreDestroy
	public void destroy(){
		this.stop();
	}

	@Override
	public String getConfigValue(String key) {
		if(this.overrideMap.containsKey(key)){
			
			String val = this.overrideMap.get(key);
			
			log.info("getConfigValue,[{}]=[{}]",key,val);
			
			return val;
		}else{
			return MessageUtil.getText(key);
		}
	}

	@Override
	public String writeOverrideConfigValue(String key, String value) {
		
		String origin = this.overrideMap.put(key, value);
		
		log.info("writeOverrideConfigValue,[{}]=[{}],originValue=[{}]",key,value,origin);
		
		return origin;
	}

	@Override
	public String readOverrideConfigValue(String key) {
		
		String val = this.overrideMap.get(key);
		
		log.info("readOverrideConfigValue,[{}]=[{}]",key,val);
		
		return val;
	}

	@Override
	public String removeOverrideConfigValue(String key) {
		
		String origin = this.overrideMap.remove(key);
		
		log.info("removeOverrideConfigValue,[{}]=[{}]",key,origin);
		
		return origin;
	}
	
	@Override
	public void removeAllOverrideConfigValue() {
		
		log.info("removeOverrideConfigValue start.................");
		
		Iterator<Entry<String, String>> it = this.overrideMap.entrySet().iterator();
		
		while(it.hasNext()){
			Entry<String, String> e = it.next();
			String key = e.getKey();
			String value = e.getValue();
			
			log.info("remove [{}]=[{}]",key,value);
			
			it.remove();
		}
		
		log.info("removeOverrideConfigValue end.................");
	}

	@Override
	public String dump() {
		StringBuffer sb = new StringBuffer("{\r\n");
		
		Iterator<Entry<String, String>> it = this.overrideMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, String> e = it.next();
			String key = e.getKey();
			String value = e.getValue();
			sb.append("[").append(key).append("=").append(value).append("]\r\n");
			
		}
		sb.append("}\r\n");
		return sb.toString();
	}

	@Override
	public boolean isEnableMutileRoleContext() {
		String val = this.getConfigValue(SystemConfigConstants.MUTILE_ROLE_CONTEXT_ENABLE_KEY);
		boolean isMutileRoleContextValue = Boolean.parseBoolean(val);
		return isMutileRoleContextValue;
	}
}
