/**
 * 
 */
package com.woshidaniu.component.bpm.management.form.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
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
 * @className:com.woshidaniu.component.bpm.management.form.controller.ModelEditorResource.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月18日上午9:36:33
 */
@Controller
@RequestMapping("/form-service")
public class ModelEditorResource implements FormModelDataJsonConstants {

	private static final Logger logger = LoggerFactory.getLogger(ModelEditorResource.class);

	@Autowired
	private FormWebService formWebService;

	@Autowired
	private RepositoryService repositoryService;

	@ResponseBody
	@RequestMapping(value = "/model/{modelId}/json", method = RequestMethod.GET)
	public Object getModelEditorJson(@PathVariable String modelId) {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode modelNode = null;
		Model model = repositoryService.getModel(modelId);
		if (model != null) {
			try {
				if (StringUtils.isNotEmpty(model.getMetaInfo())) {
					modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
				} else {
					modelNode = objectMapper.createObjectNode();
					modelNode.put(MODEL_NAME, model.getName());
				}
				modelNode.put(MODEL_ID, model.getId());
				ObjectNode editorJsonNode = (ObjectNode) objectMapper
						.readTree(new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
				modelNode.set(JSON_MODEL_DATA, editorJsonNode);

			} catch (Exception e) {
				logger.error("Error getting model JSON", e);
				throw new BPMFormException("Error getting model JSON", e);
			}
		}
		return modelNode;

	}

}
