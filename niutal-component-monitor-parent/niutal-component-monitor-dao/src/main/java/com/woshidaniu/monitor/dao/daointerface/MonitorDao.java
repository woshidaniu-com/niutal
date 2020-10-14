package com.woshidaniu.monitor.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.monitor.dao.entities.MonitorModel;

@Repository("monitorDao")
public interface MonitorDao extends BaseDao<MonitorModel> {

	void insertUsage(List<MonitorModel> list);

	void insertMemory(List<MonitorModel> list);

	List<Map<String, Object>> getHistoryMemory(@Param("begin") String begin);
	
	List<Map<String, Double>> getHistoryUsage(@Param("begin") String begin);
	
}
