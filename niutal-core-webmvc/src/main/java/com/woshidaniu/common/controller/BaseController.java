package com.woshidaniu.common.controller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;

import com.alibaba.fastjson.JSONObject;
import com.woshidaniu.common.log.User;
import com.woshidaniu.spring.web.servlet.mvc.AbstractBaseController;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.WebContext;

public class BaseController extends AbstractBaseController {

	protected static final String ERROR_VIEW = "/exception/busiError";

	protected static final String MESSAGE_STATUS_SUCCESS = "success";
	protected static final String MESSAGE_STATUS_FAIL = "fail";
	protected static final String MESSAGE_STATUS_ERROR = "error";

	@Resource(name = "messageSource")
	protected MessageSource messageSource;

	@Resource(name = "cacheManager")
	protected CacheManager cacheManager;

	public static final Object[] EMPTY_ARGS = new Object[] {};

	protected User getUser() {
		return WebContext.getUser();
	}

	/**
	 * @desc 获取用户当前角色代码
	 * @return
	 */
	protected String getUserCurrentRole() {
		User user = getUser();
		if (null == user)
			return null;
		return user.getJsdm();
	}

	protected String getMessage(String messageKey, Object[] params) {
		try {
			String rt = null;
			if (getMessageSource() != null) {
				rt = getMessageSource().getMessage(messageKey, params, WebContext.getLocale());
			}
			if (StringUtils.isEmpty(rt)) {
				rt = MessageUtil.getLocaleText(messageKey, params);
			}
			return rt;
		} catch (Exception e) {
			return MessageUtil.getLocaleText(messageKey, params);
		}
	}

	protected String getMessage(String messageKey) {
		try {
			String rt = null;
			if (getMessageSource() != null) {
				rt = getMessageSource().getMessage(messageKey, null, WebContext.getLocale());
			}
			if (StringUtils.isEmpty(rt)) {
				rt = MessageUtil.getLocaleText(messageKey);
			}
			return rt;
		} catch (Exception e) {
			return MessageUtil.getLocaleText(messageKey);
		}
	}

	protected JSONObject getJSONMessage(String messageStatus, String messageKey, Object[] params) {
		String message = MessageUtil.getText(messageKey, params);
		JSONObject json = new JSONObject();
		json.put("message", message);
		json.put("status", messageStatus);
		return json;
	}

	protected JSONObject getJSONMessage(String messageStatus, String messageKey) {
		return getJSONMessage(messageStatus, messageKey, null);
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
