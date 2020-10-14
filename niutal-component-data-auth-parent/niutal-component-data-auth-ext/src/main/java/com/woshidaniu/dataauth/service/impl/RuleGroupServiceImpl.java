package com.woshidaniu.dataauth.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.dataauth.dao.daointerface.DataAuthDao;
import com.woshidaniu.dataauth.dao.daointerface.DataRuleDao;
import com.woshidaniu.dataauth.dao.daointerface.RuleGroupDao;
import com.woshidaniu.dataauth.dao.entities.RuleCheck;
import com.woshidaniu.dataauth.dao.entities.RuleGroup;
import com.woshidaniu.dataauth.service.svcinterface.RuleGroupService;

@Service
public class RuleGroupServiceImpl extends BaseServiceImpl<RuleGroup, RuleGroupDao> implements RuleGroupService{

	@Autowired
	private RuleGroupDao ruleGroupDao;
	@Autowired
	private DataAuthDao dataAuthDao;
	@Autowired
	private DataRuleDao dataRuleDao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(ruleGroupDao);
	}
	
	@Override
	public String saveRuleGroup(RuleGroup ruleGroup) {
		if(StringUtils.isNotBlank(ruleGroup.getGroupId())) {
			dao.update(ruleGroup);
		}else {
			dao.insert(ruleGroup);
		}
		return ruleGroup.getGroupId();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public int deleteRuleGroup(String groupId, boolean relation) {
		if(relation) {
			dataAuthDao.deleteUserRelation(groupId, null, null);
			dataAuthDao.deleteRoleRelation(groupId, null);
			dataRuleDao.deleteByGroupId(groupId);
		}
		return dao.delete(groupId);
	}

	@Override
	public List<RuleCheck> checkList(String mapperId) {
		List<RuleCheck> res = new ArrayList<RuleCheck>();
		List<RuleCheck> rules = dao.listAll();
		for(RuleCheck rule : rules){
			if(StringUtils.isNoneBlank(rule.getMethodRegexs())) {
				String[] regexArr = rule.getMethodRegexs().split(",");
				for(String regex : regexArr) {
					if(Pattern.matches(regex, mapperId)) {
						res.add(rule);
						break;
					}
				}
			}
		}
		return res;
	}

}
