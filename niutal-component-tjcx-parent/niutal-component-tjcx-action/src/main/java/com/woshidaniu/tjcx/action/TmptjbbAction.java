package com.woshidaniu.tjcx.action;

import javax.servlet.http.HttpSession;

import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.tjcx.service.impl.Constants;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计报表action，示例
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-23 上午08:38:45
 * @版本： V1.0
 * @修改记录:
 */
public class TmptjbbAction extends BaseAction {
	private static final long serialVersionUID = -4763899865313037453L;

	public String tjbb() {
		HttpSession session = getSession();
		session.setAttribute(Constants.TJCX_GNMK_BS, "xsxx");
		session.setAttribute(Constants.TJCX_CURRENT_MENU, "通用模块-统计分析-统计报表");
		return SUCCESS;
	}
}
