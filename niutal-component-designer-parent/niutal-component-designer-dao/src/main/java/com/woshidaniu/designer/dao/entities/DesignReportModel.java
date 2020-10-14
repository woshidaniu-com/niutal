package com.woshidaniu.designer.dao.entities;

/**
 * 
 *@类名称: DesignReportModel.java
 *@类描述：自定义高级报表设计表Model
 *@创建人：kangzhidong
 *@创建时间：2015-6-3 上午09:31:08
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignReportModel extends DesignFuncModel {

	/**
	 * 高级报表基本信息表ID（niutal_designer_reports.report_guid）
	 */
	protected String report_guid;
	/**
	 * 高级报表名称（如：补课申请表）
	 */
	protected String report_name;
	/**
	 * 高级报表别名;用于请求报表服务器的报表文件名（如：ttksqd_02,请求报表时自动添加.cpt后缀）
	 */
	protected String report_alias;
	/**
	 * 高级报表描述
	 */
	protected String report_desc;
	/**
	 * 【 自定义高级报表】查询条件类型;默认 3,可选值 ：1：无查询条件,2：普通查询,3：高级查询
	 */
	protected String query_type;
	/**
	 * 【 自定义高级报表】对应的 高级查询配置或普通查询配置ID（niutal_designer_advanced_query.
	 * query_guid或niutal_designer_querys.query_guid）
	 */
	protected String query_guid;

	public String getReport_guid() {
		return report_guid;
	}

	public void setReport_guid(String reportGuid) {
		report_guid = reportGuid;
	}

	public String getReport_name() {
		return report_name;
	}

	public void setReport_name(String reportName) {
		report_name = reportName;
	}

	public String getReport_alias() {
		return report_alias;
	}

	public void setReport_alias(String reportAlias) {
		report_alias = reportAlias;
	}

	public String getReport_desc() {
		return report_desc;
	}

	public void setReport_desc(String reportDesc) {
		report_desc = reportDesc;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String queryType) {
		query_type = queryType;
	}

	public String getQuery_guid() {
		return query_guid;
	}

	public void setQuery_guid(String queryGuid) {
		query_guid = queryGuid;
	}

}
