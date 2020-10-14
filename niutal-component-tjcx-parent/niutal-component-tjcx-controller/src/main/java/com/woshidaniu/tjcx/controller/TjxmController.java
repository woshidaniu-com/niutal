package com.woshidaniu.tjcx.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.tjcx.dao.entites.TjxmModel;
import com.woshidaniu.tjcx.service.svcinterface.ITjxmService;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 统计项目action
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-23 上午08:38:45
 * @版本： V1.0
 * @修改记录:
 */
@Controller
@RequestMapping("/niutal/tjcx/tjxm")
public class TjxmController extends BaseController {
	private static String TJCX_GNMK_BS	= "TJCX_GNMK";
	
	@Autowired
	private ITjxmService service;

	/**
	 * 统计项目
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cxxx.zf")
	public Object cxxx(HttpServletRequest request, TjxmModel model) {
		try {
			String gnmk = model.getGnmk();
			if(gnmk == null){
				gnmk = (String)request.getSession().getAttribute(TJCX_GNMK_BS);
			}
			model.setGnmk(gnmk);
			List<TjxmModel> list = service.getModelList(model);
			return list;
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}

	public ITjxmService getService() {
		return service;
	}

	public void setService(ITjxmService service) {
		this.service = service;
	}
}
