/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.globalweb.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.util.base.MessageUtil;

/**
 * @description	： 我是大牛公司的验证码Servlet,将配置提到system.properties或runtime.properties
 * @author 		：康康（1571）
 */
public class ZfSoftKaptchaServlet extends com.google.code.kaptcha.servlet.KaptchaServlet{

	private static final long serialVersionUID = 1L;

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static final Map<String,String> CONFIG_NAME_VALUE = new HashMap<String,String>();
	
	static {
		//TODO 填写完整所有value
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_BACKGROUND_CLR_FROM,"");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_BACKGROUND_CLR_TO,"");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_BACKGROUND_IMPL,"");
		
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_BORDER,"no");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_BORDER_COLOR,"black");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_BORDER_THICKNESS,"");
		
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_IMAGE_HEIGHT,"");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_IMAGE_WIDTH,"");
		
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_NOISE_COLOR,"");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_NOISE_IMPL,"");
		
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_OBSCURIFICATOR_IMPL,"");
		
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_PRODUCER_IMPL,"");
		
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_CONFIG_DATE,"");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_CONFIG_KEY,"");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_DATE,"");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY,"");
		
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH,"4");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE,"5");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING,"abcde2345678gfynmnpwx");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR,"");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES,"");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE,"");
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_IMPL,"");
		
		CONFIG_NAME_VALUE.put(com.google.code.kaptcha.Constants.KAPTCHA_WORDRENDERER_IMPL,"");
	}
	
	@Override
	public void init(ServletConfig conf/*not used*/) throws ServletException {
		
		log.info("初始化我是大牛验证码Servlet");
		
		//目前最多只有5个配置
		Map<String,String> configValues = new HashMap<String,String>(5);
		
		for(Map.Entry<String, String> e : new ArrayList<Map.Entry<String,String>>(CONFIG_NAME_VALUE.entrySet())){
			
			String key = e.getKey();
			String orginalValue = e.getValue();
			
			String configedValue = MessageUtil.getText(key);
			String finalValue = null;
			
			//配置文件会覆盖代码中的配置
			if(StringUtils.isNotEmpty(configedValue)) {
				finalValue = configedValue;
			}else if(StringUtils.isNotEmpty(orginalValue)) {
				finalValue = orginalValue;
			}
			if(StringUtils.isNotEmpty(finalValue)) {
				log.info("验证码Servlet的配置key[{}],value[{}]",key,finalValue);
				configValues.put(key, finalValue);
			}
		}
		super.init(new DelegateServletConfig(conf.getServletName(),conf.getServletContext(),configValues));
	}
	
	/**
	 * @description	： 委托ServletConfig
	 * @author 		：康康（1571）
	 */
	private static final class DelegateServletConfig implements ServletConfig{

		private String servletName;
		private ServletContext servletContext;
		private Map<String,String> configValues = Collections.emptyMap();
		
		public DelegateServletConfig(String servletName, ServletContext servletContext,Map<String, String> configValues) {
			this.servletName = servletName;
			this.servletContext = servletContext;
			this.configValues = configValues;
		}

		@Override
		public String getServletName() {
			return this.servletName;
		}

		@Override
		public ServletContext getServletContext() {
			return this.servletContext;
		}

		@Override
		public String getInitParameter(String name) {
			return configValues.get(name);
		}

		@Override
		public Enumeration<String> getInitParameterNames() {
			Iterator<String> it = this.configValues.keySet().iterator();
			return new IteratorEnumeration(it);
		}
	}
	
	/**
	 * @description	： 迭代器的枚举
	 * @author 		：康康（1571）
	 */
	private static final class IteratorEnumeration implements Enumeration<String>{
		
		private Iterator<String> it;
		
		public IteratorEnumeration(Iterator<String> it) {
			super();
			this.it = it;
		}
		
		@Override
		public boolean hasMoreElements() {
			return it.hasNext();
		}

		@Override
		public String nextElement() {
			return it.next();
		}
	}
}
