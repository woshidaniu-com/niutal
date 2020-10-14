package com.woshidaniu.tjcx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woshidaniu.common.controller.BaseController;
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
@Controller
@RequestMapping("/tjcx/tmptjbb")
public class TmptjbbController extends BaseController {

	@RequestMapping("/tjbb.zf")
	public String tjbb(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(Constants.TJCX_GNMK_BS, "xsxx");
		session.setAttribute(Constants.TJCX_CURRENT_MENU, "通用模块-统计分析-统计报表");
		return "forward:/niutal/tjcx/tjbb/tjbb.zf";
	}
}
