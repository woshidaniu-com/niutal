package com.woshidaniu.monitor.service.svcinterface;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.monitor.dao.entities.MonitorModel;

public interface MonitorService extends BaseService<MonitorModel> {

	void insertMemory(Map<String, Object> memory);

	void insertUsage(Map<String, Double> usage);

	List<Map<String, Object>> getHistoryMemory(HttpServletRequest request);

	List<Map<String, Double>> getHistoryUsage(HttpServletRequest request);
	
	
}
