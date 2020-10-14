package com.woshidaniu.dataauth.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dataauth.dao.daointerface.DataRuleDao;
import com.woshidaniu.dataauth.dao.entities.DataRule;
import com.woshidaniu.dataauth.service.svcinterface.DataRuleService;

@Service
public class DataRuleServiceImpl extends BaseServiceImpl<DataRule, DataRuleDao> implements DataRuleService{

	@Autowired
	private DataRuleDao dataRuleDao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dataRuleDao);
	}
	
	@Override
	public String saveRule(DataRule rule) {
		if(StringUtils.isNotBlank(rule.getRuleId())) {
			dao.update(rule);
		}else {
			dao.insert(rule);
		}
		return rule.getRuleId();
	}

	@Override
	public int deleteRule(String ruleId) {
		return dao.delete(ruleId);
	}

	@Override
	public int updateEnable(String ruleId, boolean ruleEnable) {
		return dao.updateEnable(ruleId, ruleEnable);
	}
}
