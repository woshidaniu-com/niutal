package com.woshidaniu.entities;

import com.woshidaniu.common.query.ModelBase;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.BooleanUtils;

/**
 * 
 *@类名称:ZdpzModel.java
 *@类描述：JQGrid显示列模型对象
 *@创建人：kangzhidong
 *@创建时间：2014-7-1 下午06:35:27
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class ZdpzModel extends ModelBase {

	private String zdpz_id; // 表名称
	private String zd_fzdm; // 字段分组代码；不同的JQGrid为同一个组
	private String zd_label; // 当jqGrid的colNames选项数组为空时，为各列指定题头。如果colNames和此项都为空时，则name选项值会成为题头。
	private String zd_name; // 为Grid中的每个列设置唯一的名称，这是一个必需选项，其中保留字包括subgrid、cb、rn。
	private String zd_index; // 设置排序时所使用的索引名称，这个index名称会作为sidx参数（prmNames中设置的）传递到Server。
	private String zd_key; // 设置列的宽度，目前只能接受以px为单位的数值，默认为150。
	private String zd_align; // 设置列的宽度，目前只能接受以px为单位的数值，默认为150。
	private String zd_hidden; // 设置此列初始化时是否为隐藏状态，默认为false
	private String zd_resizable; // 设置列是否可以变更尺寸，默认为true。
	private String zd_sortable; // 设置该列是否可以排序，默认为true。
	private String zd_number; // 设置该列是否可以排序，默认为true。
	private String yhm;	//用户名
	
	public String label; // 当jqGrid的colNames选项数组为空时，为各列指定题头。如果colNames和此项都为空时，则name选项值会成为题头。
	public String name; // 为Grid中的每个列设置唯一的名称，这是一个必需选项，其中保留字包括subgrid、cb、rn。
	public String index; // 设置排序时所使用的索引名称，这个index名称会作为sidx参数（prmNames中设置的）传递到Server。
	public String align; // 设置列的宽度，目前只能接受以px为单位的数值，默认为150。
	public boolean hidden; // 设置此列初始化时是否为隐藏状态，默认为false
	public boolean resizable; // 设置列是否可以变更尺寸，默认为true。
	public boolean sortable; // 设置该列是否可以排序，默认为true。
	public int number; // 设置该列是否可以排序，默认为true。
	
	public String getZdpz_id() {
		return zdpz_id;
	}

	public void setZdpz_id(String zdpzId) {
		zdpz_id = zdpzId;
	}

	public String getZd_fzdm() {
		return zd_fzdm;
	}

	public void setZd_fzdm(String zdFzdm) {
		zd_fzdm = zdFzdm;
	}

	public String getZd_label() {
		return zd_label;
	}

	public void setZd_label(String zdLabel) {
		zd_label = zdLabel;
	}

	public String getZd_name() {
		return zd_name;
	}

	public void setZd_name(String zdName) {
		zd_name = zdName;
	}

	public String getZd_index() {
		return zd_index;
	}

	public void setZd_index(String zdIndex) {
		zd_index = zdIndex;
	}

	public String getZd_key() {
		return zd_key;
	}

	public void setZd_key(String zdKey) {
		zd_key = zdKey;
	}

	public String getZd_align() {
		return zd_align;
	}

	public void setZd_align(String zdAlign) {
		zd_align = zdAlign;
	}

	public String getZd_hidden() {
		return zd_hidden;
	}

	public void setZd_hidden(String zdHidden) {
		zd_hidden = zdHidden;
	}


	public String getZd_resizable() {
		return zd_resizable;
	}

	public void setZd_resizable(String zdResizable) {
		zd_resizable = zdResizable;
	}

	public String getZd_sortable() {
		return zd_sortable;
	}

	public void setZd_sortable(String zdSortable) {
		zd_sortable = zdSortable;
	}

	public String getZd_number() {
		return zd_number;
	}

	public void setZd_number(String zdNumber) {
		zd_number = zdNumber;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getLabel() {
		return getZd_label();
	}

	public String getName() {
		return getZd_name();
	}

	public String getIndex() {
		return getZd_index();
	}

	public String getAlign() {
		return getZd_align();
	}

	public boolean isHidden() {
		return !BooleanUtils.parse(getZd_hidden());
	}

	public boolean isResizable() {
		return BooleanUtils.parse(getZd_resizable());
	}

	public boolean isSortable() {
		return BooleanUtils.parse(getZd_sortable());
	}

	public int getNumber() {
		return BlankUtils.isBlank(getZd_number())?0:Integer.valueOf(getZd_number());
	}
	
}
