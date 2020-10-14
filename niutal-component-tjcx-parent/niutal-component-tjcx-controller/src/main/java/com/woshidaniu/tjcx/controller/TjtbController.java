package com.woshidaniu.tjcx.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woshidaniu.common.controller.BaseController;
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
@Controller
@RequestMapping("/niutal/tjcx/tjtb")
public class TjtbController extends BaseController {

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
	@RequestMapping("/tjtb.zf")
	public String tjtb(HttpServletRequest request, TjtbModel model) {
		request.setAttribute("model", model);
		return "/tjcx/tjtb";
	}
	
}
