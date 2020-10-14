package com.woshidaniu.globalweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/setting/")
public class SettingController {

	@RequestMapping("locale/change")
	@ResponseBody
	public String localeChange(HttpServletRequest request, HttpServletResponse response, String language) {
		return "success";
	}

	@RequestMapping("theme/change")
	@ResponseBody
	public String themeChange(HttpServletRequest request, HttpServletResponse response, String theme) {
		return "success";
	}

}
