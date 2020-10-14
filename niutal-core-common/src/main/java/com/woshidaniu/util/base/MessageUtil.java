package com.woshidaniu.util.base;

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.enhanced.utils.SpringResourceUtils;
import org.springframework.util.ResourceUtils;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.web.context.WebContext;


/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：系统资源文件工具
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月13日上午10:28:14
 */
public final class MessageUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageUtil.class);
	
	//资源池
	protected static Properties props = new Properties();
	protected static Properties logProperties = new Properties();
	protected static Properties wsProperties = new Properties();
	protected static Properties enProperties = new Properties();
	protected static String[] expressions = new String[] { "*.properties", "Key.properties", "runtime*.properties",
			"conf/message*.properties", "message*.properties", "system*.properties", "conf/*config*.properties",
			"*config*.properties","i18n/*_zh_CN.properties" };
	protected static String[] logExpressions = new String[] { "logger*.properties", "conf/logger*.properties" };
	protected static String[] enExpressions = new String[] { "system*.properties","message_en_US.properties","message-niutal_en_US.properties","i18n/*_en_US.properties","*/*_en_US.properties"};
	protected static Object[] EMPTY = new Object[0];
	/**
	 * 加载时排除   
	 * 避免安全问题
	 */
	private static String[] ExclusionFiles = {
		  "db.properties",
		  "log4j.properties",
		  "mail.properties",
		  "logAnnotation.properties"
	};
	
	protected static ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(MessageUtil.class.getClassLoader());
	private MessageUtil() {
	}
	
	static {
		loadProperties();
	}
	
	/*
	 * 加载classpath目录下的 *.properties资源文件
	 */
	public static boolean loadProperties() {
		
		try{
			Resource[] resources = null;
			if(props.isEmpty()){
				for (String expression : expressions) {
					//加载classpath目录下的 *.properties资源文件
					resources = SpringResourceUtils.getResources("classpath*:" + expression);
					for (Resource resource : resources) {
						try {
							if((isExclusion(resource.getFile().getName()))){
								continue;
							}
							LOG.debug("Loading file [" + resource.getURL().getFile() + "] !");
							if(ResourceUtils.isJarURL(resource.getURL())){
								PropertiesLoaderUtils.fillProperties(props, new InputStreamResource(resource.getInputStream()));
					        }else{
					        	PropertiesLoaderUtils.fillProperties(props, resource);
					        }
						}catch (IOException ex) {
							LOG.warn("Could not load properties from " + resource.getFilename() + ": " + ex.getMessage());
						}
					}
				}
				
			}
			if(logProperties.isEmpty()){
				for (String expression : logExpressions) {
					//加载classpath目录下的 *.properties资源文件
					resources = SpringResourceUtils.getResources("classpath*:" + expression);
					for (Resource resource : resources) {
						try {
							if((isExclusion(resource.getFile().getName()))){
								continue;
							}
							LOG.debug("Loading file [" + resource.getURL().getFile() + "] !");
							if(ResourceUtils.isJarURL(resource.getURL())){
								PropertiesLoaderUtils.fillProperties(logProperties, new InputStreamResource(resource.getInputStream()));
					        }else{
					        	PropertiesLoaderUtils.fillProperties(logProperties, resource);
					        }
						}catch (IOException ex) {
							LOG.warn("Could not load properties from " + resource.getFilename() + ": " + ex.getMessage());
						}
					}
				}
			}
			if(enProperties.isEmpty()){
				for (String expression : enExpressions) {
					//加载classpath目录下的 *.properties资源文件
					resources = SpringResourceUtils.getResources("classpath*:" + expression);
					for (Resource resource : resources) {
						try {
							if((isExclusion(resource.getFile().getName()))){
								continue;
							}
							LOG.debug("Loading file [" + resource.getURL().getFile() + "] !");
							if(ResourceUtils.isJarURL(resource.getURL())){
								PropertiesLoaderUtils.fillProperties(enProperties, new InputStreamResource(resource.getInputStream()));
					        }else{
					        	PropertiesLoaderUtils.fillProperties(enProperties, resource);
					        }
						}catch (IOException ex) {
							LOG.warn("Could not load properties from " + resource.getFilename() + ": " + ex.getMessage());
						}
					}
				}
			}
			if(wsProperties.isEmpty()){
				//加载classpath目录下的 webservice*.properties资源文件
				resources = SpringResourceUtils.getResources("classpath*:webservice*.properties");
				for (Resource resource : resources) {
					try {
						if((isExclusion(resource.getFile().getName()))){
							continue;
						}
						LOG.debug("Loading file [" + resource.getURL().getFile() + "] !");
						if(ResourceUtils.isJarURL(resource.getURL())){
							PropertiesLoaderUtils.fillProperties(wsProperties, new InputStreamResource(resource.getInputStream()));
				        }else{
				        	PropertiesLoaderUtils.fillProperties(wsProperties, resource);
				        }
					}catch (IOException ex) {
						LOG.warn("Could not load properties from " + resource.getFilename() + ": " + ex.getMessage());
					}
				}
			}

			if(LOG.isDebugEnabled()){
				LOG.debug("[************************MessageUtil Property************************]");
				if(props != null){
					Iterator<Object> iterator = props.keySet().iterator();
					while (iterator.hasNext()) {
						Object key = iterator.next();
						Object value = props.get(key);
						LOG.debug(String.format("{key= %s, value= %s}", key,value));
					}
				}
				if(logProperties != null){
					Iterator<Object> iterator = logProperties.keySet().iterator();
					while (iterator.hasNext()) {
						Object key = iterator.next();
						Object value = logProperties.get(key);
						LOG.debug(String.format("{key= %s, value= %s}", key,value));
					}
				}
				if(wsProperties != null){
					Iterator<Object> iterator = wsProperties.keySet().iterator();
					while (iterator.hasNext()) {
						Object key = iterator.next();
						Object value = wsProperties.get(key);
						LOG.debug(String.format("{key= %s, value= %s}", key,value));
					}
				}
				LOG.debug("[************************MessageUtil Property************************]");
			}
			return true;
		} catch(IOException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * <p>方法说明：刷新资源文件<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月13日上午10:32:20<p>
	 * @return boolean
	 */
	public static boolean reload(){
		LOG.info("************************Reload MessageUtil Property************************");	
		if(!props.isEmpty()){
			props.clear();
		}
		return loadProperties();
	}
	
	/**
	 * 
	 * <p>方法说明：获取*.properties中的消息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月13日上午10:37:21<p>
	 * @param key 键
	 * @return 值
	 */
	public static String getText(String key){
		return getText(key, EMPTY);
	}
	
	/**
	 * 
	 * <p>方法说明：获取*.properties中的消息<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月13日上午10:32:54<p>
	 * @param key 键
	 * @param params 参数列表
	 * @return Content
	 */
	public static String getText(String key,Object... params){
		String message = props.getProperty(key);
		return StringUtils.getSafeStr(setParams(message,params), "") ;
	}
	
	/**
	 * 
	 * <p>方法说明：只获取属性，不进行参数设置<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月13日上午10:37:51<p>
	 * @param key 键
	 * @return 值
	 */
	public static String getTextOnly(String key){
		return props.getProperty(key);
	}
	

	public static String getLocaleText(String key,Object... params){
		return getLocaleText(WebContext.getLocale(), key, params) ;
	}
	
	public static String getLocaleText(Locale locale,String key,Object... params){
		String message = props.getProperty(key + "." + locale.toString());
			   message = StringUtils.isEmpty(message) ? (Locale.US.equals(locale)?enProperties.getProperty(key):props.getProperty(key)) : message; 
		return StringUtils.getSafeStr(setParams(message,params), "") ;
	}
	
	/**
	 * 
	 *@描述：获取根目录下logger*.properties 文件中的消息
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-4上午08:58:30
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param key
	 *@param params
	 *@return
	 */
	public static String getLogText(String key,Object... params){
		String message = logProperties.getProperty(key);
		return StringUtils.getSafeStr(setParams(message,params), "") ;
	}
	
	
	/**
	 * 
	 *@描述：获取根目录下webservice*.properties 文件中的消息
	 *@创建人:kangzhidong
	 *@创建时间:2015-3-4上午08:58:30
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param key
	 *@param params
	 *@return
	 */
	public static String getWsText(String key,Object... params){
		String message = wsProperties.getProperty(key);
		return StringUtils.getSafeStr(setParams(message,params), "") ;
	}
	
	/**
	 * 
	 * <p>方法说明：获取int类型属性<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月13日上午10:38:29<p>
	 * @param key 键
	 * @return 值
	 */
	public static Integer getInt(String key){
		String textOnly = getTextOnly(key);
		if(textOnly == null || "".equals(textOnly)){
			return null;
		}else{
			return new Integer(textOnly);
		}
	}
	
	/**
	 * 
	 * <p>方法说明：为资源文件中的参数设值<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月13日上午10:34:59<p>
	 * @param params 参数列表
	 * @param message 转换前Message
	 * @return 转换后Message
	 */
	public static String setParams(String message,Object... params) {
		if (null != params && params.length > 0){
			for (int i = 0 ; i < params.length ; i++){
				message = message.replace(String.format("{%s}", i), String.valueOf(params[i]));
			}
		}
		return message;
	}
	
	private static boolean isExclusion(String fileName){
		if(ExclusionFiles == null || ExclusionFiles.length == 0 || null == fileName){
			return false;
		}
		return ArrayUtils.contains(ExclusionFiles, fileName);
	}
	
}
