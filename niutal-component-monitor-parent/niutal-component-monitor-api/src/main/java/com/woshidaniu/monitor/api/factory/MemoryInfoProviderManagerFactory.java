package com.woshidaniu.monitor.api.factory;


import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.woshidaniu.monitor.api.provider.MemoryInfoProvider;
import com.woshidaniu.monitor.api.provider.MemoryInfoProviderManager;
/**
 * 
 *@类名称		: MemoryInfoProviderManagerFactory.java
 *@类描述		：Spring集成MemoryInfoProviderManager实现
 *@创建人		：kangzhidong
 *@创建时间	：2017年6月21日 下午6:08:13
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class MemoryInfoProviderManagerFactory implements FactoryBean<MemoryInfoProviderManager>, ApplicationContextAware {

	protected ApplicationContext applicationContext;
	protected List<MemoryInfoProvider> providers;
	
	@Override
	public MemoryInfoProviderManager getObject() throws Exception {
		
		//构造密码相关策略对象管理者
		MemoryInfoProviderManager strategyManager = new MemoryInfoProviderManager();
		
		//注册扩展的密码找回策略
		Map<String, MemoryInfoProvider>  retakeMap = applicationContext.getBeansOfType(MemoryInfoProvider.class);
		if(retakeMap != null){
			for (MemoryInfoProvider provider : retakeMap.values()) {
				strategyManager.register(provider);
			}
		}
		
		return strategyManager;
	}

	@Override
	public Class<?> getObjectType() {
		return MemoryInfoProviderManager.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public List<MemoryInfoProvider> getProviders() {
		return providers;
	}

	public void setProviders(List<MemoryInfoProvider> providers) {
		this.providers = providers;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
