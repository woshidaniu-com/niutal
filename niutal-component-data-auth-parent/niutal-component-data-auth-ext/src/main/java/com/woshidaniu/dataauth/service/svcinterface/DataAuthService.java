/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dataauth.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.woshidaniu.dataauth.dao.entities.DataRule;
import com.woshidaniu.dataauth.dao.entities.GroupItem;
import com.woshidaniu.dataauth.dao.entities.RuleGroup;

public interface DataAuthService{
	final String userType = "user";
	final String roleType = "role";
	final String addOper = "add";
	final String delOper = "del";
	/**
	 * @description : 
	 * @author : 小康康(1505)
	 * @param yhm
	 * @param jsdm
	 * @return
	 */
	List<DataRule> listByUser(String yhm, String jsdm);
	/**
	 * @description : 
	 * @author : 小康康(1505)
	 * @param jsdms
	 * @return
	 */
	List<DataRule> listByRole(List<String> jsdms);
	/**
	 * @description : 根据类型查询数据规则组
	 * @author : 小康康(1505)
	 * @param groupType(role/user)
	 * @param jsdm
	 * @param yhm
	 * @return
	 */
	List<RuleGroup> listByType(String groupType, String jsdm, String yhm);
	/**
	 * @description : 查询所有角色
	 * @author : 小康康(1505)
	 * @return
	 */
	List<Map<String, String>> listRoles();
	/**
	 * @description : 查询所有用户包含所属角色
	 * @author : 小康康(1505)
	 * @return
	 */
	List<Map<String, String>> listUsers();
	/**
	 * @description : 保存数据规则和用户角色的关系
	 * @author : 小康康(1505)
	 * @param oper(del/add)
	 * @param groupId
	 * @param jsdm
	 * @param yhm
	 * @param type(user/role)
	 * @return
	 */
	int saveRuleGroupRelation(String oper, String groupId,String jsdm, String yhm, String type);
	/**
	 * @description : 查询权限组选项
	 * @author : 小康康(1505)
	 * @param groupId
	 * @param jsdm
	 * @param yhm
	 * @param type(user/role)
	 * @return
	 */
	Map<String, List<GroupItem>> itemsByRelation(String groupId, String jsdm, String yhm, String type);
	/**
	 * @description : 保存权限组选项值
	 * @author : 小康康(1505)
	 * @param groupId
	 * @param jsdm
	 * @param yhm
	 * @param itemValues
	 * @param type(user/role)
	 * @return
	 */
	int saveItemValues(String groupId,String jsdm, String yhm, String itemValues, String type);
}
