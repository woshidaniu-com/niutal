/**
 * 
 */
package com.woshidaniu.zxzx.controller;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.woshidaniu.zxzx.ZxzxConstant;
import com.woshidaniu.zxzx.service.svcinterface.ICsszService;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.zxzx.controller.ZxzxWebPageNavigator.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月19日下午5:54:09
 */

@Controller
@RequestMapping("/zxzx/web/page")
public class ZxzxWebPageNavigator extends AbstractZxzxWebResource {
	
	private static final Logger log = LoggerFactory.getLogger(ZxzxWebPageNavigator.class);
	
	@Autowired
	@Qualifier("zxzxCsszService")
	private ICsszService csszService;
	
	/**
	 * @description	： 设置配置到请求属性
	 * @param request
	 */
	protected void setConfigAttr(HttpServletRequest request) {
		
		Map<String,String> configMap = csszService.getConfig();
		
		String sfxswdwt = configMap.get(ZxzxConstant.CSSZ_SFXSWDWT);
		request.setAttribute("sfxswdwt", sfxswdwt);
		
		String sfxswytw = configMap.get(ZxzxConstant.CSSZ_SFXSWYTW);
		request.setAttribute("sfxswytw",sfxswytw);
		
		String sfyxwdlcxwt = configMap.get(ZxzxConstant.CSSZ_SFYXWDLCXWT);
		request.setAttribute("sfyxwdlcxwt", sfyxwdlcxwt);
	}

	@RequestMapping(value = "/topic")
	public String loadTopicPage(HttpServletRequest request) {
		request.setAttribute("webSearchBkdmValues", request.getParameter("webSearchBkdmValues"));
		this.setConfigAttr(request);
		return "/zxzx/web_v_3/topic";
	}

	@RequestMapping(value = "/faq", method = RequestMethod.GET)
	public String loadFAQPage(HttpServletRequest request) {
		this.setConfigAttr(request);
		return "/zxzx/web_v_3/faq";
	}

	// @RequestMapping(value = "/auth/my-topic", method = RequestMethod.GET)
	// public String loadMyTopicPage() {
	// if (!SubjectUtils.isAuthenticated()) {
	// return loadNoLoginPage();
	// }
	// return "/zxzx/web_v_3/my-topic";
	// }
	//
	// @RequestMapping(value = "/auth/new-topic", method = RequestMethod.GET)
	// public String loadNewTopicPage() {
	// if (!SubjectUtils.isAuthenticated()) {
	// return loadNoLoginPage();
	// }
	// return "/zxzx/web_v_3/new-topic";
	// }
	//
	// @RequestMapping(value = "/auth/edit-topic", method = RequestMethod.GET)
	// public String loadEditTopicPage() {
	// if (!SubjectUtils.isAuthenticated()) {
	// return loadNoLoginPage();
	// }
	// return "/zxzx/web_v_3/edit-topic";
	// }

	@RequestMapping(value = "/no-login", method = RequestMethod.GET)
	public String loadNoLoginPage(HttpServletRequest request) {
		return "/zxzx/web_v_3/no-login";
	}

	@RequestMapping(value = "/embend-login", method = RequestMethod.GET)
	public ModelAndView loadLoginPage(@RequestParam("original-url") String origialUrl, HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/zxzx/web_v_3/embend-login");
		model.addObject("originalUrl", origialUrl);
		return model;
	}

	@RequestMapping(value = "/no-open", method = RequestMethod.GET)
	public String loadNoOpenPage(HttpServletRequest request) {
		return "/zxzx/web_v_3/no-open";
	}

	/**
	 * 
	 * <p>
	 * 方法说明：保存原先的网址
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * <p>
	 * <p>
	 * 时间：2017年6月21日上午10:35:12
	 * <p>
	 * 
	 * @param request
	 */
	protected void saveOriginalUrl(HttpServletRequest request) {
		SavedRequest savedRequest = new SavedRequest(request);
		request.setAttribute("originalUrl", savedRequest.getRequestUrl());
	}

	class SavedRequest implements Serializable {

		private static final long serialVersionUID = 1L;

		private String method;
		private String queryString;
		private String requestURI;

		/**
		 * Constructs a new instance from the given HTTP request.
		 *
		 * @param request
		 *            the current request to save.
		 */
		public SavedRequest(HttpServletRequest request) {
			this.method = request.getMethod();
			this.queryString = request.getQueryString();
			this.requestURI = request.getRequestURI();
		}

		public String getMethod() {
			return method;
		}

		public String getQueryString() {
			return queryString;
		}

		public String getRequestURI() {
			return requestURI;
		}

		public String getRequestUrl() {
			StringBuilder requestUrl = new StringBuilder(getRequestURI());
			if (getQueryString() != null) {
				requestUrl.append("?").append(getQueryString());
			}
			return requestUrl.toString();
		}
	}

}
