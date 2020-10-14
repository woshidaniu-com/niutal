package com.woshidaniu.editor.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.woshidaniu.common.controller.BaseController;

@Controller
@RequestMapping(value = "/js/editor/plugins/")
public class KineditorPluginsController extends BaseController {
	
	@RequestMapping(value = "map", method = RequestMethod.GET)
    public String map(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
        return "forward:/js/editor/plugins/baidumap/map.html";
    }
	
	@RequestMapping(value = "template/html/{type}", method = RequestMethod.POST )
	public String html(HttpServletRequest request,@PathVariable("type") String type) throws Exception {
		return "forward:/js/editor/plugins/template/html/" + type + ".html";
	}
	
}