/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dataauth.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.woshidaniu.dataauth.dao.entities.DataRule;
import com.woshidaniu.dataauth.dao.entities.GroupItem;
import com.woshidaniu.dataauth.dao.entities.RuleGroup;


@Repository
public interface DataAuthDao{
	/**
	 * @description : 根据用户名查询数据规则
	 * @author : 小康康(1505)
	 * @param yhm
	 * @param jsdm
	 * @return
	 */
	List<DataRule> listByUser(@Param("yhm") String yhm, @Param("jsdm") String jsdm);
	/**
	 * @description : 根据角色代码查询数据规则
	 * @author : 小康康(1505)
	 * @param jsdms
	 * @return
	 */
	List<DataRule> listByRole(@Param("jsdms") List<String> jsdms);
	/**
	 * @description : 根据类型查询数据规则组
	 * @author : 小康康(1505)
	 * @param groupType(role/user)
	 * @param jsdm
	 * @param yhm
	 * @return
	 */
	List<RuleGroup> listByType(@Param("groupType") String groupType,@Param("jsdm") String jsdm,@Param("yhm") String yhm);
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
	 * @description : 插入数据规则与用户的关系
	 * @author : 小康康(1505)
	 * @param groupId
	 * @param yhm
	 * @param jsdm
	 * @return
	 */
	@Insert("insert into niutal_data_rule_group_user (group_id,yhm,jsdm) values (#{groupId},#{yhm},#{jsdm})")
	int insertUserRelation(@Param("groupId") String groupId,@Param("yhm") String yhm, @Param("jsdm") String jsdm);
	/**
	 * @description : 删除数据规则与用户的关系
	 * @author : 小康康(1505)
	 * @param groupId
	 * @param yhm
	 * @param jsdm
	 * @return
	 */
	int deleteUserRelation(@Param("groupId") String groupId, @Param("yhm") String yhm, @Param("jsdm") String jsdm);
	/**
	 * @description : 插入数据规则与角色的关系
	 * @author : 小康康(1505)
	 * @param groupId
	 * @param jsdm
	 * @return
	 */
	@Insert("insert into niutal_data_rule_group_role (group_id,jsdm) values (#{groupId},#{jsdm})")
	int insertRoleRelation(@Param("groupId") String groupId,@Param("jsdm") String jsdm);
	/**
	 * @description : 删除数据规则与角色的关系
	 * @author : 小康康(1505)
	 * @param groupId
	 * @param jsdm
	 * @return
	 */
	int deleteRoleRelation(@Param("groupId") String groupId,@Param("jsdm") String jsdm);
	/**
	 * @description : 根据与用户关联的选项值
	 * @author : 小康康(1505)
	 * @param groupId
	 * @param yhm
	 * @return
	 */
	@Select("select g.group_id as groupId,g.select_item as selectItem,u.yhm,u.item_values as itemValues from niutal_data_rule_group g"
			+ " left join niutal_data_rule_group_user u on u.group_id = g.group_id and u.yhm = #{yhm} and u.jsdm = #{jsdm} where g.group_id = #{groupId}")
	@ResultType(RuleGroup.class)
	RuleGroup selectUserRelation(@Param("groupId") String groupId, @Param("yhm") String yhm, @Param("jsdm") String jsdm);
	/**
	 * @description : 查询与角色关联的选项值
	 * @author : 小康康(1505)
	 * @param groupId
	 * @param jsdm
	 * @return
	 */
	@Select("select g.group_id as groupId,g.select_item as selectItem,r.jsdm,r.item_values as itemValues from niutal_data_rule_group g"
			+ " left join niutal_data_rule_group_role r on r.group_id = g.group_id and r.jsdm = #{jsdm} where g.group_id = #{groupId}")
	@ResultType(RuleGroup.class)
	RuleGroup selectRoleRelation(@Param("groupId") String groupId, @Param("jsdm") String jsdm);
	/**
	 * @description : 保存与用户关联的选项值
	 * @author : 小康康(1505)
	 * @param groupId
	 * @param yhm
	 * @param jsdm
	 * @param itemValues
	 * @return
	 */
	@Update("update niutal_data_rule_group_user set item_values = #{itemValues} where group_id = #{groupId} and yhm = #{yhm} and jsdm = #{jsdm}")
	int updateUserRelation(@Param("groupId") String groupId,@Param("yhm") String yhm, @Param("jsdm") String jsdm, @Param("itemValues") String itemValues);
	/**
	 * @description : 保存与角色关联的选项值
	 * @author : 小康康(1505)
	 * @param groupId
	 * @param jsdm
	 * @param itemValues
	 * @return
	 */
	@Update("update niutal_data_rule_group_role set item_values = #{itemValues} where group_id = #{groupId} and jsdm = #{jsdm}")
	int updateRoleRelation(@Param("groupId") String groupId,@Param("jsdm") String jsdm, @Param("itemValues") String itemValues);
	/**
	 * @description : 查询权限组选项
	 * @author : 小康康(1505)
	 * @param selectItemsql
	 * @return
	 */
	@Select("${selectItemsql}")
	@ResultType(GroupItem.class)
	List<GroupItem> selectGroupItem(@Param("selectItemsql") String selectItemsql);
}
