package com.woshidaniu.common.template.method;

import java.util.List;

import org.springframework.stereotype.Component;

import com.woshidaniu.web.context.WebContext;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
/**
 * 说明：自定义Freemarker方法，读取上下文中的Locale值
 */
@Component("localeMethod")
public class LocaleMethod implements TemplateMethodModelEx {

	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		return new SimpleScalar(WebContext.getLocale().toString());
	}

}
