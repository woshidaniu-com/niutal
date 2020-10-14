/**
 * 
 */
package com.woshidaniu.component.bpm.management.form.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;

import com.woshidaniu.component.bpm.BPMFormException;
import com.woshidaniu.component.bpm.management.FormModelDataJsonConstants;
import com.woshidaniu.component.bpm.management.form.service.FormWebService;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.component.bpm.management.form.controller.ModelSaveReource.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月18日上午9:38:27
 */
@Controller
@RequestMapping("/form-service")
public class ModelEditorSaveReource implements FormModelDataJsonConstants {
	private static final Logger logger = LoggerFactory.getLogger(ModelEditorSaveReource.class);
	@Autowired
	private FormWebService formWebService;

	@RequestMapping(value = "/model/create", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void createModel(@RequestBody MultiValueMap<String, String> values) {
		try {
			String modelName = values.getFirst(JSON_MODEL_NAME);
			String modelDesc = values.getFirst(JSON_MODEL_DESC);
			formWebService.createModel(modelName, modelDesc);
		} catch (Exception e) {
			logger.error("Error creating model", e);
			throw new BPMFormException("Error creating model", e);
		}
	}

	@RequestMapping(value = "/model/{modelId}/save", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void saveModel(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values) {
		try {
			String modelData = values.getFirst(JSON_MODEL_DATA);
			formWebService.saveModel(modelId, modelData);
		} catch (Exception e) {
			logger.error("Error saving model", e);
			throw new BPMFormException("Error saving model", e);
		}
	}

}
