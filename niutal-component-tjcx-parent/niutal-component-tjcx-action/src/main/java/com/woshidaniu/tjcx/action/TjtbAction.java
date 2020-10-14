package com.woshidaniu.tjcx.action;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.tjcx.dao.entites.TjtbModel;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计图表
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-23 上午08:38:45
 * @版本： V1.0
 * @修改记录:
 */
public class TjtbAction extends BaseAction implements ModelDriven<TjtbModel> {

	private static final long serialVersionUID = 1L;

	private TjtbModel model = new TjtbModel();

	/**
	 * 
	 * @描述:统计图表
	 * @作者：ligl
	 * @日期：2013-9-3 上午10:49:06
	 * @修改记录: 
	 * @return
	 * String 返回类型 
	 * @throws
	 */
	public String tjtb() {
		return SUCCESS;
	}

	public TjtbModel getModel() {
		return model;
	}

	public void setModel(TjtbModel model) {
		this.model = model;
	}

}
