/**
 * 
 */
package com.woshidaniu.component.bpm.management.form.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woshidaniu.component.bpm.BPMFormException;
import com.woshidaniu.component.bpm.management.FormModelDataJsonConstants;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.component.bpm.management.form.controller.ModelImportResource.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月18日上午9:40:29
 */
@Controller
@RequestMapping("/form-service")
public class ModelEditorImportResource implements FormModelDataJsonConstants {
	private static final Logger logger = LoggerFactory.getLogger(ModelEditorImportResource.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@ResponseBody
	@RequestMapping("/model/import.zf")
	public Object upload(@RequestParam(value = "file", required = true) MultipartFile file) {
		try {
			JsonNode modelData = objectMapper.readTree(new String(file.getBytes(), "UTF-8"));
			return modelData;
		} catch (Exception e) {
			logger.error("Error import model", e);
			throw new BPMFormException("Error import model", e);
		}
	}

}
