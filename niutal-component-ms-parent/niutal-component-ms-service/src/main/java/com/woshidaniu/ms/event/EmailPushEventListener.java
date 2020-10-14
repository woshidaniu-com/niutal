package com.woshidaniu.ms.event;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.woshidaniu.ms.api.MessageBody;
import com.woshidaniu.ms.api.event.EmailPushEvent;
import com.woshidaniu.ms.api.exception.MessagePushException;
import com.woshidaniu.ms.api.provider.MessageOutputProvider;

/**
 * 
 *@类名称		： EmailPushEventListener.java
 *@类描述		：邮件推送事件监听
 *@创建人		：kangzhidong
 *@创建时间	：2017年5月3日 上午11:15:04
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
@Component
public class EmailPushEventListener implements ApplicationListener<EmailPushEvent> {

	@Resource(name = "emailMessageOutputProvider")
	protected MessageOutputProvider outputProvider;
	
	@Override
	public void onApplicationEvent(EmailPushEvent event) {
		try {
			//获取绑定的参数数据
			MessageBody data = event.getBind();
			getOutputProvider().output(data);
		} catch (Exception e) {
			throw new MessagePushException(e);
		}
	}

	public MessageOutputProvider getOutputProvider() {
		return outputProvider;
	}

	public void setOutputProvider(MessageOutputProvider outputProvider) {
		this.outputProvider = outputProvider;
	}

}