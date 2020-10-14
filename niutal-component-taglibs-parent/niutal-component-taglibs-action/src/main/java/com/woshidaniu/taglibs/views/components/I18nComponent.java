package com.woshidaniu.taglibs.views.components;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsConstants;
import org.apache.struts2.StrutsException;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basemodel.bundle.MultipleResourceBundle;
import com.woshidaniu.basemodel.bundle.PairListResourceBundle;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.cache.TextProviderCacheFactory;
import com.woshidaniu.common.utils.WebUtils;
import com.woshidaniu.i18n.dao.entities.I18nModel;
import com.woshidaniu.i18n.provider.I18nProvider;

/**
 *
 * <pre>
 * <!-- Example -->
 * &lt;s:i18n modules="N0001,N0002..." /&gt;
 * </pre>
 * output:
 * <p>&lt;script type="text/javascript" src="&lt;%=path%&gt;/xxx/xx/i18n.js?ver=&lt;%=jsVersion%&gt;"&gt;&lt;/script&gt;</p>
 */
@StrutsTag(name="i18n", tldTagClass="com.woshidaniu.taglibs.views.tags.I18nTag", description="Get module resource bundle and place it on the value stack; output an i18n js url")
public class I18nComponent extends AbstractComplexStrutsUIBean {

    // 模块名称：通常指功能模块代码
 	protected String[] modules;
 	// 国际化数据提供者对象名称：用于从Spring上下文获取该对象实例
	protected String provider;
	// 国际化资源路径表达式
	protected String expression;
 	
 	//Struts指定的国际化资源文件名称
 	protected String[] resources;
    protected Container container;
    protected TextProvider textProvider;
    //国际化数据提供者实例
    protected I18nProvider i18nProvider;

    public I18nComponent(ValueStack stack,HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}
    
    /**
	 * getDefaultTemplate()方法用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件
	 */
	@Override
	protected String getDefaultTemplate() {
		return "tag-i18n";
	}
    
    
    public boolean start(Writer writer) {
        
        //未指定模块，则使用功能代码作为模块名称
    	if((this.modules == null || this.modules.length == 0)){
    		this.modules =  new String[]{ WebUtils.getFuncCode(getRequest())};
    	}
    	
    	//国际化数据提供者实例
    	this.i18nProvider = applicationContext.getBean(provider, I18nProvider.class);
    	
        final List<I18nModel> i18nList = new ArrayList<I18nModel>();
    	//加载指定模块国际化资源文件
		for (String module : modules) {
    		module = this.findString(module, "module", "Resource bundle name is required. Example: foo or foo_en");
    		//准备国际化js数据
    		I18nModel i18nModel = i18nProvider.getI18nModel(module);
    		if(i18nModel != null){
        		i18nList.add(i18nModel);
    		}
		}
    	
    	getStack().push(getTextProvider(i18nList));
    	
        this.addParameter("i18nList", i18nList);

        return false;
    }
    
    
    /**
     * If called first time it will create {@link com.opensymphony.xwork2.TextProviderFactory},
     * inject dependency (if {@link com.opensymphony.xwork2.inject.Container} is accesible) into in,
     * then will create new {@link com.opensymphony.xwork2.TextProvider} and store it in a field
     * for future references and at the returns reference to that field
     * 获取指定模块相关的TextProvider对象
     * @return reference to field with TextProvider
     */
    @SuppressWarnings("unused")
	protected TextProvider getTextProvider(List<I18nModel> i18nList) {
		
    	final Locale locale = ActionContext.getContext().getLocale();
    	final ClassLoader classLoader = getCurrentThreadContextClassLoader();
    	final String key = createMissesKey(String.valueOf(classLoader.hashCode()), locale , modules);
		//获取缓存对象
		MultipleResourceBundle ret = TextProviderCacheFactory.getMultipleResourceBundleMap().get(key);
		if (ret == null) {
			ret = getMultipleResourceBundle(locale, i18nList);
			MultipleResourceBundle existing = TextProviderCacheFactory.getMultipleResourceBundleMap().putIfAbsent(key, ret);
			if (existing != null) {
				ret = existing;
			}
		}
		
		if (container != null) {
			container.inject(TextProviderCacheFactory.getTextProviderFactory());
		}
		return TextProviderCacheFactory.getTextProviderFactory().createInstance(ret,new LocaleProvider() {
			
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
	}
	
    private MultipleResourceBundle getMultipleResourceBundle(final Locale locale,final List<I18nModel> i18nList) {
    	final List<ResourceBundle> moduleBundleList = getResourceBundles(locale, i18nList);
		return new MultipleResourceBundle(moduleBundleList.toArray(new ResourceBundle[moduleBundleList.size()]));
    }
    
    
	/**
	 * 获取指定模块+Struts默认国际化资源
	 */
	private List<ResourceBundle> getResourceBundles(final Locale locale,final List<I18nModel> i18nList) {
		
		final List<ResourceBundle> bundleList = new ArrayList<ResourceBundle>();
    	final List<ResourceBundle> moduleBundleList = new ArrayList<ResourceBundle>();
    	
    	//加载指定模块国际化资源文件
		for (String module : modules) {
    		
    		module = this.findString(module, "module", "Resource bundle name is required. Example: foo or foo_en");
            
    		//准备国际化js数据
    		if(i18nList != null){
    			for (I18nModel i18nModel : i18nList) {
    				//有独立的国际化数据
            		if(i18nModel.getI18nList() != null && i18nModel.getI18nList().size() > 0 ){
            			bundleList.add(new PairListResourceBundle(i18nModel.getI18nList()));
            		}
				}
    		}
    		
    		String i18nModule = "i18n/" + module;
    		//加载默认国际化资源文件
        	ResourceBundle bundle = (ResourceBundle) findValue("getTexts('" + i18nModule + "')");
        	if (bundle == null) {
                bundle = LocalizedTextUtil.findResourceBundle(i18nModule, locale);
        	}
        	if (bundle != null) {
        		moduleBundleList.add(bundle);
        	}
    		
		}
		
    	//根据给定的表达式加上国际化资源
    	if((this.expression != null && this.expression.length() != 0)){
    		//通过给定表达式获取指定的国际化资源
    		List<ResourceBundle> bundles = i18nProvider.getResourceBundle(expression);
    		if(bundles != null){
    			bundleList.addAll(bundles);
    		}
    	}
    	
    	//加载Struts配置的国际化资源文件和加上模块名称后的国际化资源
		for (String resource : resources) {
			
			String bundleName = "";
        	try {
        		
        		//加载默认国际化资源文件
            	ResourceBundle bundle = (ResourceBundle) findValue("getTexts('" + resource + "')");
            	
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
            		
            		module = this.findString(module, "module", "Resource bundle name is required. Example: foo or foo_en");
            		
            		bundleName = resource + "-" + module;
                	/**
                     * @see com.opensymphony.xwork2.TextProviderSupport.getTexts(String aBundleName)
                     * @see LocalizedTextUtil.findResourceBundle(aBundleName, getLocale());
                     * @see java.util.PropertyResourceBundle
                     * @see java.util.ListResourceBundle
                     */
                	ResourceBundle moduleBundle = (ResourceBundle) findValue("getTexts('" + bundleName + "')");
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
		List<ResourceBundle> bundles = i18nProvider.getResourceBundle("classpath*:i18n/message-*_" + locale.toString() + ".properties");
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

	private ClassLoader getCurrentThreadContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
    
    /**
     * Creates a key to used for lookup/storing in the bundle misses cache.
     *
     * @param prefix      the prefix for the returning String - it is supposed to be the ClassLoader hash code.
     * @param expression  the expression of the bundle .
     * @return the key to use for lookup/storing in the bundle misses cache.
     */
    private String createMissesKey(String prefix, Locale locale ,String ... modules) {
        return prefix + "_" + StringUtils.join(modules,"_") + "_" + locale.toString();
    }
    
    @Inject
    public void setContainer(Container container) {
        this.container = container;
    }

    @Inject(value = StrutsConstants.STRUTS_CUSTOM_I18N_RESOURCES)
    public void setResources(String resources) {
        this.resources = StringUtils.tokenizeToStringArray(resources);
    }

    @StrutsTagAttribute(description="module names to use (eg N0001 )", required=true, defaultValue="String")
    public void setModules(String modules) {
		this.modules = StringUtils.tokenizeToStringArray(modules);
	}
    
    @StrutsTagAttribute(description="i18n file expression ", required=false, defaultValue="String")
	public void setExpression(String expression) {
		this.expression = expression;
	}

    @StrutsTagAttribute(description="i18n data provider ", required=false, defaultValue="String")
	public void setProvider(String provider) {
    	this.provider = (provider == null ? "i18nProvider" : provider);
	}
    
    
}
