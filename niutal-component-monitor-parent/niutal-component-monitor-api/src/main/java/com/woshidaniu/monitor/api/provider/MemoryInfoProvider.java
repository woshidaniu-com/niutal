package com.woshidaniu.monitor.api.provider;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.woshidaniu.metrics.utils.CapacityUtils.Unit;

public interface MemoryInfoProvider {

	public static final String SMS_PROVIDER = "monitor.provider";
	
	/**
	 * 默认：internal；将内存使用率和使用量维护在一个内存队列中，始终保持100个长度
	 */
	public static final String DEFAULT = "internal";
	/**
	 * 数据库：database；将内存使用率和使用量维护在数据库表结构，始终保持100个长度
	 */
	public static final String BY_DATABASE = "database";
	
	
	/**
	 * 服务提供者名称，该名称将对应系统提供的存储代码
	 */
	public String name();
	
	public List<Map<String, Object>> getHistoryMemory(HttpServletRequest request);
	
	public List<Map<String, Double>> getHistoryUsage(HttpServletRequest request);
	
	public Map<String, Object> getMemory(Unit unit);
	
	public Map<String, Double> getUsage();

	public boolean setMemory(Map<String, Object> memory);
	
	public boolean setUsage(Map<String, Double> usage);
	

}
