package com.woshidaniu.pwdmgr.api.provider.def;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

import com.woshidaniu.basicutils.RandomUtils;
import com.woshidaniu.basicutils.uid.Sequence;
import com.woshidaniu.pwdmgr.api.model.BindCaptcha;
import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.api.model.BindTime;
import com.woshidaniu.pwdmgr.api.provider.CacheKeyProvider;
import com.woshidaniu.pwdmgr.api.provider.CaptchaProvider;
import com.woshidaniu.pwdmgr.api.provider.DatetimeProvider;

/**
 * 
 *@类名称		： CachedCaptchaProvider.java
 *@类描述		：基于缓存服务的验证码服务提供实现
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月10日 上午11:02:47
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class CachedCaptchaProvider implements CaptchaProvider, InitializingBean {

	protected CacheKeyProvider DEFAULT_KEY_PROVIDER = new DefaultCacheKeyProvider();
	//缓存服务提供对象
	protected CacheManager cacheManager;
	//缓存key提供对象
	protected CacheKeyProvider keyProvider;
	//时间戳提供对象
	protected DatetimeProvider timeProvider;
	//缓存对象名称
	protected String cacheName = "PWD_CAPTCHA_CACHE";
	//时间戳格式化格式
	protected String format = "yyyy-MM-dd HH:mm:ss";
	protected SimpleDateFormat sdf = new SimpleDateFormat(format);
	//验证码过期时间
	protected int effectTime = 1000 * 30;
	//高性能ID生成器
	protected Sequence sequence = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if( sequence == null){
			sequence = new Sequence();
		}
	}
	
	
	@Override
	public String name() {
		return this.getClass().getName();
	}
	
	protected String key(BindData data) {
		//获取唯一key
    	final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	return getKeyProvider().genKey(String.valueOf(classLoader.hashCode()), data);
	}
	
	@Override
	public BindCaptcha input(BindData data) {
    	//存储当前计算出的UUID值
		String uuid = this.key(data);
    	data.setUuid(uuid);
		//查询缓存对象
		ValueWrapper wrapper = getCacheManager().getCache(cacheName).get(uuid);
		if(wrapper != null){
			return (BindCaptcha) wrapper.get();
		}
		return null;
	}

	/**
	 * 只要能从缓存服务中获取到验证码就表示该验证码尚未失效，肯定是有效的值
	 * return ： -1 : 验证码对象为空；, 0 : 验证码过期, 1：验证码在有效期内
	 */
	@Override
	public String valid(BindData data,BindCaptcha captcha) {
		if(captcha == null){
			return "-1";
		}
		//当前时间
		BindTime now = timeProvider.dateTime(getFormat(), getEffectTime());
		//验证码发送时间
		BindTime send = new BindTime(captcha.getTimestamp() , getFormat(), getEffectTime());
		//验证码对象不为空且发送时间在有效时间内
		if(send.compareTo(now) > 0){
			return "1";
		}
		this.evict(data);
		return "0";
	}

	@Override
	public BindCaptcha gen(BindData data) {
		//当前时间
		BindTime now = timeProvider.dateTime(getFormat(), getEffectTime());
		return new BindCaptcha( String.valueOf(sequence.nextId()) , RandomUtils.genRandomNum(6), now.getTimestamp());
	}

	@Override
	public boolean store(BindData data,BindCaptcha captcha) {
		//存储缓存对象
		cacheManager.getCache(cacheName).put(data.getUuid(), captcha );
		return true;
	}


	@Override
	public boolean evict(BindData data) {
		//删除缓存中的过期值
		getCacheManager().getCache(cacheName).evict(data.getUuid() == null ? this.key(data) : data.getUuid());
		return true;
	}
	
	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public CacheKeyProvider getKeyProvider() {
		return keyProvider == null ? DEFAULT_KEY_PROVIDER : keyProvider ;
	}

	public void setKeyProvider(CacheKeyProvider keyProvider) {
		this.keyProvider = keyProvider;
	}
	
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
		this.sdf = new SimpleDateFormat(format);
	}

	public int getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(int effectTime) {
		this.effectTime = effectTime;
	}

	public DatetimeProvider getTimeProvider() {
		return timeProvider;
	}

	public void setTimeProvider(DatetimeProvider timeProvider) {
		this.timeProvider = timeProvider;
	}

	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}
	
}
