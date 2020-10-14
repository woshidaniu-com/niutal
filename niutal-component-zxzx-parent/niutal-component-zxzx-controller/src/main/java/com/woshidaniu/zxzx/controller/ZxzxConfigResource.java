/**
 * 
 */
package com.woshidaniu.zxzx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.zxzx.ZxzxConstant;
import com.woshidaniu.zxzx.auth.ZxzxAuthentication;
import com.woshidaniu.zxzx.service.svcinterface.ICsszService;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.zxzx.controller.ZxzxConfigResource.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月19日下午5:06:14
 */
@Controller
@RequestMapping("/zxzx/web/config")
public class ZxzxConfigResource extends AbstractZxzxWebResource {

	@Autowired
	@Qualifier("zxzxCsszService")
	private ICsszService csszService;

	// 用于校验用户登录
	@Autowired
	protected ZxzxAuthentication zxzxAuthentication;

	@ResponseBody
	@RequestMapping(value = "openStatus/json", method = RequestMethod.GET)
	public Object getOpenStatusJson() {
		Map<String, Object> openStatus = csszService.isOpen(zxzxAuthentication.isAuthenticated());
		Object message = openStatus.get("message");
		if (message != null) {
			openStatus.put("messageValue", getMessage(message.toString()));
		}
		return openStatus;
	}

	@ResponseBody
	@RequestMapping(value = "loginMode/json", method = RequestMethod.GET)
	public Object getLoginMode() {
		ObjectNode loginMode = getObjectMapper().createObjectNode();
		Map<String, String> config = csszService.getConfig();
		String dlms = config.get(ZxzxConstant.CSSZ_DLMS_DM);
		// 如果是默认的登陆模式，则取CSSZ_LOGIN_URL_DM
		if (StringUtils.equals(ZxzxConstant.CSSZ_DLMS_DEFAULT_DM, dlms)) {
			loginMode.put("mode", ZxzxConstant.CSSZ_DLMS_DEFAULT_DM);
			loginMode.put("url", config.get(ZxzxConstant.CSSZ_LOGIN_URL_DM));
			// 如果是嵌入登陆模式，取CSSZ_AUTH_URL_DM
		} else if (StringUtils.equals(ZxzxConstant.CSSZ_DLMS_EMBEND_DM, dlms)) {
			loginMode.put("mode", ZxzxConstant.CSSZ_DLMS_EMBEND_DM);
			loginMode.put("url", config.get(ZxzxConstant.CSSZ_AUTH_URL_DM));
			// 如果没有配置，页面跳转根路径
		} else {
			loginMode.put("mode", ZxzxConstant.CSSZ_DLMS_NO_CONFIGED_DM);
		}
		return loginMode;
	}

}
