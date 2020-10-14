package com.woshidaniu.ms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woshidaniu.common.controller.BaseController;

@Controller
@RequestMapping(value = "/ms/retake/")
public class MessageServerController extends BaseController{
	
	
	
	@RequestMapping(value="index")
	public String index(HttpServletRequest request){
		
		 
		return "/pwdmgr/find_password";
	}
	
	
}
