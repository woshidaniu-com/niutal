package com.woshidaniu.dataauth.service.svcinterface;

import java.util.List;

import com.woshidaniu.common.service.BaseService;
import com.woshidaniu.dataauth.dao.entities.RuleCheck;
import com.woshidaniu.dataauth.dao.entities.RuleGroup;

public interface RuleGroupService extends BaseService<RuleGroup>{
	/**
	 * @description : 保存规则组 
	 * @author : 小康康(1505)
	 * @param ruleGroup
	 * @return groupId
	 */
	String saveRuleGroup(RuleGroup ruleGroup);
	/**
	 * @description : 删除规则组 
	 * @author : 小康康(1505)
	 * @param groupId
	 * @param relation 是否删除关联数据
	 * @return
	 */
	int deleteRuleGroup(String groupId, boolean relation);
	/**
	 * @description : 返回符合条件的所有规则
	 * @author : 小康康(1505)
	 * @param mapperId
	 * @return
	 */
	List<RuleCheck> checkList(String mapperId);
}
