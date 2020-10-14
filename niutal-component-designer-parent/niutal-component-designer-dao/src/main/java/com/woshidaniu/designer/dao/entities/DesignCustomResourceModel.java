package com.woshidaniu.designer.dao.entities;

import com.woshidaniu.common.query.ModelBase;


/**
 * 
 *@类名称: DesignCustomResourceModel.java
 *@类描述：组件脚本样式资源信息表：指定系统中各个组件的资源信息js/css
 *@创建人：kangzhidong
 *@创建时间：2015-5-4 下午03:19:25
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class DesignCustomResourceModel extends ModelBase {

	/**
	 * 组件脚本样式资源信息表ID
	 */
	protected String resource_guid;
	/**
	 * 【组件脚本样式】描述
	 */
	protected String resource_desc;
	/**
	 * 【组件脚本样式】资源请求路径
	 */
	protected String resource_href;
	/**
	 * 【组件脚本样式】来源;默认 0, 0：系统内（应用程序内）,1：系统外(v5样式服务)
	 */
	protected String resource_from;
	/**
	 * 【组件脚本样式】加载顺序
	 */
	protected String resource_ordinal;

	public String getResource_guid() {
		return resource_guid;
	}

	public void setResource_guid(String resourceGuid) {
		resource_guid = resourceGuid;
	}

	public String getResource_desc() {
		return resource_desc;
	}

	public void setResource_desc(String resourceDesc) {
		resource_desc = resourceDesc;
	}

	public String getResource_href() {
		return resource_href;
	}

	public void setResource_href(String resourceHref) {
		resource_href = resourceHref;
	}

	public String getResource_from() {
		return resource_from;
	}

	public void setResource_from(String resourceFrom) {
		resource_from = resourceFrom;
	}

	public String getResource_ordinal() {
		return resource_ordinal;
	}

	public void setResource_ordinal(String resourceOrdinal) {
		resource_ordinal = resourceOrdinal;
	}

}
