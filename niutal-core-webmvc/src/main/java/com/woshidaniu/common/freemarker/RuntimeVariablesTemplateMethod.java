package com.woshidaniu.common.freemarker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 
 * @className	： RuntimeVariablesTemplateMethod
 * @description	： runtime配置方法
 * @author 		：康康（1571）
 * @date		： 2018年6月22日 上午10:09:29
 * @version 	V1.0
 */
public class RuntimeVariablesTemplateMethod
		implements TemplateMethodModelEx, ApplicationContextAware {
	
	private static final Logger log = LoggerFactory.getLogger(RuntimeVariablesTemplateMethod.class);
	
	private List<RuntimeVariables> runtimeVariableses = new ArrayList<RuntimeVariables>(1);

	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {

		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null && !"".equals(arguments.get(0))) {
			String code = arguments.get(0).toString();
			return this.find(code);
		}
		return null;
	}
	
	protected String find(String key) {
		
		for(RuntimeVariables rv : this.runtimeVariableses) {
			String v = rv.value(key);
			if(v != null) {
				return v;
			}
		}
		if(log.isDebugEnabled()) {
			log.debug("can't find key["+ key +"]'s value,return null");
		}
		return null;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, RuntimeVariables> map = applicationContext.getBeansOfType(RuntimeVariables.class);
		if (!map.isEmpty()) {
			this.runtimeVariableses = new ArrayList<RuntimeVariables>(map.values());
			AnnotationAwareOrderComparator.sort(this.runtimeVariableses);
			if(log.isDebugEnabled()) {
				log.debug("find runtimeVariables bean");
				Iterator<Entry<String, RuntimeVariables>> it = map.entrySet().iterator();
				while(it.hasNext()) {
					Entry<String, RuntimeVariables> e = it.next();
					log.debug("Bean id="+e.getKey()+",Bean ="+e.getValue());
				}
			}
		}else {
			this.runtimeVariableses = new ArrayList<RuntimeVariables>(1);
		}
	}
}