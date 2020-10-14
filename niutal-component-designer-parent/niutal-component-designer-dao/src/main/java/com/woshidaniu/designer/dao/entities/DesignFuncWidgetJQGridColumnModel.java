package com.woshidaniu.designer.dao.entities;


/**
 * 
 *@类名称: DesignFuncWidgetJQGridColumnModel.java
 *@类描述： JQGrid组件数据列信息表Model:指定设计器生成的JQGrid组件数据列信息
 *@创建人：kangzhidong
 *@创建时间：2015-4-29 下午01:39:53
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignFuncWidgetJQGridColumnModel extends DesignFuncElementModel {

	/**
	 * 功能页面自定义组件信息表ID（niutal_designer_func_widgets.widget_guid）
	 */
	protected String func_widget_guid;
	/**
	 * JQGrid组件数据列信息表ID
	 */
	protected String field_guid;
	/**
	 * 【JQGrid组件】定义单元格对齐方式；可选值：left, center, right.;默认为center
	 */
	protected String field_align;
	/**
	 * 【JQGrid组件】该参数扩展单元格td的属性，从而利用动态属性达到特定效果，如合并单元格!如果输入的字符代表函数则返回函数返回的结果，如果是字符串则作为属性直接使用
	 */
	protected String field_cellattr;
	/**
	 * 【JQGrid组件】单元格是否可编辑，1:true,0:false;默认为 0
	 */
	protected String field_editable;
	/**
	 * 【JQGrid组件】编辑的一系列选项。如： 
	 * {name:’myname’,index:’myname’,width:200,editable:true,edittype:’select’,editoptions: {dataUrl:”${ctx}/admin/deplistforstu.action”}},这个是演示动态从服务器端获取数据 
	 * 	或者  {name:’myname’,index:’myname’,width:200,editable:true,edittype:’select’,editoptions: {value:"zy:专业;dl:大类"}},这个静态数据
	 */
	protected String field_editoptions;
	/**
	 * 【JQGrid组件】编辑的规则;如：{name:’age’,index:’age’,editable:true,editrules: {edithidden:true,required:true,number:true,minValue:10,maxValue:100}},
	 * 设定年龄的最大值为100，最小值为10，而且为数字类型，并且为必输字段
	 */
	protected String field_editrules;
	/**
	 * 【JQGrid组件】可以编辑的类型。可选值：text, textarea, select, checkbox, password, button, image, file 
	 */
	protected String field_edittype;
	/**
	 * 【JQGrid组件】列宽度是否要固定不可变，1:true,0:false;默认为 0
	 */
	protected String field_fixed;

	/**
	 * 【JQGrid组件】对列进行格式化时设置的 函数名 ,select,自定义字符串;
	 *  如： {name:’myname’, edittype:’select’, formatter:’select’, editoptions:{value:"1:One;2:Two"}} 
	 */
	protected String field_formatter;
	/**
	 * 【JQGrid组件】设置此列初始化时是否为隐藏状态，1:true,0:false;默认为 0
	 */
	protected String field_hidden;
	/**
	 * 【JQGrid组件】设置排序时所使用的索引名称，这个index名称会作为sidx参数（prmNames中设置的）传递到Server。
	 */
	protected String field_index;
	/**
	 * 【JQGrid组件】设置列是否主键列，1:true,0:false;;默认为 1
	 */
	protected String field_key;
	/**
	 * 【JQGrid组件】列名称，当jqGrid的colNames选项数组为空时，为各列指定题头。如果colNames和此项都为空时，则name选项值会成为题头
	 */
	protected String field_label;
	/**
	 * 【JQGrid组件】中的每个列设置唯一的名称，这是一个必需选项，其中保留字包括subgrid、cb、rn
	 */
	protected String field_name;
	/**
	 * 【JQGrid组件】设置列是否可以变更尺寸，1:true,0:false;默认为 1
	 */
	protected String field_resizable;
	/**
	 * 【JQGrid组件】设置该列是否可以排序，1:true,0:false;默认为 1
	 */
	protected String field_sortable;
	/**
	 * 【JQGrid组件】没列的宽度;如：150px
	 */
	protected String field_width;
	/**
	 * 【JQGrid组件】列显示的顺序
	 */
	protected String field_ordinal;
	/**
	 * 【JQGrid组件】列是否已经被移除
	 */
	protected String field_removed;
	/**
	 * 【JQGrid组件】列是否作为弹窗的参数，1:true,0:false;默认为 0
	 */
	protected String field_param;
	
	public String getFunc_widget_guid() {
		return func_widget_guid;
	}

	public void setFunc_widget_guid(String funcWidgetGuid) {
		func_widget_guid = funcWidgetGuid;
	}

	public String getField_guid() {
		return field_guid;
	}

	public void setField_guid(String fieldGuid) {
		field_guid = fieldGuid;
	}

	public String getField_align() {
		return field_align;
	}

	public void setField_align(String fieldAlign) {
		field_align = fieldAlign;
	}

	public String getField_cellattr() {
		return field_cellattr;
	}

	public void setField_cellattr(String fieldCellattr) {
		field_cellattr = fieldCellattr;
	}

	public String getField_editable() {
		//Boolean.valueOf(BooleanUtil.parse(field_editable)).toString()
		return field_editable != null ? field_editable.trim() : field_editable;
	}

	public void setField_editable(String fieldEditable) {
		field_editable = fieldEditable;
	}

	public String getField_editoptions() {
		return field_editoptions;
	}

	public void setField_editoptions(String fieldEditoptions) {
		field_editoptions = fieldEditoptions;
	}

	public String getField_editrules() {
		return field_editrules;
	}

	public void setField_editrules(String fieldEditrules) {
		field_editrules = fieldEditrules;
	}

	public String getField_edittype() {
		return field_edittype;
	}

	public void setField_edittype(String fieldEdittype) {
		field_edittype = fieldEdittype;
	}

	public String getField_fixed() {
		//Boolean.valueOf(BooleanUtil.parse(field_fixed)).toString()
		return field_fixed != null ? field_fixed.trim() : field_fixed;
	}

	public void setField_fixed(String fieldFixed) {
		field_fixed = fieldFixed;
	}

	public String getField_formatter() {
		return field_formatter;
	}

	public void setField_formatter(String fieldFormatter) {
		field_formatter = fieldFormatter;
	}

	public String getField_hidden() {
		//Boolean.valueOf(BooleanUtil.parse(field_hidden)).toString()
		return field_hidden != null ? field_hidden.trim() : field_hidden;
	}

	public void setField_hidden(String fieldHidden) {
		field_hidden = fieldHidden;
	}

	public String getField_index() {
		return field_index;
	}

	public void setField_index(String fieldIndex) {
		field_index = fieldIndex;
	}

	public String getField_key() {
		//Boolean.valueOf(BooleanUtil.parse(field_key)).toString()
		return field_key != null ? field_key.trim() : field_key;
	}

	public void setField_key(String fieldKey) {
		field_key = fieldKey;
	}

	public String getField_label() {
		return field_label;
	}

	public void setField_label(String fieldLabel) {
		field_label = fieldLabel;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String fieldName) {
		field_name = fieldName;
	}

	public String getField_resizable() {
		//Boolean.valueOf(BooleanUtil.parse(field_resizable)).toString()
		return field_resizable != null ? field_resizable.trim() : field_resizable;
	}

	public void setField_resizable(String fieldResizable) {
		field_resizable = fieldResizable;
	}

	public String getField_sortable() {
		//Boolean.valueOf(BooleanUtil.parse(field_sortable)).toString()
		return field_sortable != null ? field_sortable.trim() : field_sortable;
	}

	public void setField_sortable(String fieldSortable) {
		field_sortable = fieldSortable;
	}

	public String getField_width() {
		return field_width;
	}

	public void setField_width(String fieldWidth) {
		field_width = fieldWidth;
	}

	public String getField_ordinal() {
		return field_ordinal;
	}

	public void setField_ordinal(String fieldOrdinal) {
		field_ordinal = fieldOrdinal;
	}

	public String getField_removed() {
		return field_removed;
	}

	public void setField_removed(String fieldRemoved) {
		field_removed = fieldRemoved;
	}

	public String getField_param() {
		return field_param;
	}

	public void setField_param(String fieldParam) {
		field_param = fieldParam;
	}

}
