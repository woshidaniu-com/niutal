/**
 * 
 */
package com.woshidaniu.zxzx.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
@RequestMapping("/zxzx/web/page/auth")
public class ZxzxWebAuthPageNavigator extends ZxzxWebPageNavigator {
	
	@Autowired
	@Qualifier("zxzxCsszService")
	private ICsszService csszService;
	
	@RequestMapping(value = "/my-topic", method = RequestMethod.GET)
	public String loadMyTopicPage(HttpServletRequest request) {
		if (!zxzxAuthentication.isAuthenticated()) {
			saveOriginalUrl(request);
			return loadNoLoginPage(request);
		}
		return "/zxzx/web_v_3/my-topic";
	}

	@RequestMapping(value = "/new-topic", method = RequestMethod.GET)
	public String loadNewTopicPage(HttpServletRequest request) {
		return "/zxzx/web_v_3/new-topic";
	}

	@RequestMapping(value = "/{topicId}/edit-topic", method = RequestMethod.GET)
	public String loadEditTopicPage(@PathVariable String topicId, HttpServletRequest request) {
		request.setAttribute("topicId", topicId);
		if (!zxzxAuthentication.isAuthenticated()) {
			return loadNoLoginPage(request);
		}
		return "/zxzx/web_v_3/edit-topic";
	}

}
