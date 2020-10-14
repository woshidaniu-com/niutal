package com.woshidaniu.pwdmgr.api.factory;


import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.woshidaniu.pwdmgr.api.strategy.PwdRetakeStrategy;
import com.woshidaniu.pwdmgr.api.strategy.PwdStrategyManager;
import com.woshidaniu.pwdmgr.api.strategy.PwdVerifiStrategy;
/**
 * 
 *@类名称		： PwdStrategyManagerBuilder.java
 *@类描述		：Spring集成PwdStrategyManager实现
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月7日 上午11:41:54
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class PwdStrategyManagerFactory implements FactoryBean<PwdStrategyManager>, ApplicationContextAware {

	protected ApplicationContext applicationContext;
	protected List<PwdRetakeStrategy> retakeStrategys;
	protected List<PwdVerifiStrategy> verifiStrategys;
	
	@Override
	public PwdStrategyManager getObject() throws Exception {
		
		//构造密码相关策略对象管理者
		PwdStrategyManager strategyManager = new PwdStrategyManager();
		
		
		//注册扩展的密码找回策略
		Map<String, PwdRetakeStrategy>  retakeMap = applicationContext.getBeansOfType(PwdRetakeStrategy.class);
		if(retakeMap != null){
			for (PwdRetakeStrategy pwdRetakeStrategy : retakeMap.values()) {
				strategyManager.register(pwdRetakeStrategy);
			}
		}
		/*
		if(retakeStrategys != null){
			for (PwdRetakeStrategy pwdRetakeStrategy : retakeStrategys) {
				strategyManager.register(pwdRetakeStrategy);
			}
		}*/
		
		//注册账号核实字段验证策略
		Map<String, PwdVerifiStrategy>  verifiMap = applicationContext.getBeansOfType(PwdVerifiStrategy.class);
		if(retakeMap != null){
			for (PwdVerifiStrategy pwdVerifiStrategy : verifiMap.values()) {
				strategyManager.register(pwdVerifiStrategy);
			}
		}
		/*if(verifiStrategys != null){
			for (PwdVerifiStrategy pwdVerifiStrategy : verifiStrategys) {
				strategyManager.register(pwdVerifiStrategy);
			}
		}*/
		
		return strategyManager;
	}

	@Override
	public Class<?> getObjectType() {
		return PwdStrategyManager.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public List<PwdRetakeStrategy> getRetakeStrategys() {
		return retakeStrategys;
	}

	public void setRetakeStrategys(List<PwdRetakeStrategy> retakeStrategys) {
		this.retakeStrategys = retakeStrategys;
	}

	public List<PwdVerifiStrategy> getVerifiStrategys() {
		return verifiStrategys;
	}

	public void setVerifiStrategys(List<PwdVerifiStrategy> verifiStrategys) {
		this.verifiStrategys = verifiStrategys;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
