package com.woshidaniu.designer.dao.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 *@类名称: BaseReportDetailModel.java
 *@类描述：高级报表基本信息表Model
 *@创建人：kangzhidong
 *@创建时间：2015-6-3 上午10:17:33
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class BaseReportDetailModel extends ModelBase {

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
	 * 报表模板文件
	 */
	protected byte[] report_file;

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

	public byte[] getReport_file() {
		return report_file;
	}

	public void setReport_file(byte[] reportFile) {
		report_file = reportFile;
	}

}
