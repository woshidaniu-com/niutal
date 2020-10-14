/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dataauth.dao.daointerface;

import java.util.List;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dataauth.dao.entities.RuleCheck;
import com.woshidaniu.dataauth.dao.entities.RuleGroup;

@Repository
public interface RuleGroupDao extends BaseDao<RuleGroup>{
	/**
	 * @description : 
	 * @author : 小康康(1505)
	 * @return
	 */
	@Select("select g.group_id as groupId,g.group_code as groupCode,g.group_name as groupName,g.group_type as groupType,"
			+ "r.rule_id as ruleId,r.method_regexs as methodRegexs,r.preposition_sql as prepositionSql,r.postposition_sql as postpositionSql,r.replace_regexs as replaceRegexs,"
			+ "r.replace_sqls as replaceSqls,r.rule_enable as ruleEnable from niutal_data_rule r join niutal_data_rule_group g on r.group_id = g.group_id")
	@ResultType(RuleCheck.class)
	List<RuleCheck> listAll();
}
