package com.woshidaniu.monitor.service.task;

import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.hyperic.sigar.Sigar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.api.conf.PropertiesProvider;
import com.woshidaniu.metrics.sigar.OSEnvInfo;
import com.woshidaniu.monitor.api.provider.MemoryInfoProvider;
import com.woshidaniu.monitor.api.provider.MemoryInfoProviderManager;
/**
 * @author 大康
 * 基础代码
 * @author ：康康（1571）
 * 调度线程保护，原先的设计方式有缺陷，spring的调度框架无法及时销毁线程，销毁各种Bean的顺序无法控制，导致有可能获取的是已经卸载过dll的Sigar
 */
public class MemoryInfoWatchTask extends AbstractIntervalTask{

	protected final Logger LOG = LoggerFactory.getLogger(MemoryInfoWatchTask.class);
	
	@Resource
	protected Sigar sigar = null;
	
	@Resource
	protected MemoryInfoProviderManager providerManager;
	
	@Resource(name = "wacthPropsProvider")
	protected PropertiesProvider propsProvider = null;
	
	@PostConstruct
	@Override
	public void startup() {
		super.startup();
	}
	
	@PreDestroy
	@Override
	public void shutdown() {
		super.shutdown();
	}
	
	/**
	 * 使用率采集：JVM,RAM,CPU
	 */
	public void usage(){
		PropertiesProvider pp = getPropsProvider();
		Properties prop = pp.props();
		String provider = prop.getProperty(MemoryInfoProvider.SMS_PROVIDER);

		Map<String, Double> usageMap = null; 
		try {
			usageMap = OSEnvInfo.usage(sigar);
		} catch (Throwable e) {
			LOG.error("获得使用率信息异常",e);
		}
		if(usageMap != null) {
			MemoryInfoProviderManager manage = getProviderManager();
			MemoryInfoProvider mp = manage.getMemoryInfoProvider(provider);
			mp.setUsage(usageMap);			
		}
	}
	
	/**
	 *	使用量采集：JVM,RAM,SWAP
	 */
	public void memory(){
		try {
			
			PropertiesProvider pp = getPropsProvider();
			Properties prop = pp.props();
			String provider = prop.getProperty(MemoryInfoProvider.SMS_PROVIDER);

			Map<String, Object> memMap = null; 
			try {
				memMap = OSEnvInfo.memory(sigar);
			} catch (Throwable e) {
				LOG.error("获得内存信息异常",e);
			}
			if(memMap != null) {
				MemoryInfoProviderManager manage = getProviderManager();
				MemoryInfoProvider mp = manage.getMemoryInfoProvider(provider);
				mp.setMemory(memMap);			
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
	
	public Sigar getSigar() {
		return sigar;
	}

	public void setSigar(Sigar sigar) {
		this.sigar = sigar;
	}

	public MemoryInfoProviderManager getProviderManager() {
		return providerManager;
	}

	public void setProviderManager(MemoryInfoProviderManager providerManager) {
		this.providerManager = providerManager;
	}

	public PropertiesProvider getPropsProvider() {
		return propsProvider;
	}

	public void setPropsProvider(PropertiesProvider propsProvider) {
		this.propsProvider = propsProvider;
	}

	@Override
	protected void doRun() {
		this.memory();
		this.usage();
	}

}
