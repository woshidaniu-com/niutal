/**
 * 
 */
package com.woshidaniu.component.bpm.management.form.controller;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.extend.form.FormDefinition;
import org.activiti.engine.extend.service.ExtendFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.component.bpm.management.form.controller.FormDefinitionPreviewResource.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月7日下午2:42:06
 */
@Controller
@RequestMapping("/form-service")
public class FormDefinitionPreviewResource {
	@Autowired
	protected ExtendFormService extendFormService;

	@RequestMapping("/form-definition/preview")
	public String preview(HttpServletRequest request) {
		String formDefinitionId = request.getParameter("formDefinitionId");
		FormDefinition formDefinition = extendFormService.getFormDefinition(formDefinitionId);
		request.setAttribute("formDefinition", formDefinition);
		return "/formManagement/definition/preview";
	}

}
