package com.woshidaniu.globalweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.dao.entities.ValidationModel;
import com.woshidaniu.service.common.ICommonValidationService;

/**
 * 
 *@类名称:CommonValidationAction.java
 *@类描述：公共的验证action
 *@创建人：kangzhidong
 *@创建时间：2014-6-17 下午08:21:01
 *@版本号:v1.0
 */
@Controller
@RequestMapping("/xtgl/validate")
public class CommonValidationController extends BaseController  {
	@Autowired
	private ICommonValidationService service;
	
	/**
	 * 
	 *@描述：查询唯一性验证
	 *@创建人:kangzhidong
	 *@创建时间:2014-6-17下午08:22:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	@RequestMapping("/cxUnique")
	@ResponseBody
	public Object cxUnique(ValidationModel model){
		try {
			boolean unique = service.unique(model);
			return unique?"1":"0";
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

}

