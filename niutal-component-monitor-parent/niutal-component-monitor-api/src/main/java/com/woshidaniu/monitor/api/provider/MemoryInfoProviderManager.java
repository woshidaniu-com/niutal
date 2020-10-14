package com.woshidaniu.monitor.api.provider;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MemoryInfoProviderManager {
	
	protected ConcurrentMap<String, MemoryInfoProvider> COMPLIED_MEMORY_INFO_PROVIDER = new ConcurrentHashMap<String, MemoryInfoProvider>();
	
	public MemoryInfoProviderManager(){
		
	}
	
	public Set<String> providers() {
		return COMPLIED_MEMORY_INFO_PROVIDER.keySet();
	}
	
	
	public void register(MemoryInfoProvider provider) {
		if (provider != null ) {
			COMPLIED_MEMORY_INFO_PROVIDER.putIfAbsent( provider.name(), provider);
		}
	}
	 
	public MemoryInfoProvider getMemoryInfoProvider(String provider) {
		if (provider != null) {
			MemoryInfoProvider ret = COMPLIED_MEMORY_INFO_PROVIDER.get(provider);
			if (ret != null) {
				return ret;
			}
		}
		return COMPLIED_MEMORY_INFO_PROVIDER.get(MemoryInfoProvider.DEFAULT);
	}
	 
}
