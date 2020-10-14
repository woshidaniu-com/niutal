package com.woshidaniu.designer.dao.entities;

/**
 * 
 *@类名称: DesignFuncElementFieldModel.java
 *@类描述：功能页面自定义元素关联字段信息表Model:指定设计器生成的功能页面的html元素信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午01:59:09
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncElementFieldModel extends DesignFuncElementQueryFieldModel {

	/**
	 * 功能页面自定义元素关联字段信息表ID(niutal_designer_func_elements.element_guid)
	 */
	protected String element_guid;
	/**
	 * 系统可自定义字段功能表ID（niutal_designer_func.func_guid）
	 */
	protected String func_guid;

	public String getElement_guid() {
		return element_guid;
	}

	public void setElement_guid(String elementGuid) {
		element_guid = elementGuid;
	}

	public String getFunc_guid() {
		return func_guid;
	}

	public void setFunc_guid(String funcGuid) {
		func_guid = funcGuid;
	}

}
