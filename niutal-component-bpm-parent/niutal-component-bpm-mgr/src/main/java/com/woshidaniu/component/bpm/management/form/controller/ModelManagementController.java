/**
 * 
 */
package com.woshidaniu.component.bpm.management.form.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.woshidaniu.component.bpm.BPMUtils;
import com.woshidaniu.component.bpm.common.BPMQueryModel;
import com.woshidaniu.component.bpm.management.BaseBPMController;
import com.woshidaniu.component.bpm.management.FormModelDataJsonConstants;
import com.woshidaniu.component.bpm.management.form.service.FormWebService;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.component.bpm.form.management.controller.ModelManagementController.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月23日上午9:12:34
 */
@Controller
@RequestMapping("/form/moduler")
public class ModelManagementController extends BaseBPMController implements FormModelDataJsonConstants {

	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected FormWebService formWebService;

	protected ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping("/list")
	public String listModuler() {
		try {
			return "/formManagement/moduler/list";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@ResponseBody
	@RequestMapping("/listData")
	public Object listModulerData(HttpServletRequest request, BPMQueryModel model) {
		try {
			ModelQuery createModelQuery = repositoryService.createModelQuery();
			if (BPMUtils.isNotBlank(request.getParameter("searchModelName"))) {
				createModelQuery.modelNameLike("%" + BPMUtils.trim(request.getParameter("searchModelName")) + "%");
			}

			if (BPMUtils.isNotBlank(request.getParameter("searchDeploymentState"))) {
				if (BPMUtils.equals(request.getParameter("searchDeploymentState"), "0")) {
					createModelQuery.notDeployed();
				} else {
					createModelQuery.deployed();
				}
			}
			createModelQuery.modelCategory(MODEL_CATEGORY);
			createModelQuery.orderByLastUpdateTime().desc();
			model.setTotalResult((int) createModelQuery.count());
			List<Model> models = createModelQuery.listPage(model.getCurrentResult(), model.getShowCount());
			model.setItems(models);
			return model;
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.SYSTEM_ERROR.getJson();
		}
	}

	@RequestMapping("/add")
	public String add(HttpServletRequest request) {
		try {
			return "/formManagement/moduler/add";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@ResponseBody
	@RequestMapping("/addData.zf")
	public Object addData(@RequestParam Map<String, String> params) {
		try {
			String name = params.get(MODEL_NAME);
			String description = params.get(MODEL_DESCRIPTION);
			formWebService.createModel(name, description);
			ObjectNode json = BPMMessageKey.FORM_MODEL_ADD_SUCCESS.getJson();
			return json;
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.FORM_MODEL_ADD_FAIL.getJson();
		}
	}

	@RequestMapping("/{modelId}/update-metainfo")
	public String updateMetaInfo(@PathVariable String modelId, HttpServletRequest request) {
		try {
			request.setAttribute(MODEL_ID, modelId);
			Model model = repositoryService.getModel(modelId);
			if (model != null) {
				request.setAttribute(MODEL_NAME, model.getName());
				JsonNode metaDataNode = model.getMetaInfo() == null ? null : objectMapper.readTree(model.getMetaInfo());
				if (metaDataNode != null) {
					request.setAttribute(MODEL_DESCRIPTION, metaDataNode.get(MODEL_DESCRIPTION).asText());
				}
			}
			request.setAttribute("model", model);
			return "/formManagement/moduler/update-metainfo";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@ResponseBody
	@RequestMapping("/update-metainfo-data")
	public Object updateMetaInfoData(@RequestParam Map<String, String> params) {
		try {
			String id = params.get(MODEL_ID);
			String name = params.get(MODEL_NAME);
			String description = params.get(MODEL_DESCRIPTION);
			formWebService.saveModel(id, name, description);
			ObjectNode json = BPMMessageKey.FORM_MODEL_UPDATE_SUCCESS.getJson();
			return json;
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.FORM_MODEL_UPDATE_FAIL.getJson();
		}
	}

	@ResponseBody
	@RequestMapping("/del")
	public Object del(@RequestParam Map<String, String> params) {
		String modelIds = params.get("modelIds");
		try {
			String[] modelIdList = modelIds.split(",");
			for (String modelId : modelIdList) {
				repositoryService.deleteModel(modelId);
			}
			return BPMMessageKey.FORM_MODEL_DEL_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.FORM_MODEL_DEL_FAIL.getJson();
		}
	}

	/**
	 * 
	 * <p>
	 * 方法说明：导出模型
	 * <p>
	 * <p>
	 * 作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
	 * <p>
	 * <p>
	 * 时间：2017年4月1日下午5:42:33
	 * <p>
	 */
	@RequestMapping("/{modelId}/export")
	public ResponseEntity<byte[]> export(@PathVariable String modelId) {
		try {
			String filename = null;
			Model modelData = repositoryService.getModel(modelId);
			byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());
			filename = modelData.getId() + ".form";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", filename);
			return new ResponseEntity<byte[]>(modelEditorSource, headers, HttpStatus.OK);
		} catch (Exception e) {
			logException(e);
		}
		return null;

	}

	@RequestMapping("/{modelId}/copy")
	public String copy(@PathVariable String modelId, HttpServletRequest request) {
		try {
			Model modelData = repositoryService.getModel(modelId);
			// request.setAttribute("model", modelData);
			request.setAttribute(MODEL_NAME, modelData.getName());
			request.setAttribute(MODEL_ID, modelData.getId());
			JsonNode modelMetainfo = objectMapper.readTree(modelData.getMetaInfo());
			request.setAttribute(MODEL_DESCRIPTION, modelMetainfo.get(MODEL_DESCRIPTION).asText());
			return "/formManagement/moduler/copy";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@ResponseBody
	@RequestMapping("/{modelId}/copyData")
	public Object copyData(@PathVariable String modelId, @RequestParam Map<String, String> params) {
		try {
			String modelName = params.get(MODEL_NAME);
			String modelDesc = params.get(MODEL_DESCRIPTION);
			ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
			modelObjectNode.put(MODEL_NAME, modelName);
			modelObjectNode.put(MODEL_DESCRIPTION, modelDesc);
			modelObjectNode.put(MODEL_REVISION, 1);
			formWebService.creataModel(modelName, modelDesc, repositoryService.getModelEditorSource(modelId));
			return BPMMessageKey.FORM_MODEL_COPY_SUCCESS.getJson();
		} catch (Exception ex) {
			logException(ex);
			return BPMMessageKey.FORM_MODEL_COPY_FAIL.getJson();
		}
	}

	@ResponseBody
	@RequestMapping("/{modelId}/deployData")
	public Object deployData(@PathVariable String modelId) {
		try {
			formWebService.deployModel(modelId);
			return BPMMessageKey.FORM_MODEL_DEPLOY_SUCCESS.getJson();
		} catch (Exception ex) {
			logException(ex);
			return BPMMessageKey.FORM_MODEL_DEPLOY_FAIL.getJson();
		}
	}
}
