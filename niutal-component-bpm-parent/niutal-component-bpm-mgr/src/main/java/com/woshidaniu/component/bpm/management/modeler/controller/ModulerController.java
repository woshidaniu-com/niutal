package com.woshidaniu.component.bpm.management.modeler.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.ExtBpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.extend.biz.Biz;
import org.activiti.engine.extend.service.ExtendService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.woshidaniu.component.bpm.BPMUtils;
import com.woshidaniu.component.bpm.common.BPMQueryModel;
import com.woshidaniu.component.bpm.management.BaseBPMController;
import com.woshidaniu.component.bpm.management.ProcessModelDataJsonConstants;
import com.woshidaniu.component.bpm.management.modeler.service.ModelService;
import com.woshidaniu.component.bpm.management.simpelWorkflow.SimpleWorkflow;
import com.woshidaniu.component.bpm.management.simpelWorkflow.SimpleWorkflowJsonConverter;
import com.woshidaniu.component.bpm.management.util.XmlUtil;
import com.woshidaniu.component.bpm.service.BPMService;

import net.sf.json.JSONObject;

/**
 * <p>
 * <h3>niutal框架
 * <h3><br>
 * 说明：TODO <br>
 * class：com.woshidaniu.component.bpm.management.modeler.ModulerController.java
 * <p>
 * 
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
@Controller
@RequestMapping("/processManagement/moduler")
public class ModulerController extends BaseBPMController implements ProcessModelDataJsonConstants {

	static Logger log = LoggerFactory.getLogger(ModulerController.class);

	@Autowired
	protected RepositoryService repositoryService;

	@Autowired
	protected ModelService modelService;

	@Autowired
	protected BPMService bpmService;
	
	@Autowired
	protected ExtendService extendService;
	
	@RequestMapping("/list.zf")
	public String listModuler() {
		try {
			return "/processManagement/moduler/list";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@RequestMapping("/addModuler.zf")
	public String addModeluer(HttpServletRequest request) {
		try {
			List<Biz> bizList = extendService.getBizList();
			request.setAttribute("bizList", bizList);
			return "/processManagement/moduler/add-moduler";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@ResponseBody
	@RequestMapping("/{modelId}/deployModulerData.zf")
	public Object deployModulerData(@PathVariable String modelId) {
		try {
			modelService.deployModuler(modelId);
			return BPMMessageKey.PROCESS_MODEL_DEPLOY_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.PROCESS_MODEL_DEPLOY_FAIL.getJson();
		}
	}

	@ResponseBody
	@RequestMapping("/getSimpleModelData.zf")
	public Object getSimpleModelJSONData(HttpServletRequest request) {
		String modelId = request.getParameter("modelId");
		try {
			byte[] modelEditorSource = repositoryService.getModelEditorSource(modelId);
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode readTree = (ObjectNode) mapper.readTree(modelEditorSource);
			ObjectNode message = mapper.createObjectNode();

			message.put("status", BPMMessageKey.DO_SUCCESS.getStatus());
			message.put("model", readTree);
			return message;
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.PROCESS_MODEL_DATA_QUERY_FAIL.getJson();
		}
	}

	@ResponseBody
	@RequestMapping("/{modelId}/saveSimpleModulerData.zf")
	public Object saveSimpleModulerData(@PathVariable String modelId, SimpleWorkflow workflow) {
		if (log.isDebugEnabled()) {
			log.debug("modelId: {}", modelId);
			log.debug("workflow : {} ", workflow);
		}
		try {
			SimpleWorkflowJsonConverter converter = new SimpleWorkflowJsonConverter(workflow);
			converter.convert();
			ObjectNode objectNode = converter.getObjectNode();
			if (log.isDebugEnabled()) {
				log.debug("objectNode: {}", objectNode.toString());
			}
			repositoryService.addModelEditorSource(modelId, objectNode.toString().getBytes("utf-8"));
			
			deployProcessModelIfPossiable(modelId);
			
			return BPMMessageKey.PROCESS_MODEL_SAVE_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.PROCESS_MODEL_SAVE_FAIL.getJson();
		}
	}

	protected void deployProcessModelIfPossiable(String modelId) throws Exception {
		if(BPMUtils.isNotBlank(modelId)){
			Model model = repositoryService.getModel(modelId);
			if(BPMUtils.isNotBlank(model.getDeploymentId())){
				modelService.deployModuler(modelId);
			}
		}
	}

	@RequestMapping("/upload.zf")
	public String uploadModel(HttpServletRequest request) {
		try {
			return "/processManagement/moduler/upload-moduler";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@ResponseBody
	@RequestMapping("/uploadData.zf")
	public Object uploadModelData(@RequestParam(value = "file", required = true) MultipartFile file,
			HttpServletRequest request) {
		try {
			JSONObject json = new JSONObject();
			String fileName = file.getOriginalFilename();
			if (fileName.endsWith(".bpmn20.xml") || fileName.endsWith(".bpmn")) {
				XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
				InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(file.getBytes()),"UTF-8");
				XMLStreamReader xtr = xif.createXMLStreamReader(in);
				BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

				if (bpmnModel.getMainProcess() == null || bpmnModel.getMainProcess().getId() == null) {
					json.put("status", BPMMessageKey.SAVE_FAIL);
					json.put("message", "fail");
				} else {
					if (bpmnModel.getLocationMap().isEmpty()) {
						json.put("status", BPMMessageKey.SAVE_FAIL);
						json.put("message", "fail");
					} else {
						String processName = null;
						String processDesc = null;
						if (BPMUtils.isNotEmpty(bpmnModel.getMainProcess().getName())) {
							processName = bpmnModel.getMainProcess().getName();
						} else {
							processName = bpmnModel.getMainProcess().getId();
						}
						processDesc =  bpmnModel.getMainProcess().getDocumentation();
						Model modelData = repositoryService.newModel();
						ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
						modelObjectNode.put(MODEL_NAME, processName);
						modelObjectNode.put(MODEL_REVISION, 1);
						modelObjectNode.put(MODEL_DESCRIPTION, processDesc);
						modelData.setMetaInfo(modelObjectNode.toString());
						modelData.setName(processName);
						modelData.setCategory("advanced");
						repositoryService.saveModel(modelData);
						ExtBpmnJsonConverter jsonConverter = new ExtBpmnJsonConverter();
						repositoryService.addModelEditorSource(modelData.getId(),
								jsonConverter.convertToJson(bpmnModel).toString().getBytes("utf-8"));
						json.put("status", "success");
						json.put("message", "上传成功");
					}
				}
			} else {
				json.put("status", "fail");
				json.put("message", "上传文件无法解析，文件格式不正确");
			}
			return json;
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.SAVE_FAIL.getJson();
		} finally {
			
			
			
		}
	}

	/**
	 * 
	 * <p>方法说明：导出流程模型<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">zhidong.kang[1036]<a><p>
	 * <p>时间：2017年4月1日下午5:42:33<p>
	 */
	 @RequestMapping("/{modelId}/export.zf")
	 public ResponseEntity<byte[]> exportModel(@PathVariable String modelId) {
		 try {
			 
			 byte[] bpmnBytes = null;
	         String filename = null;
	         
	         Model modelData = repositoryService.getModel(modelId);
	         
	         if ("simple".equals(modelData.getCategory())) {
	        	 
	         } else {
	           JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
	           ExtBpmnJsonConverter jsonConverter = new ExtBpmnJsonConverter();
	           BpmnModel model = jsonConverter.convertToBpmnModel(editorNode);
	           filename = model.getMainProcess().getId() + ".bpmn20.xml";
	           bpmnBytes = new BpmnXMLConverter().convertToXML(model);
	         }
 			// Http响应头
 			HttpHeaders headers = new HttpHeaders();
 			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
 			headers.setContentDispositionFormData("attachment", filename);
 			return new ResponseEntity<byte[]>(bpmnBytes, headers, HttpStatus.OK);
 		} catch (Exception e) {
 			logException(e);
 		}
 		return null;
         
       	    
	 }
	
	@RequestMapping("/{modelId}/copyModuler.zf")
	public String copyModuler(@PathVariable String modelId, HttpServletRequest request) {
		try {
			Model modelData = repositoryService.getModel(modelId);
			request.setAttribute("model", modelData);
			return "/processManagement/moduler/copy-moduler";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@ResponseBody
	@RequestMapping("/{modelId}/copyModulerData.zf")
	public Object copyModulerData(@PathVariable String modelId, HttpServletRequest request) {
		try {
			Model modelData = repositoryService.getModel(modelId);
			String modelName = request.getParameter("modelName");
			String modelDesc = request.getParameter("modelDesc");

			Model newModelData = repositoryService.newModel();
			ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();

			if (BPMUtils.isNotEmpty(modelName)) {
				modelName = modelDesc.replaceAll("\\s+", " ");
			} else {
				modelName = modelData.getName() + "(拷贝)";
			}

			if (BPMUtils.isNotEmpty(modelDesc)) {
				modelDesc = modelDesc.replaceAll("\\s+", " ");
			} else {
				modelDesc = "拷贝自:" + modelData.getName();
			}
			modelObjectNode.put(MODEL_NAME, modelName);
			modelObjectNode.put(MODEL_DESCRIPTION, modelDesc);
			modelObjectNode.put(MODEL_REVISION, 1);

			newModelData.setMetaInfo(modelObjectNode.toString());
			newModelData.setName(modelName);
			newModelData.setCategory(modelData.getCategory());
			newModelData.setKey(BPMUtils.getUniqIDHash());
			repositoryService.saveModel(newModelData);
			repositoryService.addModelEditorSource(newModelData.getId(),
					repositoryService.getModelEditorSource(modelData.getId()));
			repositoryService.addModelEditorSourceExtra(newModelData.getId(),
					repositoryService.getModelEditorSourceExtra(modelData.getId()));

			return BPMMessageKey.PROCESS_MODEL_COPY_SUCCESS.getJson();
		} catch (Exception ex) {
			logException(ex);
			return BPMMessageKey.PROCESS_MODEL_COPY_FAIL.getJson();
		}
	}

	@RequestMapping("/editModuler.zf")
	public String editModuler(HttpServletRequest request) {
		try {
			String modelId = request.getParameter("modelId");
			String editor = request.getParameter("editor");
			request.setAttribute("modelId", modelId);
			if ("simple".equals(editor)) {
				Model model = repositoryService.getModel(modelId);
				request.setAttribute("model", model);
			}
			return "/processManagement/moduler/" + editor + "-moduler-edit";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	@RequestMapping("/viewSimpleModuler.zf")
	public String viewSimpleModuler(HttpServletRequest request) {
		try {
			String modelId = request.getParameter("modelId");
			Model model = repositoryService.getModel(modelId);
			if("simple".equals(model.getCategory())){
				request.setAttribute("model", model);
			}else{
				return ERROR_VIEW;
			}
			return "/processManagement/moduler/simple-moduler-view";
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}
	
	@ResponseBody
	@RequestMapping("/delModuler.zf")
	public Object delModuler(HttpServletRequest request) {
		String modelIds = request.getParameter("modelIds");
		try {
			String[] modelIdList = modelIds.split(",");
			for (String modelId : modelIdList) {
				repositoryService.deleteModel(modelId);
			}
			return BPMMessageKey.PROCESS_MODEL_DEL_SUCCESS.getJson();
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.PROCESS_MODEL_DEL_FAIL.getJson();
		}
	}

	@ResponseBody
	@RequestMapping("/addModulerData.zf")
	public Object addModulerData(HttpServletRequest request) {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		//String editor = request.getParameter("editor");
		//业务组ID
		String biz = request.getParameter("biz");
		try {
			ObjectNode value = modelService.addModuler(name, description, MODEL_CATEGORY, biz);
			ObjectNode json = BPMMessageKey.PROCESS_MODEL_ADD_SUCCESS.getJson();
			json.putAll(value);
			return json;
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.PROCESS_MODEL_ADD_FAIL.getJson();
		}
	}

	@ResponseBody
	@RequestMapping("/listData.zf")
	public Object listModulerData(HttpServletRequest request, BPMQueryModel model) {
		try {
			ModelQuery createModelQuery = repositoryService.createModelQuery();
			if (BPMUtils.isNotBlank(request.getParameter("searchModelName"))) {
				createModelQuery.modelNameLike("%" + BPMUtils.trim(request.getParameter("searchModelName")) + "%");
			}
			
//			if(BPMUtils.isNotBlank(request.getParameter("searchEditorType"))){
//				createModelQuery.modelCategory(BPMUtils.trim(request.getParameter("searchEditorType")));
//			}
			
			if(BPMUtils.isNotBlank(request.getParameter("searchDeploymentState"))){
				if(BPMUtils.equals(request.getParameter("searchDeploymentState"), "0")){
					createModelQuery.notDeployed();
				}else{
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

	@ResponseBody
	@RequestMapping("/queryProcessDefinitionCategoryData")
	public Object queryProcessDefinitionCategoryData(HttpServletRequest request, BPMQueryModel model) {
		try {
			Set<String> processDefinitionCategories = new TreeSet<String>();
			List<ProcessDefinition> processDefinitionList = repositoryService.
					createProcessDefinitionQuery().latestVersion().
					orderByProcessDefinitionCategory().asc().list();
			for (ProcessDefinition processDefinition : processDefinitionList) {
				processDefinitionCategories.add(processDefinition.getCategory());
			}
			return processDefinitionCategories;
		} catch (Exception e) {
			logException(e);
			return BPMMessageKey.SYSTEM_ERROR.getJson();
		}
	}
}
