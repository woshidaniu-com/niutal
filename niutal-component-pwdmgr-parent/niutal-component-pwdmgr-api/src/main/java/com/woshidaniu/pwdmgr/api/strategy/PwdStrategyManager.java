package com.woshidaniu.pwdmgr.api.strategy;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PwdStrategyManager {
	
	protected ConcurrentMap<String, PwdVerifiStrategy> COMPLIED_PWD_VERIFI_STRATEGY = new ConcurrentHashMap<String, PwdVerifiStrategy>();
	protected ConcurrentMap<String, PwdRetakeStrategy> COMPLIED_PWD_RETAKE_STRATEGY = new ConcurrentHashMap<String, PwdRetakeStrategy>();
	
	public PwdStrategyManager(){
		
	}
	
	public Set<String> verifiStrategys() {
		return COMPLIED_PWD_VERIFI_STRATEGY.keySet();
	}
	
	public Set<String> retakeStrategys() {
		return COMPLIED_PWD_RETAKE_STRATEGY.keySet();
	}
	
	/**
	 * 
	 *@描述		：注册账号核实字段验证策略接口
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月12日下午3:30:54
	 *@param strategy
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public void register(PwdVerifiStrategy strategy) {
		if (strategy != null ) {
			COMPLIED_PWD_VERIFI_STRATEGY.putIfAbsent( strategy.name(), strategy);
		}
	}
	
	/**
	 * 
	 *@描述		： 根据字段名称查找账号核实字段验证策略实现对象
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月12日下午3:30:37
	 *@param strategy
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public PwdVerifiStrategy getPwdVerifiStrategy(String strategy) {
		if (strategy != null) {
			PwdVerifiStrategy ret = COMPLIED_PWD_VERIFI_STRATEGY.get(strategy);
			if (ret != null) {
				return ret;
			}
		}
		return COMPLIED_PWD_VERIFI_STRATEGY.get(PwdStrategy.DEFAULT_STRATEGY);
	}
	
	/**
	 * 
	 *@描述		：注册密码找回策略接口
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月7日上午10:29:55
	 *@param strategy
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public void register(PwdRetakeStrategy strategy) {
		if (strategy != null ) {
			COMPLIED_PWD_RETAKE_STRATEGY.putIfAbsent( strategy.name(), strategy);
		}
	}
	
	/**
	 * 
	 *@描述		：根据策略名称查找密码找回策略实现对象
	 *@创建人		: kangzhidong
	 *@创建时间	: 2017年4月7日上午10:32:03
	 *@param strategy
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public PwdRetakeStrategy getPwdRetakeStrategy(String strategy) {
		if (strategy != null) {
			PwdRetakeStrategy ret = COMPLIED_PWD_RETAKE_STRATEGY.get(strategy);
			if (ret != null) {
				return ret;
			}
		}
		return COMPLIED_PWD_RETAKE_STRATEGY.get(PwdStrategy.DEFAULT_STRATEGY);
	}
	
}
