package com.woshidaniu.monitor.api.factory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Vector;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

/**
 * @className ： SigarBeanFactory
 * @description ：  追加springContext生命周期方法，主动卸载sigar的动态链接库
 * @author ：康康（1571）
 * @date ： 2018年9月13日 下午6:53:16
 * @version V1.0
 */
public class SigarBeanFactory implements FactoryBean<Sigar>,DisposableBean{
	
	private static final Logger log = LoggerFactory.getLogger(SigarBeanFactory.class);
	
	private Sigar singleton;

	@Override
	public Sigar getObject() throws Exception {
		try {
			this.singleton  = (Sigar) SigarFactory.newSigar();
			File nativeLibrary = this.singleton.getNativeLibrary();
			log.info("sigar加载的动态链接库文件:"+nativeLibrary.getAbsolutePath());
		}catch(Exception e) {
			log.error("初始化Sigar实例异常",e);
			throw e;
		}
		return singleton;
	}

	@Override
	public Class<?> getObjectType() {
		return Sigar.class;
	}
	
	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void destroy() throws Exception {
		File nativeLibrary = this.singleton.getNativeLibrary();
		log.info("sigar卸载的动态链接库文件:"+nativeLibrary.getAbsolutePath());
		this.unloadNativeLibs();
	}
	
	private void unloadNativeLibs() {
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			Field field = ClassLoader.class.getDeclaredField("nativeLibraries");
			field.setAccessible(true);
			@SuppressWarnings("unchecked")
			Vector<Object> libs = (Vector<Object>) field.get(classLoader);
			Iterator<Object> it = libs.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				String msg = "卸载d动态链接库:"+o; 
				
				//FIXME 日志的生命周期destory在此之前调用?
				System.out.println(msg);
				
				log.info(msg);
				
				Method finalize = o.getClass().getDeclaredMethod("finalize", new Class[0]);
				finalize.setAccessible(true);
				finalize.invoke(o, new Object[0]);
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			log.error("卸载d动态链接库异常", ex);
		}
	}
}
