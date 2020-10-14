/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.monitor.service.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 		：康康（1571）
 * 基础调度执行
 */
abstract class AbstractIntervalTask implements Runnable{
	
	private static final Logger log = LoggerFactory.getLogger(AbstractIntervalTask.class);
	//任务名称
	private final String taskName = this.getClass().getSimpleName();
	//调度周期
	private int interval = 5;
	//是否存活
	private AtomicBoolean alive = new AtomicBoolean(false);
	
	//单一线程池的调度器,保证当前任务没有执行完，就不开始下一个任务
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
		//线程编号
		private AtomicInteger seq = new AtomicInteger(0);
		
		@Override
		public Thread newThread(Runnable r) {
			String name = taskName + "_" + seq.getAndIncrement();
			Thread t = new Thread(r, name);
			return t;
		}
	});

	public void startup() {
		if(alive.compareAndSet(false, true)) {
			log.info("启动周期调度任务:{},调度周期:{}秒",taskName,interval);
			executor.schedule(this, interval, TimeUnit.SECONDS);			
		}
	}
	
	public void shutdown() {
		if(alive.compareAndSet(true, false)) {
			log.info("停止周期调度任务:{}",taskName);
			executor.shutdown();
		}
	}
	
	protected  abstract void doRun();
	
	@Override
	public void run() {
		if(alive.get()) {
			this.doRun();
			if(alive.get()) {
				executor.schedule(this, interval, TimeUnit.SECONDS);
			}
		}
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
}
