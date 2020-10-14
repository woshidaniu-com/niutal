package com.woshidaniu.monitor.service.provider;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.woshidaniu.monitor.api.provider.NoticeProvider;
import com.woshidaniu.monitor.service.svcinterface.MonitorService;

/**
 * 
 *@类名称		: MemoryInfoDatabaseProvider.java
 *@类描述		：将内存使用率和使用量维护在数据库表结构，始终保持50个长度
 *@创建人		：kangzhidong
 *@创建时间	：2017年6月21日 下午5:48:13
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class MemoryInfoDatabaseProvider extends MemoryInfoProviderAdapter {

	protected final Logger LOG = LoggerFactory.getLogger(MemoryInfoDatabaseProvider.class);
	@Resource
	protected MonitorService monitorService;
	@Autowired
	protected NoticeProvider noticeProvider;
	
	@Override
	public String name() {
		return BY_DATABASE;
	}
	
	@Override
	public boolean setMemory(Map<String, Object> memory) {
		try {
			getMonitorService().insertMemory(memory);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean setUsage(Map<String, Double> usage) {
		try {
			getMonitorService().insertUsage(usage);
			getNoticeProvider().notice(usage);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
		return true;
	}
	
	@Override
	public List<Map<String, Object>> getHistoryMemory(HttpServletRequest request) {
		return getMonitorService().getHistoryMemory(request);
	}

	@Override
	public List<Map<String, Double>> getHistoryUsage(HttpServletRequest request) {
		return getMonitorService().getHistoryUsage(request);
	}

	public MonitorService getMonitorService() {
		return monitorService;
	}

	public void setMonitorService(MonitorService monitorService) {
		this.monitorService = monitorService;
	}
	
	public NoticeProvider getNoticeProvider() {
		return noticeProvider;
	}

	public void setNoticeProvider(NoticeProvider noticeProvider) {
		this.noticeProvider = noticeProvider;
	}
	
}