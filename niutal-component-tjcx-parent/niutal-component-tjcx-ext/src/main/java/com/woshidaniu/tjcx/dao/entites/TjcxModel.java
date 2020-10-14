package com.woshidaniu.tjcx.dao.entites;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.woshidaniu.dao.entities.ExportConfigModel;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计查询
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-22 下午04:00:18
 * @版本： V1.0
 * @修改记录:
 */
public class TjcxModel  implements Serializable{
	private static final long serialVersionUID = -4525876328815740360L;

	// 报表统计
	private List<HashMap<String, String>> tjcxlbList = null;// 统计查询列表
	private List<ExportConfigModel> exportConfigList = null;
	public TjcxModel() {
		super();
	}

	public List<HashMap<String, String>> getTjcxlbList() {
		return tjcxlbList;
	}

	public void setTjcxlbList(List<HashMap<String, String>> tjcxlbList) {
		this.tjcxlbList = tjcxlbList;
	}

	public List<ExportConfigModel> getExportConfigList() {
		return exportConfigList;
	}

	public void setExportConfigList(List<ExportConfigModel> exportConfigList) {
		this.exportConfigList = exportConfigList;
	}

}
