package com.woshidaniu.designer.dao.entities;

import com.woshidaniu.common.query.ModelBase;

/**
 * 
 *@类名称: DesignFuncModel.java
 *@类描述：系统自定义功能信息表Model：指定自定义功能的基本信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-28 上午09:45:49
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncModel extends ModelBase {
	
	/**
	 * 系统自定义功能信息表ID（niutal_designer_func.func_guid）
	 */
	protected String func_guid;
	/**
	 * 功能代码
	 */
	protected String func_code;
	/**
	 * 操作代码
	 */
	protected String opt_code;
	protected String old_opt_code;
	/**
	 * 系统自定义功能名称（如：个人信息校验）
	 */
	protected String func_name;
	/**
	 * 系统自定义功能描述
	 */
	protected String func_desc;
	/**
	 * 系统自定义功能类型；默认：数据展示; 可选值 ：1:'数据展示',2:'数据维护',3:'预览打印',4:'快速打印',5:'数据导出',6:'数据删除'
	 */
	protected String func_type;
	/**
	 * 系统自定义功能：可编辑状态；默认：不可编辑；0：不可编辑，1：可编辑
	 */
	protected String func_editable;
	/**
	 * 系统自定义功能：是否发布；默认：否；0：否，1：是
	 */
	protected String func_release;
	/**
	 * 系统自定义功能:功能按钮点击前JQGrid列表选择数据行检查类型：默认：否；0：不检查，1：只能选一行，2：至少选择一行
	 */
	protected String require_type;
	/**
	 * 系统自定义功能:查询条件类型;默认 0,可选值  ：0：无查询条件,1：普通查询,2：高级查询
	 */
	protected String query_type;
	/**
	 * 系统自定义功能:最后一次发布的时间戳，该值将作为自定义功能相关js，css的版本号
	 */
	protected String release_time;
	/**
	 * 系统自定义功能：作为按钮绑定的自定义功能时，弹窗宽度值，默认 800 ；单位px
	 */
	protected String func_width;
	/**
	 * 功能页面自定义元素信息表ID
	 */
	protected String func_element_guid;
	/**
	 * 功能页面自定义元素信息表ID
	 */
	protected String func_element_guid2;
	/**
	 * 系统自定义功能：是否有JQGrid列表组件；默认：是；0：否，1：是
	 */
	protected String func_jqgridable;
	
	/**
	 * 高级报表基本信息表ID（niutal_designer_reports.report_guid）
	 */
	protected String report_guid;
	
	public String getFunc_guid() {
		return func_guid;
	}

	public void setFunc_guid(String funcGuid) {
		func_guid = funcGuid;
	}

	public String getFunc_code() {
		return func_code;
	}

	public void setFunc_code(String funcCode) {
		func_code = funcCode;
	}

	public String getOpt_code() {
		return opt_code;
	}

	public void setOpt_code(String optCode) {
		opt_code = optCode;
	}
	
	public String getOld_opt_code() {
		return old_opt_code;
	}

	public void setOld_opt_code(String oldOptCode) {
		old_opt_code = oldOptCode;
	}

	public String getFunc_name() {
		return func_name;
	}

	public void setFunc_name(String funcName) {
		func_name = funcName;
	}

	public String getFunc_desc() {
		return func_desc;
	}

	public void setFunc_desc(String funcDesc) {
		func_desc = funcDesc;
	}

	public String getFunc_editable() {
		return func_editable;
	}

	public void setFunc_editable(String funcEditable) {
		func_editable = funcEditable;
	}

	public String getFunc_release() {
		return func_release;
	}

	public void setFunc_release(String funcRelease) {
		func_release = funcRelease;
	}

	public String getRequire_type() {
		return require_type;
	}

	public void setRequire_type(String requireType) {
		require_type = requireType;
	}

	public String getFunc_width() {
		return func_width;
	}

	public void setFunc_width(String funcWidth) {
		func_width = funcWidth;
	}

	public String getFunc_jqgridable() {
		return func_jqgridable;
	}

	public void setFunc_jqgridable(String funcJqgridable) {
		func_jqgridable = funcJqgridable;
	}

	public String getFunc_element_guid() {
		return func_element_guid;
	}

	public void setFunc_element_guid(String funcElementGuid) {
		func_element_guid = funcElementGuid;
	}

	public String getFunc_type() {
		return func_type;
	}

	public void setFunc_type(String funcType) {
		func_type = funcType;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String queryType) {
		query_type = queryType;
	}

	public String getReport_guid() {
		return report_guid;
	}

	public void setReport_guid(String reportGuid) {
		report_guid = reportGuid;
	}

	public String getFunc_element_guid2() {
		return func_element_guid2;
	}

	public void setFunc_element_guid2(String funcElementGuid2) {
		func_element_guid2 = funcElementGuid2;
	}

	public String getRelease_time() {
		return release_time;
	}

	public void setRelease_time(String releaseTime) {
		release_time = releaseTime;
	}

	
}
