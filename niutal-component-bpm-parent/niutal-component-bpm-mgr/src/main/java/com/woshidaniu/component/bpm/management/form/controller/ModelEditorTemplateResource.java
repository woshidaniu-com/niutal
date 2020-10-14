/**
 * 
 */
package com.woshidaniu.component.bpm.management.form.controller;

import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.woshidaniu.component.bpm.BPMFormException;
import com.woshidaniu.component.bpm.management.FormModelDataJsonConstants;
import com.woshidaniu.component.bpm.management.form.service.FormWebService;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.component.bpm.management.form.controller.ModelTemplateResource.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月18日上午9:41:09
 */
@Controller
@RequestMapping("/form-service")
public class ModelEditorTemplateResource implements FormModelDataJsonConstants {
	private static final Logger logger = LoggerFactory.getLogger(ModelEditorSaveReource.class);

	@Autowired
	private FormWebService formWebService;

	@Autowired
	private RepositoryService repositoryService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/template/create", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void createModelTemplate(@RequestBody MultiValueMap<String, String> values) {
		try {
			String modelName = values.getFirst(JSON_MODEL_NAME);
			String modelDesc = values.getFirst(JSON_MODEL_DESC);
			String modelData = values.getFirst(JSON_MODEL_DATA);
			formWebService.creataModel(modelName, modelDesc, modelData);
		} catch (Exception e) {
			logger.error("Error saving model", e);
			throw new BPMFormException("Error saving model", e);
		}
	}

	@RequestMapping(value = "/template/{templateModelId}/del", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteModelTemplate(@PathVariable String templateModelId) {
		formWebService.deleteModel(templateModelId);
	}

	@RequestMapping(value = "/template/list", method = RequestMethod.GET)
	@ResponseBody
	public Object getModelTemplates() {
		try {
			ModelQuery createModelQuery = repositoryService.createModelQuery();
			List<Model> list = createModelQuery.modelCategory(MODEL_CATEGORY_TEMPLATE).orderByModelName().asc().list();
			ArrayNode templateArrayNode = objectMapper.createArrayNode();
			if (list != null && list.size() > 0) {
				for (Model model : list) {
					ObjectNode templateNode = objectMapper.createObjectNode();
					String modelId = model.getId();
					String modelName = model.getName();
					String metaInfo = model.getMetaInfo();
					JsonNode readTree = objectMapper.readTree(metaInfo);
					JsonNode modelDefault = readTree.get(MODEL_DEFAULT);
					templateNode.put(JSON_MODEL_ID, modelId);
					templateNode.put(JSON_MODEL_NAME, modelName);
					templateNode.put(JSON_MODEL_DESC, readTree.get(MODEL_DESCRIPTION).asText());
					templateNode.put(JSON_MODEL_DEFAULT, modelDefault == null ? false : modelDefault.asBoolean());
					templateArrayNode.add(templateNode);
				}
			}
			return templateArrayNode;
		} catch (Exception e) {
			logger.error("Error getting model templates", e);
			throw new BPMFormException("Error getting model templates", e);
		}
	}

	@RequestMapping(value = "/template/{templateId}/json", method = RequestMethod.GET)
	@ResponseBody
	public Object getModelTemplateData(@PathVariable String templateId) {
		try {
			JsonNode readTree = objectMapper.readTree(new String(repositoryService.getModelEditorSource(templateId), "utf-8"));
			return readTree;
		} catch (Exception e) {
			logger.error("Error getting model", e);
			throw new BPMFormException("Error getting model", e);
		}
	}
}
