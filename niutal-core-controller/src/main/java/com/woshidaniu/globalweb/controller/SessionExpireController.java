package com.woshidaniu.globalweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "sec")
public class SessionExpireController {
	
	private static final Logger log = LoggerFactory.getLogger(SessionExpireController.class);

	@RequestMapping(value = "index",method=RequestMethod.GET)
	public String index(){
		return "/globalweb/comp/xtgl/sec/secIndex";
	}
}
