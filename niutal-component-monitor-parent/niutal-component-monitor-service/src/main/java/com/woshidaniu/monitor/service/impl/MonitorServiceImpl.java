package com.woshidaniu.monitor.service.impl;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.basicutils.TimeUtils;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.monitor.dao.daointerface.MonitorDao;
import com.woshidaniu.monitor.dao.entities.MonitorModel;
import com.woshidaniu.monitor.service.svcinterface.MonitorService;

@Service
public class MonitorServiceImpl extends BaseServiceImpl<MonitorModel, MonitorDao>
		implements MonitorService {

	@Autowired
	protected MonitorDao monitorDao;
	protected String hostIP = "127.0.0.1";
	protected String jvm = "";
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.setDao(monitorDao);
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
		}
		if (null != addr) {
			try {
				hostIP = addr.getHostAddress();
			} catch (Exception e) {
			}
		}
		
		jvm = ManagementFactory.getRuntimeMXBean().getName();
		
	}

	@Override
	public void insertMemory(Map<String, Object> memory) {
		List<MonitorModel> list = new ArrayList<MonitorModel>();
		String time = memory.get("os.timestamp").toString();
		for (String key : memory.keySet()) {
			if(!"os.timestamp".equalsIgnoreCase(key)){
				list.add(new MonitorModel(hostIP, jvm, key, time, memory.get(key).toString()));
			}
		}
		getMonitorDao().insertMemory(list);
	}

	@Override
	public void insertUsage(Map<String, Double> usage) {
		List<MonitorModel> list = new ArrayList<MonitorModel>();
		String time = usage.get("os.timestamp").toString();
		for (String key : usage.keySet()) {
			if(!"os.timestamp".equalsIgnoreCase(key)){
				list.add(new MonitorModel(hostIP, jvm, key, time, usage.get(key).toString()));
			}
		}
		getMonitorDao().insertUsage(list);
	}

	@Override
	public List<Map<String, Object>> getHistoryMemory(HttpServletRequest request) {
		//获取期望获取什么时间区间的数据
		String limit = StringUtils.getSafeStr(request.getParameter("limit"), "5s");
		//开始时间戳
		long begin = System.currentTimeMillis() - TimeUtils.getTimeMillis(limit);
		
		return getMonitorDao().getHistoryMemory(String.valueOf(begin));
	}

	@Override
	public List<Map<String, Double>> getHistoryUsage(HttpServletRequest request) {
		//获取期望获取什么时间区间的数据
		String limit = StringUtils.getSafeStr(request.getParameter("limit"), "5s");
		//开始时间戳
		long begin = System.currentTimeMillis() - TimeUtils.getTimeMillis(limit);
		
		return getMonitorDao().getHistoryUsage(String.valueOf(begin));
	}

	public MonitorDao getMonitorDao() {
		return monitorDao;
	}

	public void setMonitorDao(MonitorDao monitorDao) {
		this.monitorDao = monitorDao;
	}
	
}
