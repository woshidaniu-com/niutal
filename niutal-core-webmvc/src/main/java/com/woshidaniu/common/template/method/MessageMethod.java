package com.woshidaniu.common.template.method;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.WebContext;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：自定义Freemarker方法，读取properties文件内容
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年9月13日上午9:19:43
 */
@Component("messageMethod")
public class MessageMethod implements TemplateMethodModelEx, InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(MessageMethod.class);

	//是否启用占位符
	private boolean enablePlaceholder = false;

	//${contextPath}可以用来替换应用名称,可用在内嵌样式包这种情况下
	private final String contextPathTag = "${contextPath}";
	private final int contextPathTagLength = contextPathTag.length();

	@Resource(name = "messageSource")
	protected MessageSource messageSource;
	
	private String version = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.version = DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes());

		String val = MessageUtil.getText("niutal.messageMethod.enablePlaceholder");
		this.enablePlaceholder = val != null ? Boolean.parseBoolean(val) : this.enablePlaceholder;
	}
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null && StringUtils.isNotEmpty(arguments.get(0).toString())) {
			String message = null;
			String code = arguments.get(0).toString();
			if("niutal.jsVersion".equalsIgnoreCase(code) || "niutal.cssVersion".equalsIgnoreCase(code)){
				return new SimpleScalar(version);
			}
			if (arguments.size() > 1) {
				Object[] args = arguments.subList(1, arguments.size()).toArray();
				message = getMessage(code, args);
			} else {
				message = getMessage(code);
			}
			if(this.enablePlaceholder && message != null && message.length() >= this.contextPathTagLength && message.contains(contextPathTag)){
				String contextPath = WebContext.getRequest().getServletContext().getContextPath();
				message = message.replace(this.contextPathTag,contextPath);
			}
			return new SimpleScalar(message);
		}
		return null;
	}
	
	protected String getMessage(String key, Object[] args){
		try {
			String rt = null;
			if(getMessageSource() != null){
				rt = getMessageSource().getMessage(key, args, WebContext.getLocale());
			}
			if(StringUtils.isEmpty(rt)){
				rt = MessageUtil.getLocaleText(key, args);
			}
			return rt;
		} catch (Exception e) {
			return MessageUtil.getLocaleText(key, args);
		}
	}
	
	protected String getMessage(String key){
		try {
			String rt = null;
			if(getMessageSource() != null){
				rt = getMessageSource().getMessage(key, null, WebContext.getLocale());
			}
			if(StringUtils.isEmpty(rt)){
				rt = MessageUtil.getLocaleText(key);
			}
			return rt;
		} catch (Exception e) {
			return MessageUtil.getLocaleText(key);
		}
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	
}
