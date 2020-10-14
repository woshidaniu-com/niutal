package com.woshidaniu.pwdmgr.api.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.woshidaniu.ms.api.provider.MessageOutputProvider;
/**
 * 
 *@类名称		： MessageOutputProviderFactory.java
 *@类描述		：消息服务的消息输出服务提供者工厂，解耦密码管理服务调用消息输出时候的代码耦合问题
 *@创建人		：kangzhidong
 *@创建时间	：2017年5月9日 上午8:57:24
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class MessageOutputProviderFactory implements FactoryBean<MessageOutputProvider>, ApplicationContextAware {

	protected ApplicationContext applicationContext;
	protected String providerName;
	
	public MessageOutputProviderFactory(String providerName){
		this.providerName = providerName;
	}
	
	@Override
	public MessageOutputProvider getObject() throws Exception {
		return getApplicationContext().getBean(providerName, MessageOutputProvider.class);
	}

	@Override
	public Class<?> getObjectType() {
		return MessageOutputProvider.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
}
