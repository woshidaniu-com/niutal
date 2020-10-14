package com.woshidaniu.designer.dao.entities;

/**
 * 
 *@类名称: DesignAutoCompleteFieldModel.java
 *@类描述：系统可自定义自动完成自动信息表模型:存储系统中所有可自动完成的字段信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-24 下午03:48:54
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignAutoCompleteFieldModel extends BaseAutoCompleteFieldModel {
	
	/**
	 * 系统可自定义自动完成自动信息表ID[niutal_designer_auto_fields表主键]
	 */
	protected String table_guid;
	/**
	 * 系统可自定义字段功能对应字段设计表ID(niutal_designer_query_fields.table_guid)或 功能功能对应页面自定义字段设计表ID【niutal_designer_func_fields.table_guid】
	 */
	protected String target_guid;


	public String getTable_guid() {
		return table_guid;
	}

	public void setTable_guid(String tableGuid) {
		table_guid = tableGuid;
	}

	public String getTarget_guid() {
		return target_guid;
	}

	public void setTarget_guid(String targetGuid) {
		target_guid = targetGuid;
	}
	
	

}
