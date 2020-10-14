/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dataauth.dao.entities;

import java.util.HashMap;
import java.util.Map;

import com.woshidaniu.common.query.ModelBase;

public class DataRule extends ModelBase{
	
	private static final long serialVersionUID = -986001957435778766L;
	/**标识*/
	private String ruleId;
	/**规则组标识*/
	private String groupId;
	/**被拦截的方法(正则表达式 多个以逗号分隔)*/
	private String methodRegexs;
	/**前置SQL(被追加到源SQL之前)*/
	private String prepositionSql;
	/**后置SQL(被追加到源SQL之后)*/
	private String postpositionSql;
	/**被替换SQL正则表达式(多个以逗号分隔)*/
	private String replaceRegexs;
	/**替换的SQL(替换源SQL,多个以逗号分隔)*/
	private String replaceSqls;
	/**是否启用*/
	private boolean ruleEnable = true;
	
	/**用户名*/
	private String yhm;
	/**角色代码*/
	private String jsdm;
	/**规则组选项值*/
	private String itemValues;
	
	public Map<String, String> toMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("ruleId", ruleId);map.put("groupId", groupId);map.put("methodRegexs", methodRegexs);map.put("prepositionSql", prepositionSql);
		map.put("postpositionSql", postpositionSql);map.put("replaceRegexs", replaceRegexs);map.put("replaceSqls", replaceSqls);map.put("ruleEnable", ruleEnable+"");
		return map;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getMethodRegexs() {
		return methodRegexs;
	}

	public void setMethodRegexs(String methodRegexs) {
		this.methodRegexs = methodRegexs;
	}

	public String getPrepositionSql() {
		return prepositionSql;
	}

	public void setPrepositionSql(String prepositionSql) {
		this.prepositionSql = prepositionSql;
	}

	public String getPostpositionSql() {
		return postpositionSql;
	}

	public void setPostpositionSql(String postpositionSql) {
		this.postpositionSql = postpositionSql;
	}

	public String getReplaceRegexs() {
		return replaceRegexs;
	}

	public void setReplaceRegexs(String replaceRegexs) {
		this.replaceRegexs = replaceRegexs;
	}

	public String getReplaceSqls() {
		return replaceSqls;
	}

	public void setReplaceSqls(String replaceSqls) {
		this.replaceSqls = replaceSqls;
	}

	public boolean isRuleEnable() {
		return ruleEnable;
	}

	public void setRuleEnable(boolean ruleEnable) {
		this.ruleEnable = ruleEnable;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getJsdm() {
		return jsdm;
	}

	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}

	public String getItemValues() {
		return itemValues;
	}

	public void setItemValues(String itemValues) {
		this.itemValues = itemValues;
	}
	
	
}
