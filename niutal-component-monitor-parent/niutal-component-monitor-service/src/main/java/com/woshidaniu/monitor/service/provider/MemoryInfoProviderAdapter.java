package com.woshidaniu.monitor.service.provider;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hyperic.sigar.Sigar;

import com.woshidaniu.api.conf.PropertiesProvider;
import com.woshidaniu.metrics.sigar.OSEnvInfo;
import com.woshidaniu.metrics.utils.CapacityUtils.Unit;
import com.woshidaniu.monitor.api.provider.MemoryInfoProvider;

public abstract class MemoryInfoProviderAdapter implements MemoryInfoProvider {

	protected static final MemoryInfoComparator  INFO_COMPARATOR = new MemoryInfoComparator();
	protected static final MemoryUsageComparator  USAGE_COMPARATOR = new MemoryUsageComparator();
	
	@Resource
	protected Sigar sigar = null;
		
	protected PropertiesProvider propsProvider = null;
	
	@Override
	public boolean setMemory(Map<String, Object> memory) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setUsage(Map<String, Double> usage) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<Map<String, Object>> getHistoryMemory(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Double>> getHistoryUsage(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getMemory(Unit unit) {
		return OSEnvInfo.memory(sigar, unit);
	}

	@Override
	public Map<String, Double> getUsage() {
		return OSEnvInfo.usage(sigar);
	}
	
	
	public Sigar getSigar() {
		return sigar;
	}

	public void setSigar(Sigar sigar) {
		this.sigar = sigar;
	}
	
	public PropertiesProvider getPropsProvider() {
		return propsProvider;
	}

	public void setPropsProvider(PropertiesProvider propsProvider) {
		this.propsProvider = propsProvider;
	}

}
