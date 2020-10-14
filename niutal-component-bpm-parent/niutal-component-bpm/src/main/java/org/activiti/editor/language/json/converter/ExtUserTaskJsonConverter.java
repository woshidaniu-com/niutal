/**
 * 
 */
package org.activiti.editor.language.json.converter;

import java.util.Map;

import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.DataAssociation;
import org.activiti.bpmn.model.DataStoreReference;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.Gateway;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Lane;
import org.activiti.bpmn.model.MessageFlow;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：扩展UserTask
 * <p>
 * 
 * @className:org.activiti.editor.language.json.converter.ExtUserTaskJsonConverter.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年4月14日下午2:33:34
 */
public class ExtUserTaskJsonConverter extends UserTaskJsonConverter {

	public static void fillTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap,
			Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {

		fillJsonTypes(convertersToBpmnMap);
		fillBpmnTypes(convertersToJsonMap);
	}

	public static void fillJsonTypes(Map<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap) {
		convertersToBpmnMap.put(STENCIL_TASK_USER, ExtUserTaskJsonConverter.class);
	}

	public static void fillBpmnTypes(
			Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
		convertersToJsonMap.put(UserTask.class, ExtUserTaskJsonConverter.class);
	}

	@Override
	public void convertToJson(BaseElement baseElement, ActivityProcessor processor, BpmnModel model,
			FlowElementsContainer container, ArrayNode shapesArrayNode, double subProcessX, double subProcessY) {

		this.model = model;
		this.processor = processor;
		this.subProcessX = subProcessX;
		this.subProcessY = subProcessY;
		this.shapesArrayNode = shapesArrayNode;
		GraphicInfo graphicInfo = model.getGraphicInfo(baseElement.getId());

		String stencilId = null;
		if (baseElement instanceof ServiceTask) {
			ServiceTask serviceTask = (ServiceTask) baseElement;
			if ("mail".equalsIgnoreCase(serviceTask.getType())) {
				stencilId = STENCIL_TASK_MAIL;
			} else if ("camel".equalsIgnoreCase(serviceTask.getType())) {
				stencilId = STENCIL_TASK_CAMEL;
			} else if ("mule".equalsIgnoreCase(serviceTask.getType())) {
				stencilId = STENCIL_TASK_MULE;
			} else {
				stencilId = getStencilId(baseElement);
			}
		} else {
			stencilId = getStencilId(baseElement);
		}

		flowElementNode = BpmnJsonConverterUtil.createChildShape(baseElement.getId(), stencilId,
				graphicInfo.getX() - subProcessX + graphicInfo.getWidth(),
				graphicInfo.getY() - subProcessY + graphicInfo.getHeight(), graphicInfo.getX() - subProcessX,
				graphicInfo.getY() - subProcessY);
		shapesArrayNode.add(flowElementNode);
		ObjectNode propertiesNode = objectMapper.createObjectNode();
		propertiesNode.put(PROPERTY_OVERRIDE_ID, baseElement.getId());

		if (baseElement instanceof FlowElement) {
			FlowElement flowElement = (FlowElement) baseElement;
			if (StringUtils.isNotEmpty(flowElement.getName())) {
				propertiesNode.put(PROPERTY_NAME, flowElement.getName());
			}

			if (StringUtils.isNotEmpty(flowElement.getDocumentation())) {
				propertiesNode.put(PROPERTY_DOCUMENTATION, flowElement.getDocumentation());
			}
		}

		convertElementToJson(propertiesNode, baseElement);

		flowElementNode.put(EDITOR_SHAPE_PROPERTIES, propertiesNode);
		ArrayNode outgoingArrayNode = objectMapper.createArrayNode();

		if (baseElement instanceof FlowNode) {
			FlowNode flowNode = (FlowNode) baseElement;
			for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
				outgoingArrayNode.add(BpmnJsonConverterUtil.createResourceNode(sequenceFlow.getId()));
			}

			for (MessageFlow messageFlow : model.getMessageFlows().values()) {
				if (messageFlow.getSourceRef().equals(flowNode.getId())) {
					outgoingArrayNode.add(BpmnJsonConverterUtil.createResourceNode(messageFlow.getId()));
				}
			}
		}

		if (baseElement instanceof Activity) {

			Activity activity = (Activity) baseElement;
			for (BoundaryEvent boundaryEvent : activity.getBoundaryEvents()) {
				outgoingArrayNode.add(BpmnJsonConverterUtil.createResourceNode(boundaryEvent.getId()));
			}

			propertiesNode.put(PROPERTY_ASYNCHRONOUS, activity.isAsynchronous());
			propertiesNode.put(PROPERTY_EXCLUSIVE, !activity.isNotExclusive());

			if (activity.getLoopCharacteristics() != null) {
				MultiInstanceLoopCharacteristics loopDef = activity.getLoopCharacteristics();
				if (StringUtils.isNotEmpty(loopDef.getLoopCardinality())
						|| StringUtils.isNotEmpty(loopDef.getInputDataItem())
						|| StringUtils.isNotEmpty(loopDef.getCompletionCondition())) {

					if (loopDef.isSequential() == false) {
						propertiesNode.put(PROPERTY_MULTIINSTANCE_TYPE, "Parallel");
					} else {
						propertiesNode.put(PROPERTY_MULTIINSTANCE_TYPE, "Sequential");
					}

					if (StringUtils.isNotEmpty(loopDef.getLoopCardinality())) {
						propertiesNode.put(PROPERTY_MULTIINSTANCE_CARDINALITY, loopDef.getLoopCardinality());
					}
					if (StringUtils.isNotEmpty(loopDef.getInputDataItem())) {
						propertiesNode.put(PROPERTY_MULTIINSTANCE_COLLECTION, loopDef.getInputDataItem());
					}
					if (StringUtils.isNotEmpty(loopDef.getElementVariable())) {
						propertiesNode.put(PROPERTY_MULTIINSTANCE_VARIABLE, loopDef.getElementVariable());
					}
					// if
					// (StringUtils.isNotEmpty(loopDef.getCompletionCondition()))
					// {
					// propertiesNode.put(PROPERTY_MULTIINSTANCE_CONDITION,
					// loopDef.getCompletionCondition());
					// }

					/**
					 * 这里重写了
					 */
					if (StringUtils.isNotEmpty(loopDef.getCompletionCondition())) {
						ObjectNode conditionExpressionNode = objectMapper.createObjectNode();
						conditionExpressionNode.put("evaluateExpression", loopDef.getCompletionCondition());
						conditionExpressionNode.put("type", "custom");
						propertiesNode.put(PROPERTY_MULTIINSTANCE_CONDITION, loopDef.getCompletionCondition());
					}

				}
			}

			if (activity instanceof UserTask) {
				BpmnJsonConverterUtil.convertListenersToJson(((UserTask) activity).getTaskListeners(), false,
						propertiesNode);
			}

			BpmnJsonConverterUtil.convertListenersToJson(activity.getExecutionListeners(), true, propertiesNode);

			if (CollectionUtils.isNotEmpty(activity.getDataInputAssociations())) {
				for (DataAssociation dataAssociation : activity.getDataInputAssociations()) {
					if (model.getFlowElement(dataAssociation.getSourceRef()) != null) {
						createDataAssociation(dataAssociation, true, activity);
					}
				}
			}

			if (CollectionUtils.isNotEmpty(activity.getDataOutputAssociations())) {
				for (DataAssociation dataAssociation : activity.getDataOutputAssociations()) {
					if (model.getFlowElement(dataAssociation.getTargetRef()) != null) {
						createDataAssociation(dataAssociation, false, activity);
						outgoingArrayNode.add(BpmnJsonConverterUtil.createResourceNode(dataAssociation.getId()));
					}
				}
			}
		}

		for (Artifact artifact : container.getArtifacts()) {
			if (artifact instanceof Association) {
				Association association = (Association) artifact;
				if (StringUtils.isNotEmpty(association.getSourceRef())
						&& association.getSourceRef().equals(baseElement.getId())) {
					outgoingArrayNode.add(BpmnJsonConverterUtil.createResourceNode(association.getId()));
				}
			}
		}

		if (baseElement instanceof DataStoreReference) {
			for (Process process : model.getProcesses()) {
				processDataStoreReferences(process, baseElement.getId(), outgoingArrayNode);
			}
		}

		flowElementNode.put("outgoing", outgoingArrayNode);
	}

	@Override
	public void convertToBpmnModel(JsonNode elementNode, JsonNode modelNode, ActivityProcessor processor,
			BaseElement parentElement, Map<String, JsonNode> shapeMap, BpmnModel bpmnModel) {

		this.processor = processor;
		this.model = bpmnModel;

		BaseElement baseElement = convertJsonToElement(elementNode, modelNode, shapeMap);
		baseElement.setId(BpmnJsonConverterUtil.getElementId(elementNode));

		if (baseElement instanceof FlowElement) {
			FlowElement flowElement = (FlowElement) baseElement;
			flowElement.setName(getPropertyValueAsString(PROPERTY_NAME, elementNode));
			flowElement.setDocumentation(getPropertyValueAsString(PROPERTY_DOCUMENTATION, elementNode));

			BpmnJsonConverterUtil.convertJsonToListeners(elementNode, flowElement);

			if (baseElement instanceof Activity) {
				Activity activity = (Activity) baseElement;
				activity.setAsynchronous(getPropertyValueAsBoolean(PROPERTY_ASYNCHRONOUS, elementNode));
				activity.setNotExclusive(!getPropertyValueAsBoolean(PROPERTY_EXCLUSIVE, elementNode));

				String multiInstanceType = getPropertyValueAsString(PROPERTY_MULTIINSTANCE_TYPE, elementNode);
				String multiInstanceCardinality = getPropertyValueAsString(PROPERTY_MULTIINSTANCE_CARDINALITY,
						elementNode);
				String multiInstanceCollection = getPropertyValueAsString(PROPERTY_MULTIINSTANCE_COLLECTION,
						elementNode);
				// String multiInstanceCondition =
				// getPropertyValueAsString(PROPERTY_MULTIINSTANCE_CONDITION,
				// elementNode);
				JsonNode multiInstanceCondition = getProperty(PROPERTY_MULTIINSTANCE_CONDITION, elementNode);

				if (StringUtils.isNotEmpty(multiInstanceType) && "none".equalsIgnoreCase(multiInstanceType) == false) {

					String multiInstanceVariable = getPropertyValueAsString(PROPERTY_MULTIINSTANCE_VARIABLE,
							elementNode);

					MultiInstanceLoopCharacteristics multiInstanceObject = new MultiInstanceLoopCharacteristics();
					if ("sequential".equalsIgnoreCase(multiInstanceType)) {
						multiInstanceObject.setSequential(true);
					} else {
						multiInstanceObject.setSequential(false);
					}
					multiInstanceObject.setLoopCardinality(multiInstanceCardinality);
					multiInstanceObject.setInputDataItem(multiInstanceCollection);
					multiInstanceObject.setElementVariable(multiInstanceVariable);

					/**
					 * 这里重写了
					 */
					if (multiInstanceCondition != null) {
						if (multiInstanceCondition.isTextual() && multiInstanceCondition.isNull() == false) {
							multiInstanceObject.setCompletionCondition(multiInstanceCondition.asText());
						} else if (multiInstanceCondition.get("evaluateExpression") != null) {
							JsonNode expressionNode = multiInstanceCondition.get("evaluateExpression");
							multiInstanceObject.setCompletionCondition(expressionNode.asText());
						}
					}

					// multiInstanceObject.setCompletionCondition(multiInstanceCondition);
					activity.setLoopCharacteristics(multiInstanceObject);
				}

			} else if (baseElement instanceof Gateway) {
				JsonNode flowOrderNode = getProperty(PROPERTY_SEQUENCEFLOW_ORDER, elementNode);
				if (flowOrderNode != null) {
					flowOrderNode = BpmnJsonConverterUtil.validateIfNodeIsTextual(flowOrderNode);
					JsonNode orderArray = flowOrderNode.get("sequenceFlowOrder");
					if (orderArray != null && orderArray.size() > 0) {
						for (JsonNode orderNode : orderArray) {
							ExtensionElement orderElement = new ExtensionElement();
							orderElement.setName("EDITOR_FLOW_ORDER");
							orderElement.setElementText(orderNode.asText());
							flowElement.addExtensionElement(orderElement);
						}
					}
				}
			}
		}

		if (baseElement instanceof FlowElement) {
			FlowElement flowElement = (FlowElement) baseElement;
			if (flowElement instanceof SequenceFlow) {
				ExtensionElement idExtensionElement = new ExtensionElement();
				idExtensionElement.setName("EDITOR_RESOURCEID");
				idExtensionElement.setElementText(elementNode.get(EDITOR_SHAPE_ID).asText());
				flowElement.addExtensionElement(idExtensionElement);
			}

			if (parentElement instanceof Process) {
				((Process) parentElement).addFlowElement(flowElement);

			} else if (parentElement instanceof SubProcess) {
				((SubProcess) parentElement).addFlowElement(flowElement);

			} else if (parentElement instanceof Lane) {
				Lane lane = (Lane) parentElement;
				lane.getFlowReferences().add(flowElement.getId());
				lane.getParentProcess().addFlowElement(flowElement);
			}

		} else if (baseElement instanceof Artifact) {
			Artifact artifact = (Artifact) baseElement;
			if (parentElement instanceof Process) {
				((Process) parentElement).addArtifact(artifact);

			} else if (parentElement instanceof SubProcess) {
				((SubProcess) parentElement).addArtifact(artifact);

			} else if (parentElement instanceof Lane) {
				Lane lane = (Lane) parentElement;
				lane.getFlowReferences().add(artifact.getId());
				lane.getParentProcess().addArtifact(artifact);
			}
		}
	}

}
