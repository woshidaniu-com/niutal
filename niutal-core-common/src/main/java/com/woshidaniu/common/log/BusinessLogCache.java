package com.woshidaniu.common.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @描述 操作日志缓存
 * @author xiaokang
 *
 *
 */
public abstract class BusinessLogCache<T> {

	private static final Logger log = LoggerFactory.getLogger(BusinessLogCache.class);

	private ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
	
	/**
	 * 默认触发批量操作数目
	 */
	public static final int DEFAULT_TRIGGER = 100;

	/**
	 * 触发批量操作的缓存数量
	 */
	protected int trigger = DEFAULT_TRIGGER;

	/**
	 * 用户缓存操作日志对象
	 */
	private List<T> opeartionCache = Collections.synchronizedList(new ArrayList<T>());

	/**
	 * 清空缓存
	 * 
	 * @return
	 */
	protected void clear() {
		if (opeartionCache != null) {
			opeartionCache.clear();
		}
	}

	/**
	 * 增加
	 * 
	 * @param object
	 */
	public void add(T object) {
		if (opeartionCache != null && object != null) {
			opeartionCache.add(object);
			trigger();
		}
	}

	/**
	 * 增加
	 * 
	 * @param object
	 */
	public void addAll(List<T> objects) {
		if (opeartionCache != null && objects != null) {
			if (opeartionCache.size() == getTrigger()) {
				trigger();
			} else {
				opeartionCache.addAll(objects);
			}
		}
	}

	/**
	 * 删除
	 * 
	 * @param object
	 */
	public void delete(T object) {
		if (opeartionCache != null && opeartionCache.contains(object)) {
			opeartionCache.remove(object);
		}
	}

	/**
	 * 判断是否满足触发条件
	 * 
	 * @return
	 */
	protected boolean satisfied() {
		return opeartionCache != null && opeartionCache.size() >= getTrigger();
	}

	/**
	 * 手动触发持久化
	 */
	public void triggerManual() {
		if (opeartionCache != null && opeartionCache.size() > 0) {
			triggerMethod();
		}
	}

	/**
	 * 触发操作日志持久化操作<br>
	 * 
	 * 需要启动单独的线程操作<br>
	 */
	private void trigger() {
		if (satisfied()) {
			triggerMethod();
		}
	}

	/**
	 * 启动线程执行持久化操作
	 */
	private void triggerMethod() {
		try {
			newSingleThreadExecutor.submit(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					log.info("[-------操作日志缓存数超过阀值，触发批量持久化事件!-------]");
					boolean persistent = persistent();
					log.info(persistent ? "[-------操作日志批量持久化成功!-------]" : "[-------操作日志批量持久化失败!-------]");
					return persistent;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("日志持久化失败", e);
			handleTriggerException(e);
		}
	}

	/**
	 * 处理触发错误
	 * 
	 * @param ex
	 */
	protected void handleTriggerException(Exception ex) {
		// 暂时不做处理
	}

	/**
	 * 持久化，子类实现
	 * 
	 * 可以实现 数据库， 文件， 其他方式
	 */
	protected abstract boolean persistent() throws Exception;

	public BusinessLogCache() {
		super();
	}

	public BusinessLogCache(int trigger) {
		super();
		this.trigger = trigger;
	}

	protected List<T> getOpeartionCache() {
		return opeartionCache;
	}

	protected void setOpeartionCache(List<T> opeartionCache) {
		this.opeartionCache = opeartionCache;
	}

	public int getTrigger() {
		return trigger;
	}

	public void setTrigger(int trigger) {
		this.trigger = trigger;
	}

}
