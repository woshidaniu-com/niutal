package com.woshidaniu.monitor.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hyperic.sigar.Sigar;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.api.conf.PropertiesProvider;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.metrics.jmx.JVMInfo;
import com.woshidaniu.metrics.jmx.JVMProperty;
import com.woshidaniu.metrics.sigar.OSEnvInfo;
import com.woshidaniu.metrics.utils.CapacityUtils;
import com.woshidaniu.metrics.utils.CapacityUtils.Unit;
import com.woshidaniu.monitor.api.provider.MemoryInfoProvider;
import com.woshidaniu.monitor.api.provider.MemoryInfoProviderManager;

@Controller
@RequestMapping(value = "/monitor/watch/")
public class AppMonitorController extends BaseController {
	
	
	@Resource
	protected Sigar sigar = null;
	@Resource
	protected MemoryInfoProviderManager providerManager = null;
	@Resource(name = "wacthPropsProvider")
	protected PropertiesProvider propsProvider = null;
	
	
/*	@ModelAttribute
	public Monitor get(@RequestParam(required=false) String id) {
		Monitor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = monitorService.get(id);
		}
		if (entity == null){
			entity = new Monitor();
		}
		return entity;
	}
*/	
	@RequiresRoles({"admin"})
	@RequiresPermissions("monitor-watch:cx")
	@RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
        
		
		Map<String, Object> jmxMap = new HashMap<String, Object>();
		
		//Java运行时信息
		jmxMap.putAll(JVMInfo.info());
		//内存使用信息（JVM内存+物理内存+虚拟内存）
		jmxMap.putAll(OSEnvInfo.memory(sigar, Unit.KB));

		long estimatedTime = System.currentTimeMillis() - Long.parseLong(String.valueOf(jmxMap.get(JVMProperty.JAVA_RUNTIME_STARTTIME.getKey())));

		jmxMap.put(JVMProperty.JAVA_RUNTIME_STARTTIME.getKey(), estimatedTime / 1000 / 60);
		
		model.addAttribute("jvm", jmxMap);
		
		//Java回收器信息
		model.addAttribute("gcs", JVMInfo.gc());
		
		//操作系统相关信息
		model.addAttribute("os", OSEnvInfo.info(sigar));
				
		
		
		//Java内存信息
		//model.addAttribute("memory", JVMInfo.memory(Unit.MB));
		//Java内存池信息
		//model.addAttribute("memoryPool", JVMInfo.memoryPool(Unit.MB));
		
		//参数设置
		model.addAttribute("props", getPropsProvider().props());
		
        return "/monitor/watch/index";
    }
	
	@ResponseBody
	@RequiresRoles({"admin"})
	@RequiresPermissions("monitor-watch:cx")
	@RequestMapping(value = "info", method = RequestMethod.POST )
	public Map<String, Object> info(HttpServletRequest request) throws Exception {
		
		String provider = getPropsProvider().props().getProperty(MemoryInfoProvider.SMS_PROVIDER);
		
		MemoryInfoProvider  memoryInfoProvider  = getProviderManager().getMemoryInfoProvider(provider);
		
		Map<String, Object> historyMap = new HashMap<String, Object>();
		
		//使用率
		historyMap.put("usageHistory", memoryInfoProvider.getHistoryUsage(request));
		historyMap.put("usage", memoryInfoProvider.getUsage());
		
		//使用量
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		//迭代集合中的数据对象
		Iterator<Map<String, Object>> ite = memoryInfoProvider.getHistoryMemory(request).iterator();
		
		Map<String, Object> tmpMap = null;
		while (ite.hasNext()) {
			Map<String, Object> item = ite.next();
			//创建临时Map对象
			tmpMap = new HashMap<String, Object>();
			//迭代原始值
			for (String key : item.keySet()) {
				//使用率和时间戳不处理
				if( !(key.endsWith(".usage") || key.endsWith(".timestamp")) ){
					//JVM记录换算成MB
					if(key.startsWith("jvm.memory")){
						tmpMap.put(key, CapacityUtils.getCapacity( Long.valueOf(String.valueOf(item.get(key))), Unit.MB));
					}
					//内存换算成GB
					else {
						tmpMap.put(key, CapacityUtils.getCapacity( Long.valueOf(String.valueOf(item.get(key))), Unit.GB));
					}
					
				}else {
					tmpMap.put(key, item.get(key));
				}
			}
			mapList.add(tmpMap);
		}
		
		historyMap.put("memoryHistory", mapList);
		
		return historyMap;
	}
	
	@ResponseBody
	@RequiresRoles({"admin"})
	@RequiresPermissions("monitor-watch:cx")
	@RequestMapping(value = "status", method = RequestMethod.POST )
	public Map<String, Object> status(Model model) throws Exception {
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		String provider = getPropsProvider().props().getProperty(MemoryInfoProvider.SMS_PROVIDER);
		
		MemoryInfoProvider  memoryInfoProvider  = getProviderManager().getMemoryInfoProvider(provider);
		
		//使用率
		dataMap.putAll(memoryInfoProvider.getUsage());
		//使用量
		Map<String, Object> memory = memoryInfoProvider.getMemory(Unit.NONE);
		//迭代原始值
		for (String key : memory.keySet()) {
			//使用率和时间戳不处理
			if( !(key.endsWith(".usage") || key.endsWith(".timestamp")) ){
				//JVM记录换算成MB
				if(key.startsWith("jvm.memory")){
					dataMap.put(key, CapacityUtils.getCapacity( Long.valueOf(String.valueOf(memory.get(key))), Unit.MB));
				}
				//内存换算成GB
				else {
					dataMap.put(key, CapacityUtils.getCapacity( Long.valueOf(String.valueOf(memory.get(key))), Unit.GB));
				}
				
			}else {
				dataMap.put(key, memory.get(key));
			}
		}
		
		return dataMap;
	}
	
	@ResponseBody
	@RequiresRoles({"admin"})
	@RequiresPermissions("monitor-watch:xg")
	@RequestMapping(value = "threshold", method = RequestMethod.POST )
	public Object threshold(@RequestParam String key,@RequestParam String value) throws Exception {
		try {
			getPropsProvider().set(key, value);
			return MessageKey.MODIFY_SUCCESS.getJson();
		} catch (Exception e) {
			logException(this, e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
	
	@ResponseBody
	@RequiresRoles({"admin"})
	@RequiresPermissions("monitor-watch:xg")
	@RequestMapping(value = "notice", method = RequestMethod.POST )
	public Object notice(@RequestParam Map<String, String> params) throws Exception {
		try {
			//删除临时值
			params.remove("noticeType");
			for (String key : params.keySet()) {
				getPropsProvider().set(key, params.get(key));
			}
			return MessageKey.MODIFY_SUCCESS.getJson();
		} catch (Exception e) {
			logException(this, e);
			return MessageKey.SYSTEM_ERROR.getJson();
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
	
}