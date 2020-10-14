package com.woshidaniu.globalweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.dao.entities.SjbzModel;
import com.woshidaniu.service.svcinterface.IXzqhService;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：行政区划
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年2月4日上午10:26:57
 */
@Controller
@RequestMapping(value = "/xtgl/xzqh")
public class XzqhController extends BaseController {

	@Autowired
	private IXzqhService xzqhService;
	
	
	@ResponseBody
	@RequestMapping(value = "/getAreaSelectionData")
	public Object getAreaSelectionData(){
		try {
			List<SjbzModel> shengList = xzqhService.getShengList();
			Map<String,List<SjbzModel>> childrens = xzqhService.getChildrens();
			Map<String,Object> model = new HashMap<String, Object>();
			model.put("parent", shengList);
			model.put("childrens", childrens);
			return model;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}
}
