package com.woshidaniu.globalweb.interceptor;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.factory.SessionFactory;
import com.woshidaniu.common.global.GlobalXtszx;
import com.woshidaniu.security.algorithm.MD5Codec;
import com.woshidaniu.service.common.ICommonSqlService;
import com.woshidaniu.struts2.interceptor.BaseAbstractInterceptor;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.util.request.WebRequestUtils;
import com.woshidaniu.web.core.http.HttpStatus;
/**
 * 
 *@类名称:RequestRefreshLimitInterceptor.java
 *@类描述：防刷新拦截器；解决恶意刷新占用服务器资源【内存，数据链接】问题
 *@创建人：kangzhidong
 *@创建时间：Oct 20, 2015 11:24:16 AM
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@Deprecated
@SuppressWarnings( { "serial"})
public class RequestRefreshLimitInterceptor extends BaseAbstractInterceptor {

	private static final long serialVersionUID = 1L;
	//默认10秒钟 
	private static final long DEFAULT_INTERVAL = 10L;
	//默认20秒钟 
	private static final long DEFAULT_EXPIRE_TIME = 20L;
	//默认20秒钟 
	private static final long DEFAULT_CLEAR_TIME = 20L;
	// 默认最大访问次数
	private static final int DEFAULT_MAX_REFRESH_COUNT = 100;
	
	// 以下两个值可从文件或数据库设置，从而达到参数的可设置
	private static long interval = DEFAULT_INTERVAL;
	private static long expire = DEFAULT_EXPIRE_TIME;	
	private static long clearTime = DEFAULT_CLEAR_TIME;
	private static int maxCount = DEFAULT_MAX_REFRESH_COUNT;
	private static boolean on = true;
	private String message = "请勿频繁刷新或者点击菜单,请<label id='times' data-time='{0}'>{1}</label>秒后再操作!~";
	private static Logger LOG = LoggerFactory.getLogger(RequestRefreshLimitInterceptor.class);
    //系统设置service
    private ICommonSqlService commonSqlService;
    
    @Override
	public void init() {
		
		commonSqlService        = (ICommonSqlService)ServiceFactory.getService("commonSqlService");
		
		clearTime = StringUtils.getSafeLong(MessageUtil.getText("refresh.cache.clearTime"), DEFAULT_CLEAR_TIME + "");
		 
	}
    
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		LOG.debug("RequestRefreshLimitInterceptor..."); 
		//【防恶意刷新】过滤器是否开启(优先取数据库)
		on = StringUtils.getSafeBoolean(commonSqlService.cxXtszxz(GlobalXtszx.REFRESH_ON), StringUtils.getSafeStr(MessageUtil.getText("refresh.on"), "false"));
		//如果是关闭了防止恶意刷新过滤，直接丢给下一个过滤器处理
		if(on){
			
			//认定恶意刷新【周期时间】(优先取数据库)
			interval = StringUtils.getSafeLong(commonSqlService.cxXtszxz(GlobalXtszx.REFRESH_INTERVAL), StringUtils.getSafeStr(MessageUtil.getText("refresh.interval"), DEFAULT_INTERVAL + ""));
			//认定恶意刷新【同一URL周期内刷新的次数】(优先取数据库)
			maxCount = StringUtils.getSafeInt(commonSqlService.cxXtszxz(GlobalXtszx.REFRESH_COUNT), StringUtils.getSafeStr(MessageUtil.getText("refresh.count"), DEFAULT_MAX_REFRESH_COUNT + ""));
			
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response  =  ServletActionContext.getResponse();
			// 访问时间
			Date now = new Date();
			// 访问者的IP地址
			String sessionIP = SessionFactory.getRemoteAddr();
			// 获取doType参数
			String doType = request.getParameter("doType");
			
			/**
			 * 2.2截取请求URL
			 */
			String currentURL = request.getRequestURI();
			String targetURL = "";
			if (-1 != currentURL.indexOf("?"))// 普通<form>标签是?分隔传来的参数
			{
				String paramURL = currentURL.substring(currentURL.indexOf("?", 0), currentURL.length());// 参数URL
				int targetLength = currentURL.length() - paramURL.length();// 去掉请求参数Length
				targetURL = currentURL.substring( currentURL.indexOf("/", 1), targetLength);
				//System.out.println("去掉请求参数路径URL:" + targetURL);
			} else if (-1 != currentURL.indexOf(";"))// struts2标签<s:form>标签是;分隔传来的参数
			{
				String paramURL = currentURL.substring(currentURL.indexOf(";", 0), currentURL.length());// 参数URL
				int targetLength = currentURL.length() - paramURL.length();// 去掉请求参数Length
				targetURL = currentURL.substring(currentURL.indexOf("/", 1), targetLength);
				//System.out.println("去掉请求参数路径URL:" + targetURL);
			} else {
				targetURL = currentURL.substring(currentURL.indexOf("/", 1), currentURL.length());
				//System.out.println("请求路径URL:" + sessionIP + targetURL);
			}
			
			String securityKey = MD5Codec.encrypt((sessionIP + targetURL).getBytes()).toUpperCase();
			
			//log.info("请求IP:"+ sessionIP);
			//log.info("请求路径URI:"+targetURL);
			//log.info("请求路径SecurityKey:" + securityKey);
			
			//判断是否ajax请求
			boolean isAjaxRequest =   WebRequestUtils.isAjaxRequest(request);
			 
			// 判断当前ID是否频繁访问
			if (LocalCache.getInstance().isUpCount(securityKey)){
				if(isAjaxRequest){
					//HTTP Status 904（恶意刷新） 
            		response.setStatus(HttpStatus.SC_SPITEFUL_REFRESH);
            		if(doType!=null && doType.equalsIgnoreCase("query")){
            			model.getQueryModel().setItems(empty);
            			invocation.getStack().set(Result.DATA, model.getQueryModel());
            		}else{
            			invocation.getStack().set(Result.DATA, model);
            		}
            		return Result.DATA;
				}else{
					invocation.getStack().set(Result.MESSAGE, MessageUtil.setParams(message , new Object[]{interval,interval}));
					return Result.EX_SPITEFUL;
				}
			}else{
				// 当前访问者为初次访问
				LocalCache.getInstance().increment(securityKey, now);
			}
			return invocation.invoke();
		}else{
			return invocation.invoke();
		}
	}

	// 自己实现的本地内存缓存
	private static class LocalCache {

		// CopyOnWriteArrayList<Long>
		private static final ConcurrentHashMap<String, Visitor> map = new ConcurrentHashMap<String,Visitor>();

		private static final LocalCache cache = new LocalCache();

		private LocalCache() {
			//启动定时清理过期数据心跳线程
	        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable(){

				@Override
				public void run() {
					
					for (String key : map.keySet()) {
						Visitor vis = map.get(key);
						if ((System.currentTimeMillis() - vis.getRequestTimeQueue().getLastTime()) > 1000 * expire) {
							map.remove(vis.getSessionIP());
							LOG.info("remove cache:" + key);
						}
					}
					
				}
	        	
	        }, 0, 1000 * clearTime, TimeUnit.MILLISECONDS);
		}

		public static LocalCache getInstance() {
			return cache;
		}

		// 增长指定URL的点击次数
		public void increment(String sessionIP,Date now) {
			Visitor vis = map.get(sessionIP);
			if (vis == null) {
				// 当前访问者为初次访问
				ArrayTime timeQueue = new ArrayTime();
				timeQueue.setLength(maxCount);
				timeQueue.init();
				vis = new Visitor();
				vis.setSessionIP(sessionIP);
				vis.setRequestTimeQueue(timeQueue);
				vis.getRequestTimeQueue().insert(now.getTime());
				map.put(sessionIP, vis);
			}
			
			ArrayTime timeQueue = map.get(sessionIP).getRequestTimeQueue();
			timeQueue.setLength(maxCount);
			timeQueue.insert(now.getTime());
			// 小于10秒，但访问超过10次
			timeQueue.insert(now.getTime());// 插入当前请求时间
		}

		// 是否到达指定数量
		public boolean isUpCount(String sessionIP) {
			Visitor vis = map.get(sessionIP);
			if (vis == null) {
				return false;
			}
			ArrayTime timeQueue = map.get(sessionIP).getRequestTimeQueue();
			// 得到最后一次和第一次的访问时间差
			Long span = timeQueue.getLast() - timeQueue.getFirst();
			if (span < 1000 * interval && timeQueue.getLast() != 0) {
				return true;
			}
			return false;
		}

	}

	private static class Visitor implements Serializable {

		/*
		 * Creates a new instance of Visitor外网访问者，以sessionIP作为标识
		 * 违反访问规则将其IP列为受限IP，拒绝访问
		 */
		private String sessionIP = null;
		private ArrayTime requestTimeQueue = new ArrayTime();

		public Visitor() {
		}
		
		public String getSessionIP() {
			return sessionIP;
		}

		public void setSessionIP(String sessionIP) {
			this.sessionIP = sessionIP;
		}

		public void setRequestTimeQueue(ArrayTime requestTimeQueue) {
			this.requestTimeQueue = requestTimeQueue;
		}

		public ArrayTime getRequestTimeQueue() {
			return this.requestTimeQueue;
		}
	}
	
	@SuppressWarnings("unused")
	private static class ArrayTime implements Serializable{

		private long[] time;
		private int length = 10; // 默认为十次（10s内刷新10次则违反规则）

		public ArrayTime() {
		}

		public void init() {
			time = new long[length];
		}

		
		public int getLength() {
			return this.length;
		}

		public void setLength(int len) {
			this.length = len;
		}

		public long getLast() {
			return this.time[length - 1];
		}

		public long getLastTime() {
			long lastTime = getLast();
			for (long last : this.time) {
				if(last == 0){
					break;
				}
				lastTime = last;
			}
			return lastTime;
		}
		
		public long getFirst() {
			return this.time[0];
		}

		public long getElement(int i) {
			return time[i];
		}

		public void insert(long nextTime) {
			// 数组已经满了
			if (this.getLast() != 0){
				// 去掉首元素，将数组元素顺序前移，nextTime插到最后
				for (int i = 0; i < this.length - 1; i++) {
					time[i] = time[i + 1];
				}
				this.time[length - 1] = nextTime;
			} else {
				// 插到下一个，不用排序
				int j = 0;
				while (time[j] != 0) {
					j++;
				}
				time[j] = nextTime;
			}
		}
	}

}
