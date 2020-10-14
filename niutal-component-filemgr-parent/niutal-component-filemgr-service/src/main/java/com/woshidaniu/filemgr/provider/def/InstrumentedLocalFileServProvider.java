package com.woshidaniu.filemgr.provider.def;

import static com.codahale.metrics.MetricRegistry.name;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import com.codahale.metrics.Timer;
import com.codahale.metrics.biz.MetricsFactory;
import com.woshidaniu.filemgr.api.FileObject;

/**
 *  #文件存储方式：<br/>
 *	#FileServ.LOCAL：表示使用应用指定存路径，该方式仅适用单服务器模式，集群环境下对会出现文件找不到异常<br/>
 */
public class InstrumentedLocalFileServProvider extends LocalFileServProvider {

	@Resource
	protected MetricsFactory metricsFactory;
	protected Timer getTimer, putTimer, delTimer;
	 
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		this.getTimer = metricsFactory.getTimer(name(this.getClass(), "filemgr", "local", "gets"));
        this.putTimer = metricsFactory.getTimer(name(this.getClass(), "filemgr", "local", "puts"));
        this.delTimer = metricsFactory.getTimer(name(this.getClass(), "filemgr", "local", "puts"));
	}
	
	@Override
	public FileObject output(File file, String fileName) throws Exception {
		final Timer.Context ctx = putTimer.time();
        try {
            return super.output(file, fileName);
        } finally {
            ctx.stop();
        }
	}
	
	@Override
	public FileObject output(byte[] bytes, String fileName) throws Exception {
		final Timer.Context ctx = putTimer.time();
        try {
            return super.output(bytes, fileName);
        } finally {
            ctx.stop();
        }
	}
	
	@Override
	public File file(String uid) throws Exception {
		final Timer.Context ctx = getTimer.time();
        try {
            return super.file(uid);
        } finally {
            ctx.stop();
        }
	}
	
	@Override
	public InputStream input(String uid)  throws Exception{
		final Timer.Context ctx = getTimer.time();
        try {
            return super.input(uid);
        } finally {
            ctx.stop();
        }
	}

	@Override
	public void copyLarge(String uid, OutputStream output) throws Exception {
		final Timer.Context ctx = getTimer.time();
        try {
            super.copyLarge(uid, output);
        } finally {
            ctx.stop();
        }
	}
	
	@Override
	public boolean delete(String uid) throws Exception{
		final Timer.Context ctx = delTimer.time();
        try {
            return super.delete(uid);
        } finally {
            ctx.stop();
        }
	}
	
}
