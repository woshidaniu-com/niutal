/**
 * 
 */
package com.woshidaniu.component.bpm.management.form.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.extend.form.FormDefinition;
import org.activiti.engine.extend.form.FormDefinitionQuery;
import org.activiti.engine.extend.service.ExtendFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.component.bpm.BPMUtils;
import com.woshidaniu.component.bpm.common.BPMQueryModel;
import com.woshidaniu.component.bpm.management.BaseBPMController;
import com.woshidaniu.component.bpm.management.process.definition.dao.entities.ProcessDefinitionModel;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：已部署表单管理Controller
 * <p>
 * 
 * @className:com.woshidaniu.component.bpm.management.form.controller.DeployedFormManagementController.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月5日下午3:37:45
 */
@Controller
@RequestMapping("/form/definition")
public class DeployedFormManagementController extends BaseBPMController {

	@Autowired
	protected ExtendFormService extendFormService;

	@RequestMapping("/list")
	public String list(HttpServletRequest request, ProcessDefinitionModel model) {
		try {
			return "/formManagement/definition/list";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@ResponseBody
	@RequestMapping("/listData")
	public Object listData(HttpServletRequest request, BPMQueryModel model) {
		try {
			String name = BPMUtils.trim(request.getParameter("name"));
			String state = BPMUtils.trim(request.getParameter("state"));

			FormDefinitionQuery createFormDefinitionQuery = extendFormService.createFormDefinitionQuery();
			if (BPMUtils.isNotBlank(request.getParameter("name"))) {
				createFormDefinitionQuery.formDefinitionNameLike("%" + name + "%");
			}
			if (BPMUtils.isNoneBlank(state)) {
				if (BPMUtils.equals("1", state)) {
					createFormDefinitionQuery.active();
				}
				if (BPMUtils.equals("2", state)) {
					createFormDefinitionQuery.suspended();
				}
			}
			createFormDefinitionQuery.latestVersion().orderByFormDefinitionName().asc();
			model.setTotalResult((int) createFormDefinitionQuery.count());
			List<FormDefinition> models = createFormDefinitionQuery.listPage(model.getCurrentResult(),
					model.getShowCount());
			model.setItems(models);
			return model;
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.SYSTEM_ERROR.getJson();
		}
	}

	@ResponseBody
	@RequestMapping("/active")
	public Object active(HttpServletRequest request) {
		try {
			String formDefinitionId = request.getParameter("formDefinitionId");
			extendFormService.activateFormDefinitionById(formDefinitionId);
			return BPMMessageKey.FORM_DEFINITION_ACTIVE_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.FORM_DEFINITION_ACTIVE_FAIL.getJson();
		}
	}

	@ResponseBody
	@RequestMapping("/suspend")
	public Object suspend(HttpServletRequest request) {
		try {
			String formDefinitionId = request.getParameter("formDefinitionId");
			extendFormService.suspendFormDefinitionById(formDefinitionId);
			return BPMMessageKey.FORM_DEFINITION_SUSPEND_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.FORM_DEFINITION_SUSPEND_FAIL.getJson();
		}
	}

	@ResponseBody
	@RequestMapping("/del")
	public Object del(HttpServletRequest request) {
		try {
			String formDefinitionId = request.getParameter("formDefinitionId");
			FormDefinition formDefinition = extendFormService.createFormDefinitionQuery()
					.formDefinitionId(formDefinitionId).singleResult();
			if (formDefinition != null) {
				extendFormService.deleteDeployment(formDefinition.getDeploymentId());
				return BPMMessageKey.FORM_DEFINITION_DEL_SUCCESS.getJson();
			}
			return BPMMessageKey.FORM_DEFINITION_NOT_FOUND.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.FORM_DEFINITION_NOT_FOUND.getJson();
		}
	}

}
