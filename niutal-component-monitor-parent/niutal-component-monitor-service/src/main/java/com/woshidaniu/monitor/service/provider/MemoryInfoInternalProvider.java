package com.woshidaniu.monitor.service.provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.basicutils.TimeUtils;
import com.woshidaniu.monitor.api.provider.NoticeProvider;

/**
 * 
 *@类名称		: MemoryInfoInternalProvider.java
 *@类描述		：将内存使用率和使用量维护在一个内存队列中，始终保持50个长度
 *@创建人		：kangzhidong
 *@创建时间	：2017年6月21日 下午5:41:24
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class MemoryInfoInternalProvider extends MemoryInfoProviderAdapter {

	// 声明一个容量为100的缓存队列
	protected static final BlockingQueue<Map<String, Object>> MEMORY_SNAPSHOT = new ArrayBlockingQueue<Map<String, Object>>(50); 
	protected static final BlockingQueue<Map<String, Double>> USAGE_SNAPSHOT = new ArrayBlockingQueue<Map<String, Double>>(50); 
	@Autowired
	protected NoticeProvider noticeProvider;
	
	@Override
	public String name() {
		return DEFAULT;
	}

	@Override
	public List<Map<String, Object>> getHistoryMemory(HttpServletRequest request) {
		//获取期望获取什么时间区间的数据
		String limit = StringUtils.getSafeStr(request.getParameter("limit"), "1m");
		//开始时间戳
		long begin = System.currentTimeMillis() - TimeUtils.getTimeMillis(limit);
		//定义结果集合
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		//迭代集合中的数据对象
		Iterator<Map<String, Object>> ite = MEMORY_SNAPSHOT.iterator();
		while (ite.hasNext()) {
			Map<String, Object> item = ite.next();
			long timestamp = Long.parseLong(item.get("os.timestamp").toString());
			//筛选范围内的数据
			if(timestamp <= begin){
				mapList.add(item);
			}
		}
		return mapList;
	}

	@Override
	public List<Map<String, Double>> getHistoryUsage(HttpServletRequest request) {
		//获取期望获取什么时间区间的数据
		String limit = StringUtils.getSafeStr(request.getParameter("limit"), "1m");
		//开始时间戳
		long begin = System.currentTimeMillis() - TimeUtils.getTimeMillis(limit);
		//定义结果集合
		List<Map<String, Double>> mapList = new ArrayList<Map<String,Double>>();
		//迭代集合中的数据对象
		Iterator<Map<String, Double>> ite = USAGE_SNAPSHOT.iterator();	
		while (ite.hasNext()) {
			Map<String, Double> item = ite.next();
			long timestamp = new BigDecimal(item.get("os.timestamp").toString()).longValue();
			//筛选范围内的数据
			if(timestamp <= begin){
				mapList.add(item);
			}
		}
		return mapList;
	}
	
	@Override
	public boolean setMemory(Map<String, Object> memory) {
		return MEMORY_SNAPSHOT.offer(memory);
	}

	@Override
	public boolean setUsage(Map<String, Double> usage) {
		getNoticeProvider().notice(usage);
		return USAGE_SNAPSHOT.offer(usage);
	}

	public NoticeProvider getNoticeProvider() {
		return noticeProvider;
	}

	public void setNoticeProvider(NoticeProvider noticeProvider) {
		this.noticeProvider = noticeProvider;
	}
	
}
