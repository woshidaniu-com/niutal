/**
 * 
 */
package com.woshidaniu.component.bpm.management.modeler.controller;

import org.activiti.engine.extend.service.ExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.component.bpm.management.process.definition.service.svcinterface.IProcessDefinitionService;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.component.bpm.management.modeler.controller.ProcessCategoryResource.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月1日下午2:03:31
 * 
 */
@Controller
@RequestMapping("/activiti-rest-service")
public class ProcessCategoryResource {
	@Autowired
	protected IProcessDefinitionService processDefinitionService;

	@Autowired
	protected ExtendService extendService;

	@Deprecated
	@ResponseBody
	@RequestMapping(value = "/processCategory/list.zf", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public Object getProcessCategories() {
		return processDefinitionService.findProcessDefinitionCategoryList();
	}

	@ResponseBody
	@RequestMapping(value = "/processCategory/getAll.zf", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public Object getAll() {
		return extendService.getProcessCategoryList();
	}
}
