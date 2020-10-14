package com.woshidaniu.tjcx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.tjcx.dao.entites.CxzdModel;
import com.woshidaniu.tjcx.dao.entites.YsfModel;
import com.woshidaniu.tjcx.service.svcinterface.ICxzdService;

/**
 * 
 * @系统名称: 统计查询子系统
 * @模块名称: 查询字段action
 * @类功能描述:
 * @作者： ligl
 * @时间： 2013-7-23 上午08:38:45
 * @版本： V1.0
 * @修改记录:
 */
@Controller
@RequestMapping("/niutal/tjcx/cxzd")
public class CxzdController extends BaseController {
	
	@Autowired
	private ICxzdService service;
	//private CxzdModel model = new CxzdModel();

	@ResponseBody
	@RequestMapping("/cxzd.zf")
	public Object cxzd(CxzdModel model) {
		try {
			CxzdModel model2 = service.getModel(model);
		    return model2;
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}

	/**
	 * 
	 * @描述:取所有运算符
	 * @作者：ligl
	 * @日期：2013-8-20 下午04:27:40
	 * @修改记录: 
	 * @return
	 * String 返回类型 
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/getAllYsfList.zf")
	public Object getAllYsfList() {
		try {
			List<YsfModel> allYsfList = service.getAllYsfList();
		    return allYsfList;
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}
	
	/**
	 * 
	 * @描述:获取所有子类
	 * @作者：ligl
	 * @日期：2013-9-24 下午02:49:07
	 * @修改记录: 
	 * @return
	 * String 返回类型 
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/getChildrenList.zf")
	public Object getChildrenList(CxzdModel model) {
		try {
			List<CxzdModel> list = service.getChildrenList(model);
		    return list;
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}


	public ICxzdService getService() {
		return service;
	}

	public void setService(ICxzdService service) {
		this.service = service;
	}

}
