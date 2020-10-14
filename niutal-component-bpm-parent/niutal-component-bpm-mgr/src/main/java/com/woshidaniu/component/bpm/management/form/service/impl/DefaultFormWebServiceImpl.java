/**
 * 
 */
package com.woshidaniu.component.bpm.management.form.service.impl;

import java.io.UnsupportedEncodingException;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.extend.service.ExtendFormService;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.woshidaniu.component.bpm.BPMFormException;
import com.woshidaniu.component.bpm.BPMUtils;
import com.woshidaniu.component.bpm.management.FormModelDataJsonConstants;
import com.woshidaniu.component.bpm.management.form.service.FormWebService;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.component.bpm.form.management.service.impl.DefaultFormWebServiceImpl.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年5月22日下午3:17:32
 */
@Service
public class DefaultFormWebServiceImpl implements FormWebService, FormModelDataJsonConstants {

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ExtendFormService extendFormService;
	@Autowired
	private IdGenerator idGenerator;

	private static final String DEPLOY_CATEGORY = "form";

	private ObjectMapper objectMapper = new ObjectMapper();

	protected String generateFormKey() {
		return "fid-" + idGenerator.getNextId();
	}

	@Override
	public void createModel(String modelName, String modelDesc) {
		try {
			Model newModel = repositoryService.newModel();
			newModel.setCategory(MODEL_CATEGORY);
			newModel.setName(modelName);
			ObjectNode metaInfotNode = objectMapper.createObjectNode();
			metaInfotNode.put(MODEL_NAME, modelName);
			metaInfotNode.put(MODEL_DESCRIPTION, modelDesc);
			metaInfotNode.put(MODEL_REVISION, 1);
			newModel.setMetaInfo(metaInfotNode.toString());
			repositoryService.saveModel(newModel);
			metaInfotNode.put(KEY, generateFormKey());
			repositoryService.addModelEditorSource(newModel.getId(), metaInfotNode.toString().getBytes("utf-8"));
		} catch (Exception e) {
			throw new BPMFormException(e);
		}
	}

	@Override
	public void addModelData(String modelId, byte[] modelData) {
		repositoryService.addModelEditorSource(modelId, modelData);
	}

	@Override
	public void addModelData(String modelId, String modelData) {
		try {
			repositoryService.addModelEditorSource(modelId, modelData.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new BPMFormException(e);
		}
	}

	@Override
	public void creataModel(String modelName, String modelDesc, byte[] modelData) {
		Model newModel = repositoryService.newModel();
		newModel.setCategory(MODEL_CATEGORY);
		newModel.setName(modelName);
		ObjectNode metaInfotNode = objectMapper.createObjectNode();
		metaInfotNode.put(MODEL_NAME, modelName);
		metaInfotNode.put(MODEL_DESCRIPTION, modelDesc);
		metaInfotNode.put(MODEL_REVISION, 1);
		metaInfotNode.put(KEY, generateFormKey());
		newModel.setMetaInfo(metaInfotNode.toString());
		repositoryService.saveModel(newModel);
		addModelData(newModel.getId(), modelData);
	}

	@Override
	public void creataModel(String modelName, String modelDesc, String modelData) {
		try {
			creataModel(modelName, modelDesc, modelData.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new BPMFormException(e);
		}
	}
	
	@Override
	public void saveModel(String modelId, String modelName, String modelDesc, String modelData) {
		try {
			Model model = repositoryService.getModel(modelId);
			ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
			modelJson.put(MODEL_NAME, modelName);
			modelJson.put(MODEL_DESCRIPTION, modelDesc);
			model.setMetaInfo(modelJson.toString());
			model.setCategory(MODEL_CATEGORY);
			model.setName(modelName);
			repositoryService.saveModel(model);

			if (BPMUtils.isNotBlank(modelData)) {
				repositoryService.addModelEditorSource(model.getId(), modelData.getBytes("utf-8"));
			}
		} catch (Exception e) {
			throw new BPMFormException(e);
		}
	}

	@Override
	public void saveModel(String modelId, String modelName, String modelDesc) {
		try {
			saveModel(modelId, modelName, modelDesc, null);
		} catch (Exception e) {
			throw new BPMFormException(e);
		}
	}

	@Override
	public void saveModel(String modelId, String modelData) {
		try {
			if (BPMUtils.isNotBlank(modelData)) {
				repositoryService.addModelEditorSource(modelId, modelData.getBytes("utf-8"));
			}
		} catch (Exception e) {
			throw new BPMFormException(e);
		}
	}

	@Override
	public void deleteModel(String modelId) {
		repositoryService.deleteModel(modelId);
	}

	@Override
	public Model getModel(String modelId) {
		return repositoryService.getModel(modelId);
	}

	@Override
	public void deployModel(String modelId) {
		try {
			// 查询模型数据
			Model model = repositoryService.getModel(modelId);
			byte[] modelEditorSource = repositoryService.getModelEditorSource(modelId);
			Deployment deploy = extendFormService.createFormDeployment().addBytes(model.getName(), modelEditorSource)
					.name(model.getName()).category(DEPLOY_CATEGORY).deploy();
			model.setDeploymentId(deploy.getId());
			repositoryService.saveModel(model);
		} catch (Exception e) {
			throw new BPMFormException(e);
		}

	}

}
