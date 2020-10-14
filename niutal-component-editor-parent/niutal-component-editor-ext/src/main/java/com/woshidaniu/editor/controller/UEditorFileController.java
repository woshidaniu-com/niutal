package com.woshidaniu.editor.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baidu.ueditor.ActionEnter;
import com.woshidaniu.common.controller.BaseController;

@Controller
@RequestMapping(value = "/filemgr/ueditor/")
public class UEditorFileController extends BaseController {
	
	@RequestMapping(value = "action", method = RequestMethod.GET )
	public void action(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		response.setHeader("Content-Type" , "text/html");
		
		String rootPath = request.getServletContext().getRealPath( "/" );
		
		response.getWriter().write( new ActionEnter( request, rootPath ).exec() );
		
	}
	
}
