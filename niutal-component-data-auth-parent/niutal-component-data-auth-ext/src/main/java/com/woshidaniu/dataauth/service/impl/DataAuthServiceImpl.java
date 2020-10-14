/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dataauth.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.woshidaniu.dataauth.common.Constants;
import com.woshidaniu.dataauth.dao.daointerface.DataAuthDao;
import com.woshidaniu.dataauth.dao.entities.DataRule;
import com.woshidaniu.dataauth.dao.entities.GroupItem;
import com.woshidaniu.dataauth.dao.entities.RuleGroup;
import com.woshidaniu.dataauth.service.svcinterface.DataAuthService;
import com.woshidaniu.format.pinyin4j.PingYinUtils;

@Service
public class DataAuthServiceImpl implements DataAuthService{
	
	@Autowired
	private DataAuthDao dao;
	
	@Override
	@Cacheable(Constants.CACHE_NAME)
	public List<DataRule> listByUser(String yhm, String jsdm) {
		return dao.listByUser(yhm, jsdm);
	}

	@Override
	@Cacheable(Constants.CACHE_NAME)
	public List<DataRule> listByRole(List<String> jsdms) {
		return dao.listByRole(jsdms);
	}

	@Override
	public List<Map<String, String>> listRoles() {
		return dao.listRoles();
	}

	@Override
	public List<Map<String, String>> listUsers() {
		return dao.listUsers();
	}

	@Override
	public List<RuleGroup> listByType(String groupType, String jsdm, String yhm) {
		return dao.listByType(groupType, jsdm, yhm);
	}

	@Override
	@CacheEvict(cacheNames = Constants.CACHE_NAME, allEntries = true)
	public int saveRuleGroupRelation(String oper, String groupId, String jsdm, String yhm, String type) {
		if(userType.equals(type)) {
			if(addOper.equals(oper)) {
				return dao.insertUserRelation(groupId, yhm, jsdm);
			}else if(delOper.equals(oper)) {
				return dao.deleteUserRelation(groupId, yhm, jsdm);
			}
		}else if(roleType.equals(type)) {
			if(addOper.equals(oper)) {
				return dao.insertRoleRelation(groupId, jsdm);
			}else if(delOper.equals(oper)) {
				return dao.deleteRoleRelation(groupId, jsdm);
			}
		}
		return 0;
	}

	@Override
	public Map<String, List<GroupItem>> itemsByRelation(String groupId, String jsdm, String yhm, String type) {
		RuleGroup g = roleType.equals(type) ? dao.selectRoleRelation(groupId, jsdm) : dao.selectUserRelation(groupId, yhm, jsdm);
		if(StringUtils.isNoneBlank(g.getSelectItem())) {
			String selectItem = g.getSelectItem();
			selectItem = selectItem.replaceAll("\\{yhm\\}", "'"+yhm+"'");
			selectItem = selectItem.replaceAll("\\{jsdm\\}", "'"+jsdm+"'");
			List<GroupItem> items = dao.selectGroupItem(selectItem);
			Map<String, List<GroupItem>> res = new HashMap<String, List<GroupItem>>();
			for (GroupItem item : items) {
				item.setGroupId(groupId);
				item.setFirstPinYin(PingYinUtils.converterToFirstSpell(item.getItemName()).substring(0, 1));
				item.setHas(g.getItemValues() != null && g.getItemValues().contains("'"+item.getItemKey()+"'") ? 1 : 0);
				if(res.containsKey(item.getFirstPinYin())) {
					res.get(item.getFirstPinYin()).add(item);
				} else {
					List<GroupItem> arr = new ArrayList<GroupItem>();
					arr.add(item);
					res.put(item.getFirstPinYin(), arr);
				}
			}
			return res;
		}
		return Collections.emptyMap();
	}

	@Override
	@CacheEvict(cacheNames = Constants.CACHE_NAME, allEntries = true)
	public int saveItemValues(String groupId,String jsdm, String yhm, String itemValues, String type) {
		if(roleType.equals(type)) {
			return dao.updateRoleRelation(groupId, jsdm, itemValues);
		}
		return dao.updateUserRelation(groupId, yhm, jsdm, itemValues);
	}
}
