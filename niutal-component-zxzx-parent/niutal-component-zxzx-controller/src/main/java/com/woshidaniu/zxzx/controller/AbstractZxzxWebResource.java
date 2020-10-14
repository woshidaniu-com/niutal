/**
 * 
 */
package com.woshidaniu.zxzx.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.zxzx.auth.ZxzxAuthentication;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：在线咨询前端资源获取抽象类
 * <p>
 * 
 * @className:com.woshidaniu.zxzx.controller.ZxzxWebResource.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月19日下午4:53:38
 */
public abstract class AbstractZxzxWebResource extends BaseController {

	protected ObjectMapper objectMapper = new ObjectMapper();

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public ObjectNode getUnAuthJSON(){
		ObjectNode node = getObjectMapper().createObjectNode();
		node.put("auth-state", false);
		return node;
	}
	
	// 用于校验用户登录
	@Autowired
	protected ZxzxAuthentication zxzxAuthentication;

	public ZxzxAuthentication getZxzxAuthentication() {
		return zxzxAuthentication;
	}

	public void setZxzxAuthentication(ZxzxAuthentication zxzxAuthentication) {
		this.zxzxAuthentication = zxzxAuthentication;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	
}
