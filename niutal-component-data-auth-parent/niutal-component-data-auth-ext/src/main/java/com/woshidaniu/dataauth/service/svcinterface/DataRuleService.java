package com.woshidaniu.dataauth.service.svcinterface;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dataauth.dao.entities.DataRule;

public interface DataRuleService extends BaseService<DataRule>{
	/**
	 * @description : 保存规则 
	 * @author : 小康康(1505)
	 * @param rule
	 * @return
	 */
	String saveRule(DataRule rule);
	/**
	 * @description : 删除规则
	 * @author : 小康康(1505)
	 * @param ruleId
	 * @return
	 */
	int deleteRule(String ruleId);
	/**
	 * 改变启用状态
	 * @param ruleId
	 * @param ruleEnable
	 */
	int updateEnable(String ruleId, boolean ruleEnable);
}
