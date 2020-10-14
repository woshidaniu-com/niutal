package com.woshidaniu.i18n.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.struts2.StrutsException;
import org.springframework.enhanced.utils.ResourceBundleUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.TextProviderFactory;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.woshidaniu.basemodel.bundle.MultipleResourceBundle;
import com.woshidaniu.basemodel.bundle.PairListResourceBundle;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.i18n.dao.entities.I18nModel;
import com.woshidaniu.i18n.provider.I18nProvider;
import com.woshidaniu.struts2.utils.ValueStatckUtils;

public class I18nMessageUtils {

	protected static final String DEFAULT_KEY = "default";
	
	// 用于保存TextProvider的实例对象
    private static final ConcurrentMap<String, TextProvider> textProvider_Map = new ConcurrentHashMap<String, TextProvider>();
    
    private static ClassLoader getCurrentThreadContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
    
    /**
     * Creates a key to used for lookup/storing in the bundle misses cache.
     *
     * @param prefix      the prefix for the returning String - it is supposed to be the ClassLoader hash code.
     * @param expression  the expression of the bundle .
     * @return the key to use for lookup/storing in the bundle misses cache.
     */
    private static String createMissesKey(Locale locale, String prefix, String ... modules) {
        return prefix  + "_" + StringUtils.join(modules,"_") + locale.toString();
    }
    
 	//Struts指定的国际化资源文件名称
 	protected static String[] resources;
	// 国际化数据提供者对象名称：用于从Spring上下文获取该对象实例
	protected static String provider;
	
	public static void setResources(String[] resources) {
		I18nMessageUtils.resources = resources;
	}
	
	public static void setProvider(String provider) {
		I18nMessageUtils.provider = provider;
	}
	
	public static String[] getResources() {
		return (resources == null ? new String[]{"i18n/message"} : resources);
	}

	public static String getProvider() {
		return (provider == null ? "i18nProvider" : provider);
	}

	/**
	 * 获取Struts默认国际化资源
	 */
	public static List<ResourceBundle> getResourceBundles() {
		
		final List<ResourceBundle> bundleList = new ArrayList<ResourceBundle>();
		final List<ResourceBundle> moduleBundleList = new ArrayList<ResourceBundle>();
		final Locale locale = ActionContext.getContext().getLocale();
		
		//加载Struts配置的国际化资源文件和加上模块名称后的国际化资源
		for (String resource : getResources()) {
			
			String bundleName = "";
			try {
				
				//加载默认国际化资源文件
		    	ResourceBundle bundle = (ResourceBundle) ValueStatckUtils.findValue("getTexts('" + resource + "')");
		    	
		    	if (bundle == null) {
		            bundle = LocalizedTextUtil.findResourceBundle(resource, locale);
		    	}
		    	if (bundle != null) {
		    		bundleList.add(bundle);
		    	}
				
		    	//加载模块国际化资源文件
		    	
				//message-module,message-jwglxt-module
		    	if(StringUtils.isEmpty(resource)){
		    		continue;
		    	}
		    	
		    } catch (Exception e) {
		        String msg = "Could not find the bundle " + bundleName;
		        throw new StrutsException(msg, e);
		    }
		}
		
		//最后为了防止部分国际化资源没有加载成功，获取“message-”开头的国际化资源
		List<ResourceBundle> bundles = ResourceBundleUtils.getResourceBundles("classpath*:i18n/message-*_" + locale.toString() + ".properties");
		if(bundles != null){
			bundleList.addAll(bundles);
		}
		
		//必须有指定了模块的国际化资源，才会替换掉默认的国际化资源解析对象
	    if (moduleBundleList.size() > 0 ) {
			moduleBundleList.addAll(bundleList);
			return moduleBundleList;
	    }
	    return bundleList;
	}

	/**
	 * 获取指定模块+Struts默认国际化资源
	 */
	public static List<ResourceBundle> getResourceBundles(String ... modules) {
		
		//国际化数据提供者实例
		final I18nProvider i18nProvider = ServiceFactory.getService( getProvider(), I18nProvider.class);
		
		final List<ResourceBundle> bundleList = new ArrayList<ResourceBundle>();
		final List<ResourceBundle> moduleBundleList = new ArrayList<ResourceBundle>();
		final Locale locale = ActionContext.getContext().getLocale();

		//加载指定模块国际化资源文件
		for (String module : modules) {
			
			module = ValueStatckUtils.findString(module, "module", "Resource bundle name is required. Example: foo or foo_en");
		    
			if(i18nProvider != null){
				//准备国际化js数据
				I18nModel i18nModel = i18nProvider.getI18nModel(module);
				if(i18nModel != null){
					//有独立的国际化数据
		    		if(i18nModel.getI18nList() != null && i18nModel.getI18nList().size() > 0 ){
		    			bundleList.add(new PairListResourceBundle(i18nModel.getI18nList()));
		    		}
				}
			}
			
			String i18nModule = "i18n/" + module;
			//加载默认国际化资源文件
			ResourceBundle bundle = (ResourceBundle) ValueStatckUtils.findValue("getTexts('" + i18nModule + "')");
			if (bundle == null) {
		        bundle = LocalizedTextUtil.findResourceBundle(i18nModule, locale);
			}
			if (bundle != null) {
				moduleBundleList.add(bundle);
			}
			
		}
		
		//加载Struts配置的国际化资源文件和加上模块名称后的国际化资源
		for (String resource : getResources()) {
			
			String bundleName = "";
			try {
				
				//加载默认国际化资源文件
		    	ResourceBundle bundle = (ResourceBundle) ValueStatckUtils.findValue("getTexts('" + resource + "')");
		    	
		    	if (bundle == null) {
		            bundle = LocalizedTextUtil.findResourceBundle(resource, locale);
		    	}
		    	if (bundle != null) {
		    		bundleList.add(bundle);
		    	}
				
		    	//加载模块国际化资源文件
		    	
				//message-module,message-jwglxt-module
		    	if(StringUtils.isEmpty(resource)){
		    		continue;
		    	}
		
		    	for (String module : modules) {
		    		
		    		module = ValueStatckUtils.findString(module, "module", "Resource bundle name is required. Example: foo or foo_en");
		    		
		    		bundleName = resource + "-" + module;
		        	/**
		             * @see com.opensymphony.xwork2.TextProviderSupport.getTexts(String aBundleName)
		             * @see LocalizedTextUtil.findResourceBundle(aBundleName, getLocale());
		             * @see java.util.PropertyResourceBundle
		             * @see java.util.ListResourceBundle
		             */
		        	ResourceBundle moduleBundle = (ResourceBundle) ValueStatckUtils.findValue("getTexts('" + bundleName + "')");
		        	if (moduleBundle == null) {
		        		moduleBundle = LocalizedTextUtil.findResourceBundle(bundleName, locale);
		        	}
		        	if (moduleBundle != null) {
		        		moduleBundleList.add(moduleBundle);
		        	}
				}
		    	
		    } catch (Exception e) {
		        String msg = "Could not find the bundle " + bundleName;
		        throw new StrutsException(msg, e);
		    }
		}
		
		//最后为了防止部分国际化资源没有加载成功，获取“message-”开头的国际化资源
		List<ResourceBundle> bundles = ResourceBundleUtils.getResourceBundles("classpath*:i18n/message-*_" + locale.toString() + ".properties");
		if(bundles != null){
			bundleList.addAll(bundles);
		}
		
		//必须有指定了模块的国际化资源，才会替换掉默认的国际化资源解析对象
	    if (moduleBundleList.size() > 0 ) {
			moduleBundleList.addAll(bundleList);
			return moduleBundleList;
	    }
	    return bundleList;
	}
	
	/**
     * If called first time it will create {@link com.opensymphony.xwork2.TextProviderFactory},
     * inject dependency (if {@link com.opensymphony.xwork2.inject.Container} is accesible) into in,
     * then will create new {@link com.opensymphony.xwork2.TextProvider} and store it in a field
     * for future references and at the returns reference to that field
     * 获取指定模块相关的TextProvider对象
     * @return reference to field with TextProvider
     */
	public static TextProvider getTextProvider(String... modules) {
		final ClassLoader classLoader = getCurrentThreadContextClassLoader();
		final Locale locale = ActionContext.getContext().getLocale();
		String key = createMissesKey(locale, String.valueOf(classLoader.hashCode()), modules);
		//获取缓存对象
		TextProvider ret = textProvider_Map.get(key);
		if (ret != null) {
			return ret;
		}
		
		final List<ResourceBundle> moduleBundleList = getResourceBundles(modules);
		ResourceBundle bundle = new MultipleResourceBundle(moduleBundleList.toArray(new ResourceBundle[moduleBundleList.size()]));
		TextProviderFactory tpf = new TextProviderFactory();
		Container container = ActionContext.getContext().getContainer();
		if (container != null) {
			container.inject(tpf);
		}
		ret = tpf.createInstance(bundle,new LocaleProvider() {
			
			public Locale getLocale() {
				return locale;
			}

			public boolean isValidLocaleString(String localeStr) {
				return true;
			}

			public boolean isValidLocale(Locale locale) {
				return true;
			}
		});
		TextProvider existing = textProvider_Map.putIfAbsent(key, ret);
		if (existing != null) {
			ret = existing;
		}
		return ret;
	}

	/**
	 * 获取指定模块相关的TextProvider对象
	 */
	public static TextProvider getTextProvider(String module) {
		return getTextProvider(new String[] { module });
	}
	
	/**
	 * 获取默认系统相关的TextProvider对象
	 */
	public static TextProvider getTextProvider() {
		final ClassLoader classLoader = getCurrentThreadContextClassLoader();
		final Locale locale = ActionContext.getContext().getLocale();
		String key = createMissesKey(locale, String.valueOf(classLoader.hashCode()), DEFAULT_KEY);
		//获取缓存对象
		TextProvider ret = textProvider_Map.get(key);
		if (ret != null) {
			return ret;
		}
		final List<ResourceBundle> moduleBundleList = getResourceBundles();
		ResourceBundle bundle = new MultipleResourceBundle(moduleBundleList.toArray(new ResourceBundle[moduleBundleList.size()]));
		TextProviderFactory tpf = new TextProviderFactory();
		Container container = ActionContext.getContext().getContainer();
		if (container != null) {
			container.inject(tpf);
		}
		ret = tpf.createInstance(bundle,new LocaleProvider() {
			
			public Locale getLocale() {
				return locale;
			}

			public boolean isValidLocaleString(String localeStr) {
				return true;
			}

			public boolean isValidLocale(Locale locale) {
				return true;
			}
			
		});
		TextProvider existing = textProvider_Map.putIfAbsent(key, ret);
		if (existing != null) {
			ret = existing;
		}
		return ret;
	}

	public static String getLocaleText(String aTextName, Object... params) {
		final Locale locale = ActionContext.getContext().getLocale();
		String textValue = params != null ? LocalizedTextUtil.findDefaultText(
				aTextName, locale, params) : LocalizedTextUtil.findDefaultText(
				aTextName, locale);
		return StringUtils.isEmpty(textValue) ? aTextName : textValue;
	}
    
	public static String getText(String key){
		TextProvider textProvider = getTextProvider();
		String textValue = textProvider.getText(key);
		return StringUtils.isEmpty(textValue) ? key : textValue;
	}
    
    public static String getText(String key,String defaultValue){
		TextProvider textProvider = getTextProvider();
		String textValue = textProvider.getText(key, defaultValue);
		return StringUtils.isEmpty(textValue) ? key : textValue;
	}
    
	public static String getText(String key,String... args){
		TextProvider textProvider = getTextProvider();
		String textValue = textProvider.getText(key, args);
		return StringUtils.isEmpty(textValue) ? key : textValue;
	}
	
	public static String getText(String key,String defaultValue,String... args){
		TextProvider textProvider = getTextProvider();
		String textValue = textProvider.getText(key, defaultValue, args);
		return StringUtils.isEmpty(textValue) ? key : textValue;
	}
	
    public static String getModuleText(String module,String key){
    	TextProvider textProvider = getTextProvider(module);
    	String textValue = textProvider.getText(key);
		return StringUtils.isEmpty(textValue) ? key : textValue;
	}
    
    public static String getModuleText(String module,String key,String defaultValue){
		TextProvider textProvider = getTextProvider(module);
		String textValue = textProvider.getText(key, defaultValue);
		return StringUtils.isEmpty(textValue) ? key : textValue;
	}
    
	public static String getModuleText(String module,String key,String... args){
		TextProvider textProvider = getTextProvider(module);
		String textValue = textProvider.getText(key, args);
		return StringUtils.isEmpty(textValue) ? key : textValue;
	}
	
	public static String getModuleText(String module,String key,String defaultValue,String... args){
		TextProvider textProvider = getTextProvider(module);
		String textValue = textProvider.getText(key, defaultValue, args);
		return StringUtils.isEmpty(textValue) ? key : textValue;
	}
	
}
