/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dataauth.dao.daointerface;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.woshidaniu.common.dao.BaseDao;
import com.woshidaniu.dataauth.dao.entities.DataRule;

@Repository
public interface DataRuleDao extends BaseDao<DataRule>{
	/**
	 * @description : 根据组ID删除规则 
	 * @author : 小康康(1505)
	 * @param groupId
	 * @return
	 */
	@Delete("delete from niutal_data_rule where group_id = #{groupId}")
	public int deleteByGroupId(@Param("groupId") String groupId);
	/**
	 * 改变启用状态
	 * @param ruleId
	 * @param ruleEnable
	 * @return
	 */
	@Update("update niutal_data_rule set rule_enable = #{ruleEnable} where rule_id = #{ruleId}")
	public int updateEnable(@Param("ruleId") String ruleId, @Param("ruleEnable") boolean ruleEnable);
}
