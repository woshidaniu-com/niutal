package com.woshidaniu.common.action;

import org.apache.struts2.StrutsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.inject.Inject;
import com.woshidaniu.basicutils.BooleanUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.cache.CacheName;
import com.woshidaniu.common.log.User;
import com.woshidaniu.struts2.BaseActionSupport;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.WebContext;

@SuppressWarnings("serial")
public class BaseAction extends BaseActionSupport{
	
	protected  static final transient Logger log = LoggerFactory.getLogger(BaseAction.class);

	/**通用操作*/
	public static final String FAILED = "false";// 返回失败
	public static final String OPER_SAVE = "save";// 操作保存
	public static final String UPDATE = "update";
	public static final String SAVE = "save";
	public static final String DELETE = "delele";
	public static final String BATCH_DELETE = "batch_delete";
	public static final String BUSINESSEXCEPTION = "businessException";
	public static final String UNAUTHORIZEDEXCEPTION = "unauthorizedException";
	public static final String DATA_IE_FIX = "data-iefix";
	
	//Struts指定的国际化资源文件名称
	protected String[] resources;
	protected ApplicationContext applicationContext;
	protected String key = "";
	/**
	 * 全局异常对象
	 */
	protected Throwable exception;
	
	/**缓存实例*/
	protected CacheManager cacheManager;
	protected String cacheName;
	protected boolean cacheSupport = false;//是否支持缓存
	
	@Override
	public void prepare() throws Exception {
		this.applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.cacheManager = this.applicationContext.getBean(CacheManager.class);
		this.cacheSupport = cacheManager!=null && cacheManager.getCache(getCacheName()) != null;
	}
	
	@Inject(value = StrutsConstants.STRUTS_CUSTOM_I18N_RESOURCES)
    public void setResources(String resources) {
        this.resources = StringUtils.tokenizeToStringArray(resources);
    }
	
	/**
	 * 获取用户信息
	 */
	protected User getUser() {
		return WebContext.getUser();
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * <p>方法说明：设置栈值<p>
	 */
	protected void setKVToValuseStack(String key, Object value){
		getValueStack().set(key, value);
	}
	
	/**
	 * 校验防伪请求提交随机码
	 * @return
	 */
	protected boolean validateCsrfToken(){
		return Boolean.TRUE;
	}

	public boolean isCacheSupport() {
		return cacheSupport && BooleanUtils.toBoolean(MessageUtil.getText("niutal.cache.support"));
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public String getCacheName() {
		return cacheName == null ? CacheName.CACHE_BASIC : cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	
	public Cache getCache() {
		return getCacheManager().getCache(getCacheName());
	}
	
}
