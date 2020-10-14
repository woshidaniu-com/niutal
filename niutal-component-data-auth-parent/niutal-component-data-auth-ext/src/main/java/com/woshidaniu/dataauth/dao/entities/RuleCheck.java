/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.dataauth.dao.entities;

import java.io.Serializable;

public class RuleCheck implements Serializable{
	private static final long serialVersionUID = -1423517477864041987L;
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
	/**启用状态*/
	private boolean ruleEnable;
	
	/**数据权限规则组代码*/
	private String groupCode;
	/**数据权限规则组名称*/
	private String groupName;
	/**数据权限规则组类型(user/role)*/
	private String groupType;
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
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
}
