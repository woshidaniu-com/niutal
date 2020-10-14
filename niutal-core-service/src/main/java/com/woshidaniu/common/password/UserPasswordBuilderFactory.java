/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.password;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.password.impl.DefaultUserPasswordBuilder;
import com.woshidaniu.service.svcinterface.IYhglService;
import com.woshidaniu.util.base.MessageUtil;

/**
 * @className	： UserPasswordBuilderFactory
 * @description	： 用户密码构造器工程，依据配置文件runtime.properties或system.properties的配置，初始化单例的Bean
 * @author 		：康康（1571）
 * @date		： 2018年9月26日 下午1:55:10
 * @version 	V1.0
 */
@Component("userPasswordBuilderFactory")
public class UserPasswordBuilderFactory implements FactoryBean<UserPasswordBuilder>,ApplicationContextAware{
	
	private static final Logger log = LoggerFactory.getLogger(UserPasswordBuilderFactory.class);
	
	protected static final String CONFIG_KEY_NAME = "userManage.userPasswordBuilder";
	
	protected ApplicationContext applicationContext;
	
	@Override
	public UserPasswordBuilder getObject() throws Exception {
		log.info("初始化用户密码构建器");
		IYhglService yhglService  = this.applicationContext.getBean(com.woshidaniu.service.svcinterface.IYhglService.class);
		
		String builderClassName = MessageUtil.getText(CONFIG_KEY_NAME);
		if(StringUtils.isNotEmpty(builderClassName)) {
			log.info("初始化构建器:{}",builderClassName);
			Class<?> clazz = null;
			try {
				clazz = Class.forName(builderClassName);				
			}catch(ClassNotFoundException e) {
				log.error("无法找到构建器类:{}",builderClassName,e);
				throw e;
			}
			Object builder = clazz.newInstance();
			if(! (builder instanceof UserPasswordBuilder)) {
				throw new BeanInitializationException("构建器没有实现"+ UserPasswordBuilder.class.getName() +"接口");
			}
			UserPasswordBuilder userPasswordBuilder = (UserPasswordBuilder)builder;
			userPasswordBuilder.setYhglService(yhglService);
			return userPasswordBuilder;
		}else {
			log.info("未配置构建器,使用默认构建器:{}",DefaultUserPasswordBuilder.class.getName());
			UserPasswordBuilder userPasswordBuilder = new DefaultUserPasswordBuilder();
			userPasswordBuilder.setYhglService(yhglService);
			return userPasswordBuilder;	
		}
	}

	@Override
	public Class<?> getObjectType() {
		return UserPasswordBuilder.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
