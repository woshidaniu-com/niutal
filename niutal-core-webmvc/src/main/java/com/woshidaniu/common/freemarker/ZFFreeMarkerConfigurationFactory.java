/**
 * <p>Coryight (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.freemarker;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @author 		：康康（1571）
 * @description	：我是大牛FreeMarker配置工厂类
 * 支持配置参数放入freemarker
 * 支持预加载taglib
 */
public class ZFFreeMarkerConfigurationFactory extends FreeMarkerConfigurer implements ApplicationContextAware{
	
	private static final Logger log = LoggerFactory.getLogger(ZFFreeMarkerConfigurationFactory.class);
	
	private ApplicationContext applicationContext;
	//预加载标签库
	private List<String> preLoadTaglibUris = Collections.emptyList();
	
	@Override
	protected Configuration newConfiguration() throws IOException, TemplateException {
		Configuration config = super.newConfiguration();
		Map<String, FreeMarkerConfigurationVariablesRefresher> map = this.applicationContext.getBeansOfType(FreeMarkerConfigurationVariablesRefresher.class);
		if(map != null && !map.isEmpty()) {
			Iterator<Entry<String, FreeMarkerConfigurationVariablesRefresher>> it = map.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, FreeMarkerConfigurationVariablesRefresher> e = it.next();
				String k = e.getKey();
				FreeMarkerConfigurationVariablesRefresher refresher = e.getValue();
				refresher.setConfiguration(config);
				if(log.isDebugEnabled()) {
					log.debug("bind freemarker config by refresher bean['"+k+"']");
				}
			}
		}
		return config;
	}

	@Override
	protected void postProcessConfiguration(Configuration config) throws IOException, TemplateException {
		Map<String, FreeMarkerConfigurationVariablesRefresher> map = this.applicationContext.getBeansOfType(FreeMarkerConfigurationVariablesRefresher.class);
		if(map != null && !map.isEmpty()) {
			Iterator<Entry<String, FreeMarkerConfigurationVariablesRefresher>> it = map.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, FreeMarkerConfigurationVariablesRefresher> e = it.next();
				String k = e.getKey();
				FreeMarkerConfigurationVariablesRefresher refresher = e.getValue();
				refresher.refresh();
				if(log.isDebugEnabled()) {
					log.debug("refreshe freemarker config by refresher bean['"+k+"']");
				}
			}
		}
		
		this.preLoadTaglibs();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * @description	： 预加载标签库
	 */
	protected void preLoadTaglibs() {
		
		final TaglibFactory taglibFactory = this.getTaglibFactory();
		
		log.info("预加载的taglib size:{}",this.preLoadTaglibUris.size());
		
		for(String taglibUri:this.preLoadTaglibUris) {
			if(StringUtils.isEmpty(taglibUri)) {
				log.error("taglibUri不能为空");
			}else {
				StopWatch stopWatch = new StopWatch();
				stopWatch.start();
				
				try {
					TemplateModel model = taglibFactory.get(taglibUri);
					
					stopWatch.stop();
					
					if(model != null) {
						log.info("预加载uri为[{}]的taglib成功,耗时[{}]ms",taglibUri,stopWatch.getTime());						
					}else {
						log.warn("预加载uri为[{}]的taglib失败,耗时[{}]ms",taglibUri,stopWatch.getTime());
					}
				} catch (TemplateModelException e) {
					stopWatch.stop();
					log.error("预加载uri为[{}]的taglib异常,耗时[{}]ms",taglibUri,stopWatch.getTime(),e);
				}
			}
		}
	}

	public void setPreLoadTaglibUris(List<String> preLoadTaglibUris) {
		this.preLoadTaglibUris = preLoadTaglibUris;
	}
}
