package com.woshidaniu.common.file.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.file.TempFileService;
import com.woshidaniu.common.jmx.BaseLifecycleMBean;
import com.woshidaniu.util.base.MessageUtil;

/**
 * @author 康康 
 * the template code of active object pattern
 */
abstract class AutoCleanableTempFileService extends BaseLifecycleMBean implements TempFileService , AutoCleanableTempFileServiceMBean{

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private long tempFileKeepAliveDays = 7L;

	private long tempFileKeepAliveTime =  TimeUnit.DAYS.toMillis(tempFileKeepAliveDays);

	private File baseDir = FileUtils.getUserDirectory();

	private String tempFileRootDirName = "woshidaniu_temp";
	
	//5分钟
	private long cleanerScheduleIntervalSeconds =  60L*5;

	private ScheduledThreadPoolExecutor cleanerScheduledExecutor  = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
		
		private AtomicInteger i = new AtomicInteger(0);
		
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, String.format("%s_cleaner_%s",AutoCleanableTempFileService.class.getSimpleName(),i.getAndIncrement()));
		}
	});
	
	private ScheduledFuture<?> scheduledFuture = null;

	private File tempFileRootDir;

	private String cleanLockFileName = "cleaner.lock";
	
	private File lockFile;
	/**
	 * 后台调度次数
	 */
	protected final AtomicLong scheduleCount = new AtomicLong(0L);
	/**
	 * 创建文件个数
	 */
	protected final AtomicLong createCount = new AtomicLong(0L);
	/**
	 * 调度删除的个数
	 */
	protected final AtomicLong scheduleDeleteCount = new AtomicLong(0L);
	
	@Override
	protected void doStart() {
		
		super.doStart();
		
		{
			String val = MessageUtil.getText("niutal.autoCleanableTempFileService.tempFileKeepAliveDays");
			this.tempFileKeepAliveDays = StringUtils.isNotEmpty(val) ? Long.parseLong(val) : this.tempFileKeepAliveDays;
			this.tempFileKeepAliveTime = TimeUnit.DAYS.toMillis(tempFileKeepAliveDays);
		}
		
		{
			String val = MessageUtil.getText("niutal.autoCleanableTempFileService.cleanerScheduleIntervalSeconds");
			this.cleanerScheduleIntervalSeconds = StringUtils.isNotEmpty(val) ? Long.parseLong(val) : this.cleanerScheduleIntervalSeconds;
		}
		
		{
			String val = MessageUtil.getText("niutal.autoCleanableTempFileService.baseDir");
			if(StringUtils.isNotEmpty(val)) {
				File tempBaseDir = new File(val);
				if(!tempBaseDir.exists()) {
					try {
						tempBaseDir.mkdirs();						
						this.baseDir = tempBaseDir;
					}catch (Exception e) {
						log.error("创建base目录异常",e);
					}
				}
			}
		}
		try {
			this.doInit();
		} catch (Throwable t) {
			log.error("初始化工作目录[{}]异常",this.tempFileRootDir,t);
		}
	}

	private void doInit() {

		this.tempFileRootDir = new File(baseDir, tempFileRootDirName);
		if (this.tempFileRootDir.exists()) {
			log.info("使用公司临时文件工作根目录:{}", this.tempFileRootDir);
		} else {
			log.info("初始化创建公司临时文件工作根目录:{}", this.tempFileRootDir);
			this.tempFileRootDir.mkdirs();
		}
		this.lockFile = new File(this.tempFileRootDir,cleanLockFileName);
		if(!lockFile.exists()) {
			try {
				lockFile.createNewFile();
			} catch (IOException e) {
				//两个进程同时创建同一个名称的文件，可能会发生异常
				log.error("",e);
			}
		}
		this.scheduledFuture = this.cleanerScheduledExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				cleanModularDirs(false);
			}
		}, cleanerScheduleIntervalSeconds, cleanerScheduleIntervalSeconds, TimeUnit.SECONDS);
	}
	
	@Override
	protected void doStop() {
		if(this.scheduledFuture != null) {
			boolean cancelSuccess = this.scheduledFuture.cancel(true);
			if(cancelSuccess) {
				log.warn("stop schedule task success");
			}else {
				log.warn("stop schedule task fail");
			}
		}
		this.cleanerScheduledExecutor.shutdown();
		
		super.doStop();
	}

	private synchronized int cleanModularDirs(boolean force) {
		
		this.scheduleCount.incrementAndGet();
		
		int totalCount = 0;
		
		RandomAccessFile cleanLockFile = null;
		FileChannel channel = null;
		FileLock lock = null;
		try {
			cleanLockFile = new RandomAccessFile(this.lockFile, "rw");
			
			channel = cleanLockFile.getChannel();
			lock = channel.tryLock();
			
			if(lock != null) {
				File[] moduleDirs = this.tempFileRootDir.listFiles();
				if(moduleDirs != null && moduleDirs.length > 0) {
					for(File dir: moduleDirs) {
						if(dir.isDirectory()) {
							try {
								totalCount  += this.doCleanModularDir(dir,force);
							} catch (Throwable t) {
								log.error("清理工作目录[{}]异常",this.tempFileRootDir,t);
							}
						}else {
							//
						}
					}
				}
			}else {
				//执行到这里，说明其他的webapp实例已经在做这个动作了,我们也就不必做了,冲突避让
			}
		} catch (Throwable e) {
			log.error("",e);
		}finally {
			if(lock != null) {
				try {
					lock.release();
				} catch (IOException ex) {
					log.error("",ex);
				}
			}
			if(channel != null) {
				try {
					channel.close();
				} catch (IOException ex) {
					log.error("",ex);
				}
			}
			if(cleanLockFile != null) {
				try {
					cleanLockFile.close();					
				}catch (IOException ex) {
					log.error("",ex);
				}
			}
		}
		return totalCount;
	}

	private int doCleanModularDir(File dir,boolean force) {
		int count = 0;
		File[] files = dir.listFiles();
		if (files != null && files.length > 0) {
			long now = System.currentTimeMillis();
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f.isFile() && f.exists() && !f.isHidden()) {
					if(force) {
						f.delete();
						count++;
					}else {
						long lastModefied = f.lastModified();
						boolean needDelete = now - lastModefied > tempFileKeepAliveTime;
						if (needDelete) {
							f.delete();
							count++;
						}
						this.scheduleDeleteCount.incrementAndGet();
					}
				}
			}
		}
		return count;
	}
	
	protected synchronized File getTempRootDir() {
		if(!this.isRunning()) {
			throw new IllegalStateException("service status is not running!!!");
		}
		return this.tempFileRootDir;
	}

	@Override
	public long getScheduleCount() {
		return this.scheduleCount.get();
	}

	@Override
	public long getCreateCount() {
		return this.createCount.longValue();
	}

	@Override
	public long getScheduleDeleteCount() {
		return this.scheduleDeleteCount.longValue();
	}

	@Override
	public long dumpLeaveOverCount() {
		File[] moduleDirs = this.tempFileRootDir.listFiles();
		if(moduleDirs == null || moduleDirs.length <= 0) {
			return 0L;
		}
		long totalCount = 0L;
		for(File dir: moduleDirs) {
			if(dir.isDirectory()) {
				if(dir != null) {
					File[] fs = dir.listFiles();
					if(fs != null) {
						totalCount += fs.length;
					}
				}
			}
		}
		return totalCount;
	}
	
	@Override
	public String dumpLeaveOverTotalSize() {
		File[] moduleDirs = this.tempFileRootDir.listFiles();
		if(moduleDirs == null || moduleDirs.length <= 0) {
			return "0";
		}
		long totalSize = 0L;
		for(File dir: moduleDirs) {
			if(dir.isDirectory()) {
				if(dir != null) {
					File[] fs = dir.listFiles();
					if(fs != null) {
						for(File f : fs) {
							totalSize += f.length();
						}						
					}
				}
			}
		}
		return totalSize+" byte";
	}

	@Override
	public int forceClean() {
		return this.cleanModularDirs(true);
	}

	@Override
	public void cleanScheduleCount() {
		scheduleCount.set(0L);
	}
	
}
